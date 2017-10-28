package twalford

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext
import scala.io.StdIn

object FileConsumer extends App {
  private implicit val system: ActorSystem = ActorSystem("my-system")
  private implicit val materializer: ActorMaterializer = ActorMaterializer()
  private implicit val executionContext: ExecutionContext = system.dispatcher

  val route = path("file") {
    extractRequestContext { ctx =>
      complete {
        // Note a lot of this can be cleaned up into a CSV parser. But i wanted to show the streams API a bit
        ctx.request.entity.dataBytes
          .mapConcat(_.utf8String.toList)
          .splitAfter(_ == '\n')
          .takeWhile(_ != '\n', false)
          .fold("")(_ + _)
          .map(str => str.split(","))
          .map(arr => Record(arr(0), arr(1)))
          .concatSubstreams
          .runForeach(println)
      }
    }
  }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ ⇒ system.terminate()) // and shutdown when done
}

case class Record(name: String, value: String)