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

object DSLImplementation extends App {
  var globalRules:List[Rule] = List.empty[Rule]

  /**
   *  Global number will keep track of the number of states there are
   */
	var numberOfStates = 0

	var stringToStateMap: Map[String, MyState] = Map[String, MyState]()

  /**
   * Need a way to know how many states there are.
   */

  class MyState(stateName: String) {

	  // We map the given name to the state itself
	stringToStateMap += stateName -> this

    val stateNumber = numberOfStates
    numberOfStates += 1

    var surr: Surroundings = Surroundings(Anything, Anything, Anything, Anything)
    var direc: MoveDirection = North
    var nextS: State = State("-1")

    def passRules(rules: => Unit) = rules



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
	  createRule(surr,direc,nextS)
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

      // make new MyState if i haven't already (dummy) otherwise just get from
      // map
      if(stringToStateMap.contains(newState)) {
        val nextState = stringToStateMap(newState)
        val semanticNewState = State(nextState.stateNumber.toString())
        val moveDir = strToMoveDir(direction)
        direc = moveDir
        nextS = semanticNewState
      } else {
        val nextState = new MyState(newState);
        val semanticNewState = State(nextState.stateNumber.toString())
        val moveDir = strToMoveDir(direction)
        direc = moveDir
        nextS = semanticNewState

      }
    }

    /**
     * Need to go from dummy state (strings) to PicoBot objects
     *
     * Need to create dummy state that hasn't been made yet.
     */
    def createRule(surroundings: Surroundings,
                   direction: MoveDirection,
                   newState: State) {


      var testState = State(this.stateNumber.toString())
      globalRules = Rule(testState, surroundings, direction, newState)::globalRules
    }
  }


  // Creates a new state with
  // Needs to check if state already exists. Don't add if it already does
  def inState(newState: =>String)(rules: => Unit){

    println("YO")
    println(newState);
    if (stringToStateMap.contains(newState)){
    	stringToStateMap(newState).passRules(rules)
    }
    else{
    //Only do this step if doesnt exist in map
    new MyState(newState).passRules(rules)
    }

  }

  def surroundedBy(surroundings: String)(x: => Unit) = {}
  def thenMove(s: String, x: String) ={}


}
