package finalgame;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.Timer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

class Ball {

    public int ballposX;
    public int ballposY;
    public int balldx = -1;
    public int balldy = -1;

    public Ball(int x, int y) {

        ballposX = x;
        ballposY = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.pink);
        g.fillOval(ballposX, ballposY, 20, 20);

    }
}

class rectangle {

    private int brickWidth = 110;
    private int brickHeight = 60;

    public void draw(Graphics g) {
        g.fillRect(brickWidth, brickWidth, brickWidth, brickHeight);

    }
}

class Slider {

    public int sliderposX;
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

    public Slider(int x) {
        sliderposX = x;
    }

    public void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(sliderposX, d.height - 55, d.width / 8, 15);
    }

}

class Bricks {

    public static int brickmap[][];
    public static int postionsX[][];
    public static int postionsY[][];
    public int rows;
    public int cols;
    public int wb;
    public int hb;

    public Bricks(int r, int c) {
        rows = r;
        cols = c;
        wb = ((w.w - ((w.w / 6) + (w.w / 6))) - ((cols - 1) * 20)) / cols;
        hb = ((w.h / 3) - ((rows - 1) * 20)) / rows;
        brickmap = new int[rows][cols];
        postionsX = new int[rows][cols];
        postionsY = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                brickmap[i][j] = 1;
                postionsX[i][j] = w.w / 6 + (j * wb) + (j * 20);
                postionsY[i][j] = w.h / 8 + (i * hb) + (i * 20);
            }
        }
    }

    public Color colomrc() {
        int red = 5 * (int) (Math.random() * 52);
        int green = 5 * (int) (Math.random() * 52);
        int blue = 5 * (int) (Math.random() * 52);
        return new Color(red, green, blue);
    }

    public void draw(Graphics2D g) {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (brickmap[i][j] == 1) {

                    g.setColor(colomrc());
                    g.fillRect(w.w / 6 + (j * wb) + (j * 20), w.h / 8 + (i * hb) + (i * 20), wb, hb);

                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.white);
                    g.fillRect(w.w / 6 + (j * wb) + (j * 20), w.h / 8 + (i * hb) + (i * 20), wb, 4);

                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.white);
                    g.fillRect(w.w / 6 + (j * wb) + (j * 20), w.h / 8 + (i * hb) + (i * 20) + hb, wb, 4);

                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.white);
                    g.fillRect(w.w / 6 + (j * wb) + (j * 20), w.h / 8 + (i * hb) + (i * 20), 4, hb);

                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.white);
                    g.fillRect(w.w / 6 + (j * wb) + (j * 20) + wb, w.h / 8 + (i * hb) + (i * 20), 4, hb + 4);
                }
            }
        }
    }
}

class gamePanel extends JPanel {

    private Slider slider;
    private Ball ball;
    private Bricks bricks;
    private static ImageIcon stars;
    private static JLabel imglbl;

    public gamePanel(Slider s, Ball b, Bricks br) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        imglbl = new JLabel();
        imglbl.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("black.jpg")).getImage().getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH)));
        this.add(imglbl);
        slider = s;
        ball = b;
        bricks = br;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        slider.draw(g);
        ball.draw(g);
        bricks.draw((Graphics2D) g);
    }
}

class GameFrame extends JFrame implements ActionListener, KeyListener {

    private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private int sliderposition;
    public static int score = 0;
    public static int lives = 1;
    private static boolean begin = false;
    private Timer time;
    private JPanel background;
    private JLabel lblscore = new JLabel("Score= " + score);
    private JLabel lblLives = new JLabel("Lives= " + lives);
    private gamePanel pnlgame;
    private Slider slider = new Slider(d.width / 2 - 88);
    private Ball ball = new Ball(d.width / 2, d.height - 75);
    public Bricks bricks;
    public int totalbricks;
    private Container c = this.getContentPane();
    private int delay = 0;

    public void setscore(int s) {
        score += s;
    }

    public int getscore() {
        return score;
    }

    public GameFrame(int r, int c) {
        bricks = new Bricks(r, c);

        totalbricks = bricks.rows * bricks.cols;
        init();
    }

    public void init() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Font f = new Font("", Font.BOLD, 18);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Brick Breaker");
        this.setResizable(false);
        this.setBounds(0, 0, d.width, d.height);
        this.lblscore.setBounds(0, 0, 10, 10);
        this.lblLives.setBounds(0, 0, 10, 10);
       
        lblLives.setFont(f);
        lblscore.setFont(f);
        lblscore.setForeground(Color.red);
        lblLives.setForeground(Color.WHITE);
        time = new Timer(delay, this);
        time.start();

