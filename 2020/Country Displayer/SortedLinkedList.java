import java.util.Iterator;

import java.awt.event.ItemListener;
import java.util.Comparator;

public class SortedLinkedList implements SortedList<Country> {
  //instance variables below
  private int listLength; 
  private Node head;
  private Comparator<Country> comparator;

  /* PRIVATE node class */
  private class Node{
    //instance variables below
    private Country item;
    private Node next;
    private Node prev;
    
    /* Node constructor1 takes in a country as the item
    and sets the content of the node to given item */
    public Node(Country item){ this.item = item; }

    /* Node constructor2 takes in a country as the item
    and sets the content of the node to given item. Also
    takes in the previous node and the next node to link
    new Node into the existing LinkedList.
    */
    public Node(Country item, Node prevNode, Node nextNode){
      this.item = item;
      this.next = nextNode;
      this.prev = prevNode;
    }
  }

  /* SortedLinkedList constructor for an empty list with a
  head. Takes in a Comparator<Country> object and stores it
  in SortedLinkedList object for later sorting. */ 
  public SortedLinkedList (Comparator<Country> comparator){
    this.head = new Node(null);
    this.listLength = 0; 
    this.comparator = comparator;
  }

  /**
    * Adds item to the list in sorted order.
    * big-O notation in worst case is O(n)
    */
  public void add(Country item){
    Node current = new Node(item, head, head.next);
    this.head.next = current;
    if (this.listLength > 0){ // set previous here.
      current.next.prev = current;
    }
    this.listLength++;
    while (current.next != null && this.comparator.compare(current.item,current.next.item) > 0){
      // keep shifting item right until .compare() <= 0, meaning it's sorted
      Country tempCountry = current.next.item;
      current.next.item = current.item;         // over writes next item,
      current.item = tempCountry;               // puts temp item into current item
      current = current.next;
    }
  }

  /**
    * Remove the first occurence of targetItem from the list, 
    * shifting everything after it up one position. targetItem
    * is considered to be in the list if an item that is equal
    * to it (using .equals) is in the list.
    * (This convention for something being in the list should be
    * followed throughout.)
    * @return true if the item was in the list, false otherwise
    * Running time in big-O notation: O(n).
    */ 
  public boolean remove(Country targetItem){
    int counter = 0;
    Node currNode = this.head.next; // on the first elemt of linkedlist. 
    for (int i = 1; i < this.listLength+1; i++) {
      if(this.comparator.compare(currNode.item, targetItem) == 0){ //if two match 
      //detach currNode from the list:
        removeCountry(currNode);
        return true; 
      }
      currNode = currNode.next;
    }
    return false;
  }
  
  /**
    * Remove the item at index position from the list, shifting everything
    *  after it up one position.
    * @return the item, or throw an IndexOutOfBoundsException if the index is out of bounds.
    * Running time big-O notatioon: O(n).
    */
  public Country remove(int position){
    if (position >= this.listLength || position < 0){
      throw new IndexOutOfBoundsException("ERROR: Index out of bounds!");
    } else {  
      Node currNode = this.head.next; 
      for (int i = 0; i < position; i++) {
        currNode = currNode.next;
      } //code to remove: 
      Country tempCountry = currNode.item;
      removeCountry(currNode);
      return tempCountry;
    }
  } 

  /* 
  * remove helper function: takes in a node that needs to be removed
  * reassignes the pointers around it so that the currNode is "lost"
  * from pointers of the SortedLinkedList.
  */
  private void removeCountry(Node currNode){
    currNode = currNode.prev; // joins previous to next
    currNode.next = currNode.next.next; 
    currNode.next.prev = currNode; // joins next to previous
    this.listLength--;
  }

  /**
    * Returns the first position of targetItem in the list.
    * @return the position of the item, or -1 if targetItem is not in the list
    * Running time big-O notation: O(n).
    */
  public int getPosition(Country targetItem){
    Node currNode = this.head;
    int position = 0;
    while(currNode.next!=null){
      if(this.comparator.compare(currNode.next.item, targetItem) == 0){
        return position;
      }
      currNode = currNode.next;
      position++;
    }
    return -1;
  }

  /** 
    * Returns the item at a given index.
    * @return the item, or throw an IndexOutOfBoundsException if the index is out of bounds.
    * worst case in big-O notation is O(n)
    */
  public Country get(int position){
    if (position >= this.listLength || position < 0){
      throw new IndexOutOfBoundsException("ERROR: Index out of bounds!");
    } else {
      Node current = this.head;// index -1 basically 
      for (int i = -1; i < position; i++)
        current = current.next;
      return current.item;
    }
  } 
  

  /** Returns true if the list contains the target item.
  * worst case big-Oh notation:------- O(n).
  **/
  public boolean contains(Country targetItem){
    Node currNode = this.head.next;
    for(int i = 0; i < this.listLength; i++){
      if (this.comparator.compare(currNode.item, targetItem)==0){
        return true;
      }
      currNode = currNode.next;
    }
    return false;
  } 
  
  /** Re-sorts the list according to the given comparator.
    * All future insertions should add in the order specified
    * by this comparator.
    * resort's big-O notation is O(n^3)
  **/
  public void resort(Comparator<Country> comparator){
    this.comparator = comparator;
    Node currNode = this.head;
    for (int i = 0; i < this.listLength; i++) { 
      currNode = this.getNode(this.head, i); 
      while (currNode.prev.item!=null && this.comparator.compare(currNode.item, currNode.prev.item) < 0){
        currNode.prev.next = currNode.next;
        currNode.next = currNode.prev;
        currNode.prev = currNode.next.prev;
        currNode.next.prev = currNode;
        if(currNode.next.next!=null)
          currNode.next.next.prev = currNode.next;
        currNode.prev.next = currNode;
      }
    }
  } 

  /**
  * helper function for resort: helps get the Node at certain index 
  * big-O notation O(n) in worst case;
  **/
  private Node getNode(Node currNode, int position){
    // Node tempCur = currNode;
    for(int i = -1; i < position; i++){
      currNode = currNode.next;
    }
    return currNode;
  }

  /** Returns the length of the list: the number of items stored in it. 
  * big-O notation here is O(1).
  **/
  public int size() { return this.listLength; } // working

  /** Returns true if the list has no items stored in it. 
  * big-O notation here is in O(1)
  **/
  public boolean isEmpty() { return this.listLength == 0; } 

  /** Returns an iterator that begins just before index 0 in this list. */
  public Iterator<Country> iterator() {
    throw new UnsupportedOperationException("Iterator not implemented!");
  } // needs testing

  
  /** Removes all items from the list. 
  *   big-O notation: O(1)
  **/
  public void clear(){
    this.head = new Node(null, head, null);
    this.listLength = 0;
    //the head of the list reassigned to point at itself and null. 
  }

