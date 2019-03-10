package repositories

import com.google.inject.{Inject, Singleton}
import models.GenreTitles
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.components.GenreTitlesComponent
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

@Singleton
class GenreTitlesRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with GenreTitlesComponent {
  import profile.api._

  def insert(genreTitleList : Seq[GenreTitles]): Future[Int] = {
    val action = genreTitlesTable ++= genreTitleList
    db.run(action).map { _.get }
  }

  def findGenreTitlesByTconst(tconst: Int): Future[Seq[GenreTitles]] = {
    val action = genreTitlesTable.filter(_.tconst === tconst).result

    db.run(action.asTry) map {
      case Success(result) => result
      case _               => Seq.empty[GenreTitles]
    }
  }
}
