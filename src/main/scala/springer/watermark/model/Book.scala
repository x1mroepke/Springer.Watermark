package springer.watermark.model

/**
 * Book class derived from base class document
 */
case class Book(title: String, author: String, content: String, ticket: Ticket, typeOfTopic: Enum.TopicType.TopicType) extends Document {
   val topic: Enum.TopicType.TopicType = typeOfTopic
 }
