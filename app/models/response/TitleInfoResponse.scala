package models.response

import models.{Cast, Crew}

case class TitleInfoResponse (
  title : String,
  genres : String,
  startYear : Option[Int],
  runtimeMinutes : Option[Int],
  isAdult : Option[Boolean],
  endYear : Option[Int],
  tconst : Int,
  directors : Seq[Crew],
  writers : Seq[Crew],
  actorNames : Seq[Cast]
)
