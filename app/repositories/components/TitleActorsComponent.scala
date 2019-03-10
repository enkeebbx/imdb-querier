package repositories.components

import models.TitleActors
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

trait TitleActorsComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._

  class TitleActorsTable(tag: Tag) extends Table[TitleActors](tag, "title_actors") {
    def id = column[Int]("id")
    def tconst = column[Int]("tconst")
    def nconst = column[String]("nconst")

    def * : ProvenShape[TitleActors] = (id, tconst, nconst) <> ((TitleActors.apply _).tupled, TitleActors.unapply)
  }

  protected val TitleActorsTable: Query[TitleActorsTable, TitleActors, Seq] = TableQuery[TitleActorsTable]
}
