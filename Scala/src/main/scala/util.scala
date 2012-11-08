package scaltex.util

// Dynamic Object
// Source: http://stackoverflow.com/questions/13253557/

import language.dynamics
import scala.collection.mutable.HashMap
import scala.reflect.ClassTag

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

class DynamicObject extends DynamicBase {
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
}


// String Parser

import scala.tools.reflect.ToolBox
import scala.reflect.runtime.{currentMirror => m}

class StringExtractor (s: String) {
  val regex = "\\[(.*?)\\]".r
  def extract: List[String] = regex.findAllIn(s).toList
  def stringsForEval: List[String] = extract.map(_.split("\\]")(0).split("\\[")(1))
  def resolve: List[String] = {
    val tb = m.mkToolBox()
    stringsForEval.map(x => tb.parse(x)).map(x => tb.eval(x)).map(_.toString)
  }
  def replace: String = {
    val zip = extract.zip(resolve)
    var res = s
    for (item <- zip) {
      res = res.replace(item._1, item._2)
    }
    res
  }
}

