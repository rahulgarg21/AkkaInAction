package com.goticks

import akka.actor.{ActorLogging, PoisonPill, Actor}
import TicketProtocol._
import akka.event.LoggingReceive

class TicketSeller(val eventName: String) extends Actor with ActorLogging{

  private var tickets: Vector[Ticket] = Vector[Ticket]()

  def receive = LoggingReceive {

    case CreateTickets(newTickets: List[Ticket]) => tickets=tickets ++ newTickets

    case GetNumberOfAvailableTickets => sender ! Event(eventName, tickets.size)

    case BuyTicket =>
      if (tickets.isEmpty) {
        sender ! SoldOut
        self ! PoisonPill
      }
      tickets.headOption.foreach(ticket => {
        tickets = tickets.tail
        sender ! ticket
      })

  }

}
