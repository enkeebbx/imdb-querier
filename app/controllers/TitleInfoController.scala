package controllers

import akka.stream.Materializer
import javax.inject.{Inject, Singleton}
import models.response.TitleInfoResponse
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.TitleInfoFindService
import utils.JsonReads
import utils.JsonValidate._
import utils.JsonWrites._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TitleInfoController @Inject()(titleInfoService: TitleInfoFindService)(implicit val ec: ExecutionContext, val mat: Materializer) extends Controller {

  /**
    * Requirement 1
    * @return
    */
  def getTitleInfo(): Action[JsValue] = Action.async(parse.json) { request =>
    val json : JsValue = request.body
    withJsonValidation(json)(JsonReads.titleInfoRequestReads) match {
      case Right(titleInfoRequest) =>
        titleInfoService.findTitleInfo(titleInfoRequest.title).map {
          case Left(e) =>
            throw e
          case Right(res) =>
            val response = TitleInfoResponse (
              res.title,
              res.genres,
              res.startYear,
              res.runtimeMinutes,
              res.isAdult,
              res.endYear,
              res.tconst,
              res.directors,
              res.writers,
              res.actorNames
            )
            Ok(Json.toJson(response))
        }
      case Left(e) =>
        Future.successful(BadRequest(e.getErrorMessage))
    }
  }
}