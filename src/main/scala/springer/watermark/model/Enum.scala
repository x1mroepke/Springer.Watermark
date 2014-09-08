package springer.watermark.model

/**
 * General purpoes Enum class for setting states and status information
 */
object Enum {
  object TopicType extends Enumeration {
    type TopicType = Value
    val Business, Science, Media, NONE = Value
  }

  object TicketStatus extends Enumeration {
    type TicketStatus = Value
    val Processing, Finished, Failed, Unknown, NONE = Value
  }
}


