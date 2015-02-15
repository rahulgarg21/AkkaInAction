package com.goticks

import akka.actor._
import akka.event.LoggingReceive
import com.goticks.TicketProtocol._

class BoxOffice extends Actor with TicketSellerCreator with ActorLogging {

  def receive = {

    case CreateEvents(events: List[Event]) =>

      events.foreach(event =>
        if (context.child(event.eventName).isEmpty) {
          log.info(s"Creating new event name: ${event.eventName} with tickets: ${event.availableTickets}")
          val ticketSeller: ActorRef = createTicketSeller(event.eventName)
          val newTickets: List[Ticket] = (1 to event.availableTickets).map(Ticket(event.eventName, _)).toList
          ticketSeller ! CreateTickets(newTickets)
          sender ! EventsCreated
        } else {
          val reason = s"Event name: ${event.eventName} already has tickets. Adding tickets to existing event is not supported yet"
          sender ! ErrorMessage(reason)
        })

    case GetAllEvents =>
      log.info("Getting all events")
      if(!context.children.isEmpty) {
        context.actorOf(Props(classOf[SenderBoundEventsRetriever], sender, context.children),"SenderBoundEventsRetriever")
      } else {
        sender ! ErrorMessage("No events found")
      }

    case GetTicket(eventName: String) =>
      log.info(s"Getting a ticket for given event name: ${eventName}")
      context.child(eventName) match {
        case Some(ticketSeller) => ticketSeller.forward(BuyTicket)
        case None => sender ! ErrorMessage(s"Invalid event name: ${eventName} ")
      }

    case _ => log.error("Received Invalid Message Type")

  }

}

trait TicketSellerCreator {
  self: Actor =>
  def createTicketSeller(eventName: String): ActorRef = context.actorOf(Props(classOf[TicketSeller], eventName), eventName)
}

class SenderBoundEventsRetriever(val capturedSender: ActorRef, val children: Iterable[ActorRef]) extends Actor with ActorLogging {

  children.foreach(child => {
    log.info(s"Sending GetAllEvents to child: ${child.path.name}")
    child ! GetNumberOfAvailableTickets
  })
  var events: List[Event] = List[Event]()

  def receive = LoggingReceive{

    case event: Event =>
      events = event +: events
      isAllEventsPresent()

    case _ => log.error("Received Invalid Message Type")

  }

  def isAllEventsPresent() = {
    if (events.size == children.size) {
      capturedSender ! CurrentEvents(events)
      self ! PoisonPill
    }
  }

}
