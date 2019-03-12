package utils

import scala.annotation.tailrec

object NconstUtil {
  def padNconst(nconst: String): String = {
    @tailrec
    def loop(padded: String): String = {
      val padLength = 7 - padded.length
      if (padLength == 0) "nm" + padded
      else loop("0" + padded)

    }
    loop(nconst)
  }

  def unpadNconst(nconst: String) : String = {
    nconst.replace("nm", "").toInt.toString
  }
}
