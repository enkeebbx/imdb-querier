package controllers

import akka.stream.Materializer
import javax.inject.{Inject, Singleton}
import models.response.RatingResultResponse
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.RatingsRankService
import utils.JsonWrites._
import utils.{JsonReads, JsonValidate}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class RatingsController @Inject()(ratingsQueryService: RatingsRankService)(implicit val ec: ExecutionContext, val mat: Materializer) extends Controller {
  /**
    * Requirement #2 : Get ratings ranking of a genre
    *
    * Returns :
    * 1) title
    * 2) start year
    * 3) runtime
    * 4) is adult
    * 5) end year
    * 6) genre
    * 7) average rating
    * 8) num votes
    * @param genre
    * @return
    */
  def getRatingsRank(offset: Int, limit: Int): Action[JsValue] = Action.async(parse.json) { request =>
    val body = request.body
    JsonValidate.withJsonValidation(body)(JsonReads.ratingsRequestReads) match {
      case Right(ratingsRequest) =>
        ratingsQueryService.findTopRatedMoviesByGenre(ratingsRequest.genre, offset, limit).map {
          case Right(result) =>
            val response = result.map { elem =>
              RatingResultResponse (
                elem.title,
                elem.startYear,
                elem.endYear,
                elem.runtime,
                elem.isAdult,
                elem.averageRating,
                elem.numVotes
              )
            }
            Ok(Json.toJson(response))
          case Left(e) =>
            throw e
        }
      case Left(e) =>
        Future.successful(BadRequest(e.getErrorMessage))

    }
  }
}