package com.goticks

import spray.json.{JsValue, JsonFormat, DefaultJsonProtocol}

object TicketProtocol {

  // ====> Domain Classes
  //Ticket for an particular event with ticket Number
  case class Ticket(eventName: String, ticketNumber: Int)
  //Event with a event Name and available tickets for that event
  case class Event(eventName: String, availableTickets: Int)

  // ====> Utility Classes
  //Generic error message
  case class ErrorMessage(errorMessage: String)
  case class Message(informationMessage:String)


  // ====> Use Case: Create new event
  //Rest Interface Request to Box Office to create events
  case class CreateEvents(events: List[Event])
  //Box office sends message CreateTickets to appropriate ticket Seller
  case class CreateTickets(newTickets: List[Ticket])
  //Box office Response back to Rest Interface that event was created
  case object EventsCreated
  //Box Office Response back to Rest Interface with error message if it fails to create event

  // ====> Use Case: Get all current events and their available tickets
  //Rest Interface Request to BoxOffice to get all events
  case object GetAllEvents
  //Message sent to TicketSeller by box Office to get available tickets
  case object GetNumberOfAvailableTickets
  //Box Office Response to Rest Interface showing all available events
  case class CurrentEvents(events: List[Event])


  // ===> Use Case: Request to buy Ticket for particular event
  //Rest Interface Request to Box Office to request a ticket
  case class GetTicket(eventName: String)
  //Box Office Request to Ticket Seller to buy a ticket
  case object BuyTicket
  //Box Office response to Rest Interface in case when there is no tickets
  case object SoldOut
  //Ticket domain object will be sent out as response to Rest Interface if tickets are available


  /**
   * JSON Conversion companion objects
   */

  //Domain objects
  object Ticket extends DefaultJsonProtocol {
    implicit val format = jsonFormat2(Ticket.apply)
  }

  object Event extends DefaultJsonProtocol {
    implicit val format = jsonFormat2(Event.apply)
  }

  object Message extends DefaultJsonProtocol{
    implicit val format = jsonFormat1(Message.apply)
  }
  
  object ErrorMessage extends DefaultJsonProtocol {
    implicit val format = jsonFormat1(ErrorMessage.apply)
  }
  //Use Case: Create new events
  object CreateEvents extends DefaultJsonProtocol {
    implicit val eventFormat = jsonFormat2(Event.apply)
    implicit def createEventsFormat = jsonFormat1(CreateEvents.apply)
  }

  //Use Case: Get all current events
  object CurrentEvents extends DefaultJsonProtocol {
    implicit val eventFormat = jsonFormat2(Event.apply)
    implicit val format = jsonFormat1(CurrentEvents.apply)
  }

  //Use Case: Request to buy Ticket for particular event
  object GetTicket extends DefaultJsonProtocol {
    implicit val format = jsonFormat1(GetTicket.apply)
  }

}
