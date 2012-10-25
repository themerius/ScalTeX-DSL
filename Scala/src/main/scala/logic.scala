package scaltex.logic

import scala.collection.mutable.ListBuffer
import play.api.libs.json._

trait Entity {
  val id: Int = Tray.tray.length
  def json: String
}

class Heading (heading: String, h: String = "h1") extends Entity {

  val number = getNumber
  Tray.add(this)

  def json = Json.toJson(
    Map(
      "templateId" -> Json.toJson("heading"),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(this.id),
          "heading" -> Json.toJson(this.heading),
          "h" -> Json.toJson(this.h),
          "number" -> Json.toJson(this.number)
        )
      )
    )
  ).toString

  def getNumber = Tray.tray.filter {
    (x: Entity) => {
      x match {
        case x: Heading => true
        case _ => false
      }
    }
  }.length + 1
}

class Text (txt: String) extends Entity {

  Tray.add(this)

  def json = Json.toJson(
    Map(
      "templateId" -> Json.toJson("text_1110"),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(this.id),
          "text" -> Json.toJson(this.txt)
        )
      )
    )
  ).toString
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