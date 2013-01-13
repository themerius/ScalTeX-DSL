package scaltex.test.addons

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.ShouldMatchers._

import scaltex.addons._

class AddonSpec extends FunSpec with BeforeAndAfterEach {

  private val script = """
    x = 'this is python'
    print x
  """

  private val trimmedScript = "x = 'this is python'\nprint x"

  override def beforeEach() {
    ;
  }

  override def afterEach() {
    ;
  }

  describe("A PythonScript") {

    it("should be able to trim unnecessary whitespace at the beginning") {
      val py = new PythonScript(script)
      py.trimmedScript should be === trimmedScript
    }

    it("should construct a py-file out of a string") {
      val py = new PythonScript(script)
      py.createFile
      val source = scala.io.Source.fromFile(py.filepath)
      source.mkString should be === trimmedScript
      source.close ()
      (new java.io.File(py.filepath)).delete()
    }

    it("should be able to execute the py file and its catch the std:out") {
      val py = new PythonScript(script)
      py.run should be === "this is python\n"
    }

  }


}