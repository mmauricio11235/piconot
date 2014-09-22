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

/**
 *  This is an intentionally bad internal language, but it uses all the parts of
 *  the picolib library that you might need to implement your language
 */

object EmptyRoom extends JFXApp {
  val emptyMaze = Maze("resources" + File.separator + "empty.txt")

  val rules = List(
    Rule(State("0"),
      Surroundings(Anything, Anything, Open, Anything),
      West,
      State("0")),

    Rule(State("0"),
      Surroundings(Open, Anything, Blocked, Anything),
      North,
      State("1")),

    Rule(State("0"),
      Surroundings(Blocked, Open, Blocked, Anything),
      South,
      State("2")),

    Rule(State("1"),
      Surroundings(Open, Anything, Anything, Anything),
      North,
      State("1")),

    Rule(State("1"),
      Surroundings(Blocked, Anything, Anything, Open),
      South,
      State("2")),

    Rule(State("2"),
      Surroundings(Anything, Anything, Anything, Open),
      South,
      State("2")),

    Rule(State("2"),
      Surroundings(Anything, Open, Anything, Blocked),
      East,
      State("3")),

    Rule(State("3"),
      Surroundings(Open, Anything, Anything, Anything),
      North,
      State("3")),

    Rule(State("3"),
      Surroundings(Blocked, Open, Anything, Anything),
      East,
      State("2")))

  object EmptyBot extends Picobot(emptyMaze, rules)
    with TextDisplay with GUIDisplay

  stage = EmptyBot.mainStage
}
