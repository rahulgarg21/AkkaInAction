package com.aia.chp04.actor.lifecycle

import akka.actor.Actor
import akka.actor.ActorLogging

class LifeCycleHooks extends Actor with ActorLogging {

  println("Constructor")

  override def preStart() {
    println("prestart")
  }
  
  override def postStop() {
    println("postStop")
  }
  
  override def preRestart(reason:Throwable,message:Option[Any]) {
    println("preRestart")
    super.preRestart(reason, message)
  }
  
  override def postRestart(reason:Throwable) {
    println("postRestart")
    super.postRestart(reason)
  }
  
  
  def receive = {
    case "restart" => throw new IllegalStateException("force restart")
    case msg:AnyRef => sender ! msg
  }

}
