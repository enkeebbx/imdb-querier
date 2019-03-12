package controllers

import akka.stream.Materializer
import javax.inject.{Inject, Singleton}
import models.request.DegreeCalculateRequest
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.DegreeCalculateService
import utils.JsonReads
import utils.JsonWrites._
import utils.JsonValidate._

import scala.concurrent.ExecutionContext

@Singleton
class DegreeCalculateController @Inject()(degreeCalculateService: DegreeCalculateService)(implicit val ec: ExecutionContext, val mat: Materializer) extends Controller {
  def getDegreeWithKevinBacon(): Action[JsValue] = Action.async(parse.json) { request =>
    val body: JsValue = request.body
    val degreeCalculateRequest: DegreeCalculateRequest = withJsonValidation(body)(JsonReads.degreeCalculateRequestReads)
    degreeCalculateService.getDegreeWithKevinBacon(degreeCalculateRequest.actor).map {
      case Right(result) => Ok(Json.toJson(result))
      case Left(e) => throw e
    }
  }
}