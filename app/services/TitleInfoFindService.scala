package services

import com.google.inject.{Inject, Singleton}
import io.github.hamsters.FutureEither
import models.exceptions.{LunaException, TitleBasicsTconstNotFoundException}
import models.{Crew, TitleInfo}
import repositories.TitleBasicsRepository
import services.query.CrewQueryService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TitleInfoFindService @Inject()(
  titleBasicsRepository : TitleBasicsRepository,
  crewQueryService : CrewQueryService
)(implicit ec: ExecutionContext) {
  /**
    * Requirement #1
    * @param title
    * @return
    */

  def findTitleInfo(title: String): Future[Either[LunaException, TitleInfo]] = {
    titleBasicsRepository.findTitleBasicsByPrimaryOrOriginalTitle(title).flatMap {
      case Right(titleBasics) =>
        val tconst = titleBasics.tconst
        if (tconst.isDefined) {
          (for {
            actorNames <- FutureEither(crewQueryService.findActorNameBasicsByTconst(tconst.get))
            directorWriters <- FutureEither(crewQueryService.findDirectorWriterNameBasicsByTconst(tconst.get))
          } yield {
            TitleInfo(
              titleBasics.primaryTitle,
              titleBasics.genres,
              titleBasics.startYear,
              titleBasics.runtimeMinutes,
              titleBasics.isAdult,
              titleBasics.endYear,
              tconst.get,
              directorWriters.director.map(nameBasics => Crew(nameBasics.primaryName)),
              directorWriters.writer.map(nameBasics => Crew(nameBasics.primaryName)),
              actorNames.map(_.primaryName)
            )
          }).future
        }
        else Future.successful(Left(TitleBasicsTconstNotFoundException()))
      case Left(e) =>
        Future.successful(Left(e))
    }
  }
}
