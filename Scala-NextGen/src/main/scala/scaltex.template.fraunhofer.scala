package scaltex.template.fraunhofer

import scaltex.buildtools._
import scaltex.buildtools.logic._
import scaltex.types.academic
import play.api.libs.json._

trait FraunhoferReportTemplate extends TemplateStock {
  override def headerTemplate (title: String) = s"""
    <!DOCTYPE html>
    <html lang="en">
    <head>
    <meta charset="utf-8" />
    <title>$title</title>
    <link rel="stylesheet" href="http://scaltex.4pple.de/academic_article_fraunhofer/css/reset.css" />
    <link rel="stylesheet" href="http://scaltex.4pple.de/academic_article_fraunhofer/css/typo.css" />
    <link rel="stylesheet" href="http://scaltex.4pple.de/academic_article_fraunhofer/css/grid.css" />
    <link rel="stylesheet" href="http://scaltex.4pple.de/academic_article_fraunhofer/css/titlepage.css" />
    <link rel="stylesheet" href="http://scaltex.4pple.de/academic_article_fraunhofer/css/toc.css" />
    <script src="https://raw.github.com/janl/mustache.js/master/mustache.js"></script>
    <script src="https://raw.github.com/themerius/ScalTeX-templates/master/scaltex/src/scaltex.js"></script>
    <script src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
    </head>
    <body>
  """

  footerTemplate = """
    </body>
    </html>
  """

  addTemplateEntity (
    "main entities",
    """
    <script id="page" type="text/template">
    <div class="pageA4">
      <div id="{{{appendPoint_content}}}" class="layoutGrid"></div>
      <div id="{{{appendPoint_footer}}}"></div>
    </div>
    </script>

    <script id="pagina" type="text/template">
    <div class="layoutGridBottom pagina">
      <div class="row line">
        <div class="col3">
          <p>{{institutName}}</p>
        </div>
        <div class="col1 right">
         <p>{{pageNr}}</p>
        </div>
        <div class="row-end">&nbsp;</div>
    </div>
    </script>

    <script id="text_1110" type="text/template">
    <div class="row">
      <div class="col3">
        <p>{{{text}}}</p>
        {{#newline}}
        </br>
        {{/newline}}
      </div>
      <div class="row-end">&nbsp;</div>
    </div>
    </script>

    <script id="heading" type="text/template">
    <div class="row">
      <div class="col4"><{{h}}>{{number}}</br>{{heading}}</{{h}}></br></br></div>
      <div class="row-end">&nbsp;</div>
    </div>
    </script>

    <script id="figure_1100" type="text/template">
    <div class="row topSpace bottomSpace">
      <div class="col2">
        <img class="scaling" src="{{src}}" alt="{{description}}" />
      </div>
      <div class="col1">  <!-- style="margin-top: parentHight - self.hight px" -->
        <strong>Abbildung {{number}}: {{{description}}}</strong>
      </div>
      <div class="row-end">&nbsp;</div>
    </div>
    </script>
    """)

  addTemplateEntity (
    "toc",
    """
    <script id="toc_heading" type="text/template">
    <div class="row tocHeading">
      <div class="col4"><h1>{{heading}}</h1></div>
      <div class="row-end">&nbsp;</div>
    </div>
    </script>

    <script id="toc_mainSection" type="text/template">
    <div class="row tocMainSection">
      <div class="col3 tocLine">
        <span class="tocTitle tocFloatingDots">{{nr}}</br>{{title}} . . . . . . .
          . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
          . . . . . . . . . . . . . . . . . . . . . . . . . . . </span>
        <span class="tocNumbering"></br>&nbsp;{{page}}</span>
      </div>
      <div class="row-end">&nbsp;</div>
    </div>
    </script>

    <script id="toc_section" type="text/template">
    <div class="row">
      <div class="col3 tocLine">
        <span class="tocTitle tocFloatingDots">{{nr}}</br>{{title}} . . . . . . .
          . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
          . . . . . . . . . . . . . . . . . . . . . . . . . . . </span>
        <span class="tocNumbering"></br>&nbsp;{{page}}</span>
      </div>
      <div class="row-end">&nbsp;</div>
    </div>
    </script>
    """)

}

abstract class Heading (val heading: String) extends Entity with SectionNumber {
  var appendPoint = "content"
  val templateId = "heading"
  def toJson = Json.toJson(
    Map(
      "templateId" -> Json.toJson(templateId),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(id),
          "heading" -> Json.toJson(heading),
          "number" -> Json.toJson(sectionNumber),
          "h" -> Json.toJson(h)
        )
      )
    )
  ).toString
}

class HeadingChapter (heading: String) extends Heading (heading) with Chapter
class HeadingSection (heading: String) extends Heading (heading) with Section
class HeadingSubSection (heading: String) extends Heading (heading) with SubSection
class HeadingSubSubSection (heading: String) extends Heading (heading) with SubSubSection


class Text (txt: () => String) extends Entity {
  def this (txt: String) = this(() => txt)
  var appendPoint = "content"
  val templateId = "text_1110"
  var newlineList: List[Boolean] = List()
  def newline: Text = {
    newlineList = true :: newlineList
    this
  }
  def toJson = Json.toJson(
    Map(
      "templateId" -> Json.toJson(templateId),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(id),
          "text" -> Json.toJson(txt()),
          "newline" -> Json.toJson(newlineList)
        )
      )
    )
  ).toString
}

