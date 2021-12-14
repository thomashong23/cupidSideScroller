import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

import java.awt.image.BufferedImage;
import javax.tools.Tool;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.event.*;
import java.util.Date;
import java.util.Scanner;

public class Main implements MouseListener, MouseMotionListener, KeyListener, Runnable {
    ArrayList<Arrow> lotsOfArrows = new ArrayList<Arrow>();
    final static int WIDTH = 1024;
    final static int HEIGHT = 614;
    public static int numJumps;
    public static int speed = 5;
    public static int screenSpeed=2;
    public boolean gameOver;
    ArrayList<ScorePlayer> nameScore = new ArrayList<ScorePlayer>();
    public int[] highScores = new int[5];
    public String playerName;
    public String[] highScoreNames = new String[5];
    public Image wings;
    ArrayList<Image> platformImages = new ArrayList<Image>();
    ArrayList<Heart> lotsOfHearts = new ArrayList<Heart>();
    ArrayList<BlackHeart> blackHearts = new ArrayList<BlackHeart>();
    //ArrayList<Platform> lotsOfPlatforms = new ArrayList<Platform>();
    public Cupid character = new Cupid();
    public Ground spikes = new Ground();
    public Ground spikes2 = new Ground();
    public Platform platform1 = new Platform();
    public Platform platform2 = new Platform();
    public Platform platform3 = new Platform();
    public Platform platform4 = new Platform();
    public Platform platform5 = new Platform();
    public Date date = new Date();
    public String[] dates = new String[5];

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public BufferStrategy bufferStrategy;
    public JPanel panel;

    //Images
    public Image background;
    public int score;
    public Image groundImage;
    public Image StartscreenPic;
    public Image arrowImage;
    public Image cupidImage;
    public Image backgroundImage;
    public Image smallestPlatform;
    public Image middlePlatform;
    public Image biggestPlatform;
    public Image brokenheart;
    public Image redheart;
    public Image instructionsimage;
    public Image youlose;

    //Variables
    boolean Start = true;
    boolean Instructions = false;
    boolean GameStart;

    public static void main(String[] args) {
        Main myApp = new Main();   //creates a new instance of the app
        new Thread(myApp).start();  //start up the thread
    }
    public  void pause(int time){
        try{
            Thread.sleep(time);
        }catch (InterruptedException e) {

        }
    }


    public Main(){
        setUpGraphics();
        readFile();
        render();

        System.out.println(date.toString());
    }
    public void run() {
        playerName = JOptionPane.showInputDialog("Enter your name:");

        while(true){

            character.gravity = true;
            if(GameStart) {
                moveThings();
                collisions();
            }
            render();
            pause(20);

        }

    }
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
        background = Toolkit.getDefaultToolkit().createImage("BACKGROUND.jpg");
        groundImage = Toolkit.getDefaultToolkit().createImage("spikesGround.png");
        cupidImage = Toolkit.getDefaultToolkit().createImage("cupid.png");
        arrowImage = Toolkit.getDefaultToolkit().createImage("arrow.png");
        backgroundImage = Toolkit.getDefaultToolkit().createImage("cupidBackGroundImage.png");
        biggestPlatform = Toolkit.getDefaultToolkit().createImage("biggestPlatform.png");
        middlePlatform = Toolkit.getDefaultToolkit().createImage("middlePlatform.png");
        smallestPlatform = Toolkit.getDefaultToolkit().createImage("smallestPlatform.png");
        redheart = Toolkit.getDefaultToolkit().createImage("redheart.png");
        wings = Toolkit.getDefaultToolkit().createImage("wings.png");
        brokenheart = Toolkit.getDefaultToolkit().createImage("brokenheart.png");
        StartscreenPic = Toolkit.getDefaultToolkit().createImage("title screen.png");
        instructionsimage = Toolkit.getDefaultToolkit().createImage("InstructionsScreen.png");
        youlose = Toolkit.getDefaultToolkit().createImage("gameover.png");


        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        canvas.addKeyListener(this);
        System.out.println("DONE graphic setup");
        spikes2.xpos = Main.WIDTH+50;
        platformImages.add(smallestPlatform);
        platformImages.add(middlePlatform);
        platformImages.add(biggestPlatform);
        platform1.xPos = 0;
        platform2.xPos = 500;
        platform3.xPos = 1000;
        platform4.xPos = 1500;
        platform5.xPos = 2000;
        lotsOfHearts.add(new Heart(1000,(int)(Math.random()*500)+100));
        lotsOfHearts.add(new Heart(1500,(int)(Math.random()*500)+100));
        lotsOfHearts.add(new Heart(2000,(int)(Math.random()*500)+100));
        blackHearts.add(new BlackHeart(1000,(int)(Math.random()*500)+100));
        blackHearts.add(new BlackHeart(1500,(int)(Math.random()*500)+100));
        blackHearts.add(new BlackHeart(2000,(int)(Math.random()*500)+100));







//        lotsOfPlatforms.add(new Platform())



    }
    public void moveThings() {
        for (int i = 0; i < lotsOfArrows.size()-1; i++) {
            lotsOfArrows.get(i).move();
        }
        character.move();
        spikes.move();
        spikes2.move();
        for (int i = 0; i < lotsOfHearts.size(); i++) {
            lotsOfHearts.get(i).move();
            blackHearts.get(i).move();
        }
        if(platform1.xPos < -250){
            platform1.teleport = 200 + platform5.xPos + (int)(Math.random() * 500);
        }
        if(platform2.xPos < -250){
            platform2.teleport = 200 + platform1.xPos + (int)(Math.random() * 500);
        }
        if(platform3.xPos < -250){
            platform3.teleport = 200 + platform2.xPos + (int)(Math.random() * 500);
        }
        if(platform4.xPos < -250){
            platform4.teleport = 200 + platform3.xPos + (int)(Math.random() * 500);
        }
        if(platform5.xPos < -250) {
            platform5.teleport = 200 + platform4.xPos + (int) (Math.random() * 500);
        }
        platform1.move();
        platform2.move();
        platform3.move();
        platform4.move();
        platform5.move();
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();


            // g.fillRect(0,0,WIDTH,HEIGHT);


            // g.clearRect(0, 0, WIDTH, HEIGHT);

//        if (Start) {
//            g.drawImage(StartscreenPic, 0, 0, 1000, 700,null);
//        }
//        else {
//
//            g.drawImage(background, -100, -100, 1300, 800, null);
////            g.drawImage(flappy, birdy.xpos, birdy.ypos, 20, 20, null);
//
//
//// (300,0), (327,134)      (330, 0)  (355,120)
//            for (int i = 0; i < 10; i++) {
////                if (T[i].ypos == 0) {
////                    g.drawImage(tubes[0], T[i].xpos, T[i].ypos, T[i].xpos + T[i].width, T[i].ypos + T[i].height, 300, 0, 327, 134, null);
////                } else {
////                    g.drawImage(tubes[1], T[i].xpos, T[i].ypos, T[i].xpos + +T[i].width, T[i].ypos + +T[i].height, 330, 0, 355, 120, null);
////
////                }
//
//
//            }
//        }
            if(Start){
                g.drawImage(StartscreenPic,0,0,WIDTH,HEIGHT,null);
                if(playerName!=null){
                    g.setFont(new Font("Arial",Font.BOLD,50));
                    g.setColor(new Color(0,0,0));
                    g.drawString(playerName,400,500);
                }
            }
            if(Instructions){
                g.drawImage(instructionsimage,0,0,WIDTH,HEIGHT,null);
            }
            if(gameOver){
                g.drawImage(youlose,0,0,WIDTH,HEIGHT,null);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
                g.drawString(String.valueOf(score), 500, 300);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
                for (int i = 0; i < 5; i++) {
                    g.drawString(Integer.toString(highScores[i]),50,i*25);
                    g.drawString((highScoreNames[i]),100,i*25);
                    g.drawString((dates[i]),150,i*25);
                }
            }

            if (GameStart) {
                g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);
                g.drawString(Integer.toString(score),100,100);
                if (platform1.sizeNum == 0) {
                    g.drawImage(smallestPlatform, platform1.xPos, platform1.yPos, platform1.width, platform1.height, null);
                }
                if (platform1.sizeNum == 1) {
                    g.drawImage(middlePlatform, platform1.xPos, platform1.yPos, platform1.width, platform1.height, null);
                }
                if (platform1.sizeNum == 2) {
                    g.drawImage(biggestPlatform, platform1.xPos, platform1.yPos, platform1.width, platform1.height, null);
                }
                if (platform2.sizeNum == 0) {
                    g.drawImage(smallestPlatform, platform2.xPos, platform2.yPos, platform2.width, platform2.height, null);
                }
                if (platform2.sizeNum == 1) {
                    g.drawImage(middlePlatform, platform2.xPos, platform2.yPos, platform2.width, platform2.height, null);
                }
                if (platform2.sizeNum == 2) {
                    g.drawImage(biggestPlatform, platform2.xPos, platform2.yPos, platform2.width, platform2.height, null);
                }
                if (platform3.sizeNum == 0) {
                    g.drawImage(smallestPlatform, platform3.xPos, platform3.yPos, platform3.width, platform3.height, null);
                }
                if (platform3.sizeNum == 1) {
                    g.drawImage(middlePlatform, platform3.xPos, platform3.yPos, platform3.width, platform3.height, null);
                }
                if (platform3.sizeNum == 2) {
                    g.drawImage(biggestPlatform, platform3.xPos, platform3.yPos, platform3.width, platform3.height, null);
                }
                if (platform4.sizeNum == 0) {
                    g.drawImage(smallestPlatform, platform4.xPos, platform4.yPos, platform4.width, platform4.height, null);
                }
                if (platform4.sizeNum == 1) {
                    g.drawImage(middlePlatform, platform4.xPos, platform4.yPos, platform4.width, platform4.height, null);
                }
                if (platform4.sizeNum == 2) {
                    g.drawImage(biggestPlatform, platform4.xPos, platform4.yPos, platform4.width, platform4.height, null);
                }
                if (platform5.sizeNum == 0) {
                    g.drawImage(smallestPlatform, platform5.xPos, platform5.yPos, platform5.width, platform5.height, null);
                }
                if (platform5.sizeNum == 1) {
                    g.drawImage(middlePlatform, platform5.xPos, platform5.yPos, platform5.width, platform5.height, null);
                }
                if (platform5.sizeNum == 2) {
                    g.drawImage(biggestPlatform, platform5.xPos, platform5.yPos, platform5.width, platform5.height, null);
                }
                for (int i = 0; i < 5-numJumps; i++) {
                    g.drawImage(wings, 400+i*50,50,25,25,null);
                }


                g.drawImage(cupidImage, character.xpos, character.ypos, character.width, character.height, null);
                for (int i = 0; i < lotsOfArrows.size() - 1; i++) {
                    g.drawImage(arrowImage, lotsOfArrows.get(i).xpos, lotsOfArrows.get(i).ypos, lotsOfArrows.get(i).width, lotsOfArrows.get(i).height, null);
                }
                g.drawImage(groundImage, spikes.xpos, spikes.ypos, spikes.width, spikes.height, null);
                g.drawImage(groundImage, spikes2.xpos, spikes2.ypos, spikes2.width, spikes2.height, null);
            }
        for (int i = 0; i <lotsOfHearts.size() ; i++) {
            if(lotsOfHearts.get(i).isAlive){
                g.drawImage(redheart,lotsOfHearts.get(i).xPos,lotsOfHearts.get(i).yPos,lotsOfHearts.get(i).width,lotsOfHearts.get(i).height,null);
            }
        }
        for (int i = 0; i <blackHearts.size() ; i++) {
            g.drawImage(brokenheart, blackHearts.get(i).xPos, blackHearts.get(i).yPos, blackHearts.get(i).width,blackHearts.get(i).height,null);
        }


        for (int i = 0; i < lotsOfArrows.size(); i++) {
            if(!lotsOfArrows.get(i).isAlive){
                lotsOfArrows.remove(i);
                break;
            }
        }
        for (int i = 0; i < lotsOfHearts.size(); i++) {
            if(!lotsOfHearts.get(i).isAlive &&lotsOfHearts.get(i).xPos<0  ){
                lotsOfHearts.remove(i);
                lotsOfHearts.add(new Heart((int)(Math.random()*500)+character.xpos+900, (int)(Math.random()*500)+100));
                break;
            }
        }
        for (int i = 0; i < blackHearts.size(); i++) {
            if(!blackHearts.get(i).isAlive &&blackHearts.get(i).xPos<0  ){
                blackHearts.remove(i);
                blackHearts.add(new BlackHeart((int)(Math.random()*500)+character.xpos+900, (int)(Math.random()*500)+100));
                break;
            }
        }

        g.dispose();
        bufferStrategy.show();
        }





    private void collisions(){
        if(character.rec.intersects(spikes.rec)){
            gameOver = true;
            GameStart = false;
        }
        for (int i = 0; i < lotsOfHearts.size(); i++) {
            if(character.rec.intersects(lotsOfHearts.get(i).rec) && lotsOfHearts.get(i).isAlive){
                score++;
                lotsOfHearts.get(i).isAlive=false;
            }
        }
        for (int i = 0; i < blackHearts.size(); i++) {
            if(character.rec.intersects(blackHearts.get(i).rec) && blackHearts.get(i).isAlive){
                score++;
                blackHearts.get(i).isAlive=false;
                gameOver=true;
            }
        }
        if(character.feet.intersects(platform1.rec)&& character.dy>0){
            if(platform1.scorable) {
                score = score + 1;
                platform1.scorable = false;
            }
            character.gravity=false;
            numJumps=0;
            character.dy = 0;
            character.ypos = platform1.yPos - character.height;
        }
        if(character.feet.intersects(platform2.rec)&& character.dy>0){
            if(platform2.scorable) {
                score = score + 1;
                platform2.scorable = false;
            }
           character.gravity=false;
           numJumps=0;
            character.dy = 0;
            character.ypos = platform2.yPos - character.height;
        } if(character.feet.intersects(platform3.rec)&& character.dy>0){
            if(platform3.scorable) {
                score = score + 1;
                platform3.scorable = false;
            }
            character.gravity=false;
            numJumps=0;
            character.dy = 0;
            character.ypos = platform3.yPos - character.height;
        } if(character.feet.intersects(platform4.rec)&& character.dy>0){
            if(platform4.scorable) {
                score = score + 1;
                platform4.scorable = false;
            }
            character.gravity=false;
            numJumps=0;
            character.dy = 0;
            character.ypos = platform4.yPos - character.height;
        } if(character.feet.intersects(platform5.rec)&& character.dy>0){
            if(platform5.scorable) {
                score = score + 1;
                platform5.scorable = false;
            }
            character.gravity=false;
            numJumps=0;
            character.dy = 0;
            character.ypos = platform5.yPos - character.height;
        }
        if(character.rec.intersects(platform1.rec)&&character.dy<0){
            character.dy = 0;
        }
        if(character.rec.intersects(platform2.rec)&&character.dy<0){
            character.dy = 0;
        }if(character.rec.intersects(platform3.rec)&&character.dy<0){
            character.dy = 0;
        }if(character.rec.intersects(platform4.rec)&&character.dy<0){
            character.dy = 0;
        }if(character.rec.intersects(platform5.rec)&&character.dy<0){
            character.dy = 0;
        }
//        for (int i = 0; i < lotsOfArrows.size(); i++) {
//            for (int j = 0; j < lotsOfHearts.size(); j++) {
//                if(lotsOfArrows.get(i).rec.intersects(lotsOfHearts.get(j).rec)){
//                    lotsOfArrows.get(i).isAlive=false;
//                    lotsOfHearts.get(j).isAlive= false;
//                }
//            }
//
//        }



//        for (int i = 0; i < lotsOfHearts.size(); i++) {
//            if(lotsOfHearts.get(i).rec.intersects(character.rec)){
//                if(lotsOfHearts.get(i).isBlack){
//                    gameOver=true;
//                }
//                else{
//                   score++;
//                   if(score>highScore){
//                       highScore=score;
//                   }
//                }
//            }
//        }
        if(lotsOfArrows.size()>=1) {
            if(lotsOfHearts.size()>=1){
                for (int i = 0; i < lotsOfHearts.size(); i++) {
                    for (int j = 0; j < lotsOfArrows.size(); j++) {
                        lotsOfArrows.get(j).width = 50;
//                        lotsOfHearts.get(i).width = 25;
                        if (lotsOfHearts.get(i).isBlack) {
                            if (lotsOfArrows.get(j).isAlive && lotsOfHearts.get(i).rec.intersects(lotsOfArrows.get(j).rec) ) {
                                lotsOfHearts.get(i).isBlack = false;
                            }
                        }



                    }
                }
            }

        }






    }
    public void reset(){
        gameOver=false;
        GameStart=false;
        Start = true;
        speed = 5;
        lotsOfHearts.clear();
        lotsOfArrows.clear();
        character.ypos=0;
        platform1.xPos = 0;
        platform2.xPos = 500;
        platform3.xPos = 1000;
        platform4.xPos = 1500;
        platform5.xPos = 2000;
        lotsOfHearts.add(new Heart(1000,(int)(Math.random()*500)+100));
        lotsOfHearts.add(new Heart(1500,(int)(Math.random()*500)+100));
        lotsOfHearts.add(new Heart(2000,(int)(Math.random()*500)+100));
        date = new Date();
        calcScore();
        numJumps=0;
        for (int i = 0; i < 5; i++) {
            System.out.println(highScores[i]);
            System.out.print(" by ");
            System.out.println(highScoreNames[i]);
            System.out.print(" on ");
            System.out.println(dates[i]);
        }
        score=0;



    }
    public void calcScore(){
        readFile();
        if(score>=highScores[0]){
            String tempDateTop=dates[0];
            String tempDateBottom;
            String tempNameTop = highScoreNames[0];
            String tempNameBottom;
            int tempScoreTop = highScores[0];
            int tempScoreBottom=0;
            highScores[0]=score;
            highScoreNames[0]=playerName;
            dates[0]=date.toString();
            for (int i = 1; i < 5; i++) {
                tempScoreBottom = highScores[i];
                tempNameBottom=highScoreNames[i];
                tempDateBottom=dates[i];
                highScores[i]=tempScoreTop;
                highScoreNames[i]=tempNameTop;
                dates[i]=tempDateTop;
                tempScoreTop=tempScoreBottom;
                tempNameTop=tempNameBottom;
                tempDateTop=tempDateBottom;


            }
        }
        else if(score>=highScores[1]){
            String tempDateTop=dates[1];
            String tempDateBottom;
            String tempNameTop = highScoreNames[1];
            String tempNameBottom;
            int tempScoreTop = highScores[1];
            int tempScoreBottom;
            highScores[1]=score;
            highScoreNames[1]=playerName;
            dates[1]=date.toString();
            for (int i = 2; i < 5; i++) {
                tempScoreBottom = highScores[i];
                tempNameBottom=highScoreNames[i];
                tempDateBottom=dates[i];
                highScores[i]=tempScoreTop;
                highScoreNames[i]=tempNameTop;
                dates[i]=tempDateTop;
                tempScoreTop=tempScoreBottom;
                tempNameTop=tempNameBottom;
                tempDateTop=tempDateBottom;

            }
        }
        else if(score>=highScores[2]){
            String tempDateTop=dates[2];
            String tempDateBottom;
            String tempNameTop = highScoreNames[2];
            String tempNameBottom;
            int tempScoreTop = highScores[2];
            int tempScoreBottom;
            highScores[2]=score;
            highScoreNames[2]=playerName;
            dates[2]=date.toString();
            for (int i = 3; i < 5; i++) {
                tempScoreBottom = highScores[i];
                tempNameBottom=highScoreNames[i];
                tempDateBottom=dates[i];
                highScores[i]=tempScoreTop;
                highScoreNames[i]=tempNameTop;
                dates[i]=tempDateTop;
                tempScoreTop=tempScoreBottom;
                tempNameTop=tempNameBottom;
                tempDateTop=tempDateBottom;

            }
        }
        else if(score>=highScores[3]){
            String tempDateTop=dates[3];
            String tempDateBottom;
            String tempNameTop = highScoreNames[3];
            String tempNameBottom;
            int tempScoreTop = highScores[3];
            int tempScoreBottom;
            highScores[3]=score;
            highScoreNames[3]=playerName;
            dates[3]=date.toString();
            for (int i = 4; i < 5; i++) {
                tempScoreBottom = highScores[i];
                tempNameBottom=highScoreNames[i];
                tempDateBottom=dates[i];
                highScores[i]=tempScoreTop;
                highScoreNames[i]=tempNameTop;
                dates[i]=tempDateTop;
                tempScoreTop=tempScoreBottom;
                tempNameTop=tempNameBottom;
                tempDateTop=tempDateBottom;

            }
        }
        else if(score>=highScores[4]){
            dates[4]=date.toString();
            highScores[4]=score;
            highScoreNames[4]=playerName;
        }
        writeToFile();


    }
    public String toFileString() {
        String returner= "";
        for (int i = 0; i < 5; i++) {
        returner = returner.concat(highScoreNames[i]+"," +highScores[i]+ dates[i].toString());
        }
        return returner;
    }

    public void writeToFile(){

            try {
                FileWriter pw = new FileWriter(new File( "myObjects.txt"));
                for (int i = 0; i < highScores.length; i++) {
                    pw.write(Integer.toString(highScores[i]));
                    pw.write(",");
                    pw.write(highScoreNames[i]);
                    pw.write(",");
                    pw.write(dates[i]);
                    pw.write("\n");
                }

                pw.close();
                System.out.println("lfg");
            } catch (IOException e ) {
                System.out.println("frick");
                e.printStackTrace();
            }
    }
    public void readFile(){

            try {
                File myObj = new File("myObjects.txt");
                Scanner myReader = new Scanner(myObj);
                for (int i = 0; i < 5; i++) {
                    String hello = myReader.nextLine();
                    String values[] = hello.split(",");
                    highScores[i]= Integer.parseInt(values[0]);
                    highScoreNames[i]= (values[1]);
                    dates[i]=values[2];

                }



                myReader.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(Start){
            if(e.getKeyChar() == 'p'){
                Start = false;
                GameStart = true;
            }
            if(e.getKeyChar() == 'i'){
                Instructions = true;
                Start = false;
            }
        }
        if(Instructions){
            if(e.getKeyChar() == 'p'){
                Instructions = false;
                GameStart = true;
                readFile();
            }
        }
        if(GameStart) {
//            if (e.getKeyChar() == ' ') {
//                lotsOfArrows.add(new Arrow());
//                lotsOfArrows.get(lotsOfArrows.size() - 1).xpos = character.xpos + 20;
//                lotsOfArrows.get(lotsOfArrows.size() - 1).ypos = character.ypos + 10;
//
//            }
            if (e.getKeyChar() == 'w') {
                if (numJumps < 5) {
                    character.dy = -15;
                    numJumps += 1;
                    character.gravity = true;
                }


            }

        }
        if (e.getKeyChar() == 'r') {
            reset();
        }


    }


    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


}
