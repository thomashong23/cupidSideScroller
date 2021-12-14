import java.awt.*;

public class BlackHeart {
    public int xPos;
    public int yPos;
    public int dist;
    public int ySpeed;
    public int width=25;
    public int height=25;
    public int newY;
    public boolean isAlive=true;
    public boolean scorable=true;
    public boolean isBlack;
    public int teleport;
    public int blackInt = (int) (Math.random() * 2);
    public Rectangle rec = new Rectangle(xPos, yPos, width, height);

    public BlackHeart(int pXPos, int pYPos) {
        xPos = pXPos;
        yPos = pYPos;


    }



    public void move(){
        rec= new Rectangle(xPos, yPos, width, height);
        xPos = xPos - Main.speed;
        if(xPos<=-Main.WIDTH){
            isAlive = false;
        }

    }
}
