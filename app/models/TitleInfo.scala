package models

case class TitleInfo (
  title : String,
  genres : String,
  startYear : Option[Int],
  runtimeMinutes : Option[Int],
  isAdult : Option[Boolean],
  endYear : Option[Int],
  tconst : Int,
  directors : Seq[Crew],
  writers : Seq[Crew],
  actorNames : Seq[String]
)

case class Crew (
  name : String
)