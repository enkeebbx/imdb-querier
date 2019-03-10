package repositories.components

import models.TitlePrincipals
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

trait TitlePrincipalsComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._

  class TitlePrincipalsTable(tag: Tag) extends Table[TitlePrincipals](tag, "title_principals") {
    def category= column[String]("category")
    def principalCast= column[String]("principalCast")
    def ordering= column[String]("ordering")
    def tconst= column[Option[Int]]("tconst")
    def characters= column[String]("characters")
    def job= column[String]("job")
    def nconst= column[String]("nconst")

    def * : ProvenShape[TitlePrincipals] = (category, principalCast, ordering, tconst, characters, job, nconst) <> ((TitlePrincipals.apply _).tupled, TitlePrincipals.unapply)
  }

  protected val TitlePrincipalsTable: Query[TitlePrincipalsTable, TitlePrincipals, Seq] = TableQuery[TitlePrincipalsTable]
}
