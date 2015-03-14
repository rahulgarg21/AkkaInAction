package com.aia.chp03.silent

import akka.testkit.TestActorRef
import com.aia.chp03.common.BaseActorTest
import com.aia.chp03.silent.SingleThreadedSilentActorProtocol.SilentMessage

class SingleThreadedSilentActorTest extends BaseActorTest("testActorSystem") {

  "A Single Threaded Silent Actor" must {

    "change its internal state" in  {
      val silentActor = TestActorRef[SingleThreadedSilentActor]
      val data = "whisper"
      silentActor ! SilentMessage(data)
      silentActor.underlyingActor.state must (contain(data))
    }

  }

}
