package scaltex.test.logic

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.ShouldMatchers._

import scaltex.buildtools.logic._
import scaltex.test.buildtools.{ArealA}
import scaltex.buildtools.{Tray, Entity, EntityPageBase}

object ArealAA extends Tray[EntityPageBase]

class EntityA extends Entity {
  override val id: Int = -1  // otherwise causes concurrency issues
  var appendPoint = "content"
  val templateId = "EntityA"
  def toJson = "{}"
}

class LogicSpec extends FunSpec with BeforeAndAfterEach {

  private var areal: ArealA = _

  override def beforeEach() {
    areal = new ArealA {
      override val companion = ArealAA
    }
  }

  override def afterEach() {
    areal.companion.tray.clear
  }

  describe("The Logic trait") {

    it("is a abstract marker") {
      class L extends Logic
      (new L).isInstanceOf[Logic] should be === true
    }

  }

  describe("A FigureNumber") {

    class FigureEntity extends EntityA with FigureNumber

    it("should extend an entity with an figure number variable") {
      val fig = new FigureEntity
      fig.figureNumber should be === -1
      fig.bindToAreal(areal)
      fig.figureNumber should be === 1
    }

    it("should count figures and set the appropriate number for the particular figure") {
      val fig1 = new FigureEntity
      val fig2 = new FigureEntity
      fig1.bindToAreal(areal)
      fig2.bindToAreal(areal)
      fig1.figureNumber should be === 1
      fig2.figureNumber should be === 2
    }
  }

  describe("A SectionNumber") {

    class SectionNumberEntity extends EntityA with SectionNumber {
      sectionNumber = "1"
      val h = "h1"
    }
    class ChapterEntity extends EntityA with Chapter
    class SectionEntity extends EntityA with Section
    class SubSectionEntity extends EntityA with SubSection
    class SubSubSectionEntity extends EntityA with SubSubSection

    it("should extend an entity with a section variable") {
      (new SectionNumberEntity).sectionNumber should be === "1"
    }

    it("should extend an entity with a h variable") {
      (new SectionNumberEntity).h should be === "h1"
    }

    describe("A Chapter") {

      it("should count all chapters") {
        val chp = new ChapterEntity
        chp.bindToAreal(areal)
        chp.sectionNumber should be === "1"
        chp.h should be === "h1"
      }

    }

    describe("A Section") {

      it("should count in it's capter all sections") {
        val chp = new ChapterEntity
        val sec = new SectionEntity
        chp.bindToAreal(areal)
        sec.bindToAreal(areal)
        sec.sectionNumber should be === "1.1"
        sec.h should be === "h2"
      }

    }

    describe("A SubSection") {

      it("should count in it's section all subsections") {
        val chp = new ChapterEntity
        val sec = new SectionEntity
        val ssc = new SubSectionEntity
        chp.bindToAreal(areal)
        sec.bindToAreal(areal)
        ssc.bindToAreal(areal)
        ssc.sectionNumber should be === "1.1.1"
        ssc.h should be === "h3"
      }

    }

    describe("A SubSubSection") {

      it("should count in it's subsection all subsubsections") {
        val chp = new ChapterEntity
        val sec = new SectionEntity
        val ssc = new SubSectionEntity
        val sss = new SubSubSectionEntity
        chp.bindToAreal(areal)
        sec.bindToAreal(areal)
        ssc.bindToAreal(areal)
        sss.bindToAreal(areal)
        sss.sectionNumber should be === "1.1.1.1"
        sss.h should be === "h4"
      }

    }

    describe("A TableOfContents") {
      it("should filter all instances of SectionNumber returned as List") (pending)
      it("extends an areal class") (pending)
    }

  }

  describe("A PythonScript") {

    it("should construct a py-file out of a string") (pending)

    it("should be able to execute the py file and its catch the std:out") (pending)

  }


}