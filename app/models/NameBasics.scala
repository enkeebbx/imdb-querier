package models

case class NameBasics (
  primaryProfession: String,
  deathYear: Option[Int],
  knownForTitles : String,
  nsSoundEx : Option[String],
  nconst : Option[Int],
  snSoundEx : Option[String],
  primaryName : String,
  birthYear : Option[Int],
  sSoundEx : Option[String]
)

