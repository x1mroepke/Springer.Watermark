package springer.watermark.model

import java.security.MessageDigest

trait Document {
  def copy(watermark_arg: WatermarkSignature): Document = { watermark = watermark_arg; this }
  val ticketId: String = MessageDigest.getInstance("MD5").digest((title + content).getBytes).map("%02X".format(_)).mkString
  val title: String
  val author: String
  val content: String
  var watermark: WatermarkSignature = new WatermarkSignature("undefined","undefined","undefined", Some(TopicType.NONE))
}




