import java.awt.*;

public class Platform {
    public int sizeNum = (int)(Math.random()*3);
    public int teleport;
    public int yPos = 250;
    public int xPos;
    public int width;
    public int height=30;
    public boolean scorable = true;
    public Rectangle rec= new Rectangle(xPos, yPos, width, height);

    public Platform(){

        if(sizeNum==2){
            width = 195;
        }
        if(sizeNum==1){
            width=140;
        }
        if(sizeNum==0){
            width=65;
        }
    }
    public void move(){
        rec= new Rectangle(xPos, yPos, width, height);
        xPos = xPos - Main.speed;
        if(xPos<=-Main.WIDTH){
            sizeNum = (int)(Math.random()*3);
            xPos = teleport;
            yPos = (int)(Math.random() * 300) + 150;
            scorable = true;
        }

    }
}
