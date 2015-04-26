package com.aia.chp04.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import akka.actor.ActorSystem
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.aia.chp04.config.spring.SpringExtension
import com.aia.chp04.config.spring.SpringExtensionImpl
import com.aia.chp04.actor.lifecycle.LifeCycleHooks
import org.springframework.context.annotation.Scope
import org.springframework.beans.factory.config.ConfigurableBeanFactory

@Configuration
@Lazy
class ApplicationConfig {
  
  @Autowired implicit val applicationContext:ApplicationContext = null
  
  @Bean
  def actorSystem:ActorSystem = {
    val actorSystem:ActorSystem = ActorSystem("AkkaLogProcessingSystem", akkaConfig)
    actorSystem
  }
  @Bean
  def akkaConfig:Config = {
    ConfigFactory.load()
  }
  
  @Bean
  def springExtension:SpringExtensionImpl = {
    SpringExtensionImpl(actorSystem)
  }
  
  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  def lifeCycleHooks: LifeCycleHooks = {
    new LifeCycleHooks
  }
  

}