name := "hello"

version := "1.0"

organization := "org.my"

scalaVersion := "2.12.4"

sbtVersion := "1.1.0"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % "3.2.0",
  "com.h2database" % "h2" % "1.4.196",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)