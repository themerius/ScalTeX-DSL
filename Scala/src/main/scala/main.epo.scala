package scaltex.main.epo

import scaltex.util._
import scaltex.util.$StringContext._
import scaltex.template.epo._

import scala.language.postfixOps  // Needed for TODO

object Main {

  Chapter_1

  def main(args: Array[String]) {
    // write output file
    (new EpoPatent).write("_output/output.html")
    println("You can now `open _output/output.html`")
  }
}

object Chapter_1 extends DynamicObject {

  § > "Description"

  § >> "Technical Field"

  ++ txt $"""
    This invention relates to a remedy for overactive bladder comprising
    (R)-2-(2-arninothiazol-4-yl)-4'-[2-[(2- hydroxy-2-phenylethyl) amino]
    ethyl] acetic acid anilide or a salt thereof as an active ingredient.
  """ newline = true :: Nil  // TODO: do such things automatically

  § >> "Background Art"

  ++ txt """
    Bladder of mammals is under a dual control of autonomic nerve and detrusor
    relaxes via an adrenaline β receptor by stimulation of sympathetic nerve
    upon urination while, upon excretion of urine, it contracts via a muscarine
    receptor by stimulation of parasympathetic nerve. As a remedy for overactive
    bladder resulted when the dual control as such is unbalanced,
    anticholinergic agents such as propiverine hydrochloride and oxybutynin
    hydrochloride have been mostly used at present. However, there are
    intractable cases showing resistance to such compounds and there are side
    effects caused by anticholinergic agents such as urinary dysfunction and
    dry mouth and, therefore, it is the current status that satisfactory
    clinical results are not always achieved.
  """

  ++ figure(
    src="https://raw.github.com/themerius/ScalTeX-templates/master/patent_epo/pics/mol1.png",
    lines=9
  )

  ++ txt """
    Further, as a result of increase in population of aged people in recent
    years, numbers of patients suffering from overactive bladder are increasing
    year by year and, in view of QOL (quality of life) of patients, there has
    been a brisk demand for the development of new drugs.
  """
}
