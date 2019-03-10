version := "1.0-SNAPSHOT"

name := "imdb-querier"

resolvers ++= Seq(
  "scalaz-bintray"      at "http://dl.bintray.com/scalaz/releases",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

scalaVersion := "2.11.12"

scalacOptions ++= Seq("-feature", "-deprecation", "-unchecked", "-language:reflectiveCalls", "-language:postfixOps", "-language:implicitConversions", "-target:jvm-1.8")

crossPaths := false

lazy val imdbQuerier = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  ws,
  specs2,
  "com.typesafe.play" %% "play-slick" % "2.1.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.1.1",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.3.0",
  "joda-time" % "joda-time" % "2.9.7",
  "com.h2database" % "h2" % "1.4.193",
  "mysql" % "mysql-connector-java" % "5.1.45",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % "test",
  "io.github.scala-hamsters" %% "hamsters" % "1.5.1"
)

routesGenerator := InjectedRoutesGenerator

doc in Compile := target.map(_ / "none").value
