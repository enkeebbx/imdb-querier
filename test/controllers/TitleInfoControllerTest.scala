package controllers

import helpers.LunaTestSpecification
import models.TitleInfo
import org.mockito.Mockito.when
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.{DegreeCalculateService, TitleInfoFindService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TitleInfoControllerTest extends LunaTestSpecification {
  "TitleInfoControllerTest" should {
    "Valid title success" in new WithMemDb {
      val titleInfoFindService = mock[TitleInfoFindService]
      val controller = new TitleInfoController(titleInfoFindService)

      val title = "Title"
      val jsonBody = Json.parse(s"""{"title" : "$title"}""")
      val request = FakeRequest("POST", "/api/v1/search/title-info")
        .withBody(jsonBody)
        .withHeaders(CONTENT_TYPE -> JSON)

      when(titleInfoFindService.findTitleInfo(title)).thenReturn(Future.successful(Right(generateTitleInfo(1, title, 1).head)))

      val result = await(controller.getTitleInfo().apply(request))

      assert(result.header.status == OK)
    }

    "Invalid title fail" in new WithMemDb {
      val titleInfoFindService = mock[TitleInfoFindService]
      val controller = new TitleInfoController(titleInfoFindService)

      val title = ""
      val jsonBody = Json.parse(s"""{"title" : "$title"}""")
      val request = FakeRequest("POST", "/api/v1/search/title-info")
        .withBody(jsonBody)
        .withHeaders(CONTENT_TYPE -> JSON)

      val result = await(controller.getTitleInfo().apply(request))

      assert(result.header.status == BAD_REQUEST)
    }
  }
}
