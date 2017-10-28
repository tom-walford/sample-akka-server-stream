package twalford.stream

import java.nio.file.Paths

import akka.stream.scaladsl.FileIO

object FileStreaming {
  def streamTestFile() = FileIO.fromPath(Paths.get("test.csv"))
}
