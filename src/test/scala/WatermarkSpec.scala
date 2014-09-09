import akka.actor.{ActorRef, Props, ActorSystem}
import akka.util.Timeout
import akka.testkit.{TestKit, ImplicitSender}
import springer.watermark.actor.WaterMarkerActor.{GetWatermarkingStatusMessage, WaterMarkedDocumentMessage, WaterMarkDocumentMessage}
import springer.watermark.actor.WaterMarkingStatusActor.{WatermarkingStatusMessage, SetWatermarkingStatusMessage}
import springer.watermark.actor._
import springer.watermark.model.Enum.TicketStatus
import scala.concurrent.duration._
import org.scalatest._
import springer.watermark.model._


/**
 * Watermarking functional tests.
 */
class WatermarkSpec(_system: ActorSystem)
  extends TestKit(_system)
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  def this() = this(ActorSystem("WatermarkActorTest"))

  override def afterAll() {
    TestKit.shutdownActorSystem(system)
  }

  // val timeout = Timeout(60 seconds)

  "WatermarkActor" should {

    def expectMessages {
      Console.println("-----------------------------------------------------")
      Console.println("Please wait... I am receiving messages...")
      Console.println("-----------------------------------------------------")
      receiveWhile(max = (20 seconds), idle = (5 seconds), messages = 20) {
        case WatermarkingStatusMessage(doc: Document) => {
          Console.printf("Got watermark status for doc title %s %s\n", doc.title, doc.ticket.ticketStatus);
          20
        }
        case WaterMarkedDocumentMessage(doc) => doc match {
          case book: Book => Console.println(book); 20
          case journal: Journal => Console.println(journal); 20
          case _ => fail("Unknown document type received" + doc)
        }
      }
    }

    "create watermarks for documents" in {
      val waterMarker = system.actorOf(Props[WaterMarkerActor], name = "watermark-documents")
      val document = Book("title", "author", "content", Ticket(TicketStatus.NONE), Enum.TopicType.Business)
      waterMarker ! WaterMarkDocumentMessage(document, this.self)
      expectMsgPF(hint = "Waiting for Watermarked Document") {
        case WaterMarkedDocumentMessage(doc) => Console.println(doc); assert(doc.watermark.isDefined)
        case WatermarkingStatusMessage(doc: Document) => {
          Console.printf("Got watermark status for doc title %s %s\n", doc.title, doc.ticket.ticketStatus);
        }
      }
    }

    "create watermarks for books and check topic" in {
      val waterMarker = system.actorOf(Props[WaterMarkerActor], name = "watermark-book-check-topics")
      val document = Book("title", "author", "content", Ticket(TicketStatus.NONE), Enum.TopicType.Business)
      waterMarker ! WaterMarkDocumentMessage(document, this.self)
      expectMsgPF(hint = "Waiting for Watermarked Document") {
        case WaterMarkedDocumentMessage(doc) => doc match {
          case book: Book => Console.println(book); assert(book.topic != Enum.TopicType.NONE)
          case _ => fail("Document is not a book: " + doc)
        }
        case WatermarkingStatusMessage(doc: Document) => {
          Console.printf("Got watermark status for doc title %s %s\n", doc.title, doc.ticket.ticketStatus);
        }
      }
    }

    "create watermarks for journals" in {
      val waterMarker = system.actorOf(Props[WaterMarkerActor], name = "watermark-journal")
      val document = Journal("title", "author", "content", Ticket(TicketStatus.NONE))
      waterMarker ! WaterMarkDocumentMessage(document, this.self)
      expectMsgPF(hint = "Waiting for Watermarked Document") {
        case WaterMarkedDocumentMessage(doc) => doc match {
          case journal: Journal => Console.println(journal)
          case _ => fail("Document is not a journal: " + doc)
        }
        case WatermarkingStatusMessage(doc: Document) => {
          Console.printf("Got watermark status for doc title %s %s\n", doc.title, doc.ticket.ticketStatus);
        }
      }
    }

    "get document and watermark status by ticketid" in {
      val systemW = ActorSystem("WatermarkActorSystem")
      var waterMarker = systemW.actorOf(Props[WaterMarkerActor], name = "get-status-by-ticketid")
      Console.println(waterMarker.path)
      val documentA = Journal("titleA", "author", "content", Ticket(TicketStatus.NONE))
      val documentB = Book("titleB", "author", "content", Ticket(TicketStatus.NONE), Enum.TopicType.Science)
      val documentC = Journal("titleC", "author", "content", Ticket(TicketStatus.NONE))
      val documentD = Book("titleD", "author", "content", Ticket(TicketStatus.NONE), Enum.TopicType.Media)
      waterMarker ! WaterMarkDocumentMessage(documentA, this.self)
      waterMarker ! WaterMarkDocumentMessage(documentB, this.self)
      waterMarker ! WaterMarkDocumentMessage(documentC, this.self)
      waterMarker ! WaterMarkDocumentMessage(documentD, this.self)
      expectMessages
    }
  }
}
