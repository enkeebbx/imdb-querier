package repositories.components

import models.NameBasics
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

trait NameBasicsComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  class NameBasicsTable(tag: Tag) extends Table[NameBasics](tag, "name_basics") {

    def primaryProfession= column[String]("primaryProfession")
    def deathYear= column[Option[Int]]("deathYear")
    def knownForTitles= column[String]("knownForTitles")
    def nsSoundEx= column[Option[String]]("ns_soundex")
    def nconst= column[Option[Int]]("nconst")
    def snSoundEx= column[Option[String]]("sn_soundex")
    def primaryName= column[String]("primaryName")
    def birthYear= column[Option[Int]]("birthYear")
    def sSoundEx= column[Option[String]]("s_soundex")

    def * : ProvenShape[NameBasics] = (primaryProfession, deathYear, knownForTitles, nsSoundEx, nconst, snSoundEx, primaryName, birthYear, sSoundEx) <> ((NameBasics.apply _).tupled, NameBasics.unapply)
  }

  protected val NameBasicsTable: Query[NameBasicsTable, NameBasics, Seq] = TableQuery[NameBasicsTable]
}