class Figure (src: String, desc: String) extends Entity with FigureNumber {
  var appendPoint = "content"
  val templateId = "figure_1100"
  def toJson = Json.toJson(
    Map(
      "templateId" -> Json.toJson(templateId),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(id),
          "src" -> Json.toJson(src),
          "description" -> Json.toJson(desc),
          "number" -> Json.toJson(figureNumber)
        )
      )
    )
  ).toString
}

class TOCHead (heading: String) extends Entity {
  var appendPoint = "content"
  val templateId = "toc_heading"
  def toJson = Json.toJson(
    Map(
      "templateId" -> Json.toJson(templateId),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(id),
          "heading" -> Json.toJson(heading)
        )
      )
    )
  ).toString
}

class TOCMainSection (nr: String, title: String) extends Entity {
  var appendPoint = "content"
  val templateId = "toc_mainSection"
  def toJson = Json.toJson(
    Map(
      "templateId" -> Json.toJson(templateId),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(id),
          "nr" -> Json.toJson(nr),
          "title" -> Json.toJson(title),
          "page" -> Json.toJson("~~")
        )
      )
    )
  ).toString
}

class TOCSection (nr: String, title: String) extends Entity {
  var appendPoint = "content"
  val templateId = "toc_section"
  def toJson = Json.toJson(
    Map(
      "templateId" -> Json.toJson(templateId),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(id),
          "nr" -> Json.toJson(nr),
          "title" -> Json.toJson(title),
          "page" -> Json.toJson("~~")
        )
      )
    )
  ).toString
}

class PageA4 extends Page {
  val appendPoints = {
    Map(
      "type" -> "content",
      "templateVariable" -> "appendPoint_content",
      "maxHeight" -> "241.3mm") ::
    Map(
      "type" -> "footer",
      "templateVariable" -> "appendPoint_footer",
      "maxHeight" -> "12.6mm") :: Nil
  }
  val templateId = "page"
  val officialName = "A4"
}

class FraunhoferReportEntityBinding extends academic.EntityBinding {
  override def $ (refName: String): FraunhoferReportEntityBinding = {
    nextRefName = refName
    return this
  }
  def § (h: String)(implicit areal: Areal): HeadingChapter = {
    val heading = new HeadingChapter(h)
    heading.bindToAreal(areal)
    registerReference(heading)
    return heading
  }
  def §§ (h: String)(implicit areal: Areal): HeadingSection = {
    val heading = new HeadingSection(h)
    heading.bindToAreal(areal)
    registerReference(heading)
    return heading
  }
  def §§§ (h: String)(implicit areal: Areal): HeadingSubSection = {
    val heading = new HeadingSubSection(h)
    heading.bindToAreal(areal)
    registerReference(heading)
    return heading
  }
  def §§§§ (h: String)(implicit areal: Areal): HeadingSubSubSection = {
    val heading = new HeadingSubSubSection(h)
    heading.bindToAreal(areal)
    registerReference(heading)
    return heading
  }
  def txt (t: String)(implicit areal: Areal): Text = {
    val text = new Text(t)
    text.bindToAreal(areal)
    registerReference(text)
    return text 
  }
  def txt (t: () => String)(implicit areal: Areal): Text = {
    val text = new Text(t)
    text.bindToAreal(areal)
    registerReference(text)
    return text
  }
  def figure (src: String, desc: String)(implicit areal: Areal): Figure = {
    val fig = new Figure(src, desc)
    fig.bindToAreal(areal)
    registerReference(fig)
    return fig
  }
}

object Document extends Tray[EntityPageBase]

class Document (implicit builder: Builder=null) extends Areal with scaltex.util.$StringContext {
  val companion: Tray[EntityPageBase] = Document
  var appendPoint = "Document"
  val defaultPage = new PageA4
  setCurrentPage(defaultPage)
  val ++ = new FraunhoferReportEntityBinding
  def newpage = {
    addToList(getCurrentPage)
    this
  }
  def page_to (p: Page) = {
    setCurrentPage(p)
    addToList(getCurrentPage)
    this
  }
}

object TableOfContents extends Tray[EntityPageBase]

class TableOfContents (implicit builder: Builder=null) extends Areal {
  val companion: Tray[EntityPageBase] = TableOfContents
  var appendPoint = "TableOfContents"
  val defaultPage = new PageA4
  setCurrentPage(defaultPage)
  val ++ = new FraunhoferReportEntityBinding
  def newpage = this
  def page_to (p: Page) = this

  addToList(new TOCHead("Inhalt"))

  def scanHeadings (areals: Areal*) = {
    for (areal <- areals) {
      areal.companion.get.foreach { x =>
        x match {
          case c: HeadingChapter => addToList(new TOCMainSection(c.sectionNumber, c.heading))
          case s: HeadingSection => addToList(new TOCSection(s.sectionNumber, s.heading))
          case s: HeadingSubSection => addToList(new TOCSection(s.sectionNumber, s.heading))
          case _ => None
        }
      }
    }
  }
}

object FraunhoferReportBuilder extends Tray[Areal]

class FraunhoferReportBuilder extends Builder with FraunhoferReportTemplate {
  val companion = FraunhoferReportBuilder
  val allPages = new PageA4 :: Nil
  override val set = this
  var institutName = "Fraunhofer"
  def institut_name (name: String) = institutName = name
  def generateJsSpecialEntities = s"""
    var specialEntities = [
      {
        templateId: "pagina",
        json: {institutName: "$institutName", pageNr: "@nextPageNr"},
        requiredPageAppendPoint: "footer"
      }
    ];
  """
}