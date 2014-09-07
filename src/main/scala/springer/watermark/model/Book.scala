package springer.watermark.model

/**
 * Created by michael.roepke on 04.09.2014.
 */

import springer.watermark.model.TopicType.TopicType

case class Book(title: String, author: String, content: String, typeOfTopic: TopicType) extends Document {
   val topic: TopicType = TopicType.NONE
 }
