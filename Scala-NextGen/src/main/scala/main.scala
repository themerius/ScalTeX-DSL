package scaltex.main

import scaltex.template.fraunhofer._

object Doc extends FraunhoferReportBuilder {

  val toc = new TableOfContents(doc)
  val doc = new Document

  set document_name "Example Document"
  set institut_name "Fraunhofer SCAI"

  Chapter_1
  Chapter_2
  PythonPlot

  toc.create

  def main(args: Array[String]) {
    write("_output/output.html")
  }
}

// import scaltex.template.patent._

// object Doc extends PatentBuilder {

//   val doc = new Document

//   set document_name "Example Document"

//   Chapter_1
//   Chapter_2
//   PythonPlot

//   def main(args: Array[String]) {
//     write("_output/output_patent.html")
//   }
// }

object Chapter_1 extends Document {

  ++ § "Überschrift"

  ++ §§ "Unterüberschrift"

  ++ txt """
    Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """

  ++ $ "text_x" txt $"""
    Lorem ipsum Abb. ${Chapter_1.figname.figureNumber} dolor sit amet, consetetur
    sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """

  Chapter_1.text_x.newline.newline

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

object PythonPlot extends Document {

  newpage

  ++ § "Live generated Python Plot"

  ++ txt $"""
    Within figure ${PythonPlot.mplfig.figureNumber} is a live plot,
    created with python's mathplotlib.
    It was generated with this code:
    <pre><code>
    $script
    </code></pre>
  """

  // !! only useable if python, numpy and matplotlib are installed:

  val script = """
    import base64
    import numpy
    import matplotlib.pyplot as plt

    x = numpy.linspace(-15,15,100)
    y = numpy.sin(x)/x
    plt.plot(x,y)
    plt.plot(x,y,'co')
    plt.plot(x,2*y,x,3*y)

    # save to document:
    plt.savefig("_output/plot.png", format="png")
    with open("_output/plot.png", "rb") as img:
        print "data:image/png;base64," + base64.b64encode(img.read())
  """

  val py = new scaltex.addons.PythonScript(script)

  ++ $ "mplfig" figure(
    src=py.run,
    desc="Plot with matplotlib."
  )
}
