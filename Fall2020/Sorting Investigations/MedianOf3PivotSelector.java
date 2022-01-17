/**
*  class MedianOf3PivotSelector pivot selection methods for quicksort.
*/
public class MedianOf3PivotSelector implements PivotSelector{
    /**
     * Returns a pivot index between the integers first and last (inclusive).
     * I.e., if first = 0 and last = 3, returns one of 0, 1, 2, or 3.
     */
     public int choosePivotIndex(int[] array, int first, int last){
       int middle = (first+last)/2;
       if((array[last] < array[first] && array[first] < array[middle]) 
       || (array[middle] < array[first] && array[first] < array[last])){
          return first;
       } else if ((array[first] < array[last] && array[last] < array[middle]) 
       || (array[middle] < array[last] && array[last] < array[first])) {
         return last;
       } else {
         return middle;
       }
     }
     public static void main(String[] args){
       //test code 
      //  MedianOf3PivotSelector ps = new MedianOf3PivotSelector();
      //  int[] s = {1, 4, 3, 6, 8};
      //  int answer = ps.choosePivotIndex(s, 0, 4);
      //  System.out.println("middle is at index array[" + answer + "] : "
      //  + s[answer] );
     }
}