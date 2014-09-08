package springer.watermark.model

case class Journal(val title: String, val author: String, val content: String, val ticket: Ticket) extends Document
