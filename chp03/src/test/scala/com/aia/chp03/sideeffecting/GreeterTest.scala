package com.aia.chp03.sideeffecting

import akka.actor.{UnhandledMessage, ActorRef, Props, ActorSystem}
import akka.testkit.{EventFilter, CallingThreadDispatcher}
import com.aia.chp03.common.{BaseActorTest, BaseActorTestWithTestKit}
import com.aia.chp03.sideeffecting.GreeterProtocol.Greeting
import com.typesafe.config.ConfigFactory
import GreeterTest1._

class GreeterTest1 extends BaseActorTestWithTestKit(testSystem) {

  "The Greeter" must {

    "say Hello World! when Greeting(\"World\") is sent to it: using TestEventFilter" in {

      val dispatcherId = CallingThreadDispatcher.Id
      val props = Props(classOf[Greeter], Some(testActor)).withDispatcher(dispatcherId)
      val greeter = system.actorOf(props, "greeter2")

      EventFilter.info(message = "Hello World!", occurrences = 1).intercept {
        greeter ! Greeting("World")
      }
    }


  }

}

object GreeterTest1 {

  val testSystem = {

    val config = ConfigFactory.parseString(
      """akka.loggers = ["akka.testkit.TestEventListener"]""")

    ActorSystem("testSystem", config

    )
  }

}

class GreeterTest2 extends BaseActorTest("testActorSystem") {

  "The Greeter" must {

    "say Hello World! when Greeting(\"World\") is sent to it: using testActor and ExpectMsg" in {

      val props = Props(classOf[Greeter], Some(testActor))
      val greeter = system.actorOf(props, "greeter1")
      greeter ! Greeting("World")
      expectMsg("Hello World!")

    }

    "say something else when a proper Greeting msg is not sent" in {

      val props = Props(classOf[Greeter], Some(testActor))
      val greeter: ActorRef = system.actorOf(props, "greeter3")
      system.eventStream.subscribe(testActor, classOf[UnhandledMessage])
      greeter ! "World" // not Greeting("World")
      expectMsg(UnhandledMessage("World", system.deadLetters, greeter))
    }

  }
}


