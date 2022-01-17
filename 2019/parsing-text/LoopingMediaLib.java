
/**
 * Write a description of class LoopingMediaLib here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LoopingMediaLib
{
    public static void main()
    {
        //for (int i=1;i<=20;i++){
            //Syst.out.println(MediaFile.readString());
        //}
        
        
        String songInfo = MediaFile.readString();
        int start,end;
        start = end = 0;
       
        while (songInfo!=null){
            end = songInfo.indexOf("|");
            if(end != -1) {
                System.out.println("Title: " + songInfo.substring(start, end));
                start = end + 1;
                songInfo = songInfo.substring(start);
                end = songInfo.indexOf("|");
                start = 0;
                System.out.println("Rating: " +songInfo.substring(start, end));
                start = end + 1;
                songInfo = songInfo.substring(start);
                end = songInfo.indexOf("|");
                start = 0;
            }
            
        }
      
    }
    
    
}
