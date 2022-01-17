import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.util.*;


/**
* Maze represents a maze that can be navigated. The maze
* should indicate its start and end squares, and where the
* walls are. 
*
* Eventually, this class will be able to load a maze from a
* file, and solve the maze.
* The starter code has part of the implementation of load, but
* it does not read and store the information about where the walls of the maze are.
*
*/
public class Maze { 
    //Number of rows in the maze.
    private int numRows;
    
    //Number of columns in the maze.
    private int numColumns;
    
    //Grid coordinates for the starting maze square
    private int startRow;
    private int startColumn;
    
    //Grid coordinates for the final maze square
    private int finishRow;
    private int finishColumn;
    
    //**************YOUR CODE HERE******************
    //You'll likely want to add one or more additional instance variables
    //to store the squares of the maze
    private List<MazeSquare> squaresInMaze;

    /** Set solutionSet will store the solution for maze file into an accessable ADT
    * will be helptul in printing maze solution! **/ 
    private Set<MazeSquare> solutionSet; 

    //*************CONSTRUCTOR*****************
    /**
     * Creates an empty maze with no squares.
     */
    public Maze() {
      //You can add any code you need to initialize instance 
      this.numRows = 0; 
      this.numColumns = 0;
      this.startRow = 0;
      this.startColumn = 0;
      this.finishRow = 0;
      this.finishColumn = 0;
      //variables you've added.
      this.squaresInMaze = new ArrayList<MazeSquare>();
      this.solutionSet = new HashSet<MazeSquare>();
    } 


    //**************CLASS*METHODS***************
    /*******************************************
     * Loads the maze that is written in the given fileName.
     * Returns true if the file in fileName is formatted correctly
     * (meaning the maze could be loaded) and false if it was formatted
     * incorrectly (meaning the maze could not be loaded). The correct format
     * for a maze file is given in the assignment description. Ways 
     * that you should account for a maze file being incorrectly
     * formatted are: one or more squares has a descriptor that doesn't
     * match  *, 7, _, or | as a descriptor; the number of rows doesn't match
     * what is specified at the beginning of the file; or the number of
     * columns in any row doesn't match what's specified at the beginning
     * of the file; or the start square or the finish square is outside of
     * the maze. You can assume that the file does start with the number of
     * rows and columns.
     * 
     */
    public boolean load(String fileName) { 
      File inputFile = new File(fileName);

      Scanner scanner = null;
      try{
        scanner = new Scanner(inputFile);
      } catch (FileNotFoundException e) {
        System.err.println("ERROR: >>> " + fileName 
            + " File not found :(!\n"+ e);
        return false;
      }

      String line = "";

      for (int i = 0; i < 3; i++) {
        if(!scanner.hasNextLine()) {
        System.err.println("Error: not the correct number of lines to format maze.");
        scanner.close();
        return false; 
        } else {
          line = scanner.nextLine(); // takes in line 1,2,3
          switch(i){
            case 0:
              String[] splitLine1 = line.split(" ");
              if(splitLine1.length == 2){
                this.numColumns = Integer.parseInt(splitLine1[0]);
                this.numRows = Integer.parseInt(splitLine1[1]);
              } else {
                System.err.println("Error: not the correct input of rows and columns.");
                scanner.close();
                return false; 
              }
            case 1:
              String[] splitLine2 = line.split(" ");
              if(splitLine2.length == 2){
                this.startColumn = Integer.parseInt(splitLine2[0]);
                this.startRow = Integer.parseInt(splitLine2[1]);
              } else {
                System.err.println("Error: not the correct input of starting positions.");
                scanner.close();
                return false; 
              }
            case 2:
              String[] splitLine3 = line.split(" ");
              if(splitLine3.length == 2){
                this.finishColumn = Integer.parseInt(splitLine3[0]);
                this.finishRow = Integer.parseInt(splitLine3[1]);
              } else {
                System.err.println("Error: not the correct input of finishing positions.");
                scanner.close();
                return false; 
              }  
          }
        }
      }

      int counterRow = 0; 

      for (int row = 0; row < this.numRows; row++){
        if (!scanner.hasNextLine()) {
        System.err.println("Error: not the correct input of lines.");
        scanner.close();
        return false; 
        } else {
        String nextLine = scanner.nextLine();
        if(nextLine.length()!=numColumns) {
          System.err.println("Error: not the correct number of discriptors.");
          scanner.close();
          return false;   
        }
        for (int counterColumn = 0; counterColumn < numColumns; counterColumn++){
          char descriptor = nextLine.charAt(counterColumn);
          switch (descriptor){
            case '7': 
              this.squaresInMaze.add(new MazeSquare(counterRow,counterColumn, true, true));
            break;
            case '|': 
              this.squaresInMaze.add(new MazeSquare(counterRow,counterColumn, false, true));
              break;
            case '_': 
              this.squaresInMaze.add(new MazeSquare(counterRow,counterColumn, true, false));
              break;
            case '*': 
              this.squaresInMaze.add(new MazeSquare(counterRow,counterColumn, false, false));
              break;
            default: 
              System.err.println("Error: not the correct descriptor chars: make sure"
              +" you're using: *, |, 7, _");
              scanner.close();
              return false; 
          }
        }
        counterRow++;
      }
    } 
    if(scanner.hasNextLine()){

      System.out.println("Error: warning -- file has extra lines that were ignored...");
    }
    if(counterRow!=numRows){
        System.err.println("Error: not the correct input of finishing positions.");
        scanner.close();
        return false;
    }
      scanner.close();
      return true;//Remove this and load the maze from the file
  }

    
    /**
     * Prints the maze with the start and finish squares marked. Does
     * not include a solution.
     */
    public void print() {
        //We'll print off each row of squares in turn.
        for(int row = 0; row < numRows; row++) {
            
            //Print each of the lines of text in the row
            for(int charInRow = 0; charInRow < 4; charInRow++) {
                //Need to start with the initial left wall.
                if(charInRow == 0) {
                    System.out.print("+");
                } else {
                    System.out.print("|");
                }
                
                for(int col = 0; col < numColumns; col++) {
                    MazeSquare curSquare = this.getMazeSquare(row, col);
                    if(charInRow == 0) {
                        //We're in the first row of characters for this square - need to print
                        //top wall if necessary.
                        if(curSquare.hasTopWall()) {
                            System.out.print(getTopWallString());
                        } else {
                            System.out.print(getTopOpenString());
                        }
                    } else if(charInRow == 1 || charInRow == 3) {
                        //These are the interior of the square and are unaffected by
                        //the start/final state.
                        if(curSquare.hasRightWall()) {
                            System.out.print(getRightWallString());
                        } else {
                            System.out.print(getOpenWallString());
                        }
                    } else {
                        //We must be in the second row of characters.
                        //This is the row where start/finish should be displayed if relevant
                        
                        //Check if we're in the start or finish state
                        if(startRow == row && startColumn == col) {
                            System.out.print("  S  ");
                        } else if(finishRow == row && finishColumn == col) {
                            System.out.print("  F  ");
                        } else if(this.solutionSet.contains(curSquare)){
                            System.out.print("  *  ");
                        } else {
                            System.out.print("     ");
                        }
                        if(curSquare.hasRightWall()) {
                            System.out.print("|");
                        } else {
                            System.out.print(" ");
                        }
                    } 
                }
                
                //Now end the line to start the next
                System.out.print("\n");
            }           
        }
        
        //Finally, we have to print off the bottom of the maze, since that's not explicitly represented
        //by the squares. Printing off the bottom separately means we can think of each row as
        //consisting of four lines of text.
        printFullHorizontalRow(numColumns);
    }
    
    /**
     * Prints the very bottom row of characters for the bottom row of maze squares (which is always walls).
     * numColumns is the number of columns of bottom wall to print.
     */
    private static void printFullHorizontalRow(int numColumns) {
        System.out.print("+");
        for(int row = 0; row < numColumns; row++) {
            //We use getTopWallString() since bottom and top walls are the same.
            System.out.print(getTopWallString());
        }
        System.out.print("\n");
    }
    
    /**
     * Returns a String representing the bottom of a horizontal wall.
     */
    private static String getTopWallString() {
        return "-----+";
    }
    
    /**
     * Returns a String representing the bottom of a square without a
     * horizontal wall.
     */
    private static String getTopOpenString() {
        return "     +";
    }
    
    /**
     * Returns a String representing a left wall (for the interior of the row).
     */
    private static String getRightWallString() {
        return "     |";
    }
    
    /**
     * Returns a String representing no left wall (for the interior of the row).
     */
    private static String getOpenWallString() {
        return "      ";
    }
    
    /**
     * Implement me! This method should return the MazeSquare at the given 
     * row and column. The line "return null" is added only to make the
     * code compile before this method is implemented. Delete that line and
     * replace it with your own code.
     */
    public MazeSquare getMazeSquare(int row, int col) {
        //**************YOUR CODE HERE******************
        return this.squaresInMaze.get(row*(numColumns)+col);
    }

    /**
    * Computes and returns a solution to this maze. If there are multiple
    * solutions, only one is returned, and getSolution() makes no guarantees about
    * which one. However, the returned solution will not include visits to dead
    * ends or any backtracks, even if backtracking occurs during the solution
    * process. 
    *
    * @return a stack of MazeSquare objects containing the sequence of squares
    * visited to go from the start square (bottom of the stack) to the finish
    * square (top of the stack). If there is no solution, an empty stack is
    * returned.
    */
    public Stack<MazeSquare> getSolution(){
      Stack<MazeSquare> solutionStack = new MysteryStackImplementation<MazeSquare>(); // empty stack
      MazeSquare startingSq = getMazeSquare(this.startRow,this.startColumn);
      MazeSquare finishingSq = getMazeSquare(this.finishRow,this.finishColumn);
  
      MazeSquare curSq = startingSq;

      solutionStack.push(startingSq); // add starting position in stack solution
      startingSq.setVisitState(true); // marks that the starting square has been visitied

      while(solutionStack.isEmpty() == false && !(solutionStack.peek().equals(finishingSq))){
        curSq = solutionStack.peek();
        //right
        if( curSq.getColumn()+1 < this.numColumns 
        && getMazeSquare(curSq.getRow(),curSq.getColumn()+1).getVisitState() == false
        && curSq.hasRightWall() == false )
        { 
          // if a right square exists, that has not been visited, and isn't blocked
          // then add to stack and visit square
          solutionStack.push(getMazeSquare(curSq.getRow(),curSq.getColumn()+1));
          getMazeSquare(curSq.getRow(),curSq.getColumn()+1).setVisitState(true);
          continue; // skip the other statements below
        }

        //left
        if ( curSq.getColumn()-1 >= 0 
        && getMazeSquare(curSq.getRow(),curSq.getColumn()-1).getVisitState() == false 
        && getMazeSquare(curSq.getRow(),curSq.getColumn()-1).hasRightWall() == false )
        { 
          // if a left square exists, that has not been visited, and isn't blocked
          // then add to stack and visit square
          solutionStack.push(getMazeSquare(curSq.getRow(),curSq.getColumn()-1));
          getMazeSquare(curSq.getRow(),curSq.getColumn()-1).setVisitState(true);
          continue; // skip the other statements below
        }

        //up
        if ( curSq.getRow()-1 >= 0 
        && getMazeSquare(curSq.getRow()-1,curSq.getColumn()).getVisitState() == false 
        && curSq.hasTopWall() == false )
        { 
          // if a up square exists, that has not been visited, and isn't blocked
          // then add to stack and visit square
          solutionStack.push(getMazeSquare(curSq.getRow()-1,curSq.getColumn()));
          getMazeSquare(curSq.getRow()-1,curSq.getColumn()).setVisitState(true);
          continue; // skip the other statements below
        }

        //down
        if ( curSq.getRow()+1 < this.numRows 
        && getMazeSquare(curSq.getRow()+1,curSq.getColumn()).getVisitState() == false 
        && getMazeSquare(curSq.getRow()+1,curSq.getColumn()).hasTopWall() == false)
        { 
          // if a up square exists, that has not been visited, and isn't blocked
          // then add to stack and visit square
          solutionStack.push(getMazeSquare(curSq.getRow()+1,curSq.getColumn()));
          getMazeSquare(curSq.getRow()+1,curSq.getColumn()).setVisitState(true);
          continue; // skip the other statements below
        }

        solutionStack.pop();
      }
      createSet(solutionStack);
      return solutionStack;
    }
    /**
    * This helper method is created to create a Set of the items in the
    * solution stack: This later helps to print out the path: we can use
    * setSolution.contains(mazeSquareObject) too decide if * should be 
    * printed. 
    **/
    private void createSet(Stack<MazeSquare> solutionStack) {
      while(solutionStack.isEmpty() == false) {
        MazeSquare current = solutionStack.pop();
        this.solutionSet.add(current);
      } 
    }
 
    /**
     * You should modify main so that if there is only one
     * command line argument, it loads the maze and prints it
     * with no solution. If there are two command line arguments
     * and the second one is --solve,
     * it should load the maze, solve it, and print the maze
     * with the solution marked. No other command lines are valid.
     */ 
    public static void main(String[] args) { 
      boolean result = false;
      if (args.length  == 2) {
        String filename = args[0];
        boolean solve = false;
        if (args[1].equals("--solve")){
          solve = true;
          Maze mazeWithSolution = new Maze();
          result = mazeWithSolution.load(filename);
          if(result==true){
            mazeWithSolution.getSolution();
            mazeWithSolution.print();
          }
        } else { //if solve spelled wrong
          Maze maze = new Maze();
          result = maze.load(filename);
          if(result==true){
            maze.print();
            System.err.println("Make sure to use \'--solve\' as the second argument to print out puzzle solution!");
          }
        }
      } else if (args.length == 1) {
        String filename = args[0];
        Maze maze = new Maze();
        result = maze.load(filename);
        if (result == true) { maze.print(); }
      } else { // print user statment
        System.out.println("Make sure to have maximum of 2 commandline arguments (1: filename, 2: \'--solve\'"
        +" if you'd like to print out maze solution).");
      }
    } 
}