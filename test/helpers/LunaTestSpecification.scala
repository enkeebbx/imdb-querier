package helpers

import com.typesafe.config.ConfigFactory
import org.scalatest.mock.MockitoSugar
import org.specs2.execute.{AsResult, Result}
import play.api.inject.guice._
import play.api.test.WithServer
import play.api.{Application, Configuration}

import scala.concurrent.Future

class LunaTestSpecification extends LunaTestHelper with MockitoSugar {

  val testServerPort = 3333

  override def await[T](future: Future[T])(implicit timeout: akka.util.Timeout): T = {
    val result = super.await(future)(timeout)
    logger.info(s"[await result] $result")
    result
  }

  def testApplication: GuiceApplicationBuilder = {
    val config = Configuration(ConfigFactory.load("application-test.conf"))
    val keys = Seq("slick.dbs.default")
    val testConfigs = (config.entrySet.filter {
      case (k,_) => keys.exists(k.startsWith)
    } map {
      case (k,v) => s"\n[test configs] $k=${v.render}"
    }).toSeq.sorted.mkString("")
    logger.info(s"testApplication => $testConfigs")
    GuiceApplicationBuilder()
      .configure(config)
      .router(router)
  }

  abstract class WithMemDb(app : Application = testApplication.build()) extends WithServer(app = app, port = testServerPort){
    override def around[T](t: => T)(implicit asResult : AsResult[T]): Result = {
      super.around(t)
    }
  }
}