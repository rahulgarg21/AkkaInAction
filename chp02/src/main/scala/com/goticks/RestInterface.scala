package com.goticks

import akka.actor._
import akka.util.Timeout
import spray.http.{MediaTypes, StatusCodes}
import spray.routing.{RequestContext, Route, HttpService, HttpServiceActor}
import scala.concurrent.duration._
import TicketProtocol._
import akka.pattern.ask
import akka.pattern.pipe
import scala.language.postfixOps
import spray.httpx.SprayJsonSupport._

class RestInterface extends HttpServiceActor with RestApi with ActorLogging {

  def receive = runRoute(routes)
}

trait RestApi extends HttpService with ActorLogging {
  self: Actor =>

  import context.dispatcher

  implicit val timeout = Timeout(10 seconds)

  val boxOffice: ActorRef = context.actorOf(Props[BoxOffice], "boxOffice")
  val rest = respondWithMediaType(MediaTypes.`application/json`)

  def routes: Route =
    path("events") {
      put {
        entity(as[CreateEvents]) { createEvents =>
          log.debug(createEvents.toString)
          requestContext =>
            boxOffice.ask(createEvents).pipeTo(createResponder(requestContext))
        }
      } ~
      get {
        { requestContext =>
          boxOffice.ask(GetAllEvents).pipeTo(createResponder(requestContext))
        }
      }
    } ~
    path("ticket") {
      get {
        entity(as[GetTicket]) { getTicket =>
        requestContext =>
          boxOffice.ask(getTicket).pipeTo(createResponder(requestContext))
        }
      }
    } ~
    path("ticket" / Segment) { eventName =>
      requestContext =>
        boxOffice.ask(GetTicket(eventName)).pipeTo(createResponder(requestContext))
    }

  def createResponder(requestContext: RequestContext) = {
    context.actorOf(Props(classOf[Responder], requestContext))
  }
}

class Responder(requestContext: RequestContext) extends Actor with ActorLogging {

  def receive = {
    case EventsCreated =>
      dieAfterResponding(requestContext.complete(StatusCodes.OK,Message("Events Created")))
    case ErrorMessage(reason:String) =>
      dieAfterResponding(requestContext.complete(StatusCodes.NotFound,ErrorMessage(reason)))
    case CurrentEvents(events:List[Event]) =>
      dieAfterResponding(requestContext.complete(StatusCodes.OK,events))
    case SoldOut =>
      dieAfterResponding(requestContext.complete(StatusCodes.NotFound,ErrorMessage("Sold Out")))
    case ticket:Ticket  =>
      dieAfterResponding(requestContext.complete(StatusCodes.OK,ticket))
  }

  def dieAfterResponding(f: Unit): Unit = {
    self ! PoisonPill
  }
}