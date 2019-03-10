package repositories

import javax.inject.{Inject, Singleton}
import models.NameBasics
import models.exceptions.{LunaException, NameBasicsNotFoundException, NoMatchingNconstWithNameException}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.components.{NameBasicsComponent, TitleCrewComponent}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

@Singleton
class NameBasicsRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with NameBasicsComponent with TitleCrewComponent {
  import profile.api._

  def insert(nameBasics : Seq[NameBasics]) = {
    val action = NameBasicsTable ++= nameBasics
    db.run(action).map { _.get }
  }

  // find directors, writers, and actors
  def findNameBasicsByNconsts(nconsts: Seq[Int]): Future[Either[LunaException, Seq[NameBasics]]] = {
    val action = NameBasicsTable.filter(_.nconst.inSet(nconsts)).result
    db.run(action.asTry) map {
      case Success(result) if result.nonEmpty => Right(result)
      case _ => Left(NameBasicsNotFoundException())
    }
  }

  def findNconstByPrimaryName(primaryName: String): Future[Either[LunaException, Int]] = {
    val action = NameBasicsTable.filter(_.primaryName === primaryName).map(_.nconst).result.headOption

    db.run(action.asTry) map {
      case Success(result) =>
        if (result.flatten.nonEmpty) Right(result.flatten.get)
        else Left(NoMatchingNconstWithNameException())
      case _ =>
        Left(NoMatchingNconstWithNameException())
    }
  }

  def findKnownForTitlesByPrimaryName(primaryName: String): Future[Seq[String]] = {
    val action = NameBasicsTable.filter(_.primaryName === primaryName)
      .map(_.knownForTitles)
      .result
      .headOption

    db.run(action.asTry) map {
      case Success(result) => result.map(_.split(",").toSeq).getOrElse(Seq.empty[String])
      case _               => Seq.empty[String]
    }
  }
}
