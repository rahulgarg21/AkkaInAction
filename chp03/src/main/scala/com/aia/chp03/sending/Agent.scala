package com.aia.chp03.sending

import akka.actor.{ActorRef, Actor}
import com.aia.chp03.sending.AgentProtocol.Game

class Agent(nextAgent:ActorRef) extends Actor{

  def receive = {
    case game @ Game(name, tickets) => nextAgent ! game.copy(name,tickets.tail)
  }

}

object AgentProtocol {

  case class Ticket(number:Int)

  case class Game(name:String, tickets:Vector[Ticket])

}
