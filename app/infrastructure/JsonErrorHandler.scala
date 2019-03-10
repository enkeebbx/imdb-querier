package infrastructure

import javax.inject._
import models.exceptions.LunaException
import org.apache.http.HttpStatus._
import play.api._
import play.api.http.ContentTypes._
import play.api.http.DefaultHttpErrorHandler
import play.api.libs.json.{Format, JsValue, Json}
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing.Router

import scala.concurrent._

@Singleton
class JsonErrorHandler @Inject() (env: Environment, config: Configuration, sourceMapper: OptionalSourceMapper, router: Provider[Router])
    extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  case class JsonError(errorCode : Int = 500, errorMessage : String = "서버 오류") {
    def toJson: JsValue = Json.toJson(this)
  }

  implicit val format : Format[JsonError] = Json.format[JsonError]

  val logger = Logger(this.getClass)

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    val json = JsonError(SC_NOT_FOUND, "page not found").toJson
    logger.info(s"[onClientError] $request $json")
    Future.successful(NotFound(json).as(withCharset(JSON)(Codec.utf_8)))
  }

  override protected def onBadRequest(request: RequestHeader, message: String): Future[Result] = {
    val json = Json.toJson(JsonError(SC_BAD_REQUEST, message))
    logger.info(s"[onBadRequest] $request $json")
    Future.successful(BadRequest(json).as(withCharset(JSON)(Codec.utf_8)))
  }

  def onError(request: RequestHeader, exception: UsefulException): Future[Result] = {
    exception.getCause match {
      case kakaoCouponException : LunaException =>
        logger.info(s"[ERROR-kakaoCouponException] $request ${kakaoCouponException.toJson}")
        Future.successful(kakaoCouponException.as(withCharset(JSON)(Codec.utf_8)))
      case _ =>
        val json = env.mode match {
          case Mode.Dev => JsonError(errorMessage = exception.getCause.getMessage).toJson
          case _ => JsonError().toJson
        }
        logger.info(s"[ERROR-Exception] $request $exception $json")
        Future.successful(InternalServerError(json).as(withCharset(JSON)(Codec.utf_8)))
    }
  }

  override protected def onDevServerError(request: RequestHeader, exception: UsefulException): Future[Result] = onError(request, exception)
  override def onProdServerError(request: RequestHeader, exception: UsefulException): Future[Result] = onError(request, exception)

  override def onForbidden(request: RequestHeader, message: String): Future[Result] = {
    val json = JsonError(SC_FORBIDDEN, message).toJson
    logger.info(s"[onForbidden] $request $json")
    Future.successful(Forbidden(json).as(withCharset(JSON)(Codec.utf_8)))
  }

}
