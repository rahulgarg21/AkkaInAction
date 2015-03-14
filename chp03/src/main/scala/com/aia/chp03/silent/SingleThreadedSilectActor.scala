package com.aia.chp03.silent

import akka.actor.Actor
import com.aia.chp03.silent.SingleThreadedSilentActorProtocol.SilentMessage

class SingleThreadedSilentActor extends Actor {

  var internalState: Vector[String] = Vector[String]()

  def receive() = {
    case SilentMessage(data) => internalState = internalState :+ data
  }

  def state = internalState

}

object SingleThreadedSilentActorProtocol {

  case class SilentMessage(data: String)

}