package com.aia.chp03.filtering

import akka.actor.{ActorLogging, ActorRef, Actor}
import akka.event.LoggingReceive
import com.aia.chp03.filtering.FilteringActorProtocol.Event

class FilteringActor(nextActor:ActorRef, bufferSize:Int) extends Actor with ActorLogging{

  private var events: Vector[Event] = Vector[Event]()

  def receive = LoggingReceive{

    case event:Event => if(!events.contains(event)) {
      events = events :+ event
      nextActor ! event

      if(events.size > bufferSize) {
        events = events.tail
      }
    }

  }

}

object FilteringActorProtocol {

  case class Event(id:Long)

}
