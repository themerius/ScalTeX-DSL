package scaltex.buildtools.logic

import scaltex.buildtools.{Entity, Areal}

trait Logic {
  def execLogic: Unit = None
}

trait FigureNumber extends Logic with Entity {
  var figureNumber: Int = -1
  override def execLogic =
    figureNumber = areal.companion.get.filter(_.isInstanceOf[FigureNumber]).length
}

trait SectionNumber extends Logic with Entity {
  var sectionNumber: String = _
  val h: String
}

trait SectionNumberAlgorithm extends SectionNumber {
  def getSectionNumber: String = {
    var h1Count = 0
    var h2Count = 0
    var h3Count = 0
    var h4Count = 0
    val sections = areal.companion.get.filter(_.isInstanceOf[SectionNumber])
    for (section <- sections) {
      section match {
        case _: Chapter => {
          h1Count += 1
          h2Count = 0
        }
        case _: Section => {
          h2Count += 1
          h3Count = 0
        }
        case _: SubSection => {
          h3Count += 1
          h4Count = 0
        }
        case _: SubSubSection => {
          h4Count += 1
        }
        case _ => throw new Exception("Unknown SectionNumber.")
      }
    }

    var ret = ""
    if (this.isInstanceOf[Chapter])
      ret += h1Count
    if (this.isInstanceOf[Section])
      ret += h1Count + "." + h2Count
    if (this.isInstanceOf[SubSection])
      ret += h1Count + "." + h2Count + "." + h3Count
    if (this.isInstanceOf[SubSubSection])
      ret += h1Count + "." + h2Count + "." + h3Count + "." + h4Count
    return ret
  }
  override def execLogic = {
    sectionNumber = getSectionNumber
  }
}

trait Chapter extends SectionNumberAlgorithm {
  val h = "h1"
}
trait Section extends SectionNumberAlgorithm {
  val h = "h2"
}
trait SubSection extends SectionNumberAlgorithm {
  val h = "h3"
}
trait SubSubSection extends SectionNumberAlgorithm {
  val h = "h4"
}
