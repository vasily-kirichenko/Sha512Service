name := "Sha512Service"
version := "1.0"
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "akka-actor",
  "akka-stream",
  "akka-http-experimental",
  "akka-http-testkit")
  .map("com.typesafe.akka" %% _ % "2.4.8")