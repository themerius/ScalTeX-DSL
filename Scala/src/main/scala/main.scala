package scaltex.main

import scala.language.postfixOps

import scaltex.util._
import scaltex.util.$StringContext._
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
  ++ txtref $"""
    Lorem ipsum Abb. ${Main.figname.number} dolor sit amet, consetetur sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """

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

  ++ txtref $"""
    Lorem ipsum Abb. ${Main.otherfig.number} dolor sit amet, consetetur sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """

  ++ figure(
    src="https://raw.github.com/themerius/ScalTeX/play/public/images/plot.png",
    desc="Matplotlib example histogramm",
    name="otherfig"
  )

  def main(args: Array[String]) {
    // config
    text_1 newline = true :: true :: Nil  // this needs postfixOps

    // write output file
    (new FraunhoferArticle).write("_output/output.html")
    println("You can now `open _output/output.html`")
  }
}
