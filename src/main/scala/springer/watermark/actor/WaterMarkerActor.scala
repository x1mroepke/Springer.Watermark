package springer.watermark.actor
import akka.actor._
import akka.event.LoggingReceive
import springer.watermark.actor.WaterMarkerActor._
import springer.watermark.actor.WaterMarkingStatusActor.{WatermarkingStatusMessage, SetWatermarkingStatusMessage, GetWatermarkingStatusMessage}
import springer.watermark.exception.UnknownDocumentTypeException
import springer.watermark.model._
import scala.collection.immutable.HashMap
import scala.language.postfixOps
import scala.util.control.Exception


/**
 * actor messages for creating and monitoring watermarkin
 */
object WaterMarkerActor {

  case class WaterMarkDocumentMessage(document: Document, handler: ActorRef)

  case class WaterMarkedDocumentMessage(document: Document)

  case class GetWatermarkStatusMessage(ticket: Ticket, handler: ActorRef)

  case class GetDocumentMessage(ticket: Ticket, handler: ActorRef)

  case class DocumentNotFinished(document: Document, handler: ActorRef)
}

/**
 * creates watermarked documents and getting status about processing
 *
 */
class WaterMarkerActor
  extends Actor
  with ActorLogging {

  import scala.concurrent.duration._

  override def preStart() {
    super.preStart()
    context.setReceiveTimeout(20 seconds)
  }

  val mapOfDocuments = Map[String, Document]()

  val waterMarkingStatusActor = context.actorOf(Props[WaterMarkingStatusActor],"watermarkingStatus")

  def createWaterMark(document: Document): WatermarkSignature = {
    mapOfDocuments + document.ticket.id -> document

      val ws = WatermarkSignature(document.content, document.title, document.author)
      try {
        waterMarkingStatusActor ! SetWatermarkingStatusMessage(document, Enum.TicketStatus.NONE, sender)
        ws.preProcessing
        waterMarkingStatusActor ! SetWatermarkingStatusMessage(document, Enum.TicketStatus.Processing, sender)
        ws.processing
        ws.postProcessing
        waterMarkingStatusActor ! SetWatermarkingStatusMessage(document, Enum.TicketStatus.Finished, sender)
        ws
      }
      catch {
        case ex: Exception => { waterMarkingStatusActor ! SetWatermarkingStatusMessage(document, Enum.TicketStatus.Failed, sender); ws }
      }
  }

  override def receive: Actor.Receive = LoggingReceive {
    case WaterMarkDocumentMessage(document, handler) =>
      handler ! WaterMarkedDocumentMessage(document.copy(createWaterMark(document)))
    case GetWatermarkingStatusMessage(ticket, mapOfDocuments, handler) => {
      waterMarkingStatusActor ! GetWatermarkingStatusMessage(ticket, mapOfDocuments, handler)
    }
    case GetDocumentMessage(ticket, handler) => {
       mapOfDocuments.get(ticket.id) match {
        case doc: Document => if (doc.ticket.ticketStatus == Enum.TicketStatus.Finished) handler ! doc
        case _ => handler ! DocumentNotFinished

      }
    }
  }
}

object WaterMarkingStatusActor
{
  var mapOfDocuments = Map[String, Document]()

  case class GetWatermarkingStatusMessage(ticketId: String, mapOfDocuments: Map[String, Document], handler: ActorRef)
  case class SetWatermarkingStatusMessage(document: Document, ticketStatus: Enum.TicketStatus.TicketStatus, handler: ActorRef)
  case class WatermarkingStatusMessage(doc: Option[Document], handler: ActorRef )
}

class WaterMarkingStatusActor
  extends Actor
  with ActorLogging {



  override def receive: Actor.Receive = LoggingReceive {
    case GetWatermarkingStatusMessage(ticketId, mapOfDocuments, handler) =>
    {
      val doc = mapOfDocuments.get(ticketId)
      handler ! WatermarkingStatusMessage(doc, handler)
    }
    case SetWatermarkingStatusMessage(document, ticketStatus, sender) => document.ticket.ticketStatus = ticketStatus

  }
}
