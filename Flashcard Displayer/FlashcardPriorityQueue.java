import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/*
* A class FlashcardPriorityQueue that implements a priority queue by making a min-heap.
* Rather than building a binary Heap, this class builds a 5-heap so that each node has 
* up to 5 children. 
* -- some code implmented from OpenDSA readings
*/
public class FlashcardPriorityQueue implements PriorityQueue<Flashcard> {
  // instance variables
  private Flashcard[] Heap;                // Pointer to the Heap array
  private int size;                        // Maximum size of the Heap
  private int numItems;                    // Number of things now in Heap

  private static int DEFAULT_SIZE = 10;    // default initial size of Heap

  // constructors
  /**
  * Creates an empty priority queue.
  */
  public FlashcardPriorityQueue(){
    clear(); // creates an empty Flashcard[]
  }

  /*
  * If current numItems value + 1 == DEFAULT_SIZE 
  */
  private void ensureSpace(){
    if (this.numItems == this.size) { // filled array -- double size
      this.size *= 2;
      Flashcard [] temp = this.Heap; // store it for copying 
      this.Heap = new Flashcard[this.size];
      for (int i = 0; i < temp.length; i++) { this.Heap[i] = temp[i]; }
    }
  }

  /*
  * isLeaf returns true if there is a leaf at the passed in
  * index i and false otherwise
  */
  private boolean isLeaf(int i) 
  { 
    if (i == 0 && numItems > 1) { return false; }
    return (i >= (numItems/5) && (i < numItems)); 
    }

  /*
  * Takes in a node location i, and returns its parent. if asking for 
  * parent of i = 0, then return -1. Or else return the parent location
  * in a 5-heap = (i-1)/5.
  */
  private int parent(int i){ 
    if (i <= 0) return -1;
    return (i-1)/5; 
  }
  
  /*
  * Given 2 index of heap, these two objects are swapped. 
  */
  private void swap(int curr, int parent){
    Flashcard tempCur = this.Heap[curr]; // holds curr Flashcard
    this.Heap[curr] = this.Heap[parent]; // swaps
    this.Heap[parent] = tempCur; 
    //System.out.println("swapped");
  }

  /** Adds the given item to the queue. */
  public void add(Flashcard item) { // code from openDSA readings
    ensureSpace();
    int curr = numItems++; // starting at the end of the heap
    Heap[curr] = item;
    // shift up and compare to parents
    while ((curr!=0) && (Heap[curr].compareTo(Heap[parent(curr)]) > 0)) {
      swap(curr, parent(curr));
      curr = parent(curr);
    }
  }

  /*
  * given node i, this helper function returns the int index
  * for the most prioritized Flashcard child of node i. 
  */
  private int getSmallestChild(int i){
    if ( i != 0 ) {
      if (i >= numItems/5) return -1; // meaning no children exist
    } else { if ( numItems == 1 ) return -1; }
    int childCount = 0; 
    childCount = numItems%5;
    if(childCount == 0) { childCount = 5; }
    Flashcard minChild = this.Heap[5*i+1]; // stores first child in small child
    int index = 1;
    for (int j = 2; j < childCount; j++) { // iterates through children to find the smallest openDSA
      if (minChild.compareTo(this.Heap[5*i+j]) < 0){
        minChild = this.Heap[5*i+j];
        index = j; 
      }
    }
    index = 5*i+index;
    return index; 
  }

  /*
  * Put the item at index i in the correct place
  * so heap is complete tree and is also ordered 
  */
  private void siftdown(int i) {
    if ((i < 0) || (i >= numItems)) return; // Illegal position
    while (!isLeaf(i)) {
      int minChildIndex = getSmallestChild(i);
      //System.out.println(minChildIndex);
      //System.out.println(toString());
      if (minChildIndex == -1) { break; }

      // if minChild is equal to or less prioritized than current node:

      if (this.Heap[i].compareTo(this.Heap[minChildIndex]) >= 0) return; 
      // or else swap it and go check again..: 
      if (this.Heap[i].compareTo(this.Heap[minChildIndex]) < 0) 
        swap(minChildIndex, i);
      i = minChildIndex;
    }
    //System.out.println("i is  a leaf"); 

  }
  
