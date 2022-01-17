import java.util.Random;

/**
* Chooses a random index for pivot selection in a small area around the 
* median of 3 pivot points 
*/
public class RandomMedianPivotSelector implements PivotSelector {
    private Random randomNumberGenerator = new Random();

    /**
     * Returns a random pivot index between the integers first and last (inclusive).
     */
     public int choosePivotIndex(int[] array, int first, int last) {
        int median = (first+last)/2;
        //System.out.println("BO???");
        if (last-first < 6){ return median; } // too small of a partition
        // then just return the median
        // else generate a randomnumber in a small range around this median
        // and return that. 
        int max = (last-first-3)/3;
        int min = -1*max;
        int randomnum = randomNumberGenerator.nextInt(max+1 - min) + min;
        //System.out.println("median: " + median + "| max: " + max + " | min: " + min);
        //System.out.println("random num = " + randomnum);
        return median + randomnum;        
     }
     public static void main(String[] args) {
       //testcode
      //  RandomMedianPivotSelector rmps = new RandomMedianPivotSelector();
      //  int[] intList = {3, 1, 2, 14, 4, 1, 6, 12, 11, 8, 14}; // first = 0, last = 10
      //             //              ^  ^  ^   ^   ^             << range of results
      //  int ret = rmps.choosePivotIndex(intList, 0, 10);
      //  System.out.println("The O pivot index at array["+ret+"] is "+intList[ret]);
     }
}