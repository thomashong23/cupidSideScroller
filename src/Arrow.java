import java.awt.*;

public class Arrow {
    public int xpos;
    public int ypos;
    public int dx = 10;
    public int width=50;
    public int height=20;
    public boolean isAlive = true;
    public Rectangle rec;

    public void arrow(int Xpos, int Ypos){
        xpos = Xpos;
        ypos = Ypos;
        rec = new Rectangle(xpos, ypos, width, height);
    }
    public void move() {
        xpos = xpos + dx;
        rec = new Rectangle(xpos, ypos, width, height);
        if(xpos > Main.WIDTH){
            isAlive = false;
        }
    }

}


