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

    "create watermarks for books" in {
      val waterMarker = system.actorOf(Props[WaterMarker], name = "watermark-books")
      val document = Book("title", "author", "content", Enum.TopicType.Business)
      waterMarker ! WaterMarkDocument(document, this.self)
      expectMsgPF(hint = "no WaterMarkedDocument") {
        case WaterMarkedDocument(doc) => assert(doc.watermark.isDefined)
      }

    }

    "create watermarks for journals" in {
      val waterMarker = system.actorOf(Props[WaterMarker], name = "watermark-journals")
      val document = Journal("title", "author", "content")
      waterMarker ! WaterMarkDocument(document, this.self)
      expectMsgPF(hint = "no WaterMarkedDocument") {
        case WaterMarkedDocument(doc) => assert(doc.watermark.isDefined)
      }

    }

  }


}
