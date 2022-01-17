import java.awt.FlowLayout;
//import java.awt.event.ActionListener;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ShipButton 
{
    private JButton button;
    private int idNum;
    private boolean clicked,occupy;
    private String icon;
    
    public ShipButton(JButton button, int idNum, boolean clicked, boolean occupy, String icon)
    {
        this.button = button;
        this.idNum = idNum;
        this.clicked = clicked;
        this.occupy = occupy;
        this.icon = icon;//NOT NEEDED?
    }
    
    public void setID(int id){
        idNum = id;
    }
    
    public int getID(){
        return idNum;
    }
    
    public void setButton(JButton b){
        button = b;
    }
    
    public JButton getButton(){
        return button;
    }
    
    public void setStatus(boolean c){
        clicked = c;
    }
    
    public boolean getStatus(){
        return clicked;
    }
    
    public void setOccup(boolean o){
        occupy = o;
    }
    
    public boolean getOccup(){
        return occupy;
    }
    
    public void setIcon(String i){
        icon = i;
    }
    
    public String getIcon (){
        return icon;
    }
}
