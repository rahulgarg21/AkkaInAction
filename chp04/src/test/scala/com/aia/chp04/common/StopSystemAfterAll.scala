package com.aia.chp04.common

import akka.testkit.TestKit
import org.scalatest.{BeforeAndAfterAll, Suite}

trait StopSystemAfterAll extends BeforeAndAfterAll{

  this: TestKit with Suite =>

  override protected def afterAll(): Unit = {
    super.afterAll()
    system.shutdown()
  }

}
