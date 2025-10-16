package game;

import game.panel.mykey;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static jdk.jfr.internal.consumer.EventLog.stop;
//تعديل المشروع عن طريق حل مشكلة الكيبورد بواسطة     key bindings 
//حل مشكلة الثريد من اجل اعادة اللعب

public class Game {

    static JFrame win;

    public static void swit(JPanel newp, JPanel current) {

        win.setVisible(false);
        win.getContentPane().remove(current);
        win.getContentPane().add(newp);

        win.setVisible(true);
        win.validate();
        win.repaint();
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, ClassNotFoundException {
        data dat = new data();
        dat.load();
        win = new JFrame();
        menu men = new menu();
        win.setSize(1300, 700);
//         panel pan = new panel();
//         win.add(pan);
        win.setLocationRelativeTo(null);
        win.setTitle("BolGame");
        win.setResizable(false);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
        win.getContentPane().add(men);
        men.setBounds(-20, -10, 1310, 700);

    }

}

class panel extends JPanel implements Runnable {

    private final ImageIcon[] imgs = {new ImageIcon("src//imgs//back//Nebula Blue.png"), new ImageIcon("src//imgs//back//Nebula Red.png"),
        new ImageIcon("src//imgs//back//Nebula Aqua-Pink.png")
    };
    private final ImageIcon[] stars = {new ImageIcon("src//imgs//back//stars//Stars Small_1.png"), new ImageIcon("src//imgs//back//stars//Stars-Big_1_1_PC.png"),
        new ImageIcon("src//imgs//back//stars//Stars Small_2.png"), new ImageIcon("src//imgs//back//stars//Stars-Big_1_2_PC.png")
    };
    ImageIcon img1 = new ImageIcon("src//imgs//New Piskel (2)2.png");
    ImageIcon img2 = new ImageIcon("src//imgs//New Piskel (2)3.png");

    private static final int width = 1300;
    private static final int height = 700;
    private static final int BALL_SIZE = 20;

    private double velocityX = 5.0;
    private double velocityY = 2.0;
    private int life1 = 3, life2 = 3;
    private Point ball = new Point(width / 2, height / 2);
    private int im = 0;
    private static Point rect = new Point(10, height / 3 + 50);
    private static Point rect2 = new Point(width - 40, height / 3 + 50);
    private int counter = 0;                                       ////////
    private int scor1 = 100, scor2 = 100;
    private data my = new data();

    private int time = 1, speed = 8;


    boolean end = false;
    boolean up = false;
    boolean up2 = false;
    boolean dw = false;
    boolean dw2 = false;
    Thread th;
    File file = new File("src//sounds//hit.wav"), file2 = new File("src//sounds//wallHit.wav")
    ,file3 = new File("src//sounds//Loops//wav//Sci-Fi 1 Loop.wav");
    AudioInputStream audiostream = AudioSystem.getAudioInputStream(file), audiostream2 = AudioSystem.getAudioInputStream(file2)
    ,audiostream3 = AudioSystem.getAudioInputStream(file3);
    Clip clip = AudioSystem.getClip(), clip2 = AudioSystem.getClip(),clip3 = AudioSystem.getClip();

    Random ran = new Random();

    panel() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        clip.open(audiostream);
        clip2.open(audiostream2);
        clip3.open(audiostream3);
        play(clip3);
        th = new Thread(this);
        th.start();
        this.setFocusable(true);
        this.addKeyListener(new mykey());

    }
    public  void play(Clip clip) {
    
        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        int volume = my.music();
        float range = control.getMinimum();
        float result = range * (1 - volume / 100.0f);
        System.out.println(result);
        control.setValue(result);
        clip.setMicrosecondPosition(0);
        clip.start();
    
}

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(imgs[0].getImage(), 0, 0, null);
        if (counter==-4096) {
            counter = width;
            im = (im+1)%4;
        }
        g2.drawImage(stars[im].getImage(), counter, 0, null);
        for (int i = 0; i < 3; i++) {
            if (life1 > i) {
                g2.drawImage(img2.getImage(), i * 40, 30, null);
            } else {
                g2.drawImage(img1.getImage(), i * 40, 30, null);
            }
            if (life2 > i) {
                g2.drawImage(img2.getImage(), (width - 200) + (i * 40), 30, null);
            } else {
                g2.drawImage(img1.getImage(), (width - 200) + (i * 40), 30, null);
            }
        }
        g2.setColor(Color.WHITE);
        // g2.drawString(" To end the game press E key", 10, 30);
        g2.setFont(new Font("", Font.BOLD, 40));
        g2.drawString(my.ps()[0], 10, 30);
        g2.drawString(my.ps()[1], width - 200, 30);
        g2.drawString("" + Integer.toString(scor1).substring(1), (width / 2) - 100, 70);
        g2.drawString("" + Integer.toString(scor2).substring(1), (width / 2) + 50, 70);

