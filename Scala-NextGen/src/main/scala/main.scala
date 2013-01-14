package scaltex.main

import scaltex.template.fraunhofer._

object Doc extends FraunhoferReportBuilder {

  val toc = new TableOfContents(doc)
  val doc = new Document

  set document_name "Textsatzsysteme"
  set institut_name "Fraunhofer SCAI"

  KapitelTextsatz
  KapitelScaltex
  //PythonPlot  // only use if python+matplotlib+numpy is installed!

  toc.create

  def main(args: Array[String]) {
    write("_output/output.html")
  }
}

object KapitelTextsatz extends Document {

  ++ § "Textsatzsysteme: Einleitung"

  ++ txt """
    Textsatzsysteme übernehmen „das rechnergestützte Setzen von Dokumenten,
    die aus Texten und Bildern bestehen und später als Publikationen,
    wie zum Beispiel Broschüren, Magazine, Bücher oder Kataloge,
    ihre Verwendung finden.“ (http://de.wikipedia.org/wiki/Textsatzsystem)
  """

  ++ $ "abs1" txt $"""
    Dieses Dokument stellt ein neues Textsatzsystem vor, davon mehr in
    Kapitel ${KapitelScaltex.scaltex.sectionNumber}!
  """

  KapitelTextsatz.abs1.newline.newline

  ++ §§ "TeX"

  ++ txt $"""
    TeX ist eine weit verbreitete Programmiersprache die speziell für die
    Aufgaben eines Textsatzsystem erschaffen wurde.
    Mit TeX lassen sich qualitativ hochwertige Dokumente setzen,
    und ist insbesondere im mathematisch-naturwissenschaftlichen Bereich
    defacto Standard.
    Auf Abbildung ${KapitelTextsatz.tex_logo.figureNumber} ist das Logo
    von TeX zu sehen.
  """

  ++ $ "tex_logo" figure(
    src="http://upload.wikimedia.org/wikipedia/commons/thumb/6/68/TeX_logo.svg/500px-TeX_logo.svg.png",
    desc="TeX Logo."
  )

  ++ txt """
    TeX produziert als Ausgabeformat DVI-Dateien, wobei es mittlerweile
    auch Modifikationen gibt, die PDF-Dateien produzieren.
    <br/><br/>
  """

  ++ §§§ "Mathematik und TeX"  $ "mathe_tex"

  ++ txt """
    TeX selbst besitzt ausgezeichnete Fähigkeiten zum Setzen mathematischer
    Ausdrücke, daher ist es eben im mathematisch-naturwissenschaftlichen
    Bereich so außerordentlich beliebt.
    <br/><br/>
  """

  ++ §§ "Andere Systeme"

  ++ txt $"""
    Es gibt sehr viele Systeme auf dem Markt. Neben TeX gibt es auch noch
    Microsoft Word oder Adobe Illustrator, und noch viele andere.
    Aber in Kapitel ${KapitelScaltex.scaltex.sectionNumber} wird
    ein neues System vorgestellt.
  """
}

object KapitelScaltex extends Document {

  newpage

  ++ § "Codename „ScalTeX“"  $ "scaltex"

  ++ txt """
    Ist ein neues Textsatzsystem, welches im Zuge dieser Bachelorarbeit
    entwickelt wurde und als Resultat HTML-Dateien, mit der
    Anmut eines Printdokuments, produziert.
  """

  ++ txt $"""
    In Kapitel ${KapitelTextsatz.mathe_tex.sectionNumber} wurde darauf
    eingegangen, dass TeX außerordentliche Fähigkeiten besitzt, was
    den Mathesatz betrifft.
    ScalTeX auch TeX-Befehle verarbeiten,
    dank der MathJax JavaScript-Bibliothek. Beispiel:
    :math:
      x(t)=\\frac1{\\sqrt{2\\pi}}\\int_{-2\\pi F}^{2\\pi F}
           X(\\omega)e^{\\mathrm{i}\\omega\\,t}\\, d\\omega
    :math:
    <br/><br/>
  """

  ++ §§ "Vorwärtsverweise"

  ++ txt """
    Referenzierungen auf Entitäten innerhalb eines Dokuments sind
    vollkommen normal. Auch hier wird gerade darauf zurückgegriffen!
    Besondere Schwierigkeit ist, dass der Text meistens schon auf
    die Entität hinweist, bevor sie angezeigt wird bzw. innerhalb des
    Programms instanziiert wurde. Das Problem konnte jedoch
    überwunden werden.
    <br/><br/>
  """
}

object PythonPlot extends Document {

  ++ §§ "Automatisierung: Live generierter Python-Plot"

  ++ txt $"""
    Ein besonderer Clou ist die Möglichkeit mit anderen Programmen
    innerhalb des Dokumenten-Bauprozesses zu interagieren.
    Hier wird exemplarisch ein Stück Python-Code ausgeführt, welcher
    einen Plot mit <code>matplotlib</code> produziert.
    Der Plot in Abbildung ${PythonPlot.mplfig.figureNumber} wird also
    zur Bauzeit des Dokuments erstellt und zwar aus diesem Codestück:
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
    desc="Automatisch generierter Plot mit <code>matplotlib</code>."
  )
}
