package repositories.components

import models.entity.TitleRatings
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

trait TitleRatingsComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._

  class TitleRatingsTable(tag: Tag) extends Table[TitleRatings](tag, "title_ratings") {
    def numVotes= column[Option[Int]]("numVotes")
    def tconst= column[Option[Int]]("tconst")
    def averageRatings = column[Option[Double]]("averageRating")

    def * : ProvenShape[TitleRatings] = (numVotes, tconst, averageRatings) <> ((TitleRatings.apply _).tupled, TitleRatings.unapply)
  }

  protected val titleRatings: Query[TitleRatingsTable, TitleRatings, Seq] = TableQuery[TitleRatingsTable]
}
