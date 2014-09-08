package springer.watermark.model

import springer.watermark.model.Enum.TicketStatus.TicketStatus

case class WatermarkingStatus(ticketId: String, status: TicketStatus)
