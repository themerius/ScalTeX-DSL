package scaltex.api

import scala.language.implicitConversions
import scaltex.logic
import scaltex.util.DynamicObject

trait § {
  def > (input: String) = new logic.Heading(input, "h1")
  def >> (input: String) = new logic.Heading(input, "h2")
  def >>> (input: String) = new logic.Heading(input, "h3")
}

trait ++ {
  def txt (input: String)
  def html (input: scala.xml.Elem) = println(input)
  def figure (src: String, desc: String, name: String)
    (implicit objectRef: DynamicObject) = println(src, desc, name)
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
