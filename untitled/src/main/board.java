package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
public class board extends JPanel implements ActionListener {
    private final int B_WIDTH = 500;
    private final int B_HEIGHT = 500;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;
    private final int x[] = new int [ALL_DOTS];
    private final int y[] = new int [ALL_DOTS];
    private int chicks;
    private Image ball;
    private Image egg;
    private Image head;
    private Timer timer;
    private int egg_x;
    private int egg_y;
    private boolean leftDirection =false;
    private boolean rightDirection=true;
    private boolean upDirection=false;
    private boolean downDirection=true;
    private boolean inGame=true;

    @Override
    public void actionPerformed(ActionEvent e) {
if(inGame){
    checkEgg();
    checkCollision();
    move();
}
repaint();
    }

    private void loadImages(){
        ImageIcon iid =new ImageIcon("src/resources/chicks.png");
        ball = iid.getImage();
        ImageIcon iie= new ImageIcon("src/resources/egg.png");
        egg = iie.getImage();
        ImageIcon iih= new ImageIcon("src/resources/chicken.png");
        head = iih.getImage();
    }
    private void initGame(){
        chicks = 2;
        for (int z = 0; z< chicks; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        timer=new Timer(DELAY, this); //this-> refers to the screen present now
    timer.start();
    }
    private void initBoard(){
      addKeyListener(new TAdapter());
        setBackground(Color.cyan);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
        loadImages();
        initGame();
     }
    private void gameOver(Graphics g) {
        String msg = "Game Over!";
        String msg2 = "Press enter to restart";
        Font small = new Font("Helvetica", Font.BOLD, 40);
        FontMetrics metrics = getFontMetrics(small);
        g.setColor(Color.red);
        g.setFont(small);
        int line1Width = metrics.stringWidth(msg);
        int line2Width = metrics.stringWidth(msg2);

        int centerX = B_WIDTH / 2;
        int centerY = B_HEIGHT / 2;

        g.drawString(msg, centerX - line1Width / 2, centerY - metrics.getHeight());
        g.drawString(msg2, centerX - line2Width / 2, centerY + metrics.getHeight());
    }

    private void restartGame() {
        inGame = true;
        chicks = 2;
        for (int z = 0; z < chicks; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        locateEgg();
        timer.start();
    }

    private void locateEgg() {
        int rX = (int) (Math.random() * RAND_POS);
        int rY = (int) (Math.random() * RAND_POS);
        egg_x = rX * DOT_SIZE;
        egg_y = rY * DOT_SIZE;
    }
     private void checkEgg(){
        if((x[0]== egg_x && (y[0]== egg_y))){
           chicks++;
           locateEgg();
        }
     }
private void doDrawing(Graphics g){
        if(inGame){
            g.drawImage(egg, egg_x, egg_y,this);
            for (int i = 0; i< chicks; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                }
                else {
                    g.drawImage(ball, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
}
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
private void move(){
        for(int i = chicks; i>0; i--){
          x[i]=x[(i-1)];
          y[i]=y[(i-1)];
        }
        if(leftDirection){
            x[0]-=DOT_SIZE;
        }
        if(rightDirection){
        x[0]+=DOT_SIZE;
        }
        if(upDirection){
        y[0]-=DOT_SIZE;
        }
        if(downDirection){
        y[0]+=DOT_SIZE;
        }
}
private void checkCollision(){
    for(int z = chicks; z>0; z--){
        if((z>4)&&(x[0]==x[z])&&(y[0]==y[z])){
          inGame = false;
        }
        if(y[0]>=B_HEIGHT){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(x[0]>=B_HEIGHT){
            inGame=false;
        }
        if(x[0]<0){
            inGame=false;
        }
        if(!inGame){
            timer.stop();
        }
    }
}


    public class TAdapter extends KeyAdapter{
        @Override
    public void keyPressed(KeyEvent e){
            int key =e.getKeyCode();
            if((key==KeyEvent.VK_LEFT)&& (!rightDirection)){
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if((key==KeyEvent.VK_RIGHT)&& (!leftDirection)){
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if((key==KeyEvent.VK_UP)&& (!downDirection)){
                upDirection=true;
                rightDirection=false;
                leftDirection=false;
            }
            if((key==KeyEvent.VK_DOWN)&& (!upDirection)){
                downDirection=true;
                rightDirection=false;
                leftDirection=false;
            }
        if (key == KeyEvent.VK_ENTER && !inGame) {
            restartGame();
        }
        }

}
    public board(){
        initBoard();
    }
}
