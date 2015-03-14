package com.aia.chp03.sending

import akka.actor.Props
import com.aia.chp03.common.BaseActorTest
import com.aia.chp03.sending.AgentProtocol.{Game, Ticket}

class AgentTest extends BaseActorTest("testActorSystem") {

  "A sending Actor" must {

    "Send back a message when finished" in {

      val props: Props = Props(classOf[Agent],testActor)
      val sendingActor = system.actorOf(props, "Agent")
      val tickets = Vector(Ticket(1), Ticket(2), Ticket(3))

      val game: Game = Game("Lakers vs Bulls", tickets)
      sendingActor ! game

      expectMsgPF() {
        case Game(_,tickets) => tickets.size must be (game.tickets.size -1)
      }

    }
  }

}
