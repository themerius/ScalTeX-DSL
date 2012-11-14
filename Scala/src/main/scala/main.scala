package scaltex.main

import scaltex.util._
import scaltex.util.$StringContext._
import scaltex.template._

object Main {

  Chapter_1
  Chapter_2
  Chapter_Py

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

object Chapter_Py extends DynamicObject {
  val sc = """
import base64
import numpy as np
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt

mu, sigma = 100, 15
x = mu + sigma*np.random.randn(10000)

# the histogram of the data
n, bins, patches = plt.hist(x, 50, normed=1, facecolor='green', alpha=0.75)

# add a 'best fit' line
y = mlab.normpdf( bins, mu, sigma)
l = plt.plot(bins, y, 'r--', linewidth=1)

plt.xlabel('Smarts')
plt.ylabel('Probability')
plt.title(r'$\mathrm{Histogram\ of\ IQ:}\ \mu=100,\ \sigma=15$')
plt.axis([40, 160, 0, 0.03])
plt.grid(True)

plt.savefig("_output/plot.png", format="png")
with open("_output/plot.png", "rb") as img:
    print base64.b64encode(img.read())
  """

  val py = new scaltex.logic.Python(sc)
  val output = "data:image/png;base64," + py.exec

  ++ figure(
    src=output,
    desc="Matplotlib LIVE GENERATED",
    name="mplfig"
  )
}
