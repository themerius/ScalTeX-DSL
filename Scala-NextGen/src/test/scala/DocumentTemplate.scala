import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.ShouldMatchers._

import scaltex.buildtools._
import play.api.libs.json._

class DocumentTemplateSpec extends FunSpec with BeforeAndAfterEach {

  private var tmplSt: TemplateStock = _

  class EntityA (a: String) extends Entity {
    var appendPoint = "content"
    val templateId = "EntityA"
    def toJson = Json.toJson(Map(
      "templateId" -> Json.toJson(templateId),
      "a" -> Json.toJson(a))).toString
  }
  private var entityA: EntityA = _

  override def beforeEach() {
    tmplSt = new TemplateStock
    entityA = new EntityA("test")
  }

  describe("A TemplateStock") {

    it("should have a `headerTemplate` getter/setter") {
      tmplSt.headerTemplate should be === ("<html>")

      tmplSt.headerTemplate = "<html><head>...</head>"
      tmplSt.headerTemplate should be === ("<html><head>...</head>")
    }

    it("getting all entity snippets as String with `entityTemplate` method") {
      tmplSt.addTemplateEntity("heading", "<script>...heading...</script>")
      tmplSt.addTemplateEntity("text", "<script>...text...</script>")

      tmplSt.entityTemplate should include ("<script>...heading...</script>")
      tmplSt.entityTemplate should include ("<script>...text...</script>")
    }

    it("should have a `footerTemplate` getter/setter") {
      tmplSt.footerTemplate should be === ("</html>")

      tmplSt.footerTemplate = "...</html>"
      tmplSt.footerTemplate should be === ("...</html>")
    }

  }

  describe("A Entity") {

    it("should be a half abstract type to make concrete") {
      entityA.isInstanceOf[Entity] should be === true
    }

    it("should contain a template id name") {
      entityA.templateId should be === "EntityA"
    }

    it("knows an append point where it will appended to a page") {
      entityA.appendPoint should be === "content"
    }

    it("should generate a JSON suitable for the template snippet") {
      entityA.toJson should be === """{"templateId":"EntityA","a":"test"}"""
    }

    it("should be optional nameable via a $ method") {
      entityA.refName should be === null
      entityA.$("myname")
      entityA.refName should be === "myname"
    }

    it("should be able to set it's name as reference to an areal-object") (pending)

  }

  describe("A Tray") {

    it("should have a list buffer for type T, for extending companion objects") {
      object TestTray extends Tray[Int]
      TestTray.add(1)
      TestTray.add(2)
      TestTray.get should be === List(1, 2)
    }

  }

  describe("A Areal") {

    it("must have a companion object with a list of related entities, in the right predefined order") (pending)

    it("calls and uses the generator") (pending)

    it("is related to an append point, where pages are appended to") (pending)

    it("has as minimum one assigned default page") (pending)

    it("extends ordinary scala objects") (pending)

    it("should own a implicit reference onto itslef") (pending)

    it("should accept a implicit reference onto a Builder") (pending)

    it("should have methods for manipulating the page numbering") (pending)

    it("is able to change the page layout") (pending)

    it("is able to manipulate configuration") (pending)

  }

  describe("A Builder") {

    it("must have a companion object with a list of related areals, in the right predefined order") (pending)

    it("should set the areal append points in the right order") (pending)

    it("should fetch for every areal the JSON of each entity of the areal, to instantiate them in the js") (pending)

    it("should assemble the configuration in js for the page layouts") (pending)

    it("should assemble the configuration in js for the document name") (pending)

    it("should assemble the configuration in js for the special entities like page footer") (pending)

    it("should have a `build` method for generating the document") (pending)

    it("should have a `write` method to save the document as html") (pending)

    it("should own a implicit reference onto itslef") (pending)

  }

/*
  describe("A DocumentClass") {

    it("is a abstract type describing a document class") (pending)

    it("defines areals which are minimum known to the document class") (pending)

    it("defines pages which are minimum known to the document class") (pending)

    it("defines methods which map onto entities, this implies a minimum set for the document class") (pending)

  }
*/

  describe("A Page") {

    it("is a special form of an entity, which groups entities together") (pending)

    it("defines several append points, where entities are hooked in") (pending)

  }

  describe("The Generator") {

    it("reads the lists with entities and processes them") (pending)

  }

  describe("The Entity binding ++") {

    it("implements the document-class's methods and binds it to corrosponding entity") (pending)

    it("should register the entity implicit to the calling areal") (pending)

    it("should return the entity") (pending)

    it("should be able to register the entities reference as property to the corrosponding areal") (pending)

  }

}