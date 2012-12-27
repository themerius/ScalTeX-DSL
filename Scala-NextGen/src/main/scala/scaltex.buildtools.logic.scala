package scaltex.buildtools.logic

import scaltex.buildtools.Entity

trait Logic {
  def execLogic: Unit = None
}

trait FigureNumber extends Logic with Entity {
  var figureNumber: Int = -1
  override def execLogic =
    figureNumber = areal.companion.get.filter(_.isInstanceOf[FigureNumber]).length
}