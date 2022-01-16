import java.io.File;
import java.util.*;
import java.lang.Exception;
import java.io.FileNotFoundException;


/**
* PathFinder Class
* Goals: To apply understanding of the Graph ADT and 
* graph algorithms by implementing a shortest path finding 
* algorithm for Wikipedia data.
**/
public class PathFinder{
  /**INSTACE VARIABLES**/
  private UnweightedGraph directedGraph;
  private Map<String, Integer> articleMapKeyString;
  private Map<Integer, String> articleMapKeyInteger;

  private List<String> shortestPath;
  
  /**
  * Constructs a PathFinder that represents the graph with nodes (vertices) specified as in
  * nodeFile and edges specified as in edgeFile.
  * @param nodeFile name of the file with the node names
  * @param edgeFile name of the file with the edge names
  **/
  public PathFinder(String nodeFile, String edgeFile){
    this.directedGraph = new MysteryUnweightedGraphImplementation(true);
    this.articleMapKeyString = new HashMap<String,Integer>();
    this.articleMapKeyInteger = new HashMap<Integer,String>();
    this.shortestPath = new ArrayList<String>();
  
    //add verticies
    File inputNodeFile = new File(nodeFile);
    File inputEdgeFile = new File(edgeFile);

    Scanner nodeFileScanner = null;
    Scanner edgeFileScanner = null;
    try{
        nodeFileScanner = new Scanner(inputNodeFile);
        edgeFileScanner = new Scanner(inputEdgeFile);
    } catch (FileNotFoundException e) {
        System.err.println("ERROR: >>> one or more inputed file(s) not found :(!\n"+ e);
        System.exit(1);
    }
    scanFile(1, nodeFileScanner);
    //System.out.println("Graph SIZE(before): " + directedGraph.numVerts());

    scanFile(2, edgeFileScanner);
    
    nodeFileScanner.close();
    edgeFileScanner.close();
    
  }

  /** 
  * this is a private helper method to help scan through the 2 given files
  * by taking in a scanner and an int that describes how many items are in
  * each line of the file (1 or 2 only) 
  **/
  private void scanFile(int n, Scanner scanner){
    while (scanner.hasNextLine()){
      String line = scanner.nextLine();
      String[] splitLine = line.split(" ");

      // skip empty lines and lines that have a hastag element after spliting
      if(splitLine.length == 0 || splitLine[0].equals("#")){ continue; } 

      if(n == 1){ // scanning vertexFile, use .addVertex()
        this.articleMapKeyString.put(line, this.articleMapKeyString.size());
        this.articleMapKeyInteger.put(this.articleMapKeyInteger.size(), line);

        directedGraph.addVertex(); continue;
      } 
      
      if (splitLine.length == 2) { // scaning edgeFile
        for(int x = 0; x < 2; x++){
          if(!this.articleMapKeyString.containsKey(splitLine[x])){ 
            this.articleMapKeyString.put(splitLine[x], this.articleMapKeyString.size());
            this.articleMapKeyInteger.put(this.articleMapKeyString.size(),splitLine[x]);
            directedGraph.addVertex();
          }
        }
        directedGraph.addEdge(this.articleMapKeyString.get(splitLine[0]),this.articleMapKeyString.get(splitLine[1]));
      }
    }
  }

  /**
  * Returns a shortest path from node1 to node2, represented as list that has node1 at
  * position 0, node2 in the final position, and the names of each node on the path
  * (in order) in between. If the two nodes are the same, then the "path" is just a
  * single node. If no path exists, returns an empty list.
  * @param node1 name of the starting article node
  * @param node2 name of the ending article node
  * @return list of the names of nodes on the shortest path
  */
  public List<String> getShortestPath(String node1, String node2){
    Set<Integer> visitedSet = new HashSet<Integer>(); // holds visited verticies
    Map<Integer,Integer> previousElementMap = new HashMap<Integer, Integer>(); // map that holds the prev vertex connections
    Queue<Integer> vertexQueue = new LinkedList<Integer>(); // creates empty queue for breadth first traversal
    
    if(this.articleMapKeyString.get(node1)==null || this.articleMapKeyString.get(node2)==null){
      System.err.println("USAGE ERROR: Your input of start or/and end vetercies doesn't exists in your given files.");
      System.exit(1); // if the input of given nodes is not in the given file, exit program
    }

    visitedSet.add(this.articleMapKeyString.get(node1));
    vertexQueue.add(this.articleMapKeyString.get(node1));
  
    while (!vertexQueue.isEmpty()){ //breadth first traversal
      int frontVertex = vertexQueue.poll();
      for (int neighbor : directedGraph.getNeighbors(frontVertex)){ 
        if (!visitedSet.contains(neighbor)){
          visitedSet.add(neighbor); // visit this node
          vertexQueue.add(neighbor); // add all neighbors to queue
          previousElementMap.put(neighbor, frontVertex); 
        }
      }
      //System.out.println("looping in while loop...");
    }
    this.shortestPath = recreatePath(node1, node2, previousElementMap);
    return this.shortestPath;
  }

