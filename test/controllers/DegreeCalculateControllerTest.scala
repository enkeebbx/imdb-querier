package controllers

import helpers.LunaTestSpecification
import org.mockito.Mockito.when
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.DegreeCalculateService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DegreeCalculateControllerTest extends LunaTestSpecification {
  "DegreeCalculateControllerTest" should {
    "Valid actor name success" in new WithMemDb {
      val degreeCalculateService = mock[DegreeCalculateService]

      val controller = new DegreeCalculateController(degreeCalculateService)

      val actor = "Channing Tatum"
      val jsonBody = Json.parse(s"""{"actor" : "$actor"}""")
      val request = FakeRequest("POST", "/api/v1/search/degree")
        .withBody(jsonBody)
        .withHeaders(CONTENT_TYPE -> JSON)

      when(degreeCalculateService.getDegreeWithKevinBacon(actor)).thenReturn(Future.successful(Right(1)))

      val result = await(controller.getDegreeWithKevinBacon().apply(request))

      assert(result.header.status == OK)
    }

    "Invalid actor name fail" in new WithMemDb {
      val degreeCalculateService = mock[DegreeCalculateService]

      val controller = new DegreeCalculateController(degreeCalculateService)

      val actor = ""
      val jsonBody = Json.parse(s"""{"actor" : "$actor"}""")
      val request = FakeRequest("POST", "/api/v1/search/degree")
        .withBody(jsonBody)
        .withHeaders(CONTENT_TYPE -> JSON)

      val result = await(controller.getDegreeWithKevinBacon().apply(request))

      assert(result.header.status == BAD_REQUEST)
    }
  }
}
