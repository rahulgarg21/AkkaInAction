package com.aia.chp04.config.spring

import akka.actor.Extension
import org.springframework.context.ApplicationContext
import akka.actor.Props
import akka.actor.ActorSystem


class SpringExtensionImpl extends Extension {
  
  var applicationContext:ApplicationContext = _
  
  def initialize(implicit applicationContext:ApplicationContext):SpringExtensionImpl = {
    this.applicationContext = applicationContext
    this
  }
  
  def props(actorBeanName:String): Props = {
    Props(classOf[SpringActorProducer],applicationContext, actorBeanName)
  }
  
}

object SpringExtensionImpl {
  
  def apply(system:ActorSystem)(implicit applicationContext:ApplicationContext):SpringExtensionImpl =
    SpringExtension().get(system).initialize
  
}