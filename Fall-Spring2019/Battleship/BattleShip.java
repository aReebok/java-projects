import java.awt.FlowLayout;
//import java.awt.event.ActionListener;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BattleShip extends JFrame
{
    public static int r = 10, c = 10;
    public static BattleShip frame;
    
     static ShipButton [][] arrayButton = new ShipButton[r][c];
    
    public BattleShip(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(r,c));
        
        
        ListenerClass listener = new ListenerClass();
        //for loop below, initializes id number and status
        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                arrayButton[i][j] = new ShipButton(new JButton(),j+c*i,false,false,"X");
                panel.add(arrayButton[i][j].getButton());
                arrayButton[i][j].getButton().addActionListener(listener);
            } 
        }
        add(panel);
        //a forloop to print out??
        
    } 
    private class ListenerClass implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //JOptionPane.showMessageDialog(null, String.format("%s",event.getActionCommand()));
            JButton temp = (JButton) e.getSource();
            
            for (int i = 0; i < r; i++){
                for (int j = 0; j<c; j++){
                    if (arrayButton[i][j].getButton() == temp){
                        arrayButton[i][j].getButton().setText(arrayButton[i][j].getIcon());
                        arrayButton[i][j].setStatus(true);
                        System.out.println("Position: ("+j+", "+i+") clicked");
                    }
                }
            }
            
            
         }
     }
    public static void main(String[] args){
        //hi
        frame = new BattleShip();
        frame.setTitle("Battleship");
        frame.setSize(r*100,c*100);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        
        Ship [] allShips = {new Ship("Carrier",5,5,false, "@"),
            new Ship ("Battleship", 4,4, false, "%"), 
            new Ship("Cruiser",3,3, false, "&"),
            new Ship("Submarine",3,3, false, "#"),
            new Ship("Destroyer",2,2, false, "!")};   
        printRandStats(allShips);
        Ship tempS;
        int tempLen,initX,initY;
        for (int i = 0; i <allShips.length; i++){
            tempS = allShips[i];
            tempLen = allShips[i].getLength();
            initX = (int)(Math.random() * ((c-1+0)+1))+0;
            initY = (int)(Math.random() * ((r-1+0)+1))+0;
            
            // arrayButton[initX][initY].setOccup(true);//occupied spot!
            //check spots-
            
            boolean ans[] = intersectShips(initX,initY,tempS,arrayButton);//returned an array of boolean ans [2]
            //if both are false -- then pick a random number bw 0-1 and
            //place ship down.
            placeShips(arrayButton, ans, allShips[i] ,initX,initY);
            

            //if both are false -- 
            
            for (boolean a: ans){System.out.println("ship "+i+":"+ a+"- POS:"+initX+", "+initY);}
        } 
        for (int j = 0; j < r ; j++){
                for (int k = 0; k < c; k++){
                    System.out.print(arrayButton[j][k].getIcon());
                }
                System.out.println("");
            }
      }
      public static void printRandStats(Ship [] s){
        for (int i = 0; i<s.length; i++){
            int x= (int)(Math.random()*2);
            if (x==1){
                s[i].setOrient(true);
            }
        }
         for (int i = 0; i<s.length; i++) {
             System.out.println(s[i].getName()+". Length: "+ s[i].getLength()+". Verticle?: "+ s[i].getOrient()+" ; " +s[i].getIMG());
        } 
    }
    public static boolean [] intersectShips(int posX, int posY,Ship s, ShipButton [][] b){
        //ans[1] is UP or LEFT
        //ans[0] is DOWN or RIGHT
        //P1 is if its possible to go UP or LEFT
        //p2 is if its possible to go DOWN or RIGHT
        //posX is the columns, posY is the rows - c and r
        boolean ans [] = new boolean[2];
        for (int o = 0; o<ans.length; o++)
        {
            ans[o]=false;
        }
        //if ans[0] or [1] are false,then there is no intersecting ship.
        if (s.getOrient()==true){//vert + up or down && p1 == true && p2 == true
            for (int i = posY; i<=s.getLength(); i++){//down
                if (b[i][posX].getOccup() == true||(i-1<=-1)){
                    ans[0] = true;
                    break;
                } 
            }
            for (int i =s.getLength();i<=posY; i--){//up
                if (b[i][posX].getOccup() == true||(i-1<=-1)){
                    ans[1] = true;
                    break;
                } 
            } 
        }
        // HORIZONTAL STATEMENTS --
        else if(s.getOrient() == false ){//horiz, right AND left&& p1==true && p2==true
            for (int i = posX; i<=s.getLength(); i++){//right
                if (b[posY][i].getOccup()==true||(i-1<=-1)){
                    ans[0] = true;
                    break;
                }
            }
            for (int i = s.getLength(); i<=posX; i--){//left
                if ((b[posY][i].getOccup()==true)||(i-1<=-1)){
                    ans[1] = true;
                    break;
                }
            }   
        }
        
        return ans;
    }
    public static void placeShips(ShipButton [] [] but, boolean [] ans, Ship s, int posX, int posY){
        //posX = column, and posY = row
        //boolean [] ans will give the last most necesary info--if the ship can be placed or not
        //IF BOTH INDEXES ARE FALSE -- can print either way:
        if (s.getOrient()==true){//VERT, up(1) or down(0)
            if (ans[0]==false && ans[1]==false){
                if ((int)(Math.random()*2)==1){//eliminate one choice
                    ans[1]=true;
                }else{ans[0]=true;}
                //one choice LEFT
                //PLACE SHIP IN ORIENTATION THAT IS FALSE
                if(ans[1] == true){//PLACE DOWN 
                    for (int i = posY; i<s.getLength()+1; i++){
                        but[i][posX].setOccup(true);
                        but[i][posX].setIcon(s.getIMG());
                    }
                }else{//PLACE DOWN
                    for (int i = s.getLength()+1; i <= posY ; i--){
                        but[i][posX].setOccup(true);
                        but[i][posX].setIcon(s.getIMG());
                    }
                }
            }
           else if (ans[0]==false && ans[1]==true){//can only go down
               for (int i = posY; i<s.getLength()+1; i++){
                        but[i][posX].setOccup(true);
                        but[i][posX].setIcon(s.getIMG());
                        
                    }
            
            }else{
                for (int i = s.getLength()+1; i <= posY ; i--){
                        but[i][posX].setOccup(true);
                        but[i][posX].setIcon(s.getIMG());
                    }
                
            }
    
        }
         else {//HORIZONTAL ORIENTATION; 0 is false = right, 1 is false = LEFT
            if (ans[0]==false && ans[1]==false){
                if ((int)(Math.random()*2)==1){//eliminate one choice
                    ans[1]=true;
                }else{ans[0]=true;}
                //one choice LEFT
                //PLACE SHIP IN ORIENTATION THAT IS FALSE
                if(ans[1] == true){//PLACE RIGHT 
                    for (int i = posX; i<s.getLength()+1; i++){
                        but[posY][i].setOccup(true);
                        but[posY][i].setIcon(s.getIMG());
                        
                    }
                }else{//PLACE LEFT
                    for (int i = s.getLength()+1; i <= posX ; i--){
                        but[posY][i].setOccup(true);
                        but[posY][i].setIcon(s.getIMG());
                    }
                }
            }
           else if (ans[0]==false && ans[1]==true){//can only go RIGHT!
               for (int i = posX; i<s.getLength()+1; i++){
                        but[posY][i].setOccup(true);
                        but[posY][i].setIcon(s.getIMG());
                        
                    }
            }else{
                for (int i = s.getLength()+1; i <= posX ; i--){
                        but[posY][i].setOccup(true);
                        but[posY][i].setIcon(s.getIMG());
                    }
            }
        }
    }
}
