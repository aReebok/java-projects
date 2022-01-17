
/**
 * Write a description of class Ship here.
 *
 * @author (areeba)
 * @version (a version number or a date)
 */
public class Ship
{
    //attributes of a ship -- length, orientation,
    //boolean of occupied or not 
    private int length,health;
    private boolean orient;
    private String name, img;
    
    public Ship(String name, int length, int health, boolean orient, String img){
        this.length = length;
        this.orient = orient;//if true-its vert, else horiz
        this.name = name;
        this.img = img;
        this.health = health;
    }
    //length setter and getter
    public void setLength(int l){
        length = l;
    }
    public int getLength(){
        return length;
    }
    //orientation setter and getter
    public void setOrient(boolean o){
        orient = o;
    }
    public boolean getOrient(){
        return orient;
    }
    
    public void setIMG(String i){
        img = i;
    }
    
    public String getIMG(){
        return img;
    }
    
    public void setName(String n){
        name = n;
    }
    public String getName(){
        return name;
    }
    
    public void setHealth(int h){
        health = h;
    }
    public int getHealth(){
        return health;
    }
}
