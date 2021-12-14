import java.awt.*;

public class Ground {
    public int xpos;
    public int ypos= 570;
    public int width=Main.WIDTH +50 ;
    public int height=60;
    public Rectangle rec= new Rectangle(xpos, ypos, width, height);;
    public void ground(){

    }
    public void move(){
        xpos-=Main.speed;
        if(xpos<-(Main.WIDTH+50)){
            xpos = Main.WIDTH+50;
        }
    }
}
