package piconot

import picolib.maze._
import picolib.semantics._
import java.io.File

object PicobotSyntax {

    def main(args: Array[String]) {
        println(new java.io.File( "." ).getCanonicalPath)
        
        val mazeFilename = "resources" + File.separator + "empty.txt"
                            
        val maze = Maze(io.Source.fromFile(mazeFilename).getLines().toList)
        
        val rules = List( Rule(State("0"), 
                               Surroundings(Anything, Anything, Open, Anything), 
                               West,
                               State("0")),
                               
                          Rule(State("0"), 
                               Surroundings(Open, Anything, Blocked, Anything), 
                               North,
                               State("1")), 
                               
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
                               State("2"))            
        )
       
        new Picobot(maze, rules).run()
     }

}
