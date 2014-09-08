package springer.watermark.model

import java.security.MessageDigest

import akka.util.HashCode
import springer.watermark.model.Enum.TicketStatus.TicketStatus

case class Ticket(ticketStatus: TicketStatus, id: String = MessageDigest.getInstance("MD5").digest(HashCode.hash(10,1000).toString().getBytes()).map("%02X".format(_)).mkString )
