package springer.watermark.model

import java.security.MessageDigest

import springer.watermark.model.Enum.TopicType

trait Document {
  def copy(watermark_arg: WatermarkSignature): Document = { watermark = Some(watermark_arg); this }
  val title: String
  val author: String
  val content: String
  var watermark: Option[WatermarkSignature] = None
  val ticket: Ticket
}




