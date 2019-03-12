package repositories

import com.google.inject.{Inject, Singleton}
import models.entity.{GenreTitles, TitleBasics, TitleRatings}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.components.{GenreTitlesComponent, TitleBasicsComponent, TitleRatingsComponent}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

@Singleton
class TitlesRatingsRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with TitleRatingsComponent with GenreTitlesComponent with TitleBasicsComponent {

  import profile.api._

  def insert(titleRatingsList : Seq[TitleRatings]): Future[Int] = {
    val action = titleRatings ++= titleRatingsList
    db.run(action).map { _.get }
  }

  def findAllGenreAndTitleByGenreIdSortByRatings(genreId: Int, offset: Int, limit: Int): Future[Seq[((GenreTitles, TitleRatings), TitleBasics)]] = {
    val action = genreTitlesTable
      .join(titleRatings)
      .on((g, t) => g.tconst === t.tconst && g.genreId === genreId)
      .join(titleBasics)
      .on(_._1.tconst === _.tconst)
      .sortBy(_._1._2.averageRatings.desc)
      .drop(offset)
      .take(limit)
      .result

    db.run(action.asTry) map {
      case Success(result) => result
      case _               => Seq()
    }
  }
}
