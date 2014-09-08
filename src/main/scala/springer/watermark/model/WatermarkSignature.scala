package springer.watermark.model

/**
 * Class holding the watermark for documents
 */
case class WatermarkSignature(content: String,
                             title: String,
                             author: String,
                             topic: Option[Enum.TopicType.TopicType] = None
                              )
