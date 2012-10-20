object § {
  def > (input: String) = println(input)
  def >> (input: String) = println(input)
}

object ^ {
  def txt (input: String) = println(input.trim)
  def figure (src: String, desc: String) = println("Figure", src, desc)
  def table (input: List[Any]*) = {
    for (item <- input)
      println(item)
  }
}

object !! {  // limit: ! alone doesn't work properly
  def SpecialPage = println("SpecialPage")
  def NormalPage = println("NormalPage")
}

object ScalTexImplicits {
  class StringHelper (s: String) {
    def bold = println("Bold String: ", this.s)
  }

  implicit def String2StringHelper(s: String) = new StringHelper(s)
}

object Idea2 {
  def main(args: Array[String]) {

    import ScalTexImplicits._

    /*
    object configs {
      def list = new ListConfig...
    }*/

    //val bla =
    § > "Überschrift"

    //@Label("name") // dem ding einen namen geben ... macht aber alles ein bisschen noisy. wie impliziter ausdrücken?
    § >> "Unterüberschrift"


    ^ txt """
      Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
      sed diam nonumy eirmod tempor invidunt ut labore et dolore magna
      aliquyam erat, sed diam voluptua. At vero eos et accusam et justo
      duo dolores et ea rebum. Stet clita kasd gubergren,
    """

    ^ figure (
      src="/home/pic",
      desc="Hallo Pic"
    )

    !! SpecialPage  // or also PageBreak, ...

    ^ table (
      "Nr".bold :: "Quadratmeter" :: "Energievertrauch" :: Nil,
      "1"       :: 125            :: List(200, 300) :: Nil,
      "2"       :: 150            :: 275 :: Nil
    )

    /*
    var listConfig = new ListConfig(bullettype="*") // ausführliche konfigurationen
    ^ list("*") // auch mit List oder auch als md liste...
    */

    /*
    ^ list {
      * "Item 1"
      * "Item 2"
    }

    ^ list """
      * markdown
      * liste
    """
    */

    !! NormalPage

    // ...
  }
}


/*
// Sind dann nur Aliasse/Abkürzungen dazu!
// Mal Außenstehende fragen?
§ "Hallo Welt"

^ """
Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
sed diam nonumy eirmod tempor invidunt ut labore et dolore magna
aliquyam erat, sed diam voluptua. At vero eos et accusam et justo
duo dolores et ea rebum. Stet clita kasd gubergren,
"""

¶ """
Absatz
"""

^ (template=Y) """
Blablub
"""

* as SpecialTable """
  * Bla blub
  * blub Bla
"""

Book "Title" autors "Autor 1, Autor 2" pubDate "1.1.1970" institution "Uni X"
*/