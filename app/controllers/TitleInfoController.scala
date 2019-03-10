package controllers

import akka.stream.Materializer
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc._
import services.TitleInfoFindService
import utils.JsonWrites._

import scala.concurrent.ExecutionContext

@Singleton
class TitleInfoController @Inject()(
  titleInfoService: TitleInfoFindService
)(implicit val ec: ExecutionContext, val mat: Materializer) extends Controller {

  def getTitleInfo(title: String): Action[AnyContent] = Action.async {
    titleInfoService.findTitleInfo(title).map {
      case Left(e) => throw e
      case Right(res) => Ok(Json.toJson(res))
    }
  }
}