import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
* Class for Investigation 1, exploring how changing the pivot
* selection for quicksort affects runtime.
* Note that there are many private methods here for how quicksort works.
* You'll modify main, implement two move PivotSelectors, and you're welcome to add
* any additional methods you'd like. 
* 
* @author Anna Rafferty
*/
public class Quicksort {
    private static int MIN_SIZE = 10;

    /** 
     * Sorts an array in increasing order using quicksort.
     * Quicksort code is based off of Carrano and Henry. 
     * array will be sorted in place.
     */
    public static void quicksort(int[] array, PivotSelector pivotSelector) {
        quicksort(array, 0, array.length - 1, pivotSelector);
    }
    
    /** 
     * Helper method:
     * Sorts the part of the array a between index first and index last
     *  in increasing order using quicksort.
     */
    private static void quicksort(int[] a, int first, int last, PivotSelector pivotSelector) {
        if (last - first + 1 < MIN_SIZE) {
            insertionSort(a, first, last);
        } else {
            // create the partition: Smaller | Pivot | Larger
            int pivotIndex = partition (a, first, last, pivotSelector);
            // sort subarrays Smaller and Larger
            quicksort(a, first, pivotIndex - 1, pivotSelector);
            quicksort(a, pivotIndex + 1, last, pivotSelector);
        }
    }

    /** 
     * Swaps the array entries array[i] and array[j]. 
     */
    public static void swap (int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
   
    /**
     * Partitions the array a so that the entries in [first, last]
     * are rearranged into a "smaller" part and a larger part.
     * The return value is the location of the pivot - all the entries
     * located to the left of that index are less than the value at the
     * pivot index, and all the entries to the right of that index are
     * greater than the pivot.
     */
    private static int partition (int[] array, int first, int last, PivotSelector pivotSelector) {

        int pivotIndex = pivotSelector.choosePivotIndex(array, first, last);
        swap(array, pivotIndex, last);
        pivotIndex = last;
        int pivot = array[pivotIndex];
        // determine subarrays Smaller = a[first..endSmaller]
        // and Larger = a[endSmaller+1..last-1]
        // such that entries in Smaller are <= pivot and
        // entries in Larger are >= pivot; initially, these subarrays are empty
        int indexFromLeft = first;
        int indexFromRight = pivotIndex - 1;
        boolean done = false;
        while (!done) {
            // starting at beginning of array, leave entries that are < pivot;
            // locate first entry that is >= pivot; you will find one,
            // since last entry is >= pivot
            while (array[indexFromLeft] < pivot) {
                indexFromLeft++;
            }
            // starting at end of array, leave entries that are > pivot;
            // locate first entry that is <= pivot; you will find one,
            // since first entry is <= pivot
            while (indexFromRight >=0 && array[indexFromRight] > pivot) {
                indexFromRight--;
            }

            if (indexFromLeft < indexFromRight) {
                swap(array, indexFromLeft, indexFromRight);
                indexFromLeft++;
                indexFromRight--;
            } else {
                done = true;
            }
        } 


        // place pivot between Smaller and Larger subarrays
        swap(array, pivotIndex, indexFromLeft);
        pivotIndex = indexFromLeft;
        return pivotIndex;
    }
    
    /**
     * Sorts the specified array in increasing order using insertion sort.
     */
    public static void insertionSort(int[] a) {
        insertionSort(a, 0, a.length-1);
    }
    
    /**
     * Helper method for insertion sort to enable insertion sorting only part
     * of an array.
     */
    private static void insertionSort(int[] array, int first, int last) {    
        for(int i = first+1; i <= last; i++) {
            //i is the index in the array we're going to find the right place for
            int j = i;
            while(j > first && array[j-1] > array[j]) {
                int numToSwapOut = array[j];
                array[j] = array[j-1];
                array[j-1] = numToSwapOut;
                j--;
            }
        }
    }
    
    /**
     * Generates a pseudo-random permutation of the integers from 0 to
     * a.length - 1.
     * See p. 139 of "Seminumerical Algorithms, 2nd edition," by Donald Knuth.
     */
    public static void fillArray(int[] a) {
        // Fill the array with the integers from 0 to a.length - 1.
        for (int k = 0; k < a.length; k++) {
            a[k] = k;
        }
    } // I HAVE SEPARATED THESE FUNCTIONS -- so that we can get data on 
    // rumtimes where the array is completely sorted. 
    public static void shuffleArray(int[] a) {
        //Shuffle.
        for (int k = a.length - 1; k > 0; k--) {
            int swapIndex = (int)Math.floor(Math.random() * (k + 1));
            int temp = a[k];
            a[k] = a[swapIndex];
            a[swapIndex] = temp;
        }
    }
    /**
    * below is another method that takes in an int[] array 
    * and manipulates it so that it is written in it's reverse order
    * @param int[] array 
    **/
    public static void reverseArray(int[] a){
      //reverse
      Stack<Integer> reversalStack = new Stack<Integer>();
      for (int i = 0; i < a.length; i++)
        reversalStack.push(a[i]);
      int count = 0;
      while(!reversalStack.isEmpty()){
        a[count] = reversalStack.pop();
        count++;
      }
    }
    
    /**
     * You'll put your experiments for the investigation here. The current contents
     * is just to give you an example.
     */
    public static void main(String[] args) throws FileNotFoundException {
      //System.err.println("Investigation not yet designed and carried out!");
      
      //This is just an example of how you might do timing - you can erase it and write your own investigation.

      // file writer code: 
      FileOutputStream fos = new FileOutputStream("Quicksortoutput.csv", true);
      PrintWriter pw = new PrintWriter(fos);

      int startArraySize = 10;
      int incrementFactor = 20;
      int targetArrSize = 250;

      //String writeLine = "";
      while (startArraySize <= targetArrSize){
        int[] quicksortArray = new int[startArraySize];
        int numRuns =   50;
        List<PivotSelector> selectors = Arrays.asList(new FirstIndexPivotSelector(), new RandomPivotSelector(), new MedianOf3PivotSelector(), new RandomMedianPivotSelector());
        List<String> names = Arrays.asList("First index", "Random index", "Median index","RandomMedian index");
        //System.out.println("Array size: " + startArraySize);
        String writeLine ="";
        writeLine+=startArraySize;
        for(int j = 0; j < selectors.size(); j++) {
            PivotSelector selector = selectors.get(j);
            String name = names.get(j);
            double total = 0;
            for(int i = 0; i < numRuns + 1; i++) {
                fillArray(quicksortArray);                
                //shuffleArray(quicksortArray);
                reverseArray(quicksortArray);

                //startTime and endTime enclose only the sorting, not anything else. Make sure to 
                //avoid timing things like shuffling the array or printing (printing to the console
                //takes a very long time!)
                long startTime = System.currentTimeMillis();
                //for(int r = 0; r < 10; r++) {
                  quicksort(quicksortArray, selector);  
                //}
                long endTime = System.currentTimeMillis();
                if (i != 0) {//The first time through a particular piece of code may take longer, so we ignore it.
                    total += (endTime - startTime);
                }
            }
            //prints out average...
            if(j==selectors.size()){
              writeLine+=(total / numRuns);
            } else { writeLine+=","+(total / numRuns); }
            //System.out.println(name + ": " + (total / numRuns));
        }
        pw.println( writeLine );
        startArraySize+=incrementFactor;

    }
    pw.close();
    System.out.println("data collection complete");

  }

    
}
