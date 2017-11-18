
name := "SlickTest"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  //* So we can show Slick in action using tests
  "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.1",
  //* Postgres JDBC driver
  "org.postgresql" % "postgresql" % "42.1.4",
  //* Main Slick dependency
  "com.typesafe.play" %% "play-slick" % "2.1.0",
  //* Evolutions dependency
  "com.typesafe.play" %% "play-slick-evolutions" % "2.1.0",
  //* Embedded database dependency
  "com.h2database" % "h2" % "1.4.192"
)