package models.entity

case class TitleBasics (
  genres: String,
  titleType : Option[String],
  tSoundEx : Option[String],
  originalTitle : String,
  startYear : Option[Int],
  runtimeMinutes : Option[Int],
  primaryTitle : String,
  isAdult : Option[Boolean],
  endYear : Option[Int],
  tconst : Option[Int]
)
