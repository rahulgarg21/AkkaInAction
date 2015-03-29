package com.aia.chp04.config.spring

import akka.actor.AbstractExtensionId
import akka.actor.ExtendedActorSystem
import akka.actor.ActorSystem

class SpringExtension extends AbstractExtensionId[SpringExtensionImpl] {
  
  override def createExtension(system: ExtendedActorSystem) = new SpringExtensionImpl
  
  override def get(system: ActorSystem): SpringExtensionImpl = super.get(system)

}

object SpringExtension {
  
  def apply(): SpringExtension = new SpringExtension

}