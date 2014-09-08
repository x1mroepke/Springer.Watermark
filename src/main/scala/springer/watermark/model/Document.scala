package springer.watermark.model

import java.security.MessageDigest

import springer.watermark.model.Enum.TopicType

/**
 * Base class document for all subtypes of document types which can be watermarked
 */
trait Document {
  def copy(watermark_arg: WatermarkSignature): Document = { watermark = Some(watermark_arg); this }
  val title: String
  val author: String
  val content: String
  var watermark: Option[WatermarkSignature] = None
  val ticket: Ticket
}




