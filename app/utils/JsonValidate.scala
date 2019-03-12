package utils

import models.exception.{InvalidParameterException, LunaException}
import play.api.data.validation.ValidationError
import play.api.libs.json._
import play.mvc.Http.Status

import scala.util.control.NonFatal

object JsonValidate {
  private[utils] def logValidationError(errors: Seq[(JsPath, Seq[ValidationError])]): String = {
    errors
      .map { error =>
        // 밸리데이션 에러가 난 제이슨 필드
        val path: JsPath = error._1

        // 에러에 대한 디테일
        val causes: Seq[ValidationError] = error._2

        // 에러가 난 항목과 디테일을 읽기 쉬운 스트링으로 변환
        path.toString().tail
      }
      .mkString(", ")
  }

  // Reads[T] 를 활용하여 읽은 JSON 결과(JsResult)를 기반으로 밸리데이션한다.
  // 밸리데이션 성공 : T 리턴
  // 밸리데이션 실패 : throw InvalidRequest
  def withJsonValidation[T](json: JsValue)(implicit reads: Reads[T]): Either[LunaException, T] = {
    try {
      val requestResult: JsResult[T] = json.validate[T]
      requestResult match {
        case _ : JsSuccess[T] =>
          Right(requestResult.get)
        case e : JsError =>
          val errors: Seq[(JsPath, Seq[ValidationError])]  = e.errors
          Left(InvalidParameterException(Status.BAD_REQUEST, logValidationError(errors)))
      }
    } catch {
      case NonFatal(e) =>
        Left(InvalidParameterException(Status.BAD_REQUEST, e.getMessage))
    }
  }
}