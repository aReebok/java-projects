import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {
   public static void main(String args[]) throws FileNotFoundException {
      FileOutputStream fos = new FileOutputStream("output.csv", true);
      PrintWriter pw = new PrintWriter(fos);
      //Writing data to a csv file
      pw.println("Array Size, First index, Random index,Median index,Random Median index");
      pw.println("10,0.04,0.04,0,0");
      pw.println("50,0.12,0.04,0,0");
      pw.println("100,0.56,0.04,0.08,0.08");
      pw.close();

      System.out.println("File output.csv has been written.");
   }
}