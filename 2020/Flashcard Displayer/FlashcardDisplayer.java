
import java.time.LocalDateTime;

import java.util.*;
import java.io.*;

public class FlashcardDisplayer {
  // instance variable
  private FlashcardPriorityQueue pqFlashcards;

  /**
  * Creates a flashcard displayer with the flashcards in file.
  * File has one flashcard per line. On each line, the date the flashcard 
  * should next be shown is first (format: YYYY-MM-DDTHH-MM), followed by a comma, 
  * followed by the text for the front of the flashcard, followed by another comma,
  * followed by the text for the back of the flashcard. You can assume that the 
  * front/back text does not itself contain commas. (I.e., a properly formatted file
  * has exactly 2 commas per line.)
  */
  public FlashcardDisplayer(String file){
    File fileContent = new File(file); 
    Scanner filScanner = null;
    try{
      filScanner = new Scanner(fileContent);
    } catch (FileNotFoundException e) {
      System.err.println(e);
      System.exit(1);
    }
    // store Flashcards into flashcardpriorityqueue object
    this.pqFlashcards = new FlashcardPriorityQueue();

    while (filScanner.hasNextLine()){
      String line = filScanner.nextLine();
      //System.out.println(line);
      this.pqFlashcards.add(new Flashcard(line));
    }
    displayFlashcards();
  }
  
  /**
  * Writes out all flashcards to a file so that they can be loaded
  * by the FlashcardDisplayer(String file) constructor. Returns true
  * if the file could be written. The FlashcardDisplayer should still
  * have all of the same flashcards after this method is called as it
  * did before the method was called. However, it may be that flashcards
  * with the same exact next display date and time are removed in a different order.
  */
  public boolean saveFlashcards(String outFileName){
    Queue<Flashcard> tempQ = new LinkedList<Flashcard>();
    FileOutputStream fos = null;
    try{
      fos = new FileOutputStream(outFileName, true);
    } catch (FileNotFoundException e){
      System.err.println("Error: file not found exception ln 66!");
      System.exit(1);
    }
    PrintWriter printWriter = new PrintWriter(fos); // 
    // write this.pqFlashcards
    while(!this.pqFlashcards.isEmpty()){
      // peek
      printWriter.println(this.pqFlashcards.peek().toString());
      // poll
      tempQ.add(this.pqFlashcards.poll());
    }
    // put tempQ back into this.pqFlashcards

    while(!tempQ.isEmpty())
      this.pqFlashcards.add(tempQ.poll());

    printWriter.close();
    return true;
  }
  
  /**
  * Displays any flashcards that are currently due to the user, and 
  * asks them to report whether they got each card correct. If the
  * card was correct, it is added back to the deck of cards with a new
  * due date that is one day later than the current date and time; if
  * the card was incorrect, it is added back to the card with a new due
  * date that is one minute later than that the current date and time.
  */
  public void displayFlashcards() {
    System.out.println("Time to practice flashcards! The computer will display "
    + "your flashcards, you generate the response in your head, and then see" 
    + "if you got it right. The computer will show you cards that you miss more "
    + "often than those you know!");

    String userInput = "";
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter a command:");
    userInput = scanner.nextLine();

    while (!userInput.equals("exit")){
      if (userInput.equals("quiz")) {
        helpQuiz();
      } else if (userInput.equals("save")) { 
        System.out.println("Type a filename where you'd like to save the flashcards: ");
        String filepathString = scanner.nextLine();
        saveFlashcards(filepathString);
      } else {
        System.err.println("Please enter valid command: quiz, save, or exit");
      }
      System.out.println("Enter a command:");
      userInput = scanner.nextLine();
    }
    System.out.println("Goodbye!");
  }

  /*
  * Helper function for the displayFlashcards() function: it displays the
  * flash cards that are due in the next 24 hours.
  * it redisplays cards if you've gotten them wrong.
  */
  private void helpQuiz(){
    // scanner
    Scanner quizScanner = new Scanner(System.in);
    String userInput = "";
    LocalDateTime currentTime = LocalDateTime.now().minusHours(6).withNano(0);
    LocalDateTime timeFrame = currentTime.plusHours(12).withNano(0);
    Queue<Flashcard> tempQ = new LinkedList<Flashcard>();

    while(this.pqFlashcards.peek().getDueDate().compareTo(timeFrame) < 0) {
      System.out.println("-------------------------");
      System.out.println("-----------Card----------");

      Flashcard tempCard = this.pqFlashcards.poll();
      //System.out.println(tempCard);
      //print out front of the card:
      System.out.println(tempCard.getFrontText());
      System.out.println("[Press return for back of card]");
      userInput = quizScanner.nextLine(); // user presssses enter...

      //update time... because user just practiced this card at this instance of time
      currentTime = LocalDateTime.now().minusHours(6).withNano(0);
      //timeFrame = currentTime.plusHours(24).withNano(0);  

      System.out.println(tempCard.getBackText());
      System.out.println("Press 1 if you got it correct and 2 if not.");
      while(!userInput.equals("1") && !userInput.equals("2")){
        userInput = quizScanner.nextLine();
      } //update current time
      if (Integer.parseInt(userInput)==1) {
        tempQ.add(new Flashcard(currentTime.plusHours(24).toString(),tempCard.getFrontText(), tempCard.getBackText()));
      } else { // equals 2
        tempQ.add(new Flashcard(currentTime.plusMinutes(1).toString(),tempCard.getFrontText(), tempCard.getBackText()));
      }
    }
      System.out.println("No cards are waiting to be studied for the next 12 hours!" 
      +"\nTo review missed cards, retype 'quiz' or to save current progress, 'save'!");
      //System.out.println(this.qCardsStudied);
      // re write with new info --
      while (!tempQ.isEmpty()) {
        this.pqFlashcards.add(tempQ.poll());
      }
  }

  public static void main (String[] args) {
    if (args.length != 1) {
      System.err.println("Usage Error: make sure you've entered a filename that holds your flashcards!");
      System.exit(1);
    }
    String filePath = args[0];
    FlashcardDisplayer fcDisplayer = new FlashcardDisplayer(filePath);

  }

}