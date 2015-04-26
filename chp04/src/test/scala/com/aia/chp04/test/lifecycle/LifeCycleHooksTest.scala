package com.aia.chp04.test.lifecycle

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import com.aia.chp04.config.TestApplicationConfig
import akka.actor.ActorSystem
import com.aia.chp04.config.spring.SpringExtensionImpl
import org.springframework.context.ApplicationContext
import akka.actor.ActorRef
import com.aia.chp04.common.BaseActorTest
import akka.actor.Props
import com.aia.chp04.actor.lifecycle.LifeCycleHooks

class LifeCycleHooksTest extends BaseActorTest("testActorSystem") {

  "Test Life Cycle Hooks" must {

    "change states" in {

      intercept[IllegalStateException] {
        val lifeCycleHooksActor: ActorRef = system.actorOf(Props[LifeCycleHooks], "lifeCycleHooks")
        lifeCycleHooksActor ! "restart"
        lifeCycleHooksActor.tell("msg", testActor)
        expectMsg("msg")
      }

    }

  }

}