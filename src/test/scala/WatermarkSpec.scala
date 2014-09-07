import akka.actor.{Props, ActorSystem}
import akka.util.Timeout
import akka.testkit.{TestKit, ImplicitSender}
import springer.watermark.actor.{WaterMarker, ProcessorActor}
import springer.watermark.model.Enum.TopicType
import scala.concurrent.Await
import scala.concurrent.duration._
import org.scalatest._
import WaterMarker._
import springer.watermark.model._

//import system.dispatcher
import ProcessorActor._


/**
 * Akka system Tests.
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

  implicit val timeout = Timeout(3 seconds)

  "WatermarkActor" should {

    /*
    "create different tickets for different Documents" in {
      val watermarkerProzess = system.actorOf(Props[WaterMarker], "differnt-docs")
      val documentA: Document = Journal("title", "book", "author", "content")
      val documentB: Document = Journal("different title", "book", "author", "different content")
      val result = Await.result(
        (watermarkerProzess ? Sign(documentA)) zip (watermarkerProzess ? Sign(documentB)) map {
          case (SigningTicket(ta), SigningTicket(tb)) => ta.id != tb.id
          case _ => false
        }, 5 seconds)
      assert(result, "ticket number is the same")
    }
*/

    "create watermarks for books" in {
      val waterMarker = system.actorOf(Props[WaterMarker], name = "book-with-topic")
      val document = Book("title", "author", "content", Enum.TopicType.Business)
      waterMarker ! WaterMarkDocument(document, this.self)
      expectMsgPF(hint = "no WaterMarkedDocument") {
        case WaterMarkedDocument(doc) => assert(doc.watermark.isDefined)
           //Assertions.assert(!doc.watermark != None,"could not watermark document")
          //assert(doc.waterMark.isDefined)
          //assert(doc.waterMark.get.topic.isEmpty)
      }

    }

    /*
    "create different tickets for different Documents" in {
      val processor = system.actorOf(Props[WaterMarker], "ticket-generator")
      val documentA: Document = Book("bookTitle", "author", "Book", TopicType.Media)
      val documentB: Document = Journal("journalTitle", "author", "Journal")
      val result = Await.result(
        (processor ? Sign(documentA)) zip (processor ? Sign(documentB)) map {
          case (SigningTicket(ta), SigningTicket(tb)) => ta.id != tb.id
          case _ => false
        }, 5 seconds)
      assert(result, "ticket number is the same")
    }
*/

    /*
    "create a watermark" in {
      val processor = system.actorOf(Props[WaterActor], "create-wartermark")
      val documentA: Document = Document("title", "book", "author", "content")

      // create Ticket
      val ticket: Ticket = Await.result(processor ? Sign(documentA) map {
        case SigningTicket(tckt) => tckt
        case _ => throw new IllegalStateException("not Ticket :(")
      }, 5 seconds)

      // wait untill its ready for download
      awaitCond(Await.result(processor ? GetSigningStatus(ticket) map {
        case SigningStatus(Status(_, Finished)) => true
        case _ => false
      }, 5 seconds), 20 seconds, 2 seconds, "processor did not finish the ticket")

      // load Document
      val document: Document = Await.result(processor ? GetDocument(ticket) map {
        case SignedDocument(doc) => doc
        case _ => throw new IllegalStateException("no document received")
      }, 5 seconds)

      // check if waterMark exist
      assert(document.waterMark.isDefined)
    }

    "create watermarks for journals with topics" in {
      val waterMarker = system.actorOf(Props[WaterMarker], name = "journal-with-topic")
      import WaterMarker._
      val document = Document("title", "journal", "author", "content", Some("topic"))
      waterMarker ! WaterMarkDocument(document, this.self)
      expectMsgPF(hint = "no WaterMarkedDocument") {
        case WaterMarkedDocument(doc) => assert(doc.waterMark.isDefined)
      }
    }

    "not create watermarks for journals without topics" in {
      val waterMarker = system.actorOf(Props[WaterMarker], name = "journal-without-topic")
      import WaterMarker._
      val document = Document("title", "journal", "author", "content")
      waterMarker ! WaterMarkDocument(document, this.self)
      expectMsgPF(hint = "no WaterMarkedDocument") {
        case WaterMarkedDocument(doc) => assert(doc.waterMark.isEmpty)
      }
    }
*/

  }


}
