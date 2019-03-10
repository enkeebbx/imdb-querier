package repositories

import javax.inject.{Inject, Singleton}
import models.TitleAkas
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.components.TitleAkasComponent
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

@Singleton
class TitleAkasRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with TitleAkasComponent {

  import profile.api._

  def findTitleAkasByTitle(title: String): Future[Option[TitleAkas]] = {
    val action = titleAkas.filter(_.title === title).result.headOption

    db.run(action.asTry) map {
      case Success(result) => result
      case _               => None
    }
  }
}
