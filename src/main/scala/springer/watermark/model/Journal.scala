package springer.watermark.model

/**
 * Journal class derived from base class document
 */
case class Journal(val title: String, val author: String, val content: String, val ticket: Ticket) extends Document
