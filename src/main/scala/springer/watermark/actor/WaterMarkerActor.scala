package springer.watermark.actor
import akka.actor._
import akka.event._
import springer.watermark.actor.WaterMarkerActor._
import springer.watermark.actor.WaterMarkingStatusActor.{WatermarkingStatusMessage, SetWatermarkingStatusMessage, GetWatermarkingStatusMessage}
import springer.watermark.model._
import scala.language.postfixOps



/**
 * actor messages for creating and monitoring watermarking
 */
object WaterMarkerActor {

  case class WaterMarkDocumentMessage(document: Document, handler: ActorRef)

  case class WaterMarkedDocumentMessage(document: Document)

  case class GetWatermarkStatusMessage(ticket: Ticket, handler: ActorRef)

  case class GetDocumentMessage(ticket: Ticket, handler: ActorRef)

  case class DocumentNotFinished(document: Document, handler: ActorRef)

  var mapOfDocuments = Map[String, Document]()

  def props(mapOfDocuments: Map[String, Document]) = Props(new WaterMarkingStatusActor(mapOfDocuments))

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

  val waterMarkingStatusActor = context.actorOf(WaterMarkerActor.props(mapOfDocuments),"watermarkingStatus")

  def createWaterMark(document: Document): WatermarkSignature = {

      val ws = WatermarkSignature(document.content, document.title, document.author)
      mapOfDocuments += (document.ticket.id -> document)
      log.debug("mapOfDocuments holds [{}] elements",mapOfDocuments.size)
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
    case GetWatermarkingStatusMessage(ticket, handler) => {
      waterMarkingStatusActor ! GetWatermarkingStatusMessage(ticket, handler)
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
  case class GetWatermarkingStatusMessage(ticketId: String, handler: ActorRef)
  case class SetWatermarkingStatusMessage(document: Document, ticketStatus: Enum.TicketStatus.TicketStatus, handler: ActorRef)
  case class WatermarkingStatusMessage(doc: Option[Document], handler: ActorRef )
}

class WaterMarkingStatusActor(mapOfDocuments: Map[String, Document])
  extends Actor
  with ActorLogging {



  override def receive: Actor.Receive = LoggingReceive {
    case GetWatermarkingStatusMessage(ticketId, handler) =>
    {
      val doc = mapOfDocuments.get(ticketId)
      handler ! WatermarkingStatusMessage(doc, handler)
    }
    case SetWatermarkingStatusMessage(document, ticketStatus, sender) => {
      log.debug("Set document with title [{}] to ticket status [{}]", document.title, document.ticket.ticketStatus)
      document.ticket.ticketStatus = ticketStatus
    }

  }
}
