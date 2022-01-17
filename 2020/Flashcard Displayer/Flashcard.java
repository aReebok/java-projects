import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Creates a new flashcard with the given dueDate, text for the front
 * of the card (front), and text for the back of the card (back).
 * dueDate must be in the format YYYY-MM-DDTHH:MM. For example,
 * 2020-05-04T13:03 represents 1:03PM on May 4, 2020. It's
 * okay if this method crashes if the date format is incorrect.
 * In the format above, the time may or may not include milliseconds. 
 * The parse method in LocalDateTime can deal with this situation
 *  without any changes to your code.
 */
public class Flashcard implements Comparable<Flashcard>{
  // instance variable 
  private LocalDateTime dueDate;
  private String front, back;

  // constructors
  public Flashcard(String cardInfo) {
    // given cardInfo (example): "2020-11-01T01:03,Apia,Samoa"
    String[] info = cardInfo.split(",");
    initFlashcard(info[0], info[1], info[2]);
  }
  public Flashcard(String dueDate, String front, String back) {
    initFlashcard(dueDate, front, back);
  }
  /* private helper initializer to avoid repeated code in constructors above */
  private void initFlashcard(String dueDate, String front, String back){
    this.dueDate = LocalDateTime.parse(dueDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    this.front = front;
    this.back = back;
  }
  /**
  * Gets the text for the front of this flashcard.
  */
  public String getFrontText() { return this.front; }
  
  /**
  * Gets the text for the Back of this flashcard.
  */
  public String getBackText() { return this.back; }
  
  /**
  * Gets the time when this flashcard is next due.
  */
  public LocalDateTime getDueDate() { return dueDate; }



  /*
  * this Flashcard.compareTo(Flashcard) returns 0 if dueDate of the two
  * flashcards are the same, a positive int if the current Flashcard is due sooner
  * than one its being compared to, and a negative int if it should be given less
  * priority than the Flashcard being compared to.
  */
  @Override
  public int compareTo(Flashcard flashcard) { return -1*(this.dueDate.compareTo(flashcard.getDueDate())); }

  /*
  * The given object information is returned in a userfriendly String
  */
  @Override
  public String toString(){
    return dueDate+","+front+","+back;
  }

  public static void main(String[] args) {

    // class test code below ... 
    String card1 = "2002-01-01T01:01:01,Beijing,China";
    String card2 = "2020-11-01T01:03,Kampala,Uganda";
    String card3 = "2020-11-01T01:03,Kabul,Afghanistan";
    String card4 = "2020-12-02T16:03,Kabul,Afghanistan";

    Flashcard fc = new Flashcard(card1);
    System.out.println(fc.getDueDate() + "\n" + fc.toString());

    System.out.println("card2 " + card2.toString()); // prints positive int
    Flashcard flashcard = new Flashcard(card2);
    System.out.println(fc.compareTo(flashcard));

    System.out.println("card3 " + card3.toString()); // prints negative int
    flashcard = new Flashcard(card3);
    System.out.println(fc.compareTo(flashcard));
    
    System.out.println("card1 " + card1.toString()); // prints 0
    flashcard = new Flashcard(card1);
    System.out.println(fc.compareTo(flashcard));

    System.out.println("card4 " + card4.toString());  // prints positive int
    flashcard = new Flashcard(card4);
    System.out.println(fc.compareTo(flashcard));

  }

}


