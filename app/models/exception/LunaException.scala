package models.exception

import play.api.http.Status._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.Result
import play.api.mvc.Results.Status

abstract class LunaException(statusCode: Int, errorMessage : String) extends RuntimeException(errorMessage) {
  def errorCode: String = toSnakeCase
  def getErrorMessage = errorMessage
  def toSnakeCase: String = getClass.getSimpleName.replace("Exception", "").replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z\\d])([A-Z])", "$1_$2").toUpperCase()
  def toJson : JsValue = Json.obj("error_code" -> errorCode, "error_message" -> errorMessage)
  def toResult : Result = Status(statusCode)(toJson)
}

object LunaException {
  implicit def toResultConverter(e : LunaException) : Result = e.toResult
}

case class InvalidParameterException(
  statusCode: Int = BAD_REQUEST,
  errorMessage: String  = "Invalid Parameter"
) extends LunaException(statusCode, errorMessage)

case class NameBasicsNotFoundException(
  statusCode: Int = INTERNAL_SERVER_ERROR,
  errorMessage: String  = "Cannot find name basics"
) extends LunaException(statusCode, errorMessage)

case class NoMatchingNconstWithNameException(
  statusCode: Int = INTERNAL_SERVER_ERROR,
  errorMessage: String  = "Cannot find nconst associated with name"
) extends LunaException(statusCode, errorMessage)

case class NoMatchingNconstsWithTconstException(
  statusCode: Int = INTERNAL_SERVER_ERROR,
  errorMessage: String  = "Cannot find nconsts associated with tconst"
) extends LunaException(statusCode, errorMessage)

case class NoMatchingTconstsWithNconstException(
  statusCode: Int = INTERNAL_SERVER_ERROR,
  errorMessage: String  = "Cannot find tconsts associated with nconst"
) extends LunaException(statusCode, errorMessage)

case class NoMatchingTitleBasicsWithTitleException(
  statusCode: Int = INTERNAL_SERVER_ERROR,
  errorMessage: String  = "Cannot find title basics associated with title"
) extends LunaException(statusCode, errorMessage)

case class NoMatchingTitleCrewWithTconstException(
  statusCode: Int = INTERNAL_SERVER_ERROR,
  errorMessage: String  = "Cannot find title crew by tconst"
) extends LunaException(statusCode, errorMessage)

case class KevinBaconNotFoundException(
  statusCode: Int = INTERNAL_SERVER_ERROR,
  errorMessage: String  = "Cannot find Kevin Bacon"
) extends LunaException(statusCode, errorMessage)

case class TitleBasicsTconstNotFoundException(
  statusCode: Int = INTERNAL_SERVER_ERROR,
  errorMessage: String  = "Cannot find tconst in title basics"
) extends LunaException(statusCode, errorMessage)

case class GenreIdNotFoundInGenreException(
  statusCode: Int = INTERNAL_SERVER_ERROR,
  errorMessage: String  = "Cannot find genre id in genre"
) extends LunaException(statusCode, errorMessage)