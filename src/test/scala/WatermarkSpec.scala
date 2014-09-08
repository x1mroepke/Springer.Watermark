import akka.actor.{Props, ActorSystem}
import akka.util.Timeout
import akka.testkit.{TestKit, ImplicitSender}
import springer.watermark.actor.WaterMarker.{GetWatermarkStatus, WaterMarkedDocument, WaterMarkDocument}
import springer.watermark.actor._
import springer.watermark.model.Enum.TicketStatus
import scala.concurrent.duration._
import org.scalatest._
import springer.watermark.model._

//import system.dispatcher

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

    "create watermarks for books" in {
      val waterMarker = system.actorOf(Props[WaterMarker], name = "watermark-books")
      val document = Book("title", "author", "content", Ticket(TicketStatus.Processing), Enum.TopicType.Business)
      waterMarker ! WaterMarkDocument(document, this.self)
      expectMsgPF(hint = "no WaterMarkedDocument") {
        case WaterMarkedDocument(doc) => Console.println(doc); assert(doc.watermark.isDefined)
      }

    }

    "create watermarks for journals" in {
      val waterMarker = system.actorOf(Props[WaterMarker], name = "watermark-journals")
      val document = Journal("title", "author", "content", Ticket(TicketStatus.NONE))
      waterMarker ! WaterMarkDocument(document, this.self)
      expectMsgPF(hint = "no WaterMarkedDocument") {
        case WaterMarkedDocument(doc) => Console.println(doc); assert(doc.watermark.isDefined)
      }

    }

    "get watermark status by ticketid" in {
    }

    "get documents by ticketid" in {
    }

  }


}
