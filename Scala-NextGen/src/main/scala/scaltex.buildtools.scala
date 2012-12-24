package scaltex.buildtools

import scala.collection.mutable.{HashMap, ListBuffer}

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

abstract class Areal (implicit builder: Builder) {
  val appendPoint: String
  val defaultPage: Page
  val ++ : Binding
  implicit val areal = this

  def addEntity (e: Entity)

  val change = this
  def page_to (p: Page)
  def page_numbering_style_to (p: Page)
}

trait Page

trait Entity

trait Binding

trait Builder