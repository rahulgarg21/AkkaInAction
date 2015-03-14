package com.aia.chp03.filtering

import akka.actor.{ActorLogging, ActorRef, Props}
import com.aia.chp03.common.BaseActorTest
import com.aia.chp03.filtering.FilteringActorProtocol.Event
import org.slf4j.{Logger, LoggerFactory}

class FilteringActorTest extends BaseActorTest("testActorSystem"){

   private val log: Logger = LoggerFactory.getLogger(classOf[FilteringActorTest])

  "A Filtering Actor" must {

    "filter out particular messages" in {
      val props: Props = Props(classOf[FilteringActor], testActor, 5)
      val filteringActor: ActorRef = system.actorOf(props, "FilteringActor1")

      filteringActor ! Event(1)
      filteringActor ! Event(2)
      filteringActor ! Event(1)
      filteringActor ! Event(3)
      filteringActor ! Event(1)
      filteringActor ! Event(4)
      filteringActor ! Event(5)
      filteringActor ! Event(5)
      filteringActor ! Event(6)

      val eventIds = receiveWhile() {
        case Event(id) if id <= 5 => {
          log.debug(s"received ${id}")
          id
        }
      }

      eventIds must be(List(1,2,3,4,5))
      expectMsg(Event(6))

    }

    "filter out particular messages using expectNoMsg" in {
      val props: Props = Props(classOf[FilteringActor], testActor, 5)
      val filteringActor: ActorRef = system.actorOf(props, "FilteringActor2")

      filteringActor ! Event(1)
      filteringActor ! Event(2)
      expectMsg(Event(1))
      expectMsg(Event(2))
      filteringActor ! Event(1)
      expectNoMsg
      filteringActor ! Event(3)
      expectMsg(Event(3))
      filteringActor ! Event(1)
      expectNoMsg
      filteringActor ! Event(4)
      filteringActor ! Event(5)
      filteringActor ! Event(5)
      expectMsg(Event(4))
      expectMsg(Event(5))
      expectNoMsg
    }

  }

}
