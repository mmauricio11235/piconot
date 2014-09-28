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

object DSLMazeRoom extends JFXApp {
  val maze = Maze("resources" + File.separator + "maze.txt")

  inState("goingNorth") {
    surroundedBy("xE**") {thenMove("N", "goingNorth")}
    surroundedBy("*x**") {thenMove("E", "goingEast")}
    surroundedBy("NE**") {thenMove("X", "goingWest")}
  }

  inState("goingEast") {
    surroundedBy("*x*S") {thenMove("E", "goingEast")}
    surroundedBy("***x") {thenMove("S", "goingSouth")}
    surroundedBy("*E*S") {thenMove("X", "goingNorth")}
  }

  inState("goingSouth") {
    surroundedBy("**Wx") {thenMove("S", "goingSouth")}
    surroundedBy("**x*") {thenMove("W", "goingWest")}
    surroundedBy("**WS") {thenMove("X", "goingEast")}
  }

  inState("goingWest") {
    surroundedBy("N*x*") {thenMove("W", "goingWest")}
    surroundedBy("x***") {thenMove("N", "goingNorth")}
    surroundedBy("N*W*") {thenMove("X", "goingSouth")}
  }

  object MazeBot extends Picobot(maze, getRules())
    with TextDisplay with GUIDisplay

  stage = MazeBot.mainStage
}
