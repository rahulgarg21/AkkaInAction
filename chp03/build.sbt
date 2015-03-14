name := "chp03"

version := "1.0"

scalaVersion := "2.11.5"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases")

libraryDependencies ++= {
  val akkaVersion = "2.3.9"
  Seq(
    "com.typesafe.akka"       %%    "akka-actor"            % akkaVersion,
    "com.typesafe.akka"       %%    "akka-slf4j"            % akkaVersion,
    "ch.qos.logback"          %     "logback-classic"       % "1.1.2",
    "com.typesafe.akka"       %%    "akka-testkit"          % akkaVersion     % "test",
    "org.scalatest"           %%    "scalatest"             % "2.2.0"         % "test"
  )
}






    