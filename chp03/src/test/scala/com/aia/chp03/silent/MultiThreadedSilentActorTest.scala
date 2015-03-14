package com.aia.chp03.silent

import akka.actor.{ActorRef, Props}
import com.aia.chp03.common.BaseActorTest
import com.aia.chp03.silent.MultiThreadedSilentActorProtocol.{GetState, SilentMessage}

class MultiThreadedSilentActorTest extends BaseActorTest("testActorSystem") {

  "A Multi threaded Silent Actor" must {

    "must change its internal state when it receives a message" in {

      val silentActor: ActorRef = system.actorOf(Props[MultiThreadedSilentActor], "multiThreadedActor")

      val data1 = "whisper1"
      val data2 = "whisper2"

      silentActor ! SilentMessage(data1)
      silentActor ! SilentMessage(data2)

      silentActor ! GetState(testActor)

      expectMsg(Vector(data1,data2))

    }

  }

}
