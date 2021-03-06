package services

import com.google.inject.{Inject, Singleton}
import io.github.hamsters.FutureEither
import models.NconstDegree
import models.exception.{KevinBaconNotFoundException, LunaException}
import repositories.{NameBasicsRepository, TitlePrincipalsRepository}
import utils.NconstUtil

import scala.collection.immutable.Queue
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DegreeCalculateService @Inject()(
  nameBasicsRepository: NameBasicsRepository,
  titlePrincipalsRepository: TitlePrincipalsRepository
)(implicit ec: ExecutionContext) {

  /**
    * Requirement #3 : This is our driver function.
    *
    * @param genre
    */

  def getDegreeWithKevinBacon(actorName: String): Future[Either[LunaException, Int]] = {
    val nConstQueue: Queue[NconstDegree] = Queue[NconstDegree]()

    val output = (for {
      seedActorNconst <- FutureEither(nameBasicsRepository.findNconstByPrimaryName(actorName))
      kevinBaconNconst <- FutureEither(nameBasicsRepository.findNconstByPrimaryName("Kevin Bacon"))
    } yield {
      val seedDegree = 0
      val seedNconstQueue: Queue[NconstDegree] = nConstQueue.enqueue(NconstDegree(NconstUtil.padNconst(seedActorNconst.toString), seedDegree))
      getDegreeWithKevinBaconHelper(seedNconstQueue, NconstUtil.padNconst(kevinBaconNconst.toString), Map(), Map())
    }).future

    output.flatMap {
      case Right(fut) => fut
      case Left(e) => Future.successful(Left(e))
    }
  }

  /**
    * Helper function:
    *
    * 1. Check nconst queue empty. If empty, we are all out of actors. Terminate.
    * 2. If not empty, dequeue nconst queue for our actor to be investigated. Mark it as visited
    * so we don't perform unnecessary investigation on the actor again.
    * 3. Take the actor's nconst, gather NEW associated tconsts, mark them as visited. We don't want
    * to regather nconsts from them.
    * 4. Explore which NEW nconsts are associated with the tconsts. Add them to our dequeued queue from step 2, mark them as unvisited.
    * We are going to explore them in the future.
    * 5. Go back to step 1.
    *
    * @param nConstQueue
    * @param kevinBaconNconst
    * @param tConstVisited
    * @param nConstVisited
    * @return
    */

  private def getDegreeWithKevinBaconHelper(
    nConstQueue: Queue[NconstDegree],
    kevinBaconNconst: String,
    tConstVisited: Map[Int, Boolean],
    nConstVisited: Map[String, Boolean]
  ): Future[Either[LunaException, Int]] = {
    if (nConstQueue.isEmpty) {
      Future.successful(Left(KevinBaconNotFoundException()))
    }
    else {
      val dequeue = nConstQueue.dequeue
      val nconst: String = dequeue._1.nconst
      val degree = dequeue._1.degree

      // Kevin Bacon, I got you!
      if (nconst == kevinBaconNconst) Future.successful(Right(dequeue._1.degree))
      else {
        // or not... Kevin Bacon, where art thou? I am coming for you!!
        val output = (for {
          tconsts <- FutureEither(titlePrincipalsRepository.findTconstsByNconst(nconst))
          nconsts <- FutureEither(titlePrincipalsRepository.findNconstsByTconsts(tconsts.filter(isValidTconst(_, tConstVisited))))
        } yield {
          val currentNconstMarkedVisisted               = nConstVisited ++ Map(nconst -> true)
          val isValidNconsts: Seq[String]               = nconsts.filter(nconst => isValidNconst(nconst, currentNconstMarkedVisisted, degree+1))
          val updateTconstVisited: Map[Int, Boolean]    = tConstVisited ++ tconsts.map(tconst => (tconst, true)).toMap
          val updateNconstVisited: Map[String, Boolean] = currentNconstMarkedVisisted ++ isValidNconsts.map(nconst => (nconst, false)).toMap
          val updateQueue: Queue[NconstDegree]          = dequeue._2 ++ isValidNconsts.foldLeft(Queue[NconstDegree]())((acc, i) => acc.enqueue(NconstDegree(i, degree + 1)))

          getDegreeWithKevinBaconHelper(updateQueue, kevinBaconNconst, updateTconstVisited, updateNconstVisited)
        }).future

        output.flatMap {
          case Left(e) => Future.successful(Left(e))
          case Right(either) =>
            either.map {
              case Left(e) => Left(e)
              case Right(value) => Right(value)
            }
        }
      }
    }
  }

  private def isValidNconst(nconst: String, nconstVisited: Map[String, Boolean], degree: Int): Boolean = {
    !nconstVisited.getOrElse(nconst, false) && degree <= 6
  }

  private def isValidTconst(tconst: Int, tconstVisited: Map[Int, Boolean]): Boolean = {
    !tconstVisited.getOrElse(tconst, false)
  }
}
