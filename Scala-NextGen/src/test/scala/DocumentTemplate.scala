import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

class DocumentTemplateSpec extends FunSpec with BeforeAndAfterEach {

  private var str: String = _

  override def beforeEach() {
    str = new String("hello world")
  }

  override def afterEach() {
    // delete things
  }

  // package scaltex.abstracts

  describe("A DocumentClass") {

    it("is a abstract type describing a document class") (pending)

    it("defines basis methods which are used by the document class") (pending)

    it("should be inherited to form a document template") (pending)

  }

  describe("A Entity") {

    it("should be a half abstract type to make concrete") (pending)

    it("should contain a template snippet") (pending)

    it("should generate a JSON suitable for the template snippet") (pending)

    it("should be extensible with logic classes") (pending)

  }

  describe("The binding ++") {

    it("implements the document-class's methods and binds it to corrosponding entity") (pending)

    it("should register the entity implicit to the calling areal") (pending)

    it("should return the entity") (pending)

    it("should be able to register the entities reference as property to the corrosponding areal") (pending)

  }

}