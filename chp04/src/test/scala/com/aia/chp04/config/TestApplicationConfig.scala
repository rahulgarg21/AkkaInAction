package com.aia.chp04.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Bean
import com.aia.chp04.test.CountingService
import com.aia.chp04.test.CountingServiceImpl
import com.aia.chp04.test.CountingActor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import com.aia.chp04.test.CountingActor
import org.springframework.beans.factory.config.ConfigurableBeanFactory

@Configuration
@Import(Array(classOf[ApplicationConfig]))
class TestApplicationConfig {
  
  @Bean
  def countingService:CountingService = {
    new CountingServiceImpl
  }
  
  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  def countingActor:CountingActor = {
    new CountingActor(countingService)
  }

}