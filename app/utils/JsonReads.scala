package utils

import models.request.{DegreeCalculateRequest, RatingsRequest, TitleInfoRequest}
import play.api.libs.json.{JsPath, Reads}

object JsonReads {
  implicit val titleInfoRequestReads: Reads[TitleInfoRequest] = (JsPath \ "title").read[String].map(value => TitleInfoRequest(value))

  implicit val degreeCalculateRequestReads: Reads[DegreeCalculateRequest] = (JsPath \ "actor").read[String].map(value => DegreeCalculateRequest(value))

  implicit val ratingsRequestReads: Reads[RatingsRequest] = (JsPath \ "genre").read[String].map(value => RatingsRequest(value))
}
