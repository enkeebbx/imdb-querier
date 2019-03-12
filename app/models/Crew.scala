package models

import models.entity.NameBasics

case class Crew (
  name : String,
  primaryProfession : String,
  birthYear : Option[Int],
  deathYear : Option[Int]
) {
  def fromNameBasics(nameBasics: NameBasics): Crew = {
    Crew(
      nameBasics.primaryName,
      nameBasics.primaryProfession,
      nameBasics.birthYear,
      nameBasics.deathYear
    )
  }
}

