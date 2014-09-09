import akka.actor.{ActorRef, Props, ActorSystem}
import akka.util.Timeout
import akka.testkit.{TestKit, ImplicitSender}
import springer.watermark.actor.WaterMarkerActor.{GetDocumentMessage, GetWatermarkingStatusMessage, WaterMarkedDocumentMessage, WaterMarkDocumentMessage}
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

    def pleaseWait {
      Console.println("-----------------------------------------------------")
      Console.println("Please wait... I am receiving messages...")
      Console.println("-----------------------------------------------------")
    }
    def expectMessages {
      pleaseWait
      receiveWhile(max = (10 seconds), idle = (5 seconds), messages = 30) {
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
      val nanos = System.nanoTime()
      val document = Book("title-" + nanos, "author-" + nanos, "book content-" + nanos, Ticket(TicketStatus.NONE), Enum.TopicType.Business)
      waterMarker ! WaterMarkDocumentMessage(document, this.self)
      pleaseWait
      receiveWhile(max = (10 seconds), idle = (5 seconds), messages = 30) {
        case WaterMarkedDocumentMessage(doc) => Console.println(doc); assert(doc.watermark.isDefined)
        case WatermarkingStatusMessage(doc: Document) => // ignore here
      }
    }

    "create watermarks for books and check topic" in {
      val waterMarker = system.actorOf(Props[WaterMarkerActor], name = "watermark-book-check-topics")
      val nanos = System.nanoTime()
      val document = Book("title-" + nanos, "author-" + nanos, "book content-" + nanos, Ticket(TicketStatus.NONE), Enum.TopicType.Media)
      waterMarker ! WaterMarkDocumentMessage(document, this.self)
      pleaseWait
      receiveWhile(max = (10 seconds), idle = (10 seconds), messages = 30) {
        case WaterMarkedDocumentMessage(doc) => doc match {
          case book: Book => Console.println(book); assert(book.topic != Enum.TopicType.NONE)
          case _ => fail("Document is not a book: " + doc)
        }
        case WatermarkingStatusMessage(doc: Document) => // ignore here
      }
    }

    "create watermarks for journals" in {
      val waterMarker = system.actorOf(Props[WaterMarkerActor], name = "watermark-journal")
      val nanos = System.nanoTime()
      val document = Journal("title-" + nanos, "author-" + nanos, "journal content-" + nanos, Ticket(TicketStatus.NONE))
      waterMarker ! WaterMarkDocumentMessage(document, this.self)
      pleaseWait
      receiveWhile(max = (20 seconds), idle = (5 seconds), messages = 30) {
        case WaterMarkedDocumentMessage(doc) => doc match {
          case journal: Journal => Console.println(journal); assert(journal.watermark != None)
          case _ => fail("Document is not a journal: " + doc)
        }
        case WatermarkingStatusMessage(doc: Document) => // ignore here
      }
    }

    "get document and watermark status by ticket id" in {
      val systemW = ActorSystem("WatermarkActorSystem")
      var waterMarker = systemW.actorOf(Props[WaterMarkerActor], name = "get-status-by-ticketid")
      val documentA = Journal("titleA", "author", "content", Ticket(TicketStatus.NONE))
      val documentB = Book("titleB", "author", "content", Ticket(TicketStatus.NONE), Enum.TopicType.Science)
      val documentC = Journal("titleC", "author", "content", Ticket(TicketStatus.NONE))
      val documentD = Book("titleD", "author", "content", Ticket(TicketStatus.NONE), Enum.TopicType.Media)
      waterMarker ! WaterMarkDocumentMessage(documentA, this.self)
      waterMarker ! WaterMarkDocumentMessage(documentB, this.self)
      waterMarker ! WaterMarkDocumentMessage(documentC, this.self)
      waterMarker ! WaterMarkDocumentMessage(documentD, this.self)
      expectMessages
      waterMarker ! GetDocumentMessage(documentC.ticket, this.self)
      expectMsgPF(hint = "Look for document") {
        case doc: Document => doc match {
          case document: Document => {
            Console.GREEN
            Console.printf("Found document %s for ticket id %s\n", document.title, document.ticket.id)
            Console.BLACK
            assert(doc.title == "titleC")
          }
          case _ => fail("Document not finished or not found " + doc.title)
        }
      }
    }
  }
}
