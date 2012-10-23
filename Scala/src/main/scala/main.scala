import scaltex.api._

class MyArticleTemplate extends scaltex.logic.Tray {
  def unpack = {
    for (entity <- entities)
      println(entity.json)
  }
}

object Main {
  def main(args: Array[String]) {

    § > "Überschrift"

    ^ txt """
      Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
      nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
      sed diam voluptua.
    """

    println(scaltex.logic.Tray.get)
    println( (new MyArticleTemplate).unpack )
  }
}