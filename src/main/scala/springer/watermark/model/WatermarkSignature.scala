package springer.watermark.model

import scala.concurrent.duration._
/**
 * Class holding the watermark for documents
 */
case class WatermarkSignature(content: String,
                             title: String,
                             author: String,
                             topic: Option[Enum.TopicType.TopicType] = None
                              )
{

  def preProcessing: WatermarkSignature = { 1 second; this }
  def processing: WatermarkSignature = { 1 second; this }
  def postProcessing: WatermarkSignature = { 1 second; this }
}
