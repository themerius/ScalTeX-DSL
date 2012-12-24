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

