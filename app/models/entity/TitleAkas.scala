package models.entity

case class TitleAkas (
  ordering : Option[Int] = None,
  isOriginalTitle : Option[Boolean] = None,
  titleId : Option[Int] = None,
  types : Option[String] = None,
  title : String,
  tSoundEx: Option[String] = None,
  attributes : Option[String] = None,
  region : Option[String] = None,
  language : Option[String] = None
)
