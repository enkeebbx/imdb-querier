package repositories.components

import models.TitleCrew
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

trait TitleCrewComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  class TitleCrewTable(tag: Tag) extends Table[TitleCrew](tag, "title_crew") {

    def directors= column[String]("directors")
    def writers= column[String]("writers")
    def tconst = column[Option[Int]]("tconst")

    def * : ProvenShape[TitleCrew] = (directors, writers, tconst) <> ((TitleCrew.apply _).tupled, TitleCrew.unapply)
  }

  protected val TitleCrewTable: Query[TitleCrewTable, TitleCrew, Seq] = TableQuery[TitleCrewTable]
}
