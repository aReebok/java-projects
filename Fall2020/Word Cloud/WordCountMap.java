import java.util.*;
/**
* This class is the to hold your words and their counts.
**/
public class WordCountMap{
    // instance variables ----
    private TreeNode root;


    // TreeNode class ----
    private class TreeNode{
      private String item;
      private int count;
      private Map<String, TreeNode> childrenNodes;

      public TreeNode(String item){
        this.item = item;
        this.childrenNodes = new HashMap<String, TreeNode>();
      }
    }

    /**
    * Constructs an empty WordCountMap.
    */         
    public WordCountMap() {
      this.root = new TreeNode("");
      this.root.count = 0;
    }
    
    /**
    * Adds 1 to the existing count for word, or adds word to the WordCountMap
    * with a count of 1 if it was not already present.
    * Implementation must be recursive, not iterative.
    */
    public void incrementCount(String word){
      incrementCount(word, this.root);
    }
    /** recursion helper function for incrementCount **/
    private void incrementCount(String word, TreeNode curNode){
      // base case if only 1 letter is being added
      if (word.length() == 0) {
        // nothing to add
        //System.err.println("String does not contain alphabets, or is empty: nothing was added!");
      } else if(word.length() == 1) {
        // is it contained in curNode.childrenNodes? if not, then add and increment count. 
        if (!curNode.childrenNodes.containsKey(word)) {
          TreeNode tempWord = new TreeNode(word);
          tempWord.count = 1; 
          curNode.childrenNodes.put(word, tempWord);
        } else {
          curNode.childrenNodes.get(word).count++;
        }
      } else { // the  he  e
        if (!curNode.childrenNodes.containsKey(word.substring(0, 1))) {
          TreeNode tempWord = new TreeNode(word.substring(0, 1));
          curNode.childrenNodes.put(word.substring(0, 1), tempWord);
          incrementCount(word.substring(1), tempWord);
        } else {
          incrementCount(word.substring(1), curNode.childrenNodes.get(word.substring(0, 1)));
        }
      }
    }
    
    /**
    * Returns the count of word, or -1 if word is not in the WordCountMap.
    * Implementation must be recursive, not iterative.
    */
    public int getCount(String word) { 
      return getCount(word, this.root);
    }
    /** recursive helper function for getCount **/
    private int getCount(String word, TreeNode curNode) {
      // base case: if length of word is 1, then see if count > 0
      if (word.length() == 0) return -1;
      if (word.length() == 1) {
        if (!curNode.childrenNodes.containsKey(word)) return -1;
        if (curNode.childrenNodes.get(word).count == 0)  return -1;
        return curNode.childrenNodes.get(word).count;
      } else { // recurse here... --
        if (!curNode.childrenNodes.containsKey(word.substring(0,1))) return -1;
        return getCount(word.substring(1), curNode.childrenNodes.get(word.substring(0,1)));
      }
    } 

    /**
    * Returns true if word is stored in this WordCountMap with
    * a count greater than 0, and false otherwise.
    * Implementation must be recursive, not iterative.
    */
    public boolean contains(String word) { 
      return contains(word, this.root);
    }

    /** recursive helper function for contains **/
    private boolean contains(String word, TreeNode curNode) {
      if (word.length() == 0) return false;
      if (word.length() == 1){
        if (!curNode.childrenNodes.containsKey(word)) return false; 
        if (curNode.childrenNodes.get(word).count == 0)  return false;
        return true;
      } else {
        if (!curNode.childrenNodes.containsKey(word.substring(0,1))) return false;
        return contains(word.substring(1), curNode.childrenNodes.get(word.substring(0,1)));
      }
    }
    
    /**
    * Remove 1 to the existing count for word. If word is not present, does
    * nothing. If word is present and this decreases its count to 0, removes
    * any nodes in the tree that are no longer necessary to represent the
    * remaining words.
    */
    public void decrementCount(String word){
      decrementCount(word, this.root);
    }
    /** recursive helper function for decrementCount **/
    private boolean decrementCount(String word, TreeNode curNode){
      if (word.length() == 0) { return true; } // nothing to remove -- exit recursion func
      if (word.length() == 1) {
        if (curNode.childrenNodes.containsKey(word)){ // else do nothing if its not in the tree
          if (curNode.childrenNodes.get(word).count > 0) {
            curNode.childrenNodes.get(word).count--;
          } //else do nothing because word not in map
        }
      } else {
        if (!curNode.childrenNodes.containsKey(word.substring(0,1))) return true;
        if (decrementCount(word.substring(1), curNode.childrenNodes.get(word.substring(0,1))) == false){
          //then delete next node and delete it from curNode.childrenNodes
          curNode.childrenNodes.remove(word.substring(0,1));
        } //else do nothing 
      } 
      if(curNode.count == 0 && curNode.childrenNodes.isEmpty()) return false; // then delete node...
      return true;
    }

    /** 
    * Returns a list of WordCount objects, one per word stored in this 
    * WordCountMap, sorted in decreasing order by count. 
    */
    public List<WordCount> getWordCountsByCount(){
      List<WordCount> listOfWords = new ArrayList<WordCount>();
      
      listOfWords = getWordCountsByCount(listOfWords, this.root, "");
      // now sorting list of words, largest to smallest..
      listOfWords = sortList(listOfWords);

      return listOfWords;
    }

    /**
    * helper sorting function -- insertion sorting
    **/
    private List<WordCount> sortList(List<WordCount> list) {
      for(int i = 1; i < list.size(); i++) {
        int j = i;
        while( j > 0 && list.get(j-1).getCount() < list.get(j).getCount()){
          //swap these
          WordCount tempWC = list.get(j-1);
          list.set(j-1,list.get(j));
          list.set(j, tempWC);
          j = j-1;
        } 
      }
      return list;
    }

    /** recursive helper function for getWordCountsByCount **/
    private List<WordCount> getWordCountsByCount(List<WordCount> list, TreeNode curNode, String word){ // similar to count nodes below
      if(!curNode.childrenNodes.isEmpty()){
        for(String key : curNode.childrenNodes.keySet()) {
          //System.out.println("current key: " + key + " | count: "+ curNode.childrenNodes.get(key).count);
          //word += curNode.item;
          if(curNode.childrenNodes.get(key).count != 0){
            //System.out.println("current key in if statement: " + key);
            word += key;
            WordCount wc = new WordCount(word, curNode.childrenNodes.get(key).count);
            list.add(wc);
            //System.out.println("Word added: " + word);
          } else {
            word += key;
          }
          list = getWordCountsByCount(list, curNode.childrenNodes.get(key), word);
          word = word.substring(0, word.length()-1);
        } //
      }
      return list;
    }
      
    /** 
    * Returns a count of the total number of nodes in the tree. 
    * A tree with only a root is a tree with one node; it is an acceptable
    * implementation to have a tree that represents no words have either
    * 1 node (the root) or 0 nodes.
    * Implementation must be recursive, not iterative.
    */
    public int getNodeCount() { 
      return getNodeCount(this.root);
    }
    /** recursive helper getNodeCount **/
    private int getNodeCount(TreeNode curNode) {
      int count = 0; // for the current node, count of childrenNodes is 0
      if (!curNode.childrenNodes.isEmpty()) {
        for(String key : curNode.childrenNodes.keySet()){ // for each child, add 1 to count
          count += 1;
          count += getNodeCount(curNode.childrenNodes.get(key));
        }
      } // if childrenNodes is empty, then do nothing...
      return count;
    }

    public static void main (String[] args) {
      WordCountMap wcm = new WordCountMap();
      wcm.incrementCount("hello");
      wcm.incrementCount("hello");
      wcm.incrementCount("hello");
      wcm.incrementCount("hello");
      wcm.incrementCount("halloween");//1
      wcm.incrementCount("helloween");
      wcm.incrementCount("helloween");
      wcm.incrementCount("helloween");
      wcm.incrementCount("helloween");//4
      wcm.incrementCount("ron");      
      wcm.incrementCount("ronold");
      wcm.incrementCount("ronold");
      wcm.incrementCount("ronold");
      wcm.incrementCount("ronold");
      wcm.incrementCount("ronold");
      wcm.incrementCount("ronold");
      wcm.incrementCount("ronold"); //7
      wcm.incrementCount("pillow");//1
      wcm.incrementCount("project");//1

      wcm.incrementCount("panda"); 
      wcm.incrementCount("panda"); //2
      wcm.incrementCount("hellow");
      wcm.incrementCount("hellow");
      wcm.incrementCount("ron");      
      wcm.incrementCount("ron"); //3
      wcm.incrementCount("hellow");
      wcm.incrementCount("hellow");
      wcm.incrementCount("hellow");
      wcm.incrementCount(""); // should not add anything !
      System.out.println("list: " + wcm.getWordCountsByCount());

      System.out.println("Contains Ron?: " + wcm.contains("ron"));
      System.out.println("Contains water?: " + wcm.contains("water"));


      wcm.decrementCount("ron");
      System.out.println("decrementing ron"); // two
      wcm.decrementCount("ron");
      System.out.println("decrementing ron"); // one
      wcm.decrementCount("ron");
      System.out.println("decrementing ron"); // zero

      wcm.incrementCount("water");
      System.out.println("incrementing water"); // 1

      //System.out.println("ronold:" + wcm.getCount("ronold")); // 7


      System.out.println("list: " + wcm.getWordCountsByCount());
      System.out.println("node count:" + wcm.getNodeCount()); // 38!

      System.out.println("list size: " + wcm.getWordCountsByCount().size());
    }
}