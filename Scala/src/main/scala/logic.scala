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
    var h3Count = 0
    val headings = Tray.tray.filter( _.isInstanceOf[Heading])
    for (heading <- headings) {
      heading match {
        case Heading(_, "h1") => {
          h1Count += 1
          h2Count = 0
        }
        case Heading(_, "h2") => {
          h2Count += 1
          h3Count = 0
        }
        case Heading(_, "h3") => h3Count += 1
        case _ => throw new Exception("This should not happen.")
      }
    }

    var res = ""
    if (this.h == "h1") res += h1Count
    if (this.h == "h2") res += h1Count + "." + h2Count
    if (this.h == "h3") res += h1Count + "." + h2Count + "." + h3Count
    res
  }
}

abstract class Text(txt: () => String) extends Entity {
  Tray.add(this)
  var newline: List[Boolean] = List()
}

class Figure (src: String, desc: String) extends Entity {

  Tray.add(this)
  val number = getNumber

  def json = Json.toJson(
    Map(
      "templateId" -> Json.toJson("figure_1100"),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(this.id),
          "src" -> Json.toJson(this.src),
          "description" -> Json.toJson(this.desc),
          "number" -> Json.toJson(this.number)
        )
      )
    )
  ).toString

  def getNumber: String = Tray.tray.filter(_.isInstanceOf[Figure]).length.toString
}

object Tray {
  val tray = ListBuffer.empty[Entity]
  def add(e: Entity) = tray += e
  def get = tray.toList
}

class Tray {
  def entities = Tray.get

  def unpack = {
    var ret = ""
    for (entity <- entities)
      ret += entity.json + ","
    ret
  }
}