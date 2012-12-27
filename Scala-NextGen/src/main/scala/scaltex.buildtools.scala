package scaltex.buildtools

import scala.collection.mutable.{HashMap, ListBuffer}
import scaltex.util.DynamicObject
import play.api.libs.json._
import java.io.{OutputStreamWriter, FileOutputStream}

object EntityIdCount {
  var id = 0
  def getId: Int = {
    id += 1
    return id
  }
}

trait TemplateStock {
  def headerTemplate (title: String) = s"<html><title>$title</title>"
  var footerTemplate = "</html>"

  val entityStock = new HashMap[String,String]()
  def addTemplateEntity(name: String, entity: String) = {
    entityStock += {name -> entity}
  }
  def getTemplateEntity(name: String) = entityStock(name)
  def entityTemplate = entityStock.view map {
    case (key, value) => value
  } mkString("")
}

trait Tray[T] {
  val tray = ListBuffer.empty[T]

  def add (t: T) = tray += t
  def get: List[T] = tray.toList
}

abstract class Areal (implicit val builder: Builder) extends DynamicObject with AppendPoint {
  if (builder != null)
    builder.addToList(this)

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
  val officialName: String
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

trait Builder extends TemplateStock {
  val companion: Tray[Areal]
  implicit val builder = this
  val allPages: List[Page]
  def addToList (a: Areal) = companion.add(a)

  def generateJsEntityInstances: String =
    (for (areal <- companion.get) yield (new Generator(areal)).generate).mkString("")

  def generateJsPageFactory: String = {
    var ret = "var pageFactory = new scaltex.PageFactory({"
    for (page <- allPages) {
      ret += page.officialName + ": "
      ret += page.toJson + ","
    }
    ret += "});"
    ret
  }

  def generateHeader =
    if (documentName != null)
      headerTemplate(documentName)
    else
      headerTemplate("Document")

  def generateFooter = footerTemplate

  def generateJsSpecialEntities: String

  def generateJsArealSetup: String = {
    var ret = ""
    for (areal <- companion.get) {
      ret += "var " + areal.appendPoint + "_areal = new scaltex.Areal("
      ret += "\"" + areal.appendPoint + "\", " + areal.appendPoint + ", "
      ret += "pageFactory, specialEntities);"
      ret += areal.appendPoint + "_areal" + ".generateEntities();"
      ret += areal.appendPoint + "_areal" + ".renderEntities();"
      ret += areal.appendPoint + "_areal" + ".mountEntitiesToConstructionArea();"
    }
    ret += "window.onload = function () {"
    for (areal <- companion.get) {
      ret += areal.appendPoint + "_areal" + ".moveEntitiesToNewPages();"
      ret += areal.appendPoint + "_areal" + ".destructConstructionAreas();"
    }
    ret += "};"
    return ret
  }

  def generateArealAppendPoints: String = {
    var ret = ""
    for (areal <- companion.get) {
      ret += "<div id=\"" + areal.appendPoint + "\"></div>"
    }
    ret
  }

  val set = this
  var documentName: String = _
  def document_name (name: String) = documentName = name

  def build: String = {
    generateHeader + generateArealAppendPoints +
    entityTemplate + "<script>" + generateJsEntityInstances +
    generateJsSpecialEntities + generateJsPageFactory +
    generateJsArealSetup + "</script>" + generateFooter
  }

  def write (destination: String) = {
    val out = new OutputStreamWriter(new FileOutputStream(destination), "UTF-8")
    out.append(build)
    out.close
  }
}