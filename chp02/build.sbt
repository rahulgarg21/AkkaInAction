enablePlugins(JavaServerAppPackaging)

name := "goticks"

version := "1.0"

organization := "com.goticks"

scalaVersion := "2.11.5"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases",
                  "Spray Repository" at "http://repo.spray.io")

libraryDependencies ++= {
  val akkaVersion     = "2.3.9"
  val sprayVersion    = "1.3.2"
  Seq(
    "com.typesafe.akka"       %%    "akka-actor"            % akkaVersion,
    "com.typesafe.akka"       %%    "akka-slf4j"            % akkaVersion,
    "io.spray"                %%    "spray-can"             % sprayVersion,
    "io.spray"                %%    "spray-routing"         % sprayVersion,
    "io.spray"                %%    "spray-json"            % "1.3.1",
    "ch.qos.logback"          %     "logback-classic"       % "1.1.2",
    "com.typesafe.akka"       %%    "akka-testkit"          % akkaVersion     % "test",
    "org.scalatest"           %%    "scalatest"             % "2.2.0"         % "test"
  )
}

mainClass in Global := Some("com.goticks.Main")

assemblyJarName in assembly := "goticks-server.jar"
