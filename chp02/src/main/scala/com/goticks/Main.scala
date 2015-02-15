package com.goticks

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import akka.util.Timeout
import com.typesafe.config.{Config, ConfigFactory}
import spray.can.Http
import scala.concurrent.duration._
import akka.pattern.ask

object Main extends App{

  private val config: Config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")

  implicit val system = ActorSystem("goticks")
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  val api = system.actorOf(Props(classOf[RestInterface]),"httpInterface")

  IO(Http).ask(Http.Bind(listener = api, interface = host, port = port))
  .mapTo[Http.Event]
  .map {
    case Http.Bound(address) =>
      println(s"REST interface bound to address: ${address}")
      case Http.CommandFailed(cmd) =>
      println(s"REST interface count not bind to ${host}:${port} \n ${cmd.failureMessage}")
      system.shutdown()
  }

}
