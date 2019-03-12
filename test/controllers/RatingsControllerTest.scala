package controllers

import helpers.LunaTestSpecification
import models.RatingResult
import org.mockito.Mockito.when
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.RatingsRankService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class RatingsControllerTest extends LunaTestSpecification {
  "RatingsControllerTest" should {
    "Valid genre success" in new WithMemDb {
      val ratingsQueryService = mock[RatingsRankService]
      val controller = new RatingsController(ratingsQueryService)

      val genre = "Comedy"
      val offset = 0
      val limit = 5
      val jsonBody = Json.parse(s"""{"genre" : "$genre"}""")
      val request = FakeRequest("POST", "/api/v1/search/ratings")
        .withBody(jsonBody)
        .withHeaders(CONTENT_TYPE -> JSON)

      when(ratingsQueryService.findTopRatedMoviesByGenre(genre, offset, limit)).thenReturn(Future.successful(Right(Seq(RatingResult("")))))

      val result = await(controller.getRatingsRank(offset, limit).apply(request))

      assert(result.header.status == OK)
    }

    "Invalid title fail" in new WithMemDb {
      val ratingsQueryService = mock[RatingsRankService]
      val controller = new RatingsController(ratingsQueryService)

      val genre = ""
      val offset = 0
      val limit = 5
      val jsonBody = Json.parse(s"""{"genre" : "$genre"}""")
      val request = FakeRequest("POST", "/api/v1/search/ratings")
        .withBody(jsonBody)
        .withHeaders(CONTENT_TYPE -> JSON)

      val result = await(controller.getRatingsRank(offset, limit).apply(request))

      assert(result.header.status == BAD_REQUEST)
    }
  }
}
