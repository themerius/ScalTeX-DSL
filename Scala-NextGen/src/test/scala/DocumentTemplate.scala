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

  describe("A Areal") {

    it("must have a companion object with the list of related entities") (pending)

    it("is related to an append point, where pages are appended to") (pending)

    it("has as minimum one assigned default page") (pending)

    it("extends ordinary scala objects") (pending)

    it("should own a implicit reference onto itslef") (pending)

    it("should accept a implicit reference onto a Builder") (pending)

    it("should have methods for manipulating the page numbering") (pending)

  }

  describe("A Entity") {

    it("should be a half abstract type to make concrete") (pending)

    it("should contain a template snippet") (pending)

    it("should generate a JSON suitable for the template snippet") (pending)

    it("knows an append point where it will appended to a page") (pending)

    it("should be optional nameable") (pending)

    it("should set it's name as reference to it's assigned object") (pending)

    it("should have a bind method, for binding e.g. to ++") (pending)

    it("should be extensible with logic classes") (pending)

  }

  describe("A Page") {

    it("is a special form of an entity, which groups entities together") (pending)

    it("defines several append points, where entities are hooked in") (pending)

  }

  describe("The Entity binding ++") {

    it("implements the document-class's methods and binds it to corrosponding entity") (pending)

    it("should register the entity implicit to the calling areal") (pending)

    it("should return the entity") (pending)

    it("should be able to register the entities reference as property to the corrosponding areal") (pending)

  }

  describe("The ControlCommand binding !!") {

    it("is able to change the page layout") (pending)

    it("is able to manipulate configuration") (pending)

  }

}