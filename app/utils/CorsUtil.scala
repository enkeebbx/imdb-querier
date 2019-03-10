package utils

import play.api.mvc.Result

object CorsUtil {

  implicit class withCors(result : Result) {
    def enableCors: Result =
      result.withHeaders(
        "Access-Control-Allow-Origin"      -> "*",
        "Access-Control-Allow-Methods"     -> "GET, POST, PUT, DELETE, OPTIONS",
        "Access-Control-Allow-Headers"     -> "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, talk_user",
        "Access-Control-Allow-Credentials" -> "true",
        "Access-Control-Max-Age"           -> (60 * 60 * 24).toString
      )
  }
}
