package controllers

import akka.stream.Materializer
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc._
import services.DegreeCalculateService
import utils.JsonWrites._

import scala.concurrent.ExecutionContext

@Singleton
class DegreeCalculateController @Inject()(degreeCalculateService: DegreeCalculateService)(implicit val ec: ExecutionContext, val mat: Materializer) extends Controller {
  def getDegreeWithKevinBacon(actor: String): Action[AnyContent] = Action.async {
    degreeCalculateService.getDegreeWithKevinBacon(actor).map {
      case Right(result) => Ok(Json.toJson(result))
      case Left(e) => throw e
    }
  }
}