package springer.watermark.model

case class Ticket(id: String)

object Ticket {

  sealed trait TicketStatus

  case object Processing extends TicketStatus

  case object Finished extends TicketStatus

  case object Failed extends TicketStatus

  case object UnknownStatus extends TicketStatus

  case class Status(id: String, status: TicketStatus)

}