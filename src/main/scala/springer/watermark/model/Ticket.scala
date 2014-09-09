package springer.watermark.model

import java.security.MessageDigest

import akka.util.HashCode
import springer.watermark.model.Enum.TicketStatus.TicketStatus

/**
 * Ticket class for finding documents and getting watermarking status
 */
case class Ticket(var ticketStatus: TicketStatus, id: String = System.nanoTime.toString() )
