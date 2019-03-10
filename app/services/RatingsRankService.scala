package services

import com.google.inject.{Inject, Singleton}
import models.RatingResult
import models.exceptions.{GenreIdNotFoundInGenreException, LunaException}
import repositories.{GenreTitlesRepository, GenresRepository, TitlesRatingsRepository}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class RatingsRankService @Inject()(
  genresRepository: GenresRepository,
  genreTitleRepository: GenreTitlesRepository,
  titleRatingsRepository: TitlesRatingsRepository
)(implicit ec: ExecutionContext) {
  private[services] def findRatingResultsDescByGenreId(genreId : Int): Future[Seq[RatingResult]] = {
    titleRatingsRepository.findAllGenreAndTitleByGenreIdSortByRatings(genreId).map { genreAndTitles =>
      genreAndTitles.map { genreAndTitle =>
        val titleRatings = genreAndTitle._1._2
        val titleBasics = genreAndTitle._2

        RatingResult (
          title = titleBasics.originalTitle,
          startYear = titleBasics.startYear,
          endYear = titleBasics.endYear,
          runtime = titleBasics.runtimeMinutes,
          isAdult = titleBasics.isAdult,
          averageRating = titleRatings.averageRating,
          numVotes = titleRatings.numVotes
        )
      }
    }
  }

  /**
    * Requirement #2
    * @param genre
    */

  def findTopRatedMoviesByGenre(genre: String): Future[Either[LunaException, Seq[RatingResult]]] = {
    genresRepository.findGenreIdByGenre(genre).flatMap {
      case Some(genreId) =>
        findRatingResultsDescByGenreId(genreId).map(Right(_))
      case _ =>
        Future.successful(Left(GenreIdNotFoundInGenreException()))
    }
  }
}
