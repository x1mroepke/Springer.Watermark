package springer.watermark.model

/**
 * Created by michael.roepke on 04.09.2014.
 */

object TopicType extends Enumeration {
  type TopicType = Value
  val Business, Science, Media, NONE = Value
}

object TicketStatus extends Enumeration {
  type TicketStatus = Value
  val Processing, Finished, Failed, Unknown, NONE = Value
}



