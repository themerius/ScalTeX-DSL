package scaltex.main

import scaltex.template.fraunhofer._

object Doc extends FraunhoferReportBuilder {

  (new Document)

  Chapter_1
  Chapter_2

  def main(args: Array[String]) {
    write("_output/output.html")
  }
}

object Chapter_1 extends Document {

  ++ § "Überschrift"

  ++ §§ "Unterüberschrift"

  ++ txt """
    Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """

  ++ txt $"""
    Lorem ipsum Abb. ${Chapter_1.figname.figureNumber} dolor sit amet, consetetur
    sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """


  ++ § "Beispiel"

  ++ §§ "Konkretes Beispiel"

  ++ §§ "Fazit"

  ++ §§§ "Überschrift 3. Ordnung"   $ "ueb"

  ++ txt """
    <em>Lorem ipsum</em> dolor sit amet, consetetur sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """

  ++ $ "figname" figure(
    src="https://raw.github.com/themerius/ScalTeX/play/public/images/plot.png",
    desc="Matplotlib example histogramm"
  )

  ++ txt $"""
    Lorem ipsum Abb. ${Chapter_2.otherfig.figureNumber} dolor sit amet, consetetur
    sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """
}

object Chapter_2 extends Document {
  ++ § "Other"

  ++ $ "otherfig" figure(
    src="https://raw.github.com/themerius/ScalTeX/play/public/images/plot.png",
    desc="Matplotlib example histogramm"
  )

  ++ txt $"""
    Paragraph within Other.
    Reference on Fig ${Chapter_1.figname.figureNumber} in
    chapter ${Chapter_1.ueb.sectionNumber}.
  """
}
