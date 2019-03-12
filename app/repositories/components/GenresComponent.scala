package repositories.components

import models.entity.Genres
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

trait GenresComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._

  class GenresTable(tag: Tag) extends Table[Genres](tag, "genres") {
    def id = column[Int]("id")
    def genre = column[String]("genre")

    def * : ProvenShape[Genres] = (id, genre) <> ((Genres.apply _).tupled, Genres.unapply)
  }

  protected val genres: Query[GenresTable, Genres, Seq] = TableQuery[GenresTable]
}
