package models.exception

import play.api.http.Status._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.Result
import play.api.mvc.Results.Status

abstract class LunaException(errorMessage : String) extends RuntimeException(errorMessage) {
  def errorCode: String = toSnakeCase
  def statusCode : Int = INTERNAL_SERVER_ERROR
  def toSnakeCase: String = getClass.getSimpleName.replace("Exception", "").replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z\\d])([A-Z])", "$1_$2").toUpperCase()
  def toJson : JsValue = Json.obj("error_code" -> errorCode, "error_message" -> errorMessage)
  def toResult : Result = Status(statusCode)(toJson)
}

object LunaException {
  implicit def toResultConverter(e : LunaException) : Result = e.toResult
}

case class NameBasicsNotFoundException(errorMessage: String  = "Cannot find name basics") extends LunaException(errorMessage)

case class NoMatchingNconstWithNameException(errorMessage: String  = "Cannot find nconst associated with name") extends LunaException(errorMessage)

case class NoMatchingNconstsWithTconstException(errorMessage: String  = "Cannot find nconsts associated with tconst") extends LunaException(errorMessage)

case class NoMatchingTconstsWithNconstException(errorMessage: String  = "Cannot find tconsts associated with nconst") extends LunaException(errorMessage)

case class NoMatchingTitleBasicsWithTitleException(errorMessage: String  = "Cannot find title basics associated with title") extends LunaException(errorMessage)

case class NoMatchingTitleCrewWithTconstException(errorMessage: String  = "Cannot find title crew by tconst") extends LunaException(errorMessage)

case class KevinBaconNotFoundException(errorMessage: String  = "Cannot find Kevin Bacon") extends LunaException(errorMessage)

case class DegreeMoreThanSixException(errorMessage: String  = "Kevin Bacon, you are wrong!! You are not within my six degree radius!") extends LunaException(errorMessage)

case class TitleBasicsTconstNotFoundException(errorMessage: String  = "Cannot find tconst in title basics") extends LunaException(errorMessage)

case class GenreIdNotFoundInGenreException(errorMessage: String  = "Cannot find genre id in genre") extends LunaException(errorMessage)