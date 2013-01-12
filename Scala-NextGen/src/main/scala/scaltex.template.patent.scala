package scaltex.template.patent

import scaltex.buildtools._
import scaltex.buildtools.logic._
import scaltex.types.academic
import play.api.libs.json._

trait PatentTemplate extends TemplateStock {
  override def headerTemplate (title: String) = s"""
    <!DOCTYPE html>
  <html lang="en">
  <head>
  <meta charset="utf-8" />
  <title>ScalTex Templating</title>
  <link rel="stylesheet" href="http://scaltex.4pple.de/patent_epo/css/reset.css" />
  <link rel="stylesheet" href="http://scaltex.4pple.de/patent_epo/css/typo.css" />
  <link rel="stylesheet" href="http://scaltex.4pple.de/patent_epo/css/page.css" />
  <link rel="stylesheet" href="http://scaltex.4pple.de/patent_epo/css/img.css" />
  <script src="https://raw.github.com/janl/mustache.js/master/mustache.js"></script>
  <script src="https://raw.github.com/themerius/ScalTeX-templates/master/scaltex/src/scaltex.js"></script>
  <script src="http://hyphenator.googlecode.com/svn/trunk/Hyphenator.js"></script>
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
      <div id="{{{appendPoint_header}}}" class="header"></div>
      <div class="lineNumbering">
        <br />
        <br />
        <br />
        <br />
        5<br />
        <br />
        <br />
        <br />
        <br />
        10<br />
        <br />
        <br />
        <br />
        <br />
        15<br />
        <br />
        <br />
        <br />
        <br />
        20<br />
        <br />
        <br />
        <br />
        <br />
        25<br />
        <br />
        <br />
        <br />
        <br />
        30<br />
        <br />
        <br />
        <br />
        <br />
        35<br />
        <br />
        <br />
        <br />
        <br />
        40<br />
        <br />
        <br />
        <br />
        <br />
        45<br />
        <br />
        <br />
        <br />
        <br />
        50<br />
        <br />
        <br />
        <br />
        <br />
        55<br />
        <br />
        <br />
        <br />
      </div>
      <div id="{{{appendPoint_content}}}" class="layoutGrid"></div>
      <div id="{{{appendPoint_footer}}}" class="footer"></div>
    </div>
    </script>

    <script id="header" type="text/template">
    <div class="header">
      <br /><br /><br /><br />
      <p>{{title}}</p>
    </div>
    </script>

    <script id="footer" type="text/template">
    <div class="footer">
      <br />
      <p>{{pageNr}}</p>
    </div>
    </script>

    <script id="text" type="text/template">
    <div class="paragraph">
      <p class="hyphenate">
        <span class="par_nr">
          [{{parNr}}]
        </span>
        {{{text}}}
      </p>
    </div>
    {{#newline}}
      <br />
    {{/newline}}
    </script>

    <script id="text_indented" type="text/template">
    <div class="paragraph">
      <p class="indented">
      {{{text}}}
      </p>
    </div>
    {{#newline}}
      <br />
    {{/newline}}
    </script>

    <script id="heading" type="text/template">
    <{{h}}>{{heading}}</{{h}}>
    <br />
    </script>

    <script id="figure" type="text/template">
    <img class="scaling lines-{{lines}}" src="{{src}}" alt="{{description}}" />
    {{#description}}
    <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {{{description}}}</div>
    {{/description}}
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


class Text (txt: () => String)(a: Areal) extends Entity {
  def this (txt: String)(a: Areal) = this(() => txt)(a)
  bindToAreal(a)
  var appendPoint = "content"
  val templateId = "text"
  var newlineList: List[Boolean] = List()
  def newline: Text = {
    newlineList = true :: newlineList
    this
  }
  val paragraphCount =  areal.companion.get.filter(_.isInstanceOf[Text]).length
  def toJson = Json.toJson(
    Map(
      "templateId" -> Json.toJson(templateId),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(id),
          "parNr" -> Json.toJson(paragraphCount),
          "text" -> Json.toJson(txt()),
          "newline" -> Json.toJson(newlineList)
        )
      )
    )
  ).toString
}

class Figure (src: String, desc: String) extends Entity with FigureNumber {
  var appendPoint = "content"
  val templateId = "figure"
  var lines = 10
  def with_lines (l: Int) = lines = l
  def toJson = Json.toJson(
    Map(
      "templateId" -> Json.toJson(templateId),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(id),
          "src" -> Json.toJson(src),
          "description" -> Json.toJson(desc),
          "number" -> Json.toJson(figureNumber),
          "lines" -> Json.toJson(lines)
        )
      )
    )
  ).toString
}

class PageA4 extends Page {
  val appendPoints = {
    Map(
      "type" -> "header",
      "templateVariable" -> "appendPoint_header",
      "maxHeight" -> "26.75mm") ::
    Map(
      "type" -> "content",
      "templateVariable" -> "appendPoint_content",
      "maxHeight" -> "249.5mm") ::
    Map(
      "type" -> "footer",
      "templateVariable" -> "appendPoint_footer",
      "maxHeight" -> "20.75mm") :: Nil
  }
  val templateId = "page"
  val officialName = "A4"
}

class PatentEntityBinding extends academic.EntityBinding {
  override def $ (refName: String): PatentEntityBinding = {
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
    val text = new Text(t)(areal)
    //text.bindToAreal(areal)
    registerReference(text)
    return text 
  }
  def txt (t: () => String)(implicit areal: Areal): Text = {
    val text = new Text(t)(areal)
    //text.bindToAreal(areal)
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
  val ++ = new PatentEntityBinding
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

object PatentBuilder extends Tray[Areal]

class PatentBuilder extends Builder with PatentTemplate with scaltex.util.$StringContext {
  val companion = PatentBuilder
  val allPages = new PageA4 :: Nil
  override val set = this
  var institutName = "Fraunhofer"
  def institut_name (name: String) = institutName = name
  def generateJsSpecialEntities = s"""
    var specialEntities = [
      {
        templateId: "footer",
        json: {pageNr: "@nextPageNr"},
        requiredPageAppendPoint: "footer"
      },
      {
        templateId: "header",
        json: {title: document.getElementsByTagName("title")[0].innerHTML},
        requiredPageAppendPoint: "header"
      }
    ];
  """
}