
/**
 * Write a description of class MediaLib here.
 *
 * @author (areeba khan)
 * @version (sept7)
 */
public class MediaLib
{
    private double totalCost;
    private int numSongs;
    private int totalRating;
    private double averageCost;
    private int averageRating;
    private String runTime;
    private static int x;
    
    public static void main (){
        
        int numsSongs = 0;
        double totalCost=0.0;
        int totalRating = 0;
        
        System.out.println("Welcome to your Media Library.");
        
        Song song1 = new Song();
        song1.setTitle("Johnny B. Goode");  
        System.out.println(song1.getTitle());
        
        Song song2 = new Song();
        song2.setTitle("Despacito");  
        System.out.println(song2.getTitle());
        
        Song song3 = new Song();
        song3.setTitle("I Like It");  
        System.out.println(song3.getTitle());
        numsSongs = numsSongs + 1; 
        
        Movie movie1 = new Movie();
        movie1.setTitle("Inception");
        movie1.setDuration(199);
        x=movie1.getDuration();
        System.out.println(movie1.getTitle());
        System.out.println ("Duration (hour:miniute) -- " +x/60 +":"+ x%60);
        
        Movie movie2 = new Movie();
        movie2.setTitle("Shape of Voice");
        System.out.println(movie2.getTitle());
        
        Movie movie3 = new Movie();
        movie3.setTitle("Deadpool 2");
        System.out.println(movie3.getTitle());
        
        
        Book book1 = new Book();
        book1.setTitle("The Maze Runner");
        System.out.println(book1.getTitle());
        
        Book book2 = new Book();
        book2.setTitle("The Great Gatsby");
        System.out.println(book2.getTitle());
        
        Book book3 = new Book();
        book3.setTitle("The Alchemist");
        System.out.println(book3.getTitle());
        
        
        
        Song song11 = new Song ("Respect", 1.29, 2);
        System.out.println(song11.getTitle());
        numsSongs = numsSongs + 1; 
        totalCost = totalCost + song11.getPrice();
                totalRating = totalRating + song11.getRating();
        
        Song song12 = new Song ("Stereo Hearts", 0.99, 1);
        System.out.println(song12.getTitle());
        numsSongs = numsSongs + 1; 
        totalCost = totalCost + song12.getPrice();
                totalRating = totalRating + song12.getRating();
        
        Song song13 = new Song ("Where are you now", 1.29, 3);
        System.out.println(song13.getTitle());
        numsSongs = numsSongs + 1; 
        totalCost = totalCost + song13.getPrice();
                totalRating = totalRating + song13.getRating();
        
        Song song14 = new Song ("The One", 1.29, 5);
                System.out.println(song14.getTitle());
                numsSongs = numsSongs + 1; 
                totalCost = totalCost + song14.getPrice();
                totalRating = totalRating + song14.getRating();
        
        Song song16 = new Song ("Sorry", 0.99, 4);
                System.out.println(song16.getTitle());
                numsSongs = numsSongs + 1; 
                totalCost = totalCost + song16.getPrice();
                totalRating = totalRating + song16.getRating();
        
        Song song17 = new Song ("Hello", 0.99, 6);
                System.out.println(song17.getTitle());
                numsSongs = numsSongs + 1; 
                totalCost = totalCost + song17.getPrice();
                totalRating = totalRating + song17.getRating();
        
        Song song15 = new Song ("Life of Pablo", 1.29, 7);
                System.out.println(song15.getTitle());
                numsSongs = numsSongs + 1; 
                totalCost = totalCost + song15.getPrice();
                totalRating = totalRating + song15.getRating();
                
        
        double averageCost = 0.0;
        averageCost = totalCost/numsSongs;
        System.out.println("Average Cost: " + averageCost);
        
        String runTime = "";
        
        
        int averageRating = 0;
        averageRating = totalRating/numsSongs;
        System.out.println("Average Rating: " + averageRating);   
        
        MediaFile.writeString(song11.getTitle()+"|"+song11.getRating());
        //MediaFile.writeString("\n");
        MediaFile.writeString(song12.getTitle()+"|"+song12.getRating());
        //MediaFile.writeString("\n");
        MediaFile.writeString(song13.getTitle()+"|"+song13.getRating());
        //MediaFile.writeString("\n");
        MediaFile.writeString(song14.getTitle()+"|"+song14.getRating());
        //MediaFile.writeString("\n");
        MediaFile.writeString(song15.getTitle()+"|"+song15.getRating());
        //MediaFile.writeString("\n");
        MediaFile.writeString(song16.getTitle()+"|"+song16.getRating());
        //MediaFile.writeString("\n");
        MediaFile.writeString(song17.getTitle()+"|"+song17.getRating());
        //MediaFile.writeString("\n");
        MediaFile.writeString("Arms Around You|8");
        //MediaFile.writeString("\n");
        MediaFile.writeString("Spring Waltz|10");
        //MediaFile.writeString("\n");
        MediaFile.writeString("Beethoven Silence|9");
        //MediaFile.writeString("\n");
        
        MediaFile.saveAndClose();
        
    }
    
}