        pnlgame = new gamePanel(slider, ball, bricks);
        pnlgame.add(lblscore);
        pnlgame.add(lblLives);
        this.addKeyListener(this);
        this.addActionListener(this);

        c.add(pnlgame);

    }

    public void keyPressed(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
            begin = true;
            if (slider.sliderposX >= d.width - (d.width / 8)) {
                slider.sliderposX = d.width - (d.width / 8);
            }
            slider.sliderposX += 30;
        }
        if (key.getKeyCode() == KeyEvent.VK_LEFT) {
            begin = true;
            if (slider.sliderposX <= 0) {
                slider.sliderposX = 0;
            }
            slider.sliderposX -= 30;
        }
    }

    public void Scores() {
        ArrayList<String> score = new ArrayList<String>();
        FileReader fr;
        BufferedReader br;

        try {
            fr = new FileReader("scores.txt");
            br = new BufferedReader(fr);

            while ((br.ready())) {
                score.add(br.readLine());
            }
            br.close();
            fr.close();
        } catch (IOException ex) {
        }

        FileWriter fw;
        PrintWriter pw;
        try {
            fw = new FileWriter("scores.txt");
            pw = new PrintWriter(fw);
            for (int i = 0; i < score.size(); i++) {
                pw.println(score.get(i));
            }
            pw.println(getscore());
            pw.close();
            fw.close();
        } catch (IOException ex) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        A:
        if (begin) {
            time.start();
            sliderposition = (slider.sliderposX + (d.width / 8));
            if (ball.ballposX == slider.sliderposX && (ball.ballposY >= (d.height - 55) - 15) && (ball.ballposY <= (d.height - 55))) {

                ball.balldx = ball.balldx;
                ball.balldy = -ball.balldy;
            }

            if (ball.ballposX >= slider.sliderposX && ball.ballposX <= sliderposition && ((ball.ballposY > (d.height - 55) - 15))) {

                ball.balldx = ball.balldx;
                ball.balldy = -ball.balldy;
            }

            if (ball.ballposY > (d.height - 55) + 35) {
                if (lives == 0) {
                    JOptionPane.showMessageDialog(null, "GAME OVER" + "\nYour score is " + getscore());
                    Scores();
                    begin = false;
                    System.exit(0);
                    break A;
                } else if (lives>0){
                    begin = false;
                    slider.sliderposX = d.width / 2 - 88;
                    ball.ballposX = d.width / 2;
                    ball.ballposY = d.height - 100;
                    repaint();
                    lives--;
                    lblLives.setText("Live = " + lives);
                    JOptionPane.showMessageDialog(null, "Live= " + lives + " Score=" + score);
                    break A;
                }
            }

            if (ball.ballposX >= d.width - 20) {
                ball.balldx = -ball.balldx;
            }

            if (ball.ballposX <= 0) {
                ball.balldx = -ball.balldx;
            }

            if (ball.ballposY <= 0) {
                ball.balldy = -ball.balldy;
            }

            for (int i = 0; i < bricks.rows; i++) {
                for (int j = 0; j < bricks.cols; j++) {
                    if (bricks.brickmap[i][j] == 1) {
                        //fo2
                        if ((((ball.ballposX) >= (bricks.postionsX[i][j])) || ((ball.ballposX + 10) >= (bricks.postionsX[i][j])) || ((ball.ballposX + 20) >= (bricks.postionsX[i][j]))) && (((ball.ballposX) <= (bricks.postionsX[i][j] + bricks.wb + 4)) || ((ball.ballposX + 10) <= (bricks.postionsX[i][j] + bricks.wb + 4)) || ((ball.ballposX + 20) <= (bricks.postionsX[i][j] + bricks.wb + 4))) && (((ball.ballposY + 20) == bricks.postionsY[i][j]) || ((ball.ballposY + 10) == bricks.postionsY[i][j]))) {
                            if (ball.balldy == -1) {
                                ball.balldy = -1;
                            } else if (ball.balldy == 1) {
                                ball.balldy = -1;
                            }
                            if (ball.balldx == -1) {
                                ball.balldx = -1;

                            } else if (ball.balldx == 1) {
                                ball.balldx = 1;
                            }
                            Bricks.brickmap[i][j] = 0;
                            if (Bricks.brickmap[i][j] == 0) {
                                totalbricks -= 1;
                            }
                            setscore(10);
                            int sc = getscore();

                        }//fo2
                        else if ((((ball.ballposX) >= (bricks.postionsX[i][j])) || ((ball.ballposX + 10) >= (bricks.postionsX[i][j])) || ((ball.ballposX + 20) >= (bricks.postionsX[i][j]))) && (((ball.ballposX) <= (bricks.postionsX[i][j] + bricks.wb + 4)) || ((ball.ballposX + 10) <= (bricks.postionsX[i][j] + bricks.wb + 4)) || ((ball.ballposX + 20) <= (bricks.postionsX[i][j] + bricks.wb + 4))) && (((ball.ballposY) == (bricks.postionsY[i][j] + bricks.hb + 4)) || ((ball.ballposY + 10) == (bricks.postionsY[i][j] + bricks.hb + 4)) || ((ball.ballposY + 20) == (bricks.postionsY[i][j] + bricks.hb + 4)))) {

                            if (ball.balldy == -1) {
                                ball.balldy = 1;
                            } else if (ball.balldy == 1) {
                                ball.balldy = 1;
                            }

                            if (ball.balldx == -1) {
                                ball.balldx = -1;

                            } else if (ball.balldx == 1) {
                                ball.balldx = 1;
                            }
                            Bricks.brickmap[i][j] = 0;
                            if (Bricks.brickmap[i][j] == 0) {
                                totalbricks -= 1;
                            }
                            setscore(10);
                            int sc = getscore();
                        } //gnb shmal
                        else if ((((ball.ballposX + 20) == (bricks.postionsX[i][j] - 4)) || ((ball.ballposX + 10) == (bricks.postionsX[i][j] - 4)) || ((ball.ballposX) == (bricks.postionsX[i][j] - 4))) && (((ball.ballposY) >= (bricks.postionsY[i][j])) || ((ball.ballposY + 10) >= (bricks.postionsY[i][j])) || ((ball.ballposY + 20) >= (bricks.postionsY[i][j]))) && (((ball.ballposY) <= (bricks.postionsY[i][j] + bricks.hb + 4)) || ((ball.ballposY + 10) <= (bricks.postionsY[i][j] + bricks.hb + 4)) || ((ball.ballposY + 20) <= (bricks.postionsY[i][j] + bricks.hb + 4)))) {
                            if (ball.balldy == -1) {
                                ball.balldy = -1;
                            } else if (ball.balldy == 1) {
                                ball.balldy = 1;
                            }
                            if (ball.balldx == -1) {
                                ball.balldx = -1;
                            } else if (ball.balldx == 1) {
                                ball.balldx = -1;
                            }
                            Bricks.brickmap[i][j] = 0;
                            if (Bricks.brickmap[i][j] == 0) {
                                totalbricks -= 1;
                            };
                            setscore(10);
                            int sc = getscore();
                        } //gnb ymyn
                        else if ((((ball.ballposX) == (bricks.postionsX[i][j] + bricks.wb + 4)) || ((ball.ballposX + 10) == (bricks.postionsX[i][j] + bricks.wb + 4)) || ((ball.ballposX + 20) == (bricks.postionsX[i][j] + bricks.wb + 4))) && (((ball.ballposY) >= (bricks.postionsY[i][j])) || ((ball.ballposY + 10) >= (bricks.postionsY[i][j])) || ((ball.ballposY + 20) >= (bricks.postionsY[i][j]))) && (((ball.ballposY) <= (bricks.postionsY[i][j] + bricks.hb + 4)) || ((ball.ballposY + 10) <= (bricks.postionsY[i][j] + bricks.hb + 4)) || ((ball.ballposY + 20) <= (bricks.postionsY[i][j] + bricks.hb + 4)))) {
                            if (ball.balldy == -1) {
                                ball.balldy = -1;
                            } else if (ball.balldy == 1) {
                                ball.balldy = 1;
                            }
                            if (ball.balldx == -1) {
                                ball.balldx = 1;
                            } else if (ball.balldx == 1) {
                                ball.balldx = 1;
                            }
                            Bricks.brickmap[i][j] = 0;
                            if (Bricks.brickmap[i][j] == 0) {
                                totalbricks -= 1;
                            }
                            setscore(10);
                            int sc = getscore();
                        }
                    }
                }
            }
            repaint();
            if (totalbricks == 0) {
                JOptionPane.showMessageDialog(null, "YOU WIN!" + "\nYour score is " + getscore());
                Scores();
                System.exit(0);
                begin = false;
                break A;
            }

            ball.ballposX += ball.balldx;
            ball.ballposY += ball.balldy;
            lblscore.setText("Live = " + score);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }
    @Override
    public void keyReleased(KeyEvent ke) {
    }
    private void addActionListener(GameFrame This) {
    }
}

public class Finalgame {

    public static NewGame newgame = new NewGame();

    public static void visible() {
        newgame.setVisible(true);
    }

    public static void main(String[] args) {
        playmusic("xxx.wav");
        visible();
    }

    public static void playmusic(String filepath) {
        InputStream music;
        try {
            music = new FileInputStream(new File(filepath));
            AudioStream audio = new AudioStream(music);
            AudioPlayer.player.start(audio);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
}
