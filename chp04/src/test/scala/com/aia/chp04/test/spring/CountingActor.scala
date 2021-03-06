package com.aia.chp04.test.spring

import akka.actor.Actor
import com.aia.chp04.test.spring.CountingActorProtocol._

class CountingActor(val countingService:CountingService) extends Actor{
  
 private var count = 0
  
 def receive = {
   case IncrementCount => count = countingService.increment(count)
   case GetCount => sender ! count
 }

}

object CountingActorProtocol {
  case object GetCount
  case object IncrementCount
}