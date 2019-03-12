package repositories.components

import models.entity.TitleAkas
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

trait TitleAkasComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._

  class TitleAkasTable(tag: Tag) extends Table[TitleAkas](tag, "title_akas") {
    def ordering = column[Option[Int]]("ordering")
    def isOriginalTitle = column[Option[Boolean]]("isOriginalTitle")
    def titleId = column[Option[Int]]("titleId")
    def types = column[Option[String]]("types")
    def title = column[String]("title")
    def tSoundEx= column[Option[String]]("t_soundex")
    def attributes = column[Option[String]]("attributes")
    def region = column[Option[String]]("region")
    def language = column[Option[String]]("language")

    def * : ProvenShape[TitleAkas] = (ordering, isOriginalTitle, titleId, types, title, tSoundEx, attributes, region, language) <> ((TitleAkas.apply _).tupled, TitleAkas.unapply)
  }

  protected val titleAkas: Query[TitleAkasTable, TitleAkas, Seq] = TableQuery[TitleAkasTable]
}
