package services.query

import com.google.inject.{Inject, Singleton}
import io.github.hamsters.FutureEither
import models.NameBasics
import models.exceptions.LunaException
import repositories.{NameBasicsRepository, TitleActorsRepository, TitleCrewRepository}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CrewQueryService @Inject()(
  titleActorsRepository: TitleActorsRepository,
  nameBasicsRepository: NameBasicsRepository,
  titleCrewRepository: TitleCrewRepository
)(implicit ec: ExecutionContext) {
  case class DirectorsWritersNconst (
    directors : Seq[Int],
    writers : Seq[Int]
  )

  case class DirectorWriterNameBasics (
    director : Seq[NameBasics],
    writer : Seq[NameBasics]
  )

  /**
    * Find nconst of directors and writers associated with the given tconst
    *
    * @param tconst
    * @return
    */

  private[query] def findDirectorsWritersNconstByTconst(tconst: Int): Future[Either[LunaException, DirectorsWritersNconst]] = {
    titleCrewRepository.findTitleCrewByTconst(tconst).map {
      case Right(titleCrew) =>
        val directors = titleCrew.directors.split(',').map(_.toInt)
        val writers = titleCrew.directors.split(',').map(_.toInt)
        Right(DirectorsWritersNconst(directors, writers))
      case Left(e) =>
        Left(e)
    }
  }

  /**
    * Finds all directors and writers by their tconst
    *
    * @param tconst
    * @return
    */

  def findDirectorWriterNameBasicsByTconst(tconst: Int): Future[Either[LunaException, DirectorWriterNameBasics]] = {
    (for {
      nconsts <- FutureEither(findDirectorsWritersNconstByTconst(tconst))
      directors <- FutureEither(nameBasicsRepository.findNameBasicsByNconsts(nconsts.directors))
      writers <- FutureEither(nameBasicsRepository.findNameBasicsByNconsts(nconsts.writers))
    } yield {
      DirectorWriterNameBasics(directors, writers)
    }).future
  }

  /**
    * Finds all actors by tconst
    *
    * @param tconst
    * @return
    */

  def findActorNameBasicsByTconst(tconst: Int): Future[Either[LunaException, Seq[NameBasics]]] = {
    (for {
      nconsts <- FutureEither(titleActorsRepository.findNconstsByTconst(tconst))
      actors <- FutureEither(nameBasicsRepository.findNameBasicsByNconsts(nconsts))
    } yield actors).future
  }
}
