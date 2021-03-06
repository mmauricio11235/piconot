package piconot

import java.io.File

import picolib.maze.Maze
import picolib.semantics.Anything
import picolib.semantics.Blocked
import picolib.semantics.East
import picolib.semantics.GUIDisplay
import picolib.semantics.North
import picolib.semantics.Open
import picolib.semantics.Picobot
import picolib.semantics.Rule
import picolib.semantics.South
import picolib.semantics.State
import picolib.semantics.Surroundings
import picolib.semantics.TextDisplay
import picolib.semantics.West
import scalafx.application.JFXApp
import scalafx.application.JFXApp

import DSLImplementation.surroundedBy
import DSLImplementation.getRules
import DSLImplementation.inState
import DSLImplementation.thenMove
import DSLImplementation.globalRules

object DSLEmptyRoom extends JFXApp {
  val emptyMaze = Maze("resources" + File.separator + "empty.txt")

  inState("up") {
    surroundedBy("xx**") {thenMove("N", "up")}
    surroundedBy("Nx**") {thenMove("E", "down")}
  }
  inState("down") {
    surroundedBy("*x*x") {thenMove("S", "down")}
    surroundedBy("*x*S") {thenMove("X", "up")}
    surroundedBy("*Exx") {thenMove("S", "down")}
    surroundedBy("xExS") {thenMove("W", "goingWest")}
  }
  inState("goingWest") {
    surroundedBy("**x*") {thenMove("W", "goingWest")}
    surroundedBy("**W*") {thenMove("X", "up")}
  }

  object EmptyBot extends Picobot(emptyMaze, getRules())
    with TextDisplay with GUIDisplay

  stage = EmptyBot.mainStage
}
