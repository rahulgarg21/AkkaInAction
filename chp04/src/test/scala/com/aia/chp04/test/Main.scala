package com.aia.chp04.test

import scala.concurrent.duration.DurationInt
import scala.util.Failure
import scala.util.Success

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

import com.aia.chp04.config.TestApplicationConfig
import com.aia.chp04.config.spring.SpringExtensionImpl
import com.aia.chp04.test.CountingActorProtocol.GetCount
import com.aia.chp04.test.CountingActorProtocol.IncrementCount

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.actorRef2Scala
import akka.pattern.ask
import akka.util.Timeout

object Main extends App{

  val appContext:ApplicationContext = new AnnotationConfigApplicationContext(classOf[TestApplicationConfig])
  
  val system:ActorSystem = appContext.getBean(classOf[ActorSystem])
  val extension:SpringExtensionImpl = appContext.getBean(classOf[SpringExtensionImpl]) 
  
  val counter:ActorRef = system.actorOf(extension.props("countingActor"),"counter")
  
  counter ! IncrementCount
  counter ! IncrementCount
  counter ! IncrementCount
  
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)
  
  val result = counter.ask(GetCount).mapTo[Int]
  
  result.onComplete { 
    case Success(result) => println(s"Got back result: $result")
    case Failure(failure) => println(s"Got an exception: $failure")
    }
  
  system.shutdown()
  system.awaitTermination()

}