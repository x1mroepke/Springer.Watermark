package springer.watermark.model

/**
 * Created by michael.roepke on 04.09.2014.
 */
case class WatermarkSignature(content: String,
                             title: String,
                             author: String,
                             topic: Option[Enum.TopicType.TopicType]
                              )
