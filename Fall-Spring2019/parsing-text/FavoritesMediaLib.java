
/**
 * Write a description of class FavoritesMediaLib here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FavoritesMediaLib
{
    public static void main()
    {
        String songInfo = MediaFile.readString();
        int start,end;
        start = end = 0;
        
        System.out.println("My Favorite Songs");
        System.out.println("------------------");
        
       
        while (songInfo!=null){
            end = songInfo.indexOf("|");
            if(end != -1) {
                
                    String l1 =  "Title: " + songInfo.substring(start, end);
                    start = end + 1;
                    songInfo = songInfo.substring(start);
                    end = songInfo.indexOf("|");
                    start = 0;
                    String l2 = songInfo.substring(start, end);
                    if (Integer.parseInt(songInfo.substring(start,end))<=6){
                        System.out.println(l1 + " ("+l2+")");
                    }
                    start = end + 1;
                    songInfo = songInfo.substring(start);
                    end = songInfo.indexOf("|");
                    start = 0;
                
                
            }
            
        }
    }
}
