package com.aia.chp03.common

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.{MustMatchers, WordSpecLike}

class BaseActorTest(val testActorSystem: String) extends BaseActorTestWithTestKit(ActorSystem(testActorSystem))

class BaseActorTestWithTestKit(val actorSystem: ActorSystem) extends TestKit(actorSystem)
with WordSpecLike
with MustMatchers
with StopSystemAfterAll
