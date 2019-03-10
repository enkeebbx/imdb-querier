package helpers

import models._
import org.joda.time.LocalDate
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json
import play.api.mvc.{Action, Results}
import play.api.routing.Router
import play.api.routing.sird._
import play.api.test.{DefaultAwaitTimeout, FutureAwaits}
import play.api.{Application, Logger}

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

  def generateGenres(size : Int, genre: String): Seq[Genres] = {
    Range(0, size).map { i => Genres (i+1, genre) }
  }

  def generateGenreTitles(size: Int, genreId : Int) : Seq[GenreTitles] = {
    Range(0, size).map { i => GenreTitles (i+1, genreId, i+1) }
  }

  def generateTitleRatings(size: Int) : Seq[TitleRatings] = {
    Range(0, size).map { i => TitleRatings (Some(i+1), Some(i+1), Some((i+1).toDouble)) }
  }

  def generateTitleCrew(size: Int) : Seq[TitleCrew] = {
    Range(0, size).map { i => TitleCrew((i+1).toString, (i+1).toString, Some(i+1)) }
  }

  def generateTitleActors(size: Int) : Seq[TitleActors] = {
    Range(0, size).map { i => TitleActors(i+1, i+1, (i+1).toString) }
  }

  def generateNameBasics(size: Int, name : String, nconst: Int) : Seq[NameBasics] = {
    Range(0, size).map { i =>
      NameBasics (
        "actor",
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

  def generateTitleBasics(size: Int, title: String) : Seq[TitleBasics] = {
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
        Some(i)
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
        nconst
      )
    }
  }
}