package scaltex.main

import scaltex.util._
import scaltex.util.$StringContext._
import scaltex.template._

object Main {

  Chapter_1
  Chapter_2

  def main(args: Array[String]) {
    // write output file
    (new FraunhoferArticle).write("_output/output.html")
    println("You can now `open _output/output.html`")
  }
}

object Chapter_1 extends DynamicObject {

  § > "Überschrift"

  § >> "Unterüberschrift"

  ++ txt """
    Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """

  val text_1 =
  ++ txt $"""
    Lorem ipsum Abb. ${Chapter_1.figname.number} dolor sit amet, consetetur
    sadipscing elitr, sed diam
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

  ++ txt $"""
    Lorem ipsum Abb. ${Chapter_2.otherfig.number} dolor sit amet, consetetur
    sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """

  // config
  text_1.newline = true :: true :: Nil
}

object Chapter_2 extends DynamicObject {
  § > "Other"

  ++ figure(
    src="https://raw.github.com/themerius/ScalTeX/play/public/images/plot.png",
    desc="Matplotlib example histogramm",
    name="otherfig"
  )

  ++ txt $"""
    Paragraph within Other.
    Reference on Fig ${Chapter_1.figname.number} in Chapter 1.
  """
}
