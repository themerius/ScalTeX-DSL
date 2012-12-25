package scaltex.buildtools

import scala.collection.mutable.{HashMap, ListBuffer}
import scaltex.util.DynamicObject

class TemplateStock {
  var headerTemplate = "<html>"
  var footerTemplate = "</html>"

  val entityStock = new HashMap[String,String]()
  def addTemplateEntity(name: String, entity: String) = {
    entityStock += {name -> entity}
  }
  def getTemplateEntity(name: String) = entityStock(name)
  def entityTemplate = entityStock.view map {
    case (key, value) => value
  } mkString("\n\n\n")
}

trait Tray[T] {
  val tray = ListBuffer.empty[T]

  def add (t: T) = tray += t
  def get: List[T] = tray.toList
}

abstract class Areal (implicit builder: Builder) extends DynamicObject {
  val appendPoint: String
  val defaultPage: Page
  val ++ : EntityBinding
  implicit val areal = this

  def addEntity (e: Entity)

  val change = this
  def page_to (p: Page)
  def page_numbering_style_to (p: Page)
}

trait AppendPoint {
  var appendPoint: String
}

trait AppendPoints {
  val appendPoint: List[String]
}

trait EntityPageBase {
  val templateId: String
  def toJson: String
}

trait Entity extends EntityPageBase with AppendPoint {
  var refName: String = _
  def $ (name: String) = refName = name
  def bindToAreal (areal: Areal) = areal.addEntity(this)
}

trait Page extends EntityPageBase with AppendPoints

trait EntityBinding {
  def addReference (obj: Areal, ety: Entity) = obj.updateDynamic(ety.refName)(ety)
}

trait Builder