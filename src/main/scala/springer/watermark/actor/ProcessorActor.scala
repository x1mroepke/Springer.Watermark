package springer.watermark.actor

import akka.actor._
import akka.event.LoggingReceive
import springer.watermark.exception.UnknownDocumentTypeException
import springer.watermark.model.Enum.TopicType
import springer.watermark.model.Ticket.Status
import springer.watermark.model._
import WaterMarker._

/**
 * signing acion messages
 */
object ProcessorActor {

  case class Sign(document: Document)

  case class SigningTicket(ticket: Ticket)

  case class GetSigningStatus(ticket: Ticket)

  case class SigningStatus(status: Status)

  case class GetDocument(ticket: Ticket)

  case class SignedDocument(document: Document)

  case object NoDocument

}

/**
 * messages
 */
object WaterMarker {

  case class WaterMarkDocument(document: Document, handler: ActorRef)

  case class WaterMarkedDocument(document: Document)

}

/**
 * creates watermarks
 *
 * The logic for creating a watermark on a document.
 */
class WaterMarker
  extends Actor
  with ActorLogging {


  def createWaterMark(document: Document): WatermarkSignature = document match {
    case book: Book => WatermarkSignature(book.content, book.title, book.author, Some(book.topic))
    case journal: Journal => WatermarkSignature(journal.content, journal.title, journal.author, Some(TopicType.NONE))
    case _ => throw new UnknownDocumentTypeException()
  }

  override def receive: Actor.Receive = LoggingReceive {
    case WaterMarkDocument(document, handler) =>
      handler ! WaterMarkedDocument(document.copy(createWaterMark(document)))
  }
}



/*
def createWaterMark(document: Document): WatermarkSignature = document match {
  case book: Book => WatermarkSignature(book.content, book.title, book.author, Some(book.typeOfTopic))
  case journal: Journal => WatermarkSignature(journal.content, journal.title, journal.author)
  case _ => throw new UnknownDocumentTypeException()
}
*/
/*
def createWaterMark(book: Book): WatermarkSignature = WatermarkSignature(book.content, book.title, book.author, Some(book.topic))
def createWaterMark(journal: Journal): WatermarkSignature = WatermarkSignature(journal.content, journal.title, journal.author)
*/

/*
class HelloActor extends Actor {
  def receive = {
    case "hello" => println("hello back at you")
    case _       => println("huh?")
  }
}

object Main extends App {
  val system = ActorSystem("HelloSystem")
  // default Actor constructor
  val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
  helloActor ! "hello"
  helloActor ! "buenos dias"
}
*/