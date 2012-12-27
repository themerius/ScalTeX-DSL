import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

class LogicSpec extends FunSpec with BeforeAndAfterEach {

  private var str: String = _

  override def beforeEach() {
    str = new String("hello world")
  }

  override def afterEach() {
    // delete things
  }

  describe("The Logic trait") {

    it("is a abstract marker") (pending)

  }

  describe("A FigureNumber") {

    it("should extend an entity with an figure number variable") (pending)

    it("should count figures and set the appropriate number for the particular figure") (pending)
  }

  describe("A SectionNumber") {

    it("should extend an entity with a section variable") (pending)

    it("should count sections entities hierachial") (pending)

    it("should have a `getSectionNumbering` method") (pending)

    describe("A Chapter") {
      it("should count all chapters") (pending)
    }

    describe("A Section") {
      it("should count in it's capter all sections") (pending)
    }

    describe("A SubSection") {
      it("should count in it's section all subsections") (pending)
    }

    describe("A SubSubSection") {
      it("should count in it's subsection all subsubsections") (pending)
    }

    describe("A TableOfContents") {
      it("should filter all instances of SectionNumber returned as List") (pending)
    }

  }

  describe("A PythonScript") {

    it("should construct a py-file out of a string") (pending)

    it("should be able to execute the py file and its catch the std:out") (pending)

  }


}