package scaltex.test

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.ShouldMatchers._

import scaltex.buildtools.logic._


class LogicSpec extends FunSpec with BeforeAndAfterEach {

  private var areal: ArealA = _

  override def beforeEach() {
    areal = new ArealA
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

    class FigureEntity (a: String) extends EntityA(a) with FigureNumber

    it("should extend an entity with an figure number variable") {
      val fig = new FigureEntity("test")
      fig.figureNumber should be === -1
      fig.bindToAreal(areal)
      fig.figureNumber should be === 1
    }

    it("should count figures and set the appropriate number for the particular figure") {
      val fig1 = new FigureEntity("test1")
      val fig2 = new FigureEntity("test2")
      fig1.bindToAreal(areal)
      fig2.bindToAreal(areal)
      fig1.figureNumber should be === 1
      fig2.figureNumber should be === 2
    }
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