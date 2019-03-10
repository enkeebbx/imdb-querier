package repositories

import com.google.inject.{Inject, Singleton}
import models.TitleActors
import models.exceptions.{LunaException, NoMatchingNconstsWithTconstException}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.components.TitleActorsComponent
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

@Singleton
class TitleActorsRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with TitleActorsComponent {
  import profile.api._

  def insert(titleActors: Seq[TitleActors]): Future[Int] = {
    val action = TitleActorsTable ++= titleActors
    db.run(action).map { _.get }
  }

  def findNconstsByTconst(tconst: Int): Future[Either[LunaException, Seq[Int]]] = {
    val action = TitleActorsTable.filter(_.tconst === tconst).map(_.nconst).result

    db.run(action.asTry) map {
      case Success(result) if result.nonEmpty => Right(result.map(_.toInt))
      case _               => Left(NoMatchingNconstsWithTconstException())
    }
  }
}
