package com.aia.chp04.test.spring

trait CountingService {
  
  def increment(count:Int):Int 

}

class CountingServiceImpl extends CountingService {
  
  def increment(count:Int):Int = count+1
  
}