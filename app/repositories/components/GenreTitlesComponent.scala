package repositories.components

import models.entity.GenreTitles
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

trait GenreTitlesComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._

  class GenreTitlesTable(tag: Tag) extends Table[GenreTitles](tag, "genre_titles") {
    def id = column[Int]("id")
    def genreId = column[Int]("genre_id")
    def tconst = column[Int]("tconst")

    def * : ProvenShape[GenreTitles] = (id, genreId, tconst) <> ((GenreTitles.apply _).tupled, GenreTitles.unapply)
  }

  protected val genreTitlesTable: Query[GenreTitlesTable, GenreTitles, Seq] = TableQuery[GenreTitlesTable]
}
