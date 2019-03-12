package models.entity

case class TitleEpisode (
  id : Option[Long] = None,
  title : String,
  region : String,
  language : String
)
