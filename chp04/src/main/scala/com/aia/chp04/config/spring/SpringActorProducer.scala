package com.aia.chp04.config.spring

import akka.actor.IndirectActorProducer
import org.springframework.context.ApplicationContext
import akka.actor.Actor


class SpringActorProducer(val applicationContext: ApplicationContext,
  val actorBeanName: String) extends IndirectActorProducer {

  override def actorClass: Class[_ <: Actor] = {
    applicationContext.getType(actorBeanName).asInstanceOf[Class[_ <: Actor]]
  }
  
  override def produce: Actor = applicationContext.getBean(actorBeanName,classOf[Actor])

}