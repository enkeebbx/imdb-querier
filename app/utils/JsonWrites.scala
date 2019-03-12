package utils

import models.response.{DegreeCalculateResponse, RatingResultResponse, TitleInfoResponse}
import models.{Cast, Crew}
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
        "name" -> response.name,
        "profession" -> response.primaryProfession,
        "birth_year" -> response.birthYear,
        "death_year" -> response.deathYear
      )
    }
  }

  implicit val castWrites : Writes[Cast] = new Writes[Cast] {
    def writes(response: Cast): JsValue = {
      Json.obj(
        "name" -> response.name,
        "profession" -> response.primaryProfession,
        "birth_year" -> response.birthYear,
        "death_year" -> response.deathYear
      )
    }
  }

  implicit val degreeCalculateResponseWrites : Writes[DegreeCalculateResponse] = new Writes[DegreeCalculateResponse] {
    def writes(response: DegreeCalculateResponse): JsValue = {
      Json.obj(
        "degree" -> response.degree
      )
    }
  }

  implicit val titleInfoWrites : Writes[TitleInfoResponse] = new Writes[TitleInfoResponse] {
    def writes(response: TitleInfoResponse): JsValue = {
      Json.obj(
        "title" -> response.title,
        "genres" -> response.genres,
        "start_year" -> response.startYear,
        "runtime_minutes" -> response.runtimeMinutes,
        "is_adult" -> response.isAdult,
        "end_year" -> response.endYear,
        "tconst" -> response.tconst,
        "directors" -> response.directors,
        "writers" -> response.writers,
        "actor_names" -> response.actorNames
      )
    }
  }
}
