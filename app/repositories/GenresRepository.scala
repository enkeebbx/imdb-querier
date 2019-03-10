package repositories

import com.google.inject.{Inject, Singleton}
import models.Genres
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.components.GenresComponent
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

@Singleton
class GenresRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with GenresComponent {
  import profile.api._

  def insert(genresList : Seq[Genres]) = {
    val action = genres ++= genresList
    db.run(action).map { _.get }
  }

  def findGenresById(id : Int): Future[Option[Genres]] = {
    val action = genres.filter(_.id === id).result.headOption

    db.run(action.asTry) map {
      case Success(result) =>
        println(result)
        result
      case _               => None
    }
  }

  def findGenreIdByGenre(genre: String): Future[Option[Int]] = {
    val action = genres.filter(_.genre === genre).map(_.id).result.headOption

    db.run(action.asTry) map {
      case Success(result) => result
      case _               => None
    }
  }
}
