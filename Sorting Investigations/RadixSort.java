import java.util.*;
import java.util.PrimitiveIterator.OfDouble;
import java.util.concurrent.LinkedBlockingDeque;
import java.io.*;
import java.lang.Exception;


/**
* This class RadixSort was created to sort strings of words using
* LSD and MSD Radix sorting -- additionally, this class times these 
* two sorts and prints out the average runtimes in the console.
**/
public class RadixSort{
  // static instance variable
  private final static int ASCII_LOWERCASE_A = 97; 
  
  /**
  * Uses standard least significant digit radix sort to sort the
  * words.
  *
  * @param A list of words. Each word contains only lower case letters in a-z.
  * @return A list with the same words as the input argument, in sorted order
  */
  public static List<String> radixSort(List<String> words){
      //creating list of queues ---
      List<Queue<String>> listOfQueues = new ArrayList<Queue<String>>();
      Queue <String> toSort = new LinkedList<String>();

      // 1 - 26 indecies  -- for each alphabets 
      //(at index 0, there is a space for some elements who are too small)...
      for (int i = 0; i < 27; i++) 
        listOfQueues.add(new LinkedList<String>()); // initialize 27 queues

      //fill up toSort with the words list
      for(String word : words) 
        toSort.add(word);
      int maxWordSize = getMaxStringLength(words);

      // radix sorting loop : 
      for(int placeValue = maxWordSize-1; placeValue >= 0; placeValue--) {
        while(!toSort.isEmpty()){
          if(toSort.peek().length() <= placeValue) 
            listOfQueues.get(0).add(toSort.poll());
           else 
            listOfQueues.get((int) (toSort.peek().charAt(placeValue)) - ASCII_LOWERCASE_A + 1).add(toSort.poll());
        }
        // dequeue everything back into toSort queue
        for(int j = 0; j < listOfQueues.size(); j++) 
          while(!listOfQueues.get(j).isEmpty()) 
             toSort.add(listOfQueues.get(j).poll());
      }

      //finished sorting, now return list of sorted words.
      List <String> sortedWords = new ArrayList<String>();

      while(!toSort.isEmpty())
        sortedWords.add(toSort.poll());

      return sortedWords;
  }

  /**
  * This is a private helper function that gets the length of the largest word
  * in the given list of words. This can help us determine how many times to 
  * iterate...
  * @param List<String> : list of words
  * @return int: length of the longest string in list of words.
  **/
  private static int getMaxStringLength(List<String> words) {
    int max = 0;
    for(String word : words)
      if (word.length() > max)
        max = word.length();

    return max;
  }

  /**
  * A variation on radix sort that sorts the words into buckets by their initial letter,
  * and then uses standard radix sort to separately sort each of the individual 
  * buckets. Recombines at the end to get a fully sorted list.
  *
  * @param A list of words. Each word contains only lower case letters in a-z.
  * @return A list with the same words as the input argument, in sorted order
  */
  public static List<String> msdRadixSort(List<String> words){ 
      // creating list of queues ---
      List<Queue<String>> listOfQueues = new ArrayList<Queue<String>>();
      // 0 - 25 indecies  -- for each alphabets 
      for (int i = 0; i < 26; i++) 
        listOfQueues.add(new LinkedList<String>()); // initialize 26 queues  
      // sort by first letter:
      for(String word : words)
        listOfQueues.get((int)(word.charAt(0)) - ASCII_LOWERCASE_A).add(word);
      // sorting using radixSort():
      for (int i = 0; i < listOfQueues.size(); i++) {
        List<String> tempList = new ArrayList<String>();
        // if queue only holds 1 word then its already sorted...
        if(listOfQueues.get(i).size() == 0 || listOfQueues.get(i).size() == 1){
          continue; // if the queue has only one word, then there is nothing 
          // to be sorted, so continue to the next iteration. 
        } else {
          while(!listOfQueues.get(i).isEmpty()) // store into temp list
          tempList.add(listOfQueues.get(i).poll());
          // call radixsort and store into tempList
          tempList = radixSort(tempList);
          // put it back into the queue at index i in listOfQueues
          for(String item : tempList)
            listOfQueues.get(i).add(item);
        }
      }
      // move list of queues into List result (after clearing words)
      List<String> result = new ArrayList<String>();
      for(int i = 0; i < listOfQueues.size(); i++)
        while(!listOfQueues.get(i).isEmpty())
          result.add(listOfQueues.get(i).poll());

      return result;
  }


  /**
  * below are some test lines for the radix sorting algorithm 
  * additionally, the .txt files can be read in below, along 
  * with the average runtime calcualtions are done below too!
  **/
  public static void main(String[] args) {
      //Read in string file into list and shuffle :
      List<String> words = new ArrayList<String>();
      File smallWord = new File("word_array.txt");
      Scanner scanner = null;
      try{ 
        scanner = new Scanner(smallWord);
      } catch (FileNotFoundException e) {
          System.err.println("ERROR: >>> file not found :(!\n"+ e);
          System.exit(1);
      }

      while (scanner.hasNextLine()){
        String word = scanner.nextLine();
        if(word.length()==0 || word.equals(" ")){
          continue; //skip newlines and empty strings
        }
        words.add(word);
      }
      System.out.println("Words length: " +words.size());
      //shuffle List using Collections method and TIMING code below
      int numRuns = 100;
      double total = 0;
      for (int sort = 0; sort < 2; sort++){
        for(int i = 0; i < numRuns + 1; i++) {
          Collections.shuffle(words); 
          if (sort == 0) {
            //startTime 
            long startTime = System.currentTimeMillis();
            RadixSort.radixSort(words);
            long endTime = System.currentTimeMillis();
            if (i != 0) {//The first time through a particular piece of code may take longer, so we ignore it.
                total += (endTime - startTime);
            }
          } else {
            //startTime 
            long startTime = System.currentTimeMillis();
            RadixSort.msdRadixSort(words);
            long endTime = System.currentTimeMillis();
            if (i != 0) {//The first time through a particular piece of code may take longer, so we ignore it.
                total += (endTime - startTime);
            }
          }
        }
        //System.out println here average 
        if (sort == 0) {
          System.out.println("LSD Radix Sorting: " + total/numRuns);
        } else { System.out.println("MSD Radix Sorting: " + total/numRuns); }
      }
      
      //calculate average 
      // System.out.println("List before shuffeling (sorted) : " + words); 
      // System.out.println("\nList after shuffeling : " + words ); 
    
      // System.out.println("\nList after sorting : " + words); 
      
      // // test code for char to int and int to char // // 
      //    int ASCII_LOWERCASE_A = 97; 
      //    System.out.print("a in ascii: "); 
      //    System.out.println((int)('a'));
      //    System.out.println("a will be stored in index: " + ((int)('a')-ASCII_LOWERCASE_A));
      //    System.out.print("d in ascii: "); 
      //    System.out.println((int)('d'));    
      //    System.out.println("d will be stored in index: " + ((int)('d')-ASCII_LOWERCASE_A)); 
      //    System.out.println("d is "+((int)('d')-ASCII_LOWERCASE_A)+ " which is ..." +(char)(3+ASCII_LOWERCASE_A));
  }
}