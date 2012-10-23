import scaltex.api._

object Main {
  def main(args: Array[String]) {
    § > "Überschrift"

    ^ txt """
      Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
      nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
      sed diam voluptua.
    """

    println(scaltex.logic.Tray.get)
  }
}