package piconot

import java.io.File
import picolib.maze.Maze
import picolib.semantics.Anything
import picolib.semantics.Blocked
import picolib.semantics.East
import picolib.semantics.GUIDisplay
import picolib.semantics.MoveDirection
import picolib.semantics.North
import picolib.semantics.Open
import picolib.semantics.Picobot
import picolib.semantics.RelativeDescription
import picolib.semantics.Rule
import picolib.semantics.South
import picolib.semantics.State
import picolib.semantics.StayHere
import picolib.semantics.Surroundings
import picolib.semantics.TextDisplay
import picolib.semantics.West
import scalafx.application.JFXApp

object DSLImplementation {
  var globalRules:List[Rule] = List()

  /**
    *  Global number will keep track of the number of states there are
    */
  var numberOfStates = -1

  var stringToStateMap: Map[String, State] = Map[String, State]()

  def createRule(currentState: State,
    surroundings: Surroundings,
    direction: MoveDirection,
    newState: State) {

    globalRules = globalRules ::: List(Rule(currentState, surroundings, direction,
      newState))
  }

  var currentState:State = new State("0");

  def nextStateNum():String = {
    numberOfStates += 1
    return numberOfStates.toString()
  }

  // Creates a new state with
  // Needs to check if state already exists. Don't add if it already does
  def inState(newState: =>  String)(rules: => Unit) {
    if (stringToStateMap.contains(newState) )  {
      currentState = stringToStateMap(newState)
    } else {
      currentState = State(nextStateNum)
      stringToStateMap += newState -> currentState
    }
    rules
  }

  var surr: Surroundings = Surroundings(Anything, Anything, Anything, Anything)
  var direc: MoveDirection = North
  var nextS: State = State("0")

  def surroundedBy(surroundings: String)(x: => Unit) = {

    def charToSurr(x:Char):RelativeDescription = {
      x match {
        case close:Char if "NEWS".contains(close) => Blocked
        case 'x' => Open
        case '*' => Anything
      }
    }

    val tmp = surroundings.toList.map(y => charToSurr(y))
    val semanticSurroundings = Surroundings(tmp(0), tmp(1), tmp(2), tmp(3))

    surr = semanticSurroundings
    x
    createRule(currentState, surr,direc,nextS)
  }


  def thenMove(direction: String, newState: String) = {
    def strToMoveDir(x:String):MoveDirection = {
      x match {
        case "N" => North
        case "E" => East
        case "W" => West
        case "S" => South
        case "X" => StayHere
      }
    }

    if(stringToStateMap.contains(newState)) {
      val nextState = stringToStateMap(newState)
      val moveDir = strToMoveDir(direction)
      direc = moveDir
      nextS = nextState
    } else {
      val nextState = State(newState);
      stringToStateMap += newState -> nextState
      val moveDir = strToMoveDir(direction)
      direc = moveDir
      nextS = nextState

    }
  }

  def getRules() = {
    globalRules
  }
}
