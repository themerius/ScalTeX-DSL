package scaltex.api

import scala.language.implicitConversions
import scaltex.logic

object § {
  def > (input: String) = new logic.Heading(input, "h1")
  def >> (input: String) = new logic.Heading(input, "h2")
  def >>> (input: String) = new logic.Heading(input, "h3")
}

object ++ {
  def txt (input: String) = new logic.Text(input)
  def txtref (input: () => String) = new logic.TextWithRef(input)
  def html (input: scala.xml.Elem) = println(input)
  def figure (src: String, desc: String, name: String) = {
    val ret = new logic.Figure(src, desc)
    scaltex.main.Chapter_1.updateDynamic(name)(ret)
    ret
  }
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

object !! {
  def SpecialPage = println("SpecialPage")
  def NormalPage = println("NormalPage")
}

object Implicits {
  class StringHelper (s: String) {
    def bold = println("Bold String: ", this.s)
  }

  implicit def String2StringHelper(s: String) = new StringHelper(s)
}

class ListItem {
  def * (input: String): ListItem = {
    println("ListItem: ", input)
    new ListItem
  }
}
