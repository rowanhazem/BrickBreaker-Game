package finalgame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class HallOfFame extends JFrame {

    private JButton back = new JButton("Back");

    private JPanel center = new JPanel();
    private JPanel top = new JPanel();
    private JPanel Bottom = new JPanel();

    private JLabel[] scores = new JLabel[5];
    private JLabel word = new JLabel("Hall Of Fame");

    public HallOfFame() {
        init();
    }

    public void init() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(d.width / 4, d.height / 8, d.width / 2, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);
        Container con = this.getContentPane();

        word.setFont(new Font("Tempus Sans ITC", Font.BOLD, 70));
        word.setForeground(Color.BLACK);

        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tempus Sans ITC", Font.BOLD, 40));
        back.setBackground(Color.BLACK);
        back.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                visible();
            }
        }
        );
        for (int i = 0; i < 5; i++) {
            scores[i] = new JLabel();
            scores[i].setHorizontalAlignment((int) CENTER_ALIGNMENT);
            scores[i].setFont(new Font("Tempus Sans ITC", Font.BOLD, 50));
            scores[i].setForeground(new Color(0, 153, 153));
            scores[i].setAlignmentY(CENTER_ALIGNMENT);
        }
        readscores();
        top.add(word);
        Bottom.add(back);
        con.add(top, BorderLayout.NORTH);
        con.add(center, BorderLayout.CENTER);
        con.add(Bottom, BorderLayout.SOUTH);
        center.setLayout(new GridLayout(5, 1));
    }

    public void visible() {
        this.setVisible(false);
        Finalgame.newgame.setVisible(true);
    }

    public void readscores() {
        ArrayList<String> Scores = new ArrayList<String>();
        FileReader fr;
        BufferedReader br;
        try {
            fr = new FileReader("scores.txt");
            br = new BufferedReader(fr);
            while (br.ready()) {
                Scores.add(br.readLine());
            }
            br.close();
        } catch (IOException ex) {
        }
        int[] Array = new int[Scores.size()];
        for (int i = 0; i < Scores.size(); i++) {
            Array[i] = Integer.parseInt(Scores.get(i));
        }
        Arrays.sort(Array);
        int count = 0;
        for (int i = Array.length; i >= Array.length - 5; i--) {
            try {
                scores[count].setText(Array[i] + "");
                center.add(scores[count]);
                count++;
            } catch (Exception ex) {
            }
        }
    }
}
