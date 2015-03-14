package com.aia.chp03.sideeffecting

import akka.actor.{ActorRef, ActorLogging, Actor}
import akka.event.LoggingReceive
import com.aia.chp03.sideeffecting.GreeterProtocol.Greeting

class Greeter(val listener:Option[ActorRef] = None) extends Actor with ActorLogging{

  override def receive: Receive = LoggingReceive{
    case Greeting(who) =>
      val message = "Hello " + who + "!"
      log.info(message)
      listener.foreach(_ ! message)
  }
}

object GreeterProtocol {
  case class Greeting(who:String)
}
