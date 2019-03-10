package utils

import models.{Crew, RatingResultResponse, TitleInfo}
import play.api.libs.json._

object JsonWrites extends DefaultWrites {
  implicit val ratingResultResponseWrites : Writes[RatingResultResponse] = new Writes[RatingResultResponse] {
    def writes(response: RatingResultResponse): JsValue = {
      Json.obj(
        "title" -> response.title,
        "start_year" -> response.startYear,
        "end_year" -> response.endYear,
        "runtime" -> response.runtime,
        "is_adult" -> response.isAdult,
        "average_rating" -> response.averageRating,
        "num_votes" -> response.numVotes
      )
    }
  }

  implicit val crewWrites : Writes[Crew] = new Writes[Crew] {
    def writes(response: Crew): JsValue = {
      Json.obj(
        "name" -> response.name
      )
    }
  }

  implicit val titleInfoWrites : Writes[TitleInfo] = new Writes[TitleInfo] {
    def writes(response: TitleInfo): JsValue = {
      Json.obj(
        "title" -> response.title
      )
    }
  }
}
