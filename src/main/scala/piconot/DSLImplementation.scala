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
 *  To Do List
 *  	1.) inState
 *   	2.) thenMove (combination of move and then
 */
object DSLImplementation extends App {

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
	
	 // Assigning a number to the string name
    // NEED TO DO CHECK IF IT ALREADY EXISTS FIRST
	// OTHERWISE CREATE NEW
	val stateNumber = numberOfStates
    numberOfStates += 1
    
    def passRules(rules: => Unit) = rules
    
    
    
	//
    def surroundedBy(surroundings: String)(x: => Unit) = {
	  val n = x
	  createRule(surroundings, n(0), n(1))
	}
    
   
    def thenMove(direction: String, newState: String)  {
	
      List(direction, newState)
    }
    
    /**
     * Need to go from dummy state (strings) to PicoBot objects
     * 
     * Need to create dummy state that hasn't been made yet. 
     */
    def createRule(surroundings: String, direction: String, newState: String){
      
      
      Rule(State(this.stateNumber.toChar().toString())), 
    }
  }


  // Creates a new state with
  // Needs to check if state already exists. Don't add if it already does
  def inState(newState: =>  String)(rules: => Unit){
 
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


  /**
   *  Definition of a state
   */
  inState("stuckOnLeft") {
    surroundedBy("NExx"){thenMove ("W", "confused")}
    surroundedBy("Nxxx") (thenMove ("W", "confused"))
    surroundedBy("xxxx") (thenMove ("W", "notConfused"))
  }
}