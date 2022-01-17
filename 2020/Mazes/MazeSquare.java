/**
* MazeSquare represents a single square within a Maze.
* @author Anna Rafferty
*/ 
public class MazeSquare {
    //Wall variables
    private boolean hasTopWall = false;
    private boolean hasRightWall = false;
		
    //Location of this square in a larger maze.
    private int row;
    private int col;

    //variable indicating if the square has been visited
    private boolean visited = false;
		
    /**
    * Constructor for class object MazeSquare
    * takes in 2 booleans, 2 integets
    */
    public MazeSquare(int row, int col, boolean hasTop, boolean hasRight){
      this.row = row;
      this.col = col;
      this.hasTopWall = hasTop;
      this.hasRightWall = hasRight;
    }
    /**
     * Returns true if this square has a top wall.
     */
    public boolean hasTopWall() {
        return hasTopWall;
    }
		
    /**
     * Returns true if this square has a right wall.
     */
    public boolean hasRightWall() {
        return hasRightWall;
    }
		
    /**
     * Returns the row this square is in.
     */
    public int getRow() {
        return row;
    }
		
    /**
     * Returns the column this square is in.
     */
    public int getColumn() {
        return col;
    }

    /**
     * Given a boolean state, MazeSquare
     * can be marked visited/unvisited.
     */
    public void setVisitState(boolean state){
      this.visited = state;
    }

    /**
     * Returns if this square has been visited or not.
     */
    public boolean getVisitState(){
      return visited;
    }
} 