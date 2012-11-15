package scaltex.template.epo

import play.api.libs.json._
import java.io.{OutputStreamWriter, FileOutputStream}
import scaltex.util.DynamicObject

class EpoPatent extends scaltex.logic.Tray {
  def htmlHead = """<!DOCTYPE html>
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

<div id="DocumentAreal"></div>
"""

  def htmlTemplates = """
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

<script id="ketcherChemistry" type="text/template">
<div id="ketcherChemistryMolfile-{{molfileId}}" name="ketcherChemistry" style="display:none;">
  {{{molfile}}}
</div>
<div id="ketcherChemistryView-{{molfileId}}" class="scaling lines-{{lines}}"></div>
</script>
"""

  def htmlMainScript(entities: String) = s"""
<script>
var seq = [
  {
    pageType: "A4",
    entities: [
      $entities
    ]
  }
];

specialEntities = [
  {
    templateId: "pagina",
    json: {pageNr: "@nextPageNr"},
    requiredPageAppendPoint: "footer"
  },
  {
    templateId: "header",
    json: {title: "E 1 559 427 A1"},
    requiredPageAppendPoint: "header"
  }
];

var page = {
  template: "page",
  appendPoints: [
    {type: "header", templateVariable: "appendPoint_header", maxHeight: "26.75mm"},
    {type: "content", templateVariable: "appendPoint_content", maxHeight: "249.5mm"},
    {type: "footer", templateVariable: "appendPoint_footer", maxHeight: "20.75mm"}
  ]
}

var pageFactory = new scaltex.PageFactory({
  A4: page
});

var documentAreal = new scaltex.Areal("DocumentAreal", seq, pageFactory, specialEntities);
documentAreal.generateEntities();
documentAreal.renderEntities();
documentAreal.mountEntitiesToConstructionArea();

Hyphenator.config({
  useCSS3hyphenation: true,
  displaytogglebox : true,
  minwordlength : 4
});
Hyphenator.run();

window.onload = function () {
  documentAreal.moveEntitiesToNewPages();
  documentAreal.destructConstructionAreas();
};
</script>
"""

  def htmlFoot = """
</body>
</html>
"""

  def write (filePath: String) = {
    val out = new OutputStreamWriter(
      new FileOutputStream(filePath), "UTF-8")
    out.append(this.htmlHead + this.htmlTemplates + this.htmlMainScript(this.unpack) + this.htmlFoot)
    out.close
  }

}

class Text(txt: () => String) extends scaltex.logic.Text(txt) {
  def this (txt: String) = this(() => txt)
  def json = Json.toJson(
    Map(
      "templateId" -> Json.toJson("text"),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(id),
          "text" -> Json.toJson(txt()),
          "parNr" -> Json.toJson("%04d".format(count)),
          "newline" -> Json.toJson(newline)
        )
      )
    )
  ).toString
}

class Figure (src: String, lines: Int) extends scaltex.logic.Figure(src) {
  def json = Json.toJson(
    Map(
      "templateId" -> Json.toJson("figure"),
      "json" -> Json.toJson(
        Map(
          "id" -> Json.toJson(this.id),
          "src" -> Json.toJson(this.src),
          "lines" -> Json.toJson(this.lines)
        )
      )
    )
  ).toString
}

object ++ extends scaltex.api.++ {
  def txt(s: String) = new Text(s)
  def txt(s: () => String) = new Text(s)
  def figure (src: String, lines: Int) = new Figure(src, lines)
}

object § extends scaltex.api.§
