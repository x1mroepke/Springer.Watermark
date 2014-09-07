package springer.watermark.model

import springer.watermark.model.TopicType.TopicType

/**
 * Created by michael.roepke on 04.09.2014.
 */
case class WatermarkSignature(content: String,
                             title: String,
                             author: String,
                             topic: Option[TopicType]
                              )
