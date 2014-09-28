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

  // Make a Rule and add to list of global rules.  All arguments should already
  // be converted into picolib.semantics objects.
  def createRule(currentState: State,
    surroundings: Surroundings,
    direction: MoveDirection,
    newState: State) {

    globalRules = globalRules ::: List(Rule(currentState, surroundings, direction,
      newState))
  }

  // Initial State
  var currentState:State = new State("0");

  // Increment global state number (must be unique State <-> number)
  def nextStateNum():String = {
    numberOfStates += 1
    return numberOfStates.toString()
  }

  // Creates a new state from state name and rules
  def inState(newState: =>  String)(rules: => Unit) {
    // Check to see if we already made this state
    if (stringToStateMap.contains(newState) )  {
      currentState = stringToStateMap(newState)
    } else {
      currentState = State(nextStateNum)
      stringToStateMap += newState -> currentState
    }
    // Parse rules for this state
    rules
  }

  // Variables for rules to fill in
  var surr: Surroundings = Surroundings(Anything, Anything, Anything, Anything)
  var direc: MoveDirection = North
  var nextS: State = State("0")

  // Parse the surroundings
  def surroundedBy(surroundings: String)(x: => Unit) = {

    // Helper to map from chars to semantics.RelativeDescription
    def charToSurr(x:Char):RelativeDescription = {
      x match {
        case close:Char if "NEWS".contains(close) => Blocked
        case 'x' => Open
        case '*' => Anything
      }
    }

    // Make the Surroundings object
    val tmp = surroundings.toList.map(y => charToSurr(y))
    val semanticSurroundings = Surroundings(tmp(0), tmp(1), tmp(2), tmp(3))
    surr = semanticSurroundings

    // run the thenMove part, which sets direc and nextS
    x
    createRule(currentState, surr,direc,nextS)
  }


  // parse the destination state and direction
  def thenMove(direction: String, newState: String) = {

    // Helper to map from input move direction to semantics.MoveDirection
    def strToMoveDir(x:String):MoveDirection = {
      x match {
        case "N" => North
        case "E" => East
        case "W" => West
        case "S" => South
        case "X" => StayHere
      }
    }

    // Create new state as destination if necessary, set direc and nextS
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

  // return the rules
  def getRules() = {
    globalRules
  }
}
