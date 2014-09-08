package springer.watermark.model

case class Book(title: String, author: String, content: String, ticket: Ticket, typeOfTopic: Enum.TopicType.TopicType) extends Document {
   val topic: Enum.TopicType.TopicType = Enum.TopicType.NONE
 }
