package models.entity

import models.{Cast, Crew}

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
) {
  def toCrew(): Crew = {
    Crew(
      primaryName,
      primaryProfession,
      birthYear,
      deathYear
    )
  }

  def toCast(): Cast = {
    Cast(
      primaryName,
      primaryProfession,
      birthYear,
      deathYear
    )
  }
}