  /** Removes the first item according to compareTo from the queue, and returns it.
    * Throws a NoSuchElementException if the queue is empty.
    */
  public Flashcard poll() {
    if (numItems == 0) return null;  // empty heap
    swap(0, --numItems); // Swap Heap[0] with last value
    siftdown(0);   // Put Flashcard at index 0 in correct place
    return this.Heap[numItems];
    }
  
  /** Returns the first item according to compareTo in the queue, without removing it.
    * Throws a NoSuchElementException if the queue is empty.
    */
  public Flashcard peek() { 
    if (numItems == 0) return null;  // empty heap
    return this.Heap[0];
  }
  
  /** Returns true if the queue is empty. */
  public boolean isEmpty() { return numItems == 0; }
  
  /** Removes all items from the queue. */
  public void clear() {
    this.numItems = 0;
    this.size = DEFAULT_SIZE;
    this.Heap = new Flashcard[this.size];
  }

  /*
  * Overrides default toString()
  * returns String in form "[[dueDate1,front1,back1],[dueDate2,front2,back2],...]"
  */
  @Override
  public String toString(){
    String returnString = "Heap size = " + this.numItems + " || max size = " + this.size+ "\n[";
    for (int i = 0 ; i < this.numItems; i++){
      if (i == 0) {returnString += "[";}
      else { returnString += " ["; }
      returnString += this.Heap[i].getDueDate();
      if ( i == this.numItems-1 ) { returnString += "]"; }
      else { returnString += "],\n"; }
    }
    returnString += "]";
    return returnString;
  }


  /*
  * Comprehensive testing of class FlashcardPriorityQueue below!
  */
  public static void main(String[] args){
    // test SampleFlashCards.txt
    File filePath = new File("SampleFlashcards.txt");
    Scanner fileScanner = null;
    try{
      fileScanner = new Scanner(filePath);
    } catch (FileNotFoundException e){
      System.err.println(e);
      System.exit(1);
    }

    FlashcardPriorityQueue pqFlashcard = new FlashcardPriorityQueue();
    FlashcardPriorityQueue pqFlashcard2 = new FlashcardPriorityQueue();

    System.out.println("Empty FlashcardPriorityQueue created!");
      System.out.println(pqFlashcard.toString());

    System.out.println("Try to peek() an empty heap: " + pqFlashcard.peek());
    System.out.println("Try to poll() an empty heap: " + pqFlashcard.poll());

    System.out.println("-------------------------------------");

    System.out.println("Scanning in SampleFlashCards.txt");
    while(fileScanner.hasNextLine()){ 
      // checks ensureSpace() method and add(Flashcard item)
      String line = fileScanner.nextLine();
      pqFlashcard.add(new Flashcard(line));
      pqFlashcard2.add(new Flashcard(line));
    }
    System.out.println(pqFlashcard.toString());

    fileScanner.close();

    System.out.println("-------------------------------------");
    System.out.println("Testing peek method...");
    System.out.println("peek(): " + pqFlashcard.peek());
    System.out.println("heap after peek method (should be the same)...");
    System.out.println(pqFlashcard.toString());


    System.out.println("-------------------------------------");
    // tests on methods ---------------------
    System.out.println("Testing add method further...");
    for (int currYear = 2000; currYear < 2015; currYear++) {
      Flashcard card = new Flashcard(currYear + "-01-01T01:01:01,"+ currYear + ","+ currYear + "Back");
      pqFlashcard.add(card);
    }
    System.out.println(pqFlashcard.toString());
    System.out.println("Writing out the 5-heap tree: looks good.");
    // i wrote it out on paper and it looks good!
    System.out.println("-------------------------------------");

    System.out.println("Testing poll method...");
    System.out.println("polling returns: " + pqFlashcard.poll());
    System.out.println(pqFlashcard.toString());
    System.out.println("Top most Flashcard was removed (year 2000)");
    System.out.println("Writing out the 5-heap tree: looks good.");
      // again, i wrote it out on paper and it looks good!

    System.out.println("-------------------------------------");
    System.out.println("Testing clear() method...");
    pqFlashcard.clear();
    System.out.println(pqFlashcard.toString());
    System.out.println("-------------------------------------");
    System.out.println("Testing poll() method further...");
    System.out.println(pqFlashcard2.toString());

    while(!pqFlashcard2.isEmpty()){
      System.out.println("polled item: " + pqFlashcard2.poll());
      System.out.println(pqFlashcard2.toString());
    }

    System.out.println("-----------TESTING COMPLETE----------");
    System.out.println("-------------------------------------");
  }
}