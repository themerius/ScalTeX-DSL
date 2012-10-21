object § {
  def > (input: String) = println(input)  // oder ' (Überschrift 1. Ordnung)
  def >> (input: String) = println(input) // oder '' (Überschrift 2. Ordnung) etc.
}

class ListItem {
  def * (input: String): ListItem = {
    println("ListItem: ", input)
    new ListItem
  }
}

object ^ {
  def txt (input: String) = println(input.trim)
  def html (input: scala.xml.Elem) = println(input)
  def figure (src: String, desc: String) = println("Figure", src, desc)
  def table (input: List[Any]*) = {
    for (item <- input)
      println(item)
  }
  def list = new ListItem
  def list (li: ListItem) = println(li)
  def list (input: String) = println(input)
}

object & {
  def book (
    titel: String = "",
    autors: List[String] = "" :: Nil,
    pubDate: String = "",
    publisher: String = ""
  ) = println("Book: ", titel, autors, pubDate, publisher)
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
      def list = new ListConfig...  // via DI implizit einbringen?
    }*/

    //val bla =
    § > "Überschrift"

    //@Label("name") // dem ding einen namen geben ... macht aber alles ein bisschen noisy. wie impliziter ausdrücken?
    § > "Unterüberschrift"

    val ref = 42

    ^ txt s"""
      Lorem ${ref} ipsum dolor sit amet, consetetur sadipscing elitr,
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

    ((^ list)
      * "Item 1"
      * "Item 2"
    )

    ^ list (new ListItem
      * "Other item 1"
      * "Other item 2"
    )

    ^ list """
      * markdown
      * liste
    """

    !! NormalPage

    ^ html <p>
      Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
      sed diam nonumy eirmod tempor invidunt ut labore et dolore magna
      aliquyam erat, sed diam voluptua. At vero eos et accusam et justo
      duo dolores et ea rebum. Stet clita kasd gubergren,
    </p>

    // ...

    & book (
      titel = "Titel",
      autors = "Autor 1" :: "Autor 2" :: Nil,
      pubDate = "1.1.1970",
      publisher = "HTWG Konstanz"
    )
  }
}


/*
§ == Überschriften, Titel, ...
^ == Universelle Entities
_ == Referenzen
!! == Steuerbefehle (z.B. Seitenumbrüche (oder sogar Konfigurationen?))
*/