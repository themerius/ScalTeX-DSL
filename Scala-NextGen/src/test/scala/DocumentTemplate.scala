import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.ShouldMatchers._

import scaltex.buildtools._
import play.api.libs.json._

class DocumentTemplateSpec extends FunSpec with BeforeAndAfterEach {

  // PREPARATION

  private var tmplSt: TemplateStock = _

  class EntityA (a: String) extends Entity {
    var appendPoint = "content"
    val templateId = "EntityA"
    def toJson = Json.toJson(
      Map(
        "templateId" -> Json.toJson(templateId),
        "json" -> Json.toJson(
          Map(
            "id" -> Json.toJson(id),
            "a" -> Json.toJson(a)
          )
        )
      )
    ).toString
  }
  private var entityA: EntityA = _

  class PageA extends Page {
    val appendPoints = {
      Map(
        "type" -> "content",
        "templateVariable" -> "appendPoint_content",
        "maxHeight" -> "241.3mm") ::
      Map(
        "type" -> "footer",
        "templateVariable" -> "appendPoint_footer",
        "maxHeight" -> "12.6mm") :: Nil
    }
    val templateId = "PageA"
    val officialName = "A4"
  }
  private var pageA: PageA = _

  class PageB extends Page {
    val appendPoints = {
      Map(
        "type" -> "content",
        "templateVariable" -> "appendPoint_content",
        "maxHeight" -> "241.3mm") ::
      Map(
        "type" -> "footer",
        "templateVariable" -> "appendPoint_footer",
        "maxHeight" -> "12.6mm") :: Nil
    }
    val templateId = "PageB"
    val officialName = "A4Horizontal"
  }
  private var pageB: PageB = _

  class EntityBindingA extends EntityBinding {
    override def $ (refName: String): EntityBindingA = {
      nextRefName = refName
      return this
    }
    def entitya (arg: String)(implicit areal: Areal): EntityA = {
      val e = new EntityA(arg)
      e.bindToAreal(areal)
      registerReference(e)
      return e
    }
  }

  object ArealA extends Tray[EntityPageBase]

  class ArealA(implicit builder: Builder = null) extends Areal {
    val companion = ArealA
    var appendPoint = "ArealA"
    val defaultPage = new PageA
    setCurrentPage(defaultPage)
    val ++ = new EntityBindingA
    def newpage = {
      addToList(getCurrentPage)
      this
    }
    def page_to (p: Page) = {
      setCurrentPage(p)
      addToList(getCurrentPage)
      this
    }
  }

  object BuilderA extends Tray[Areal]

  class BuilderA extends Builder {
    val companion = BuilderA
    val allPages = new PageA :: new PageB :: Nil
    def build = None
    def write (dest: String) = None
  }

  // SETUP

  override def beforeEach() {
    tmplSt = new TemplateStock
    entityA = new EntityA("test")
    pageA = new PageA
    pageB = new PageB
  }

  // TEARDOWN

  override def afterEach() {
    EntityIdCount.id = 0
    ArealA.tray.clear
    BuilderA.tray.clear
  }

  // TESTCODE

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
      entityA.isInstanceOf[EntityPageBase] should be === true
      entityA.isInstanceOf[Entity] should be === true
    }

    it("should contain a template id name") {
      entityA.templateId should be === "EntityA"
    }

    it("knows an append point where it will appended to a page") {
      entityA.appendPoint should be === "content"
    }

    it("should have an unique id") {
      entityA.id should be === 1
      (new EntityA("x")).id should be === 2
      (new EntityA("y")).id should be === 3
    }

    it("should generate a JSON suitable for the template snippet") {
      entityA.toJson should be === """{"templateId":"EntityA","json":{"id":1,"a":"test"}}"""
    }

    it("should be optional nameable via a $ method") {
      entityA.refName should be === null
      entityA.$("myname")
      entityA.refName should be === "myname"
    }

    it("should be able to set it's name as property to an areal-object") {
      val areal = new ArealA
      entityA.$("myname")
      entityA.bindToAreal(areal)
      entityA.registerReference
      (areal.myname.## == entityA.##) should be === true
    }

  }

  describe("A Page") {

    it("is a special form of an entity") {
      pageA.isInstanceOf[EntityPageBase] should be === true
    }

    it("defines several append points, where entities are hooked in") {
      pageA.appendPoints.isInstanceOf[List[Map[String,String]]] should be === true
    }

    it("defines for every append point a type, templateVariable and maximum height") {
      pageA.toJson should be === "{\"template\":\"PageA\"," +
        "\"appendPoints\":[" +
          "{\"type\":\"content\"," +
           "\"templateVariable\":\"appendPoint_content\"," +
           "\"maxHeight\":\"241.3mm\"}," +
          "{\"type\":\"footer\"," +
           "\"templateVariable\":\"appendPoint_footer\"," +
           "\"maxHeight\":\"12.6mm\"}]}"
    }

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

    describe("The Entity binding ++") {

      it("binds the entity-method to corrosponding the entity-class") {
        val b = new EntityBindingA
        b.entitya("test")(new ArealA).isInstanceOf[EntityA] should be === true
      }

      it("should register the entity implicit to the calling areal's companion object's list") {
        implicit val areal = new ArealA
        val b = new EntityBindingA
        val ret = b.entitya("test")
        ArealA.get(0) should be theSameInstanceAs (ret)
      }

      it("should be able to register the entities reference as property to the corrosponding areal") {
        implicit val areal = new ArealA
        val b = new EntityBindingA
        var ret = b entitya "test" $ "myname"
        (areal.myname.## == ret.##) should be === true

        ret = b $ "othername" entitya "othertest"
        (areal.othername.## == ret.##) should be === true
      }

    }

    it("is related to an append point, where pages are appended to") {
      (new ArealA).appendPoint should be === "ArealA"
    }

    it("has as minimum one assigned default page") {
      (new ArealA).getCurrentPage.isInstanceOf[PageA] should be === true
    }

    it("extends ordinary scala objects") {
      object ArealInstance extends ArealA
      ArealInstance.isInstanceOf[ArealA] should be === true
    }

    it("should own a implicit reference onto itslef") {
      val aa = new ArealA
      aa.areal should be theSameInstanceAs (aa)
    }

    it("is able to change the page layout") {
      val aa = new ArealA
      aa.getCurrentPage.isInstanceOf[PageA] should be === true
      aa.change page_to pageB
      aa.getCurrentPage should be theSameInstanceAs (pageB)
    }

    describe("The Generator") {

      it("produces out of the areal entity-list a json datastructure") {
        object ArealInstance extends ArealA {
          ++ entitya "test1"
          ++ entitya "test2"
          change page_to pageB
          ++ entitya "test3"
          ++ entitya "test4"
          newpage
          ++ entitya "test5"
          ++ entitya "test6"
        }
        val result = (new Generator(ArealInstance)).generate
        val expect = """
          var ArealA = [
            {
              pageType: "PageA",
              entities: [
                {"templateId":"EntityA","json":{"id":2,"a":"test1"}},
                {"templateId":"EntityA","json":{"id":3,"a":"test2"}},
              ]
            },
            {
              pageType: "PageB",
              entities: [
                {"templateId":"EntityA","json":{"id":4,"a":"test3"}},
                {"templateId":"EntityA","json":{"id":5,"a":"test4"}},
              ]
            },
            {
              pageType: "PageB",
              entities: [
                {"templateId":"EntityA","json":{"id":6,"a":"test5"}},
                {"templateId":"EntityA","json":{"id":7,"a":"test6"}},
              ]
            }
          ];
        """.replaceAllLiterally("  ", "").replaceAllLiterally("\n", "")
        result should be === expect
      }

    }

    it("should accept a implicit reference onto a Builder") {
      (new ArealA).builder should be === null

      implicit val builder = new BuilderA
      val areal = new ArealA
      areal.builder should be theSameInstanceAs (builder)
    }

  }

  describe("A Builder") {

    it("must have a companion object with a list of related areals, in the right predefined order") {
      implicit val builder = new BuilderA
      val areal = new ArealA
      BuilderA.get(0) should be theSameInstanceAs (areal)
    }

    it("should own a implicit reference onto itslef") {
      val builder = new BuilderA
      builder.builder should be theSameInstanceAs (builder)
    }

    it("should set the areal append points in the right order") {
      implicit val builder = new BuilderA
      val a1 = new ArealA
      val a2 = new ArealA
      BuilderA.get(0) should be theSameInstanceAs (a1)
      BuilderA.get(1) should be theSameInstanceAs (a2)
    }

    it("should know all pages available for this template") {
      val builder = new BuilderA
      builder.allPages(0).isInstanceOf[PageA] should be === true
    }

    it("calls the Generator for every Areal to form the js entity instances") {
      object BuilderObject extends BuilderA {
        val areal = new ArealA
        ArealObject1
        ArealObject2
      }
      object ArealObject1 extends ArealA {
        ++ entitya "test1"
      }
      object ArealObject2 extends ArealA {
        ++ entitya "test2"
      }

      BuilderObject.generateJsEntityInstances.mkString("") should be === """
        var ArealA = [
          {
            pageType: "PageA",
            entities: [
              {"templateId":"EntityA","json":{"id":2,"a":"test1"}},
              {"templateId":"EntityA","json":{"id":3,"a":"test2"}},
            ]
          }
        ];
      """.replaceAllLiterally("  ", "").replaceAllLiterally("\n", "")
    }

    it("should assemble the page factory") {
      val builder = new BuilderA
      builder.generateJsPageFactory should be === """
      var pageFactory = new scaltex.PageFactory({
        A4: {
          "template":"PageA",
          "appendPoints":[
            {
              "type":"content",
              "templateVariable":"appendPoint_content",
              "maxHeight":"241.3mm"
            },
            {
              "type":"footer",
              "templateVariable":"appendPoint_footer",
              "maxHeight":"12.6mm"
            }
          ]
        },
        A4Horizontal: {
          "template":"PageB",
          "appendPoints":[
            {
              "type":"content",
              "templateVariable":"appendPoint_content",
              "maxHeight":"241.3mm"
            },
            {
              "type":"footer",
              "templateVariable":"appendPoint_footer",
              "maxHeight":"12.6mm"
            }
          ]
        },
      });
      """.replaceAllLiterally("  ", "").replaceAllLiterally("\n", "")
    }

    it("should assemble the configuration in js for the document name") (pending)

    it("should assemble the configuration in js for the special entities like page footer") (pending)

    it("should have a `build` method for generating the document") (pending)

    it("should have a `write` method to save the document as html") (pending)

  }

/*
  describe("A DocumentClass") {

    it("is a abstract type describing a document class") (pending)

    it("defines areals which are minimum known to the document class") (pending)

    it("defines pages which are minimum known to the document class") (pending)

    it("defines methods which map onto entities, this implies a minimum set for the document class") (pending)

  }
*/


}