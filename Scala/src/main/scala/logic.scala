package scaltex.logic

import scala.collection.mutable.ListBuffer

trait Entity

class Heading(heading: String) extends Entity
class Text(txt: String) extends Entity

object Tray {
  val tray = ListBuffer.empty[Entity]
  def add(e: Entity) = tray += e
  def get = tray.toList

  def headings = "Headings"  // filter for headings
  def texts = "Texts"  // filter for texts
}