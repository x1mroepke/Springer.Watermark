package springer.watermark.actor

import akka.actor._
import akka.event.LoggingReceive
import springer.watermark.actor.WaterMarker._
import springer.watermark.exception.UnknownDocumentTypeException
import springer.watermark.model._

/**
 * actor messages for creating and monitoring watermarkin
 */
object WaterMarker {

  case class WaterMarkDocument(document: Document, handler: ActorRef)

  case class WaterMarkedDocument(document: Document)

  case class GetWatermarkStatus(ticket: Ticket, handler: ActorRef)

  case object NoDocumentReceived

  case class GetDocument(ticket: Ticket)

}

/**
 * creates watermarked documents and getting status about processing
 *
 */
class WaterMarker
  extends Actor
  with ActorLogging {

  import scala.concurrent.duration._

  override def preStart() {
    super.preStart()
    context.setReceiveTimeout(20 seconds)
  }

  def createWaterMark(document: Document): WatermarkSignature = document match {
    case book: Book => WatermarkSignature(book.content, book.title, book.author, Some(book.topic))
    case journal: Journal => WatermarkSignature(journal.content, journal.title, journal.author)
    case _ => throw new UnknownDocumentTypeException()
  }

  override def receive: Actor.Receive = LoggingReceive {
    case WaterMarkDocument(document, handler) =>
      handler ! WaterMarkedDocument(document.copy(createWaterMark(document)))
    case GetWatermarkStatus(ticket, handler) =>
    case msg: GetDocument =>
  }
}