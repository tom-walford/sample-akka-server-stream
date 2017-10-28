val deps = Seq(
      "com.typesafe.akka" %% "akka-stream" % "2.4.19",
      "com.typesafe.akka" %% "akka-http" % "10.0.10"
    )

val opts = Seq(
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-unused-import",
  "-Ywarn-numeric-widen"
)

val server = (project in file("server"))
  .settings(Seq(
    name := "server",
    scalacOptions ++= opts,
    libraryDependencies ++= deps,
    scalaVersion := "2.12.4"
  ))

val client = (project in file("client"))
  .settings(Seq(
    name := "client",
    scalacOptions ++= opts,
    libraryDependencies ++= deps,
    scalaVersion := "2.12.4"
  ))
