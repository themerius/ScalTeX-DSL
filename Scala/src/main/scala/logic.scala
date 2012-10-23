package scaltex.logic

import scala.collection.mutable.ListBuffer

trait Entity {
  def json: String
}

class Heading(heading: String) extends Entity {
  def json = this.heading
}
class Text(txt: String) extends Entity {
  def json = this.txt
}

object Tray {
  val tray = ListBuffer.empty[Entity]
  def add(e: Entity) = tray += e
  def get = tray.toList

  def headings = "Headings"  // filter for headings
  def texts = "Texts"  // filter for texts
}

class Tray {
  def entities = Tray.get
}