  public static void main(String[] args) {
    //test code below for public methods in class SortedLinkedList
    CountryComparator comparator = new CountryComparator("UrbanPopulationGrowth", false);
    SortedLinkedList list1 = new SortedLinkedList(comparator);
    Country c1 = new Country("Argentina,42.66,4.63,99.64,10.02,8.84,1.05,1.19"); //4th
    Country c2 = new Country("Armenia,2.91,1.79,99.96,9.04,23.11,0.24,0.17"); // 2nd
    Country c3 = new Country("Aruba,0.1,15.82,99.17,6.42,12.79,0.47,0.47"); // 3rd
    Country c4 = new Country("Afghanistan,33.26,0.33,72.7,15.73,0.1,2.99,3.89");// 7th
    Country c5 = new Country ("Albania,2.89,1.76,100,38.62,17.56,-0.23,1.61");//5th
    Country c6 = new Country ("Algeria,39,3.47,99.51,0.15,7.5,1.98,2.91"); // 6th
    Country c7 = new Country ("Andorra,0.08,5.93,100,19.41,24.88,-1.03,-1.13"); //1st

    Country c8 = new Country ("Aruba,0.1,15.82,99.17,6.42,12.79,0.47,0.47");//to remove targetItem for C5 Albania
    Country c9 = new Country ("Australia,2.91,1.79,99.96,9.04,23.11,0.24,0.17");//to remove targetItem for C5 Albania

    // TESTING for .add(), and .get(int position) below ---------------------------- 
    list1.add(c1);
    list1.add(c2);
    list1.add(c3);
    list1.add(c4);
    //System.out.println("list size (should print 4): " + list1.size());
    list1.add(c5);
    list1.add(c6);
    list1.add(c7);
    //System.out.println("list size (should print 7): " + list1.size());

    for (int i = 0; i < list1.size(); i++){
      System.out.println(list1.get(i).getCountryName());
    } 
    // for (int i = 0; i < list1.size(); i++){
    //   System.out.println(list1.get(i).getCountryName());
    // }

    //TESTING for .contains(Country c) and getPosition(Country c) ---------------------------
    // System.out.println("Does list1 contains() ANDORRA       -- " + list1.contains(c7));
    // System.out.println();
    // System.out.println("Does list1 contains() Argentina   -- " + list1.contains(c1));
    // System.out.println();
    // getPosition --------------------------------------------------------------------------
    // System.out.println("getPosition from list1 of Aruba   -- " + list1.getPosition(c8));
    // System.out.println();
    // System.out.println("getPosition from list1 of Australia   -- " + list1.getPosition(c9));
    
    //get position of Afghanistan and remove it:
    System.out.println("getPosition from list1 of Argentina (ret 3)-- " + list1.getPosition(c1));
    System.out.println("removing Argentina...");
    list1.remove(list1.getPosition(c1));


    //get position of ANDORRA (first elemnt) and remove it: 
    
    System.out.println("getPosition from list1 of Andorra (ret 0)-- " + list1.getPosition(c7));
    System.out.println("removing ANDORRA...");
    list1.remove(list1.getPosition(c7)); // works well!! :))))))

    for (int i = 0; i < list1.size(); i++){
      System.out.println(list1.get(i).getCountryName());
    }

    //TESTING print statements for remove() ------------------------------------------
    //--------------------------------------------------------------------------------
    // System.out.println("---------------------------------------------------------");
    // System.out.println("---------------------------------------------------------");
    // System.out.println();
    
    // //remove (targetitem):
    // System.out.println("removing Armenia: --------------------------- " + list1.remove(c8));

    // for (int i = 0; i < list1.size(); i++){
    //   System.out.println(list1.get(i).getCountryName());
    // }
    // System.out.println("---------------------------------------------------------");

    //TEST FOR isEmpty(), clear() below----------------------------------------------
    //--------------------------------------------------------------------------------
    // System.out.println("is the list empty? (before clear()): " + list1.isEmpty());

    // list1.clear();
    // System.out.println("list size (after clear()): " + list1.size());
    // System.out.println("is the list empty? (after clear()): " + list1.isEmpty());

    // for (int i = 0; i <  list1.size(); i++){ // should give an index out of bounds error: 
    //   System.out.println(i+". "+list1.get(i).getCountryName());
    // }
    // System.out.println("---------------------------------------------------------");
    // //TEST CODE FOR RESORT METHOD:
    // CountryComparator comparatorB = new CountryComparator("UrbanPopulationGrowth", true);
    // System.out.println("ComparatorB created: sorting UrbanPopulationGrowth from Greatest to Least");

    // list1.resort(comparatorB);

    // for (int i = 0; i < list1.size(); i++){ // should give an index out of bounds error: 
    //   System.out.println(list1.get(i).getCountryName());
    // }
  }
}