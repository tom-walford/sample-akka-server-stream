package twalford

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import twalford.stream.FileStreaming

import scala.concurrent.ExecutionContext

object FileSender extends App {
  private final val destination: String = "http://localhost:8080/file"
  private implicit val system: ActorSystem = ActorSystem("my-system")
  private implicit val materializer: ActorMaterializer = ActorMaterializer()
  private implicit val executionContext: ExecutionContext = system.dispatcher

  Http().singleRequest(HttpRequest(uri = destination,
    method = HttpMethods.PUT,
    entity = HttpEntity(
      contentType = ContentTypes.`text/csv(UTF-8)`,
      data = FileStreaming.streamTestFile())))
    .flatMap(_ => system.terminate())
}
