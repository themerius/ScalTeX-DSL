package scaltex.logic

import scala.collection.mutable.ListBuffer
import play.api.libs.json._

trait Entity {
  val id: Int = Tray.tray.length
  def json: String
}

case class Heading (heading: String, h: String = "h1") extends Entity {

  Tray.add(this)
  val number = getNumber

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

  def getNumber: String = {
    var h1Count = 0
    var h2Count = 0
    val headings = Tray.tray.filter( _.isInstanceOf[Heading])
    for (heading <- headings) {
      heading match {
        case Heading(_, "h1") => {
          h1Count += 1
          h2Count = 0
        }
        case Heading(_, "h2") => h2Count += 1
        case _ => throw new Exception("This should not happen.")
      }
    }

    var res = ""
    if (this.h == "h1") res += (h1Count).toString
    if (this.h == "h2") res += (h1Count).toString + "." + h2Count.toString

    res
  }
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