  /**
  * This is a helper function. recreatePath takes in node1 and node2 along with
  * the map that helps keep track of the previous item >>
  * then backtraces the node2 (endVertex) till node1 (frontVertex) is reached.
  * the back trace is done into a stack which then gets popped into a List
  * in the corrected order of start to end!
  **/
  private List<String> recreatePath(String node1, String node2, Map<Integer,Integer> previousElementMap){
    List<String> pathSolution = new ArrayList<String>();
    Stack<Integer> backwardsPath = new Stack<Integer>();
    // here below recreate backward path:
    int current = this.articleMapKeyString.get(node2);
    while(current!=this.articleMapKeyString.get(node1) && previousElementMap.containsKey(current)){
      // push into stack and set current to previous 
      backwardsPath.push(current);  
      current = previousElementMap.get(current); //current = previous
    }
    if(backwardsPath.size() < 1){ //if path is empty: then returns empty list
       return pathSolution;
    } 
    //else if not empty, then pop stack into the List and return the list.
    backwardsPath.push(this.articleMapKeyString.get(node1)); // add starting node
    while (!backwardsPath.isEmpty()) {
      pathSolution.add(this.articleMapKeyInteger.get(backwardsPath.pop()));
    }

    return pathSolution;
  }

  /**
  * Returns the length of the shortest path from node1 to node2. If no path exists,
  * returns -1. If the two nodes are the same, the path length is 0.
  * @param node1 name of the starting article node
  * @param node2 name of the ending article node
  * @return length of shortest path
  */
  public int getShortestPathLength(String node1, String node2){
    if(this.shortestPath.size()==0)
      this.shortestPath = getShortestPath(node1, node2);
    if(node1.equals(node2) && this.shortestPath.size()==0) { return 0; } // equivalent nodes
    if(this.shortestPath.size()==0){ return -1; } // no path exists
    return this.shortestPath.size()-1; // a path exists, greater than 0
  }

  /**
  * Returns a shortest path from node1 to node2 that includes the node intermediateNode.
  * This may not be the absolute shortest path between node1 and node2, but should be 
  * a shortest path given the constraint that intermediateNodeAppears in the path. If all
  * three nodes are the same, the "path" is just a single node.  If no path exists, returns
  * an empty list.
  * @param node1 name of the starting article node
  * @param node2 name of the ending article node
  * @return list that has node1 at position 0, node2 in the final position, and the names of each node 
  *      on the path (in order) in between. 
  */
  public List<String> getShortestPath(String node1, String intermediateNode, String node2){
    List<String> path1 = getShortestPath(node1,intermediateNode);
    List<String> path2 = getShortestPath(intermediateNode,node2);
    if(path1.size()==0 || path2.size()==0){ return this.shortestPath; }

    for ( int i = 1; i < path2.size(); i++){ path1.add(path2.get(i)); }
    this.shortestPath = path1; 
    return this.shortestPath;
  }


  public static void main(String[] args){
    /**User statement:
    * This PathFinder accepts 4 or 5 commandline arguments like this:
    * > java PathFinder vertexFile edgeFile startVertex endVertex
    * > java PathFinder vertexFile edgeFile startVertex intermediateVertex endVertex
    * the first one will give a direct path from the startVertex to endVertex
    * while the second input will make sure that the path goes through the intermediateNode
    **/
    String vertexFile="", edgeFile="", startVertex="", endVertex="";
    String intermediateVertex ="";

    if (args.length == 5){ // want to find path with intermediateVertex
        vertexFile = args[0];
        edgeFile = args[1];
        startVertex = args[2];
        intermediateVertex = args[3];
        endVertex = args[4];      
    } else if (args.length == 4){ // want to find path with intermediateVertex
        vertexFile = args[0];
        edgeFile = args[1];
        startVertex = args[2];
        endVertex = args[3];
    } else { // display user statement: wrong number of arguments given.
        System.err.format("Usage error: You entered %d number of arguments."
        +" Please enter 4 or 5 arguments only.\n", args.length);
        System.exit(1);
    }

    PathFinder wikiPathFinder = new PathFinder(vertexFile, edgeFile);
    List <String> shortestPath = new ArrayList<String>();
    if(args.length == 5){
      shortestPath = wikiPathFinder.getShortestPath(startVertex,intermediateVertex,endVertex);
      System.out.println("Path from " +startVertex+" to "+intermediateVertex + " to " + endVertex 
      + ", length = "+ wikiPathFinder.getShortestPathLength(startVertex,endVertex)+":");     
    } else {
      shortestPath = wikiPathFinder.getShortestPath(startVertex,endVertex);
      System.out.println("Path from " +startVertex+" to "+endVertex +", length = "
      + wikiPathFinder.getShortestPathLength(startVertex,endVertex)+":");
    }

    

    if(!shortestPath.isEmpty()){
      System.out.print(shortestPath.get(0));
      for (int i = 1; i < shortestPath.size(); i++){
        System.out.print(" --> " + shortestPath.get(i));
      }
    } else { 
      if (startVertex.equals(endVertex)){
        System.out.println("The starting and the end nodes are the same.");
      } else { System.out.print("There is no path!"); }
    }
    System.out.println();
  }
}