import java.awt.*;

public class Cupid {

    public int xpos=50;                //the x position
    public int ypos=0;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy=2;                    //the speed of the hero in the y direction
    public int width=50;
    public int height=46;
    public boolean isAlive=true;//a boolean to denote if the hero is alive or dead.
    public boolean gravity=true;



    public Rectangle rec= new Rectangle(xpos, ypos, width, height);
    public Rectangle feet= new Rectangle(xpos, ypos+45, width, height);

    public void cupid() {


         //construct a rectangle.  This one uses width and height varibles

    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
       // xpos = xpos + dx;

        ypos = ypos + dy;

        rec= new Rectangle(xpos, ypos, width, height);
        feet= new Rectangle(xpos, ypos+45, width, height);




        if (gravity) {
            dy = dy + 1;
        }
        else{
            dy = 0;
        }






        //always rebuild or update the rectangle after you've changed an object's position, height or width
        rec = new Rectangle(xpos, ypos, width, height);

    }
}
