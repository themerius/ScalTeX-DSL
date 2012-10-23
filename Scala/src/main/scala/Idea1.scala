package idea1

class Content {
  def txt (input: String) = println(input.trim)
  def html (input: scala.xml.Elem) = println(input)
}

object Template {
  object Section {
    def ! (conf: Int): Content = {
      println("With Config " + conf)
      return new Content
    }
  }

  def sec (conf: Int)(input: String) = {
    println(input.trim)
    println("With Config " + conf)
  }
}

object Idea1 {
  def main(args: Array[String]) {
    val config = 0

    Template.Section ! {config + 1} txt """
      Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
      sed diam nonumy eirmod tempor invidunt ut labore et dolore magna
      aliquyam erat, sed diam voluptua. At vero eos et accusam et justo
      duo dolores et ea rebum. Stet clita kasd gubergren,
    """

    Template.Section ! config + 2 html <p>
      Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
      sed diam nonumy eirmod tempor invidunt ut labore et dolore magna
      aliquyam erat, sed diam voluptua. At vero eos et accusam et justo
      duo dolores et ea rebum. Stet clita kasd gubergren,
    </p>

    Template.sec(config + 3)("""
      Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
      sed diam nonumy eirmod tempor invidunt ut labore et dolore magna
      aliquyam erat, sed diam voluptua. At vero eos et accusam et justo
      duo dolores et ea rebum. Stet clita kasd gubergren,
    """)

  }
}