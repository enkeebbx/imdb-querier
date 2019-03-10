package models

case class TitleEpisode (
  id : Option[Long] = None,
  title : String,
  region : String,
  language : String
)

