package repositories

import com.google.inject.{Inject, Singleton}
import models.entity.TitlePrincipals
import models.exception.{LunaException, NoMatchingNconstsWithTconstException, NoMatchingTconstsWithNconstException}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.components.TitlePrincipalsComponent
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

@Singleton
class TitlePrincipalsRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with TitlePrincipalsComponent {
  import profile.api._

  def insert(titlePrincipals : Seq[TitlePrincipals]) = {
    val action = TitlePrincipalsTable ++= titlePrincipals
    db.run(action).map { _.get }
  }

  def findTconstsByNconst(nconst: String): Future[Either[LunaException, Seq[Int]]] = {
    val action = TitlePrincipalsTable.filter(_.nconst === nconst).map(_.tconst).result

    db.run(action.asTry) map {
      case Success(result) => Right(result.foldLeft(Seq.empty[Int])((acc, i) => if(i.isDefined) acc ++ Seq(i.get) else acc))
      case _ => Left(NoMatchingTconstsWithNconstException())
    }
  }

  def findNconstsByTconsts(tconsts: Seq[Int]): Future[Either[LunaException, Seq[String]]] = {
    val action = TitlePrincipalsTable.filter(_.tconst.inSet(tconsts)).map(_.nconst).result

    db.run(action.asTry) map {
      case Success(result) => Right(result.foldLeft(Seq.empty[String])((acc, i) => acc ++ Seq(i)))
      case _ => Left(NoMatchingNconstsWithTconstException())
    }
  }
}
