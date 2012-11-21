package scaltex.template

import play.api.libs.json._
import java.io.{OutputStreamWriter, FileOutputStream}
import scaltex.util.DynamicObject

class FraunhoferArticle extends scaltex.logic.Tray {
  def htmlHead = """<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>ScalTex Templating</title>
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

<div id="TitlepageAreal"></div>
<div id="TableOfContentsAreal"></div>
<div id="DocumentAreal"></div>
"""

  def htmlTemplates = """
<script id="titlepage" type="text/template">
<div class="pageA4">
  <div id="{{{appendPoint_logo}}}"></div>
  <div class="layoutGrid">
    <div id="{{{appendPoint_longName}}}"></div>
    <div id="{{{appendPoint_mainTitle}}}"></div>
    <div id="{{{appendPoint_partner}}}"></div>
  </div>
  <div id="{{{appendPoint_footer}}}"></div>
</div>
</script>

<script id="titlepage_logo" type="text/template">
<img class="titlepage_logo" src="{{src}}" alt="{{description}}" />
</script>

<script id="titlepage_longName" type="text/template">
<div class="row">
  <div class="col4">
    <p class="titlepage_longName">{{{text}}}</p>
  </div>
  <div class="row-end">&nbsp;</div>
</div>
</script>

<script id="titlepage_mainTitle" type="text/template">
<div class="row">
  <div class="col3">
    <p class="titlepage_main">{{{main}}}</p>
    <p class="titlepage_sub">{{{sub}}}</p>
  </div>
  <div class="row-end">&nbsp;</div>
</div>
</script>

<script id="titlepage_partner" type="text/template">
<div class="titlepage_partner">
  <p>In Zusammenarbeit mit</p>
  </br>
  <img class="titlepage_partnerlogo" src="{{src}}" alt="{{description}}" />
</div>
</script>


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
"""

  def htmlMainScript(entities: String, tocEntities: String) = s"""
<script>
var seq = [
  {
    pageType: "A4",
    entities: [
      $entities
    ]
  }
];

var toc_seq = [
  {
    pageType: "A4",
    entities: [
      $tocEntities
    ]
  }
];

specialEntities = [
  {
    templateId: "pagina",
    json: {institutName: "Fraunhofer SCAI", pageNr: "@nextPageNr"},
    requiredPageAppendPoint: "footer"
  }
];

var page = {
  template: "page",
  appendPoints: [
    {type: "content", templateVariable: "appendPoint_content", maxHeight: "241.3mm"},
    {type: "footer", templateVariable: "appendPoint_footer", maxHeight: "12.6mm"}
  ]
}

var pageFactory = new scaltex.PageFactory({
  A4: page
});

var documentAreal = new scaltex.Areal("DocumentAreal", seq, pageFactory, specialEntities);
documentAreal.generateEntities();
documentAreal.renderEntities();
documentAreal.mountEntitiesToConstructionArea();

var tocAreal = new scaltex.Areal("TableOfContentsAreal", toc_seq, pageFactory);
tocAreal.generateEntities();
tocAreal.renderEntities();
tocAreal.mountEntitiesToConstructionArea();

window.onload = function () {
  documentAreal.moveEntitiesToNewPages();
  documentAreal.destructConstructionAreas();

  tocAreal.moveEntitiesToNewPages();
  tocAreal.destructConstructionAreas();
};


// Mathjax Config
MathJax.Hub.Config({
  tex2jax: {inlineMath: [ ['$$','$$'], ["\\(","\\)"] ]}
});
</script>
"""

  def htmlFoot = """
</body>
</html>
"""

  def write (filePath: String) = {
    val out = new OutputStreamWriter(
      new FileOutputStream(filePath), "UTF-8")
    val toc = new TableOfContents
    out.append(this.htmlHead + this.htmlTemplates + this.htmlMainScript(this.unpack, toc.unpack) + this.htmlFoot)
    out.close
  }
}

class Text(txt: () => String) extends scaltex.logic.Text(txt) {
  def this (txt: String) = this(() => txt)
  def json = Json.toJson(
    Map(
      "templateId" -> Json.toJson("text_1110"),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(id),
          "text" -> Json.toJson(txt()),
          "newline" -> Json.toJson(newline)
        )
      )
    )
  ).toString
}

class Figure (src: String, desc: String) extends scaltex.logic.Figure(src) {
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
}

class TableOfContents extends scaltex.logic.TableOfContents {
  var id = 10000

  def jsonHeading = {
    id += 1
    Json.toJson(
    Map(
      "templateId" -> Json.toJson("toc_heading"),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(id),
          "heading" -> Json.toJson("Inhalt")
        )
      )
    )
  ).toString
  }

  def jsonMainSection(h: scaltex.logic.Heading) = {
    id += 1
    Json.toJson(
    Map(
      "templateId" -> Json.toJson("toc_mainSection"),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(id),
          "nr" -> Json.toJson(h.number),
          "title" -> Json.toJson(h.heading),
          "page" -> Json.toJson(0)
        )
      )
    )
  ).toString
  }

  def jsonSection(h: scaltex.logic.Heading) = {
    id += 1
    Json.toJson(
    Map(
      "templateId" -> Json.toJson("toc_section"),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(id),
          "nr" -> Json.toJson(h.number),
          "title" -> Json.toJson(h.heading),
          "page" -> Json.toJson(0)
        )
      )
    )
  ).toString
  }

  def unpack = {
    var ret = ""
    ret += jsonHeading + ","
    for (heading <- generate) {
      if (heading.h == "h1")
        ret += jsonMainSection(heading) + ","
      else
        ret += jsonSection(heading) + ","
    }
    ret
  }
}

object ++ extends scaltex.api.++ {
  def txt(s: String) = new Text(s)
  def txt(s: () => String) = new Text(s)
  override def figure (src: String, desc: String, name: String)
    (implicit objectRef: DynamicObject) = {
    val ret = new Figure(src, desc)
    objectRef.updateDynamic(name)(ret)
    ret  // TODO: eleminate warning
  }
}

object § extends scaltex.api.§
