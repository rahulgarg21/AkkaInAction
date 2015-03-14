package com.aia.chp03.silent

import akka.actor.{ActorRef, Actor}
import com.aia.chp03.silent.MultiThreadedSilentActorProtocol.{GetState, SilentMessage}

class MultiThreadedSilentActor extends Actor {

  var internalState:Vector[String] = Vector[String]()

  def receive() = {

    case SilentMessage(data) => internalState = internalState :+ data

    case GetState(receiver) => receiver ! internalState

  }

}

object MultiThreadedSilentActorProtocol {

  case class SilentMessage(data:String)

  case class GetState(receiver:ActorRef)

}
