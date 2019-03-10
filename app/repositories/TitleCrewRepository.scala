package repositories

import javax.inject.{Inject, Singleton}
import models.TitleCrew
import models.exceptions.NoMatchingTitleCrewWithTconstException
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.components.TitleCrewComponent
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

@Singleton
class TitleCrewRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with TitleCrewComponent {

  import profile.api._

  def insert(titleCrew: Seq[TitleCrew]): Future[Int] = {
    val action = TitleCrewTable ++= titleCrew
    db.run(action).map { _.get }
  }

  def findTitleCrewByTconst(tconst : Int): Future[Either[NoMatchingTitleCrewWithTconstException, TitleCrew]] = {
    val action = TitleCrewTable.filter(_.tconst === tconst).result.headOption

    db.run(action.asTry) map {
      case Success(result) if result.isDefined => Right(result.get)
      case _ => Left(NoMatchingTitleCrewWithTconstException())
    }
  }
}
