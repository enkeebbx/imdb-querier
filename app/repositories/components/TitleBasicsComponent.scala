package repositories.components

import models.TitleBasics
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

trait TitleBasicsComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  class TitleBasicsTable(tag: Tag) extends Table[TitleBasics](tag, "title_basics") {

    def genres = column[String]("genres")
    def titleType = column[Option[String]]("titleType")
    def tSoundEx= column[Option[String]]("t_soundex")
    def originalTitle = column[String]("originalTitle")
    def startYear = column[Option[Int]]("startYear")
    def runtimeMinutes = column[Option[Int]]("runtimeMinutes")
    def primaryTitle = column[String]("primaryTitle")
    def isAdult = column[Option[Boolean]]("isAdult")
    def endYear = column[Option[Int]]("endYear")
    def tconst = column[Option[Int]]("tconst")

    def * : ProvenShape[TitleBasics] = (genres, titleType, tSoundEx, originalTitle, startYear, runtimeMinutes, primaryTitle, isAdult, endYear, tconst) <> ((TitleBasics.apply _).tupled, TitleBasics.unapply)
  }

  protected val titleBasics: Query[TitleBasicsTable, TitleBasics, Seq] = TableQuery[TitleBasicsTable]
}
