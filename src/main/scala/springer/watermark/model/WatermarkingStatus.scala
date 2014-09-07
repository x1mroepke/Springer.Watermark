package springer.watermark.model

import springer.watermark.model.Enum.TicketStatus

// import springer.watermark.model.TicketStatus

/**
 * Created by michael.roepke on 04.09.2014.
 */
case class WatermarkingStatus(ticketId: String, status: TicketStatus.TicketStatus)
