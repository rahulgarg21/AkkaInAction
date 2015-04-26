package com.aia.chp04.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Scope
import com.aia.chp04.test.spring.CountingActor
import com.aia.chp04.test.spring.CountingActor
import com.aia.chp04.test.spring.CountingService
import com.aia.chp04.test.spring.CountingServiceImpl
import com.aia.chp04.test.spring.CountingActor
import com.aia.chp04.test.spring.CountingService
import com.aia.chp04.test.spring.CountingServiceImpl
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import com.aia.chp04.actor.lifecycle.LifeCycleHooks


@Configuration
@Import(Array(classOf[ApplicationConfig]))
class TestApplicationConfig {

  @Bean
  def countingService: CountingService = {
    new CountingServiceImpl
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  def countingActor: CountingActor = {
    new CountingActor(countingService)
  }
  
  
}