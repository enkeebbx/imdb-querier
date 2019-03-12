package models

import models.entity.NameBasics

case class Cast (
  name : String,
  primaryProfession : String,
  birthYear : Option[Int],
  deathYear : Option[Int]
) {
  def fromNameBasics(nameBasics: NameBasics): Cast = {
    Cast(
      nameBasics.primaryName,
      nameBasics.primaryProfession,
      nameBasics.birthYear,
      nameBasics.deathYear
    )
  }
}

