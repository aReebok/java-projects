import java.util.*;
/**
* a small class in order to easily give someone
* a word and its count
**/
public class WordCount {
  // instance variables 
  private String word;
  private int count;
  // constructor and methods
  public WordCount(String word, int count){
    this.word = word;
    this.count = count;
  }

  /**
  * Gets the word stored by this WordCount
  */
  public String getWord(){ return this.word; }

  /** 
  * Gets the count stored by this WordCount
  */
  public int getCount() { return this.count; }  

  /**
  * Return a user-friendly String of information 
  * on the WordCount objects. 
  **/
  @Override
  public String toString(){
    return String.format(word + ":" + count); 
  }
}