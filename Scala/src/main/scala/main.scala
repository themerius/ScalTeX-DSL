package scaltex.main

import scala.language.postfixOps

import scaltex.util._
import scaltex.api._
import scaltex.template.FraunhoferArticle

object Main extends DynamicObject {

  § > "Überschrift"

  § >> "Unterüberschrift"

  ++ txt """
    Lorem ipsum Abb. ${fig.number} dolor sit amet, consetetur sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """

  val text_1 =
  ++ txtref (() => s"""
    Lorem ipsum Abb. ${Main.figname.number} dolor sit amet, consetetur sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """)

  § > "Beispiel"

  § >> "Konkretes Beispiel"

  § >> "Fazit"

  § >>> "Überschrift 3. Ordnung"

  ++ txt """
    <em>Lorem ipsum</em> dolor sit amet, consetetur sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """

  ++ figure(
    src="https://raw.github.com/themerius/ScalTeX/play/public/images/plot.png",
    desc="Matplotlib example histogramm",
    name="figname"
  )

  def main(args: Array[String]) {
    // config
    text_1 newline = true :: true :: Nil  // this needs postfixOps

    import scala.tools.reflect.ToolBox
    import scala.reflect.runtime.{currentMirror => m}
    val tb = m.mkToolBox()
    val tree = tb.parse("import scaltex.main.Main; Main.figname.number")
    val res = tb.eval(tree)
    println(res)


    // write output file
    (new FraunhoferArticle).write("_output/output.html")
    println("You can now `open _output/output.html`")
  }
}
