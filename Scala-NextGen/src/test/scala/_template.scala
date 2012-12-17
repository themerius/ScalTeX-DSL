import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

import scala.collection.mutable.Stack

class ExampleSpec extends FunSpec with BeforeAndAfterEach {

  private var str: String = _

  override def beforeEach() {
    str = new String("hello world")
  }

  override def afterEach() {
    // delete things
  }

  describe("A String") {

    it("should equal the 'hello world' string literal") {
      assert(str === "hello world")
    }

    it("should know it's length") {
      assert(str.length === 11)
    }
  }
}