package repositories

import javax.inject.{Inject, Singleton}
import models.TitleBasics
import models.exceptions.NoMatchingTitleBasicsWithTitleException
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.components.TitleBasicsComponent
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

@Singleton
class TitleBasicsRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with TitleBasicsComponent {

  import profile.api._

  def insert(titleBasicsList : Seq[TitleBasics]) = {
    val action = titleBasics ++= titleBasicsList
    db.run(action).map { _.get }
  }

  def findTitleBasicsByPrimaryOrOriginalTitle(title: String): Future[Either[NoMatchingTitleBasicsWithTitleException, TitleBasics]] = {
    val action = titleBasics.filter(titleBasic => titleBasic.originalTitle === title || titleBasic.primaryTitle === title).result.headOption

    db.run(action.asTry) map {
      case Success(result) if result.isDefined => Right(result.get)
      case _ => Left(NoMatchingTitleBasicsWithTitleException())
    }
  }
}
