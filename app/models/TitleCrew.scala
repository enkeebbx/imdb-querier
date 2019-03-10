package models

case class TitleCrew (
  directors : String,
  writers : String,
  tconst : Option[Int] = None
)

