object § {
  def > (input: String) = {
    println(input.trim)
  }

  def >> (input: String) = {
    println(input.trim)
  }
}

object Idea2 {
  def main(args: Array[String]) {

    /*
    object configs {
      def list = new ListConfig...
    }*/

    //val bla =
    § > "Überschrift"

    //@Label("name") // dem ding einen namen geben ... macht aber alles ein bisschen noisy. wie impliziter ausdrücken?
    § >> "Unterüberschrift"

    /*
    ^ txt """
      Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
      sed diam nonumy eirmod tempor invidunt ut labore et dolore magna
      aliquyam erat, sed diam voluptua. At vero eos et accusam et justo
      duo dolores et ea rebum. Stet clita kasd gubergren,
    """

    ^ figure {
      src="/home/pic",
      desc="Hallo Pic"
    }

    ! SpecialPage

    ^ table {
      "Nr".bold :: "Quadratmeter" :: "Energievertrauch" :: Nil,
      "1"       :: 125            :: List(200, 300) :: Nil,
      "1"       :: 150            :: 275 :: Nil
    }

    var listConfig = new ListConfig(bullettype="*") // ausführliche konfigurationen
    ^ list("*") // auch mit List oder auch als md liste...

    ! NormalPage

    ...
    */
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