import java.util.*;
import java.io.*;
/**
* The WordCounter class can count the words in a text file and then output them 
* in some format. WordCounter counts all of the words in the file except "stop words".
* There are two usage cases for WordCounter:
* > java WordCounter textFileName --> 
* prints out a list of words and their occurrence counts:  like so -- word:2
* > java WordCounter textFileName numberOfWordsToInclude outFileName
* write HTML to the file outFileName, containing a word cloud generated by 
* WordCloudMaker.java with the given specifications
**/

public class WordCounter{
  // instance variables----------------------------
  private WordCountMap wordMap;
  private Set<String> stopWords;
  private List<WordCount> wcList;

  // constructor (2)-------------------------------
  /**  
  * class constructor takes in just the text file name, prints out the map 
  **/
  public WordCounter(String textFileName){
    this.stopWords = new HashSet<String>();
    wordMap = new WordCountMap();
    getStopWords();// store stopWords
    File textFile = new File(textFileName);

    Scanner textFileScanner = null;
    try{
      textFileScanner = new Scanner(textFile);
    } catch (FileNotFoundException e) {
      System.err.println("ERROR: >>> textFileName cannot be found (argument 1) :(!\n"+ e);
        System.exit(1);
    }

    while(textFileScanner.hasNextLine()){
      String line = textFileScanner.nextLine();
      // strip line of all the punctuation and numbers...
      String[] lineArr = line.split(" ");

      for (int i = 0 ; i < lineArr.length; i++) {
        String stripedWord = stripWord(lineArr[i]); 
        if(!stopWords.contains(stripedWord) && stripedWord.length() > 0) // if not a stopword: add to map!
        {
          wordMap.incrementCount(stripedWord);
        }
      }
    }
    this.wcList = wordMap.getWordCountsByCount();
  }
  /**  
  * constructor takes in the textFileName, numberOfWordsToInclude, and writes a file into 
  * outFileName --> html
  **/
  public WordCounter(String textFileName, int n, String outFileName){
    this(textFileName);
    List<WordCount> shorterList = new ArrayList<WordCount>();
    if (n < this.wcList.size()){
      for (int i = 0; i < n; i++) 
        shorterList.add(wcList.get(i));
    } else { shorterList = this.wcList; }
    String doc = WordCloudMaker.getWordCloudHTML("Word Cloud", shorterList);
    //write String doc to file outFileName
    FileOutputStream fos = null;
    try{
      fos = new FileOutputStream(outFileName, true);
    } catch (FileNotFoundException e){
      System.err.println("Error: file not found exception ln 66!");
      System.exit(1);
    }

    PrintWriter printWriter = new PrintWriter(fos);
    printWriter.println(doc);
    
    // for(int k = 0; k < shorterList.size(); k++) {
    //   String line = shorterList.get(k).getWord() + ":" 
    //   + shorterList.get(k).getCount();
    //   printWriter.println( line );
    // }
    printWriter.close();
  }

  /**
  * Helper function: strip word from punctuations.
  **/
  private String stripWord(String word){
    word = word.toLowerCase();
    int i = 0;
    // front punctuations: 
    if (word.length() == 0) { return ""; }
    while (!Character.isLetter(word.charAt(i)) && i < word.length()-1) i++;
    int j = word.length()-1;
    // back punctuations:
    while(!Character.isLetter(word.charAt(j)) && j > i) j--;
    if (i == j) { return ""; }
    String stripedWord = word.substring(i,j+1);
    return stripedWord;
  }
  
  /** helper function to take in the stop words from StopWords.txt**/
  private void getStopWords(){
    String stopWordsPath = "StopWords.txt";
    File stopWordsFile = new File(stopWordsPath);

    Scanner stopWordScanner = null;
    try{
      stopWordScanner = new Scanner(stopWordsFile);
    } catch (FileNotFoundException e) {
      System.err.println("ERROR: >>> File name miss written in code line 30 :(!\n"+ e);
        System.exit(1);
    }
    while (stopWordScanner.hasNextLine()){
      String line = stopWordScanner.nextLine();
      this.stopWords.add(line);
    }
    stopWordScanner.close();
  }

  /** 
  * this function overrides the default to string function: 
  * it takes the data and prints it in a user friendly format
  * Example: "word1:100\nword2:50\nword3:20"
  **/
  @Override
  public String toString() {
    String output = "";
    for (int i = 0; i < wcList.size(); i++)
      output+=wcList.get(i).getWord()+":"+wcList.get(i).getCount()+"\n";
    return output;
  }

  public static void main(String[] args) {
    // commandline prompts must look like either of these:
    // 1.  java WordCounter textFileName
    // 2.  java WordCounter textFileName numberOfWordsToInclude outFileName
    String textFileName = "";
    int numberOfWordsToInclude = 0;
    String outFileName = "";
    if(args.length == 1) { // given only textFileName
      textFileName = args[0];
      WordCounter wcObject = new WordCounter(textFileName);
      System.out.print(wcObject.toString());
    } else if (args.length == 3) {
      textFileName = args[0];
      numberOfWordsToInclude = Integer.parseInt(args[1]);
      outFileName = args[2]; // take in all 3 args
      WordCounter wcObj = new WordCounter(textFileName,numberOfWordsToInclude,outFileName);

    } else { System.out.println("Usage Error: must give 1 or 3 arguments to run this program!");}
  }
}