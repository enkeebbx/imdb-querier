package controllers

import akka.stream.Materializer
import javax.inject.{Inject, Singleton}
import models.response.DegreeCalculateResponse
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.DegreeCalculateService
import utils.JsonReads
import utils.JsonValidate._
import utils.JsonWrites._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DegreeCalculateController @Inject()(degreeCalculateService: DegreeCalculateService)(implicit val ec: ExecutionContext, val mat: Materializer) extends Controller {
  def getDegreeWithKevinBacon(): Action[JsValue] = Action.async(parse.json) { request =>
    val body: JsValue = request.body
    withJsonValidation(body)(JsonReads.degreeCalculateRequestReads) match {
      case Right(degreeCalculateRequest) =>
        degreeCalculateService.getDegreeWithKevinBacon(degreeCalculateRequest.actor).map {
          case Right(result) =>
            val response = DegreeCalculateResponse(result)
            Ok(Json.toJson(response))
          case Left(e) =>
            throw e
        }
      case Left(e) =>
        Future.successful(BadRequest(e.getErrorMessage))
    }
  }
}