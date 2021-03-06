package helpers

import models.TitleInfo
import models.entity._
import org.joda.time.LocalDate
import org.scalatestplus.play.PlaySpec
import play.api.mvc.{Action, Results}
import play.api.routing.Router
import play.api.routing.sird._
import play.api.test.{DefaultAwaitTimeout, FutureAwaits}
import play.api.{Application, Logger}
import utils.NconstUtil

import scala.reflect.ClassTag

abstract class LunaTestHelper extends PlaySpec with Results with DefaultAwaitTimeout with FutureAwaits {

  def instance[T : ClassTag](implicit app:Application): T = app.injector.instanceOf[T]

  val logger = Logger(this.getClass)

  val today: String = new LocalDate().toString("yyyyMMdd")

  val router: Router = Router.from {
    case GET(p"/ping") => Action {
      Ok("200")
    }
  }

  def generateGenres(genreId: Int, genre: String): Genres = {
    Genres (genreId, genre)
  }

  def generateGenreTitles(size: Int, genreId : Int, tconst: Int) : Seq[GenreTitles] = {
    Range(0, size).map { i => GenreTitles (i+1, genreId, tconst) }
  }

  def generateTitleRatings(size: Int, tconst: Int, ratings: Double) : Seq[TitleRatings] = {
    Range(0, size).map { i => TitleRatings (Some(i+1), Some(tconst), Some(ratings)) }
  }

  def generateTitleCrew(size: Int, directors: String, writers: String, tconst: Int) : Seq[TitleCrew] = {
    Range(0, size).map { i => TitleCrew(directors, writers, Some(tconst)) }
  }

  def generateTitleActors(size: Int, tconst: Int, nconst: String) : Seq[TitleActors] = {
    Range(0, size).map { i => TitleActors(i+1, tconst, nconst) }
  }

  def generateNameBasics(size: Int, name : String, profession: String, nconst: Int) : Seq[NameBasics] = {
    Range(0, size).map { i =>
      NameBasics (
        profession,
        Some(1992),
        "title",
        Some("sound"),
        Some(nconst),
        Some("sound"),
        name,
        Some(1993),
        Some("sound")
      )
    }
  }

  def generateTitleBasics(size: Int, title: String, tconst: Int) : Seq[TitleBasics] = {
    Range(0, size).map { i =>
      TitleBasics (
        "genres",
        Some("title"),
        Some("sound"),
        "original title",
        Some(1992),
        Some(10),
        title,
        Some(true),
        Some(1993),
        Some(tconst)
      )
    }
  }

  def generateTitleInfo(size: Int, title: String, tconst: Int) : Seq[TitleInfo] = {
    Range(0, size).map { i =>
      TitleInfo (
        title,
        "Comedy",
        Some(1992),
        Some(10),
        Some(true),
        Some(1993),
        tconst,
        Seq(),
        Seq(),
        Seq()
      )
    }
  }

  def generateTitlePrincipals(size: Int, tconst: Int, nconst: String) : Seq[TitlePrincipals] = {
    Range(0, size).map { i =>
      TitlePrincipals (
        "genres",
        "title",
        "sound",
        Some(tconst),
        "",
        "",
        NconstUtil.padNconst(nconst)
      )
    }
  }
}