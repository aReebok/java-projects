
/**
 * Write a description of class Movie here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Movie
{
    private int duration;
    // instance variables - replace the example below with your own
    private int rating;
    private String title;
    /**
     * Constructor for objects of class Movie
     */
    public Movie()
    {
        // initialise instance variables
        rating = 0;
        title = "";
        duration = 0;
    }

    public String getTitle() {
        return title; 
    }
    public void setTitle(String t) {
    title = t;
     }
     public int getDuration() {
        return duration; 
    }
    public void setDuration(int d) {
        duration = d;

     }
     
}