        g2.setColor(Color.blue);
        g2.fillRect(rect.x, rect.y, 20, 150);
        g2.setColor(Color.red);
        g2.fillRect(rect2.x, rect2.y, 20, 150);
        g2.setColor(Color.white);
        g2.fillOval(ball.x, ball.y, BALL_SIZE, BALL_SIZE);
        for (int i = 0; i < 800; i += 25) {
            g2.fillRect(width / 2, i, 4, 15);
        }
        if (end) {
            th = new Thread(this);
            String d = "Congratulations the winner is : ";
            String d2 = "";

            //g.drawImage(img2.getImage(), 0, 0, null);
            g.setFont(new Font("", Font.PLAIN, 35));
            g.drawString("--> Press ok to exit ", 50, 400);
            g.setColor(Color.BLUE);
            g.drawString(my.ps()[0] + ": " + Integer.toString(scor1).substring(1), 50, 175);
            g.setColor(Color.red);

            g.drawString(my.ps()[0] + ": " + Integer.toString(scor2).substring(1), 50, 250);
            g.setFont(new Font("", Font.BOLD, 50));

            g.setColor(Color.yellow);
            if (scor1 > scor2) {
                d2 = my.ps()[0];
            } else if (scor1 == scor2) {
                d2 = "Draw!";
            } else {
                d2 = my.ps()[1];
            }
            g.drawString(d, 350, 100);
            g.drawString(d2, 350, 200);
            g.drawString("Score", 50, 100);

        }
    }

    private void moveBall() {
        // Update position based on velocity
        ball.x += velocityX;
        ball.y += velocityY;

        // Apply friction to gradually slow down the ball
        velocityX *= 1;
        velocityY *= 1;
    }

    private void checkWallCollision() {
        double value;
        if (ball.x <= 0) {

            ball.setLocation(width / 2, height / 2);
            velocityX = -velocityX * 1;

            life1--;
            if (life1 == 0) {
                repaint();
                stop();
            }
        } else if (ball.x >= width - BALL_SIZE) {
            ball.setLocation(width / 2, height / 2);
            velocityX = -velocityX * 1;
            life2--;
            if (life2 == 0) {
                repaint();
                stop();
            }
        }

        if (ball.y >= rect.y - BALL_SIZE && (ball.y <= rect.y + 150) && ball.x < rect.x + BALL_SIZE && ball.x > rect.x) {
            play(clip);
            //clip.setMicrosecondPosition(0);
            //clip.start();

            value = (ball.y - rect.y) / 30;
            value = value / 2;
            if (value > 1) {
                velocityY = 2.0;
            } else if (value < 1) {
                velocityY = -2.0;
            }
            velocityX = -(4 + value);

            velocityX = -velocityX * 1;

            scor1++;
            //tach = true;
            play(clip);
            System.out.println();

        } else if (ball.y >= rect2.y - BALL_SIZE && (ball.y <= rect2.y + 150) && ball.x > rect2.x - BALL_SIZE && ball.x < rect2.x) {

            play(clip);
            value = (ball.y - rect2.y) / 30;
            value = value / 2;
            if (value > 1) {
                velocityY = 2.0;
            } else if (value < 1) {
                velocityY = -2.0;
            }
            velocityX = 4 + value;
            velocityX = -velocityX * 1;
            scor2++;
            //tach = true;

            play(clip);
        }

        if (ball.y <= 0) {
            // Top wall collision
            play(clip2);
            ball.y = 0;
            velocityY = -velocityY * 1;
        } else if (ball.y >= height - BALL_SIZE - 40) {
            // Bottom wall collision
            play(clip2);
            ball.y = height - BALL_SIZE - 40;
            velocityY = -velocityY * 1;
        }
    }

    public void Rectmove() {
        if (up) {
            if (rect.y > 0) {
                rect.move(rect.x, rect.y - 10);
            }

        } else if (dw) {
            if (rect.y < height - 190) {
                rect.move(rect.x, rect.y + 10);
            }

        }
        if (up2) {
            if (rect2.y > 0) {
                rect2.move(rect2.x, rect2.y - 10);
            }
        } else if (dw2) {
            if (rect2.y < height - 190) {
                rect2.move(rect2.x, rect2.y + 10);
            }
        }

    }

    @Override
    public void run() {
        try {

            while (true) {
                Thread.sleep(speed);
                counter--;
                /*  if ((time%3000 == 0)&&(speed>1)) {
                    speed--;
                    System.out.println(time);
                }
                
                time++;
                //System.out.println(t + ":::" + t2);

               

                 */

                moveBall();
                checkWallCollision();
                Rectmove();
                repaint();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public class mykey implements KeyListener {

        public void keyPressed(KeyEvent e) {
            
            
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    up = true;
                    break;
                case KeyEvent.VK_S:
                    dw = true;
                    break;
                case KeyEvent.VK_UP:
                    up2 = true;
                    break;
                case KeyEvent.VK_DOWN:
                    dw2 = true;
                    break;
                case KeyEvent.VK_E:
                    stop();

                    break;
                case KeyEvent.VK_ESCAPE:

                    if (end) {
                        System.exit(0);
                    }
                    break;
                case KeyEvent.VK_R:
                    start();
                    break;

            }

        }

        public void stop() {
            end = true;
            th.interrupt();
            repaint();
        }

        public void start() {
            time = 1;
            speed = 8;
            scor1 = 100;
            scor2 = 100;
            rect.setLocation(10, height / 3 + 50);
            rect2.setLocation(width - 40, height / 3 + 50);
            ball.setLocation(width / 2, height / 2);
            th.start();
            end = false;
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                up = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                dw = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                up2 = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                dw2 = false;
            }

        }
    }

}
