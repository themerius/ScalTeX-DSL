package scaltex.main

import scala.language.postfixOps
import language.dynamics
import scala.collection.mutable.HashMap
import scala.reflect.ClassTag

import scaltex.api._
import scaltex.template.FraunhoferArticle

trait DynamicBase extends Dynamic {
  def as[T:ClassTag]: T
  def selectDynamic[T](name: String): DynamicBase
  def updateDynamic(name:String)(value: Any)
}

class ReflectionDynamic( val self: Any ) extends DynamicBase with Proxy {
  def as[T:ClassTag]: T = { implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]].cast( self ) }
   // TODO: cache method lookup for faster access + handle NoSuchMethodError
  def selectDynamic[T](name: String): DynamicBase = {
    val ref = self.asInstanceOf[AnyRef]
    val clazz = ref.getClass
    clazz.getMethod(name).invoke( ref ) match {
      case dyn: DynamicBase => dyn
      case res => new ReflectionDynamic( res )
    }
  }
  def updateDynamic( name: String )( value: Any ) = {
    val ref = self.asInstanceOf[AnyRef]
    val clazz = ref.getClass
    // FIXME: check parameter type, and handle overloads
    clazz.getMethods.find(_.getName == name+"_=").foreach{ meth =>
      meth.invoke( ref, value.asInstanceOf[AnyRef] )
    }
  }
}

object Main extends DynamicBase {

  def as[T:ClassTag]: T = { implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]].cast( this ) }
  private val map = new HashMap[String, DynamicBase]
  def selectDynamic[T](name: String): DynamicBase = { map(name) }
  def updateDynamic(name:String)(value: Any) = {
    val dyn = value match {
      case dyn: DynamicBase => dyn
      case _ => new ReflectionDynamic( value )
    }
    map(name) = dyn
  }

  § > "Überschrift"

  § >> "Unterüberschrift"

  ++ txt """
    Lorem ipsum Abb. ${fig.number} dolor sit amet, consetetur sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """

  val text_1 =
  ++ txtref (() => s"""
    Lorem ipsum Abb. ${Main.figname.number} dolor sit amet, consetetur sadipscing elitr, sed diam
    nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
    sed diam voluptua.
  """)

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

  def main(args: Array[String]) {
    // config
    text_1 newline = true :: true :: Nil  // this needs postfixOps

    // write output file
    (new FraunhoferArticle).write("_output/output.html")
    println("You can now `open _output/output.html`")
  }
}
