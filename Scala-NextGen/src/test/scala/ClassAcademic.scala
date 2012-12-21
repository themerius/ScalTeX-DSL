import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

class AcademicDocumentClassSpec extends FunSpec with BeforeAndAfterEach {

  private var str: String = _

  override def beforeEach() {
    str = new String("hello world")
  }

  override def afterEach() {
    // delete things
  }

  describe("AcademicEntities") {
    it("defines a § method for a chapter") (pending)
    it("defines a §§ method for a section") (pending)
    it("defines a §§§ method for a subsection") (pending)
    it("defines a §§§§ method for a subsubsection") (pending)

    it("defines a txt method for a text entity") (pending)

    it("defines a figure method for a figure entity") (pending)
  }

  describe("AcademicAreals") {
    it("should have a TableOfContents areal") (pending)
    it("should have a Document areal") (pending)
    it("should have Titlepage areal") (pending)
  }

  describe("AcademicPages") {
    it("defines a A4Horizontal value") (pending)
    it("defines a A4Vertical value") (pending)
  }
}