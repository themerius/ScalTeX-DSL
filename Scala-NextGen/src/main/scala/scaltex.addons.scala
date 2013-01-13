package scaltex.addons

import java.io.{OutputStreamWriter, FileOutputStream}
import scala.sys.process._

class PythonScript (val script: String, leadingWhitespace: String="    ") {
  val filename = java.security.MessageDigest.getInstance("MD5").digest(script.getBytes)
  val filepath = "_output/py" + filename.mkString("") + ".py"

  def trimmedScript: String = {
    val lines = script.trim.split("\n")
    lines.map(_.replaceFirst(leadingWhitespace, "")).mkString("\n")
  }

  def createFile: String = {
    val file = new OutputStreamWriter(
      new FileOutputStream(filepath), "UTF-8")
    file.append(trimmedScript)
    file.close

    return filepath
  }

  def exec: String = ("python " + createFile).!!

  def rm: String = ("rm " + filepath).!!

  def run: String = {
    val out = exec
    rm
    out
  }
}
