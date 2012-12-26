package scaltex.buildtools

import scala.collection.mutable.{HashMap, ListBuffer}
import scaltex.util.DynamicObject
import play.api.libs.json._

object EntityIdCount {
  var id = 0
  def getId: Int = {
    id += 1
    return id
  }
}

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

abstract class Areal extends DynamicObject with AppendPoint {
  // (implicit builder: Builder)
  val companion: Tray[EntityPageBase]
  val defaultPage: Page
  var currentPage: Page = _
  def setCurrentPage (p: Page) = currentPage = p
  def getCurrentPage = currentPage
  implicit val areal = this
  val ++ : EntityBinding  // the compiler doesn't accept ++:

  def addToList (e: EntityPageBase) = companion.add(e)

  val change = this
  def newpage: Areal
  def page_to (p: Page): Areal
}

trait AppendPoint {
  var appendPoint: String
}

trait AppendPoints {
  val appendPoints: List[Map[String, String]]
}

trait EntityPageBase {
  val templateId: String
  def toJson: String
}

trait Entity extends EntityPageBase with AppendPoint {
  val id: Int = EntityIdCount.getId
  var areal: Areal = _
  var refName: String = _
  def $ (name: String) = {
    refName = name
    registerReference
    this
  }
  def bindToAreal (a: Areal) = {
    a.addToList(this)
    areal = a
  }
  def registerReference = {
    if (areal != null && refName != null) areal.updateDynamic(refName)(this)
  }
}

trait Page extends EntityPageBase with AppendPoints {
  def toJson = Json.toJson(
    Map(
      "template" -> Json.toJson(templateId),
      "appendPoints" -> Json.toJson(appendPoints)
    )
  ).toString
}

trait EntityBinding {
  var nextRefName: String = _
  def $ (refName: String): EntityBinding
  def registerReference (e: Entity) = {
    if (this.nextRefName != null) {
      e.$(nextRefName)
      this.nextRefName = null
    }
  }
}

class Generator (areal: Areal) {

  def getEntityPageList: List[EntityPageBase] = {
    if (!areal.companion.get(0).isInstanceOf[Page])
      areal.defaultPage :: areal.companion.get
    else
      areal.companion.get
  }

  def generate: String = {
    val entities = getEntityPageList

    var ret = "var " + areal.appendPoint + " = ["
    ret += "{pageType: \"" + entities.head.templateId + "\",entities: ["

    for (entity <- entities.tail) {
      if (entity.isInstanceOf[Page]) {
        ret += "]},"  // close the page before
        ret += "{pageType: \"" + entity.templateId + "\",entities: ["
      } else {
        ret += entity.toJson + ","
      }
    }
    ret += "]}];"
    return ret
  }

}

trait Builder