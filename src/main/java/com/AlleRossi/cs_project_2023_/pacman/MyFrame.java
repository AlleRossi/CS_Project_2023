package com.AlleRossi.cs_project_2023_.pacman;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyFrame extends JFrame implements ActionListener {
    //variable to know what to do once the button is clicked
    boolean isStart = true;
    //Jpanel to show info at beginning and end of the game
    JPanel Bpan;
    //button to start, end or restart the game
    JButton btn;
    //panel instance
    GamePanel pan;
    BufferedImage icon;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //loading the frame icon
    {
        try {
            icon = ImageIO.read(new File("src/images/pacman.png"));
        } catch (IOException e) {
            System.out.println("Error loading Pac's icon");
            throw new RuntimeException(e.getCause());
        }
    }

    public MyFrame() {

        this.setSize(800, 600);
        this.setIconImage(icon);
        this.setLocation((int) (screenSize.getWidth() / 2 - this.getSize().getWidth() / 2), (int) (screenSize.getHeight() / 2 - this.getSize().getHeight() / 2));
        this.setResizable(false);
        this.setTitle("PacMan");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void BeginningScreen() {
        btn = new JButton("Start the game");
        Bpan = new JPanel();
        Font f = new Font("Mv Boli", Font.BOLD, 20);
        Bpan.setSize(this.getSize());
        Bpan.setOpaque(true);
        Bpan.setBackground(Color.BLACK);
        btn.setBounds(200, 100, 200, 50);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.GREEN);
        btn.setBorder(new BasicBorders.ButtonBorder(Color.GREEN, Color.RED, Color.GREEN, Color.RED));
        btn.setFont(f);
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setFocusable(false);
        btn.addActionListener(this);
        btn.setVisible(true);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setLocation(300, 450);
        Bpan.add(btn);
        Bpan.setLayout(null);
        JLabel info = new JLabel("CS Project 2023");
        JLabel dis = new JLabel("Disclaimer:");
        JLabel dis1 = new JLabel("I don't own any rights regarding the game");
        JLabel dis2 = new JLabel("or");
        JLabel dis3 = new JLabel("any of the images used in it");
        JLabel dis4 = new JLabel("Educational purposes only");
        dis.setForeground(Color.GREEN);
        dis.setBounds(350, 130, 200, 50);
        dis1.setForeground(Color.GREEN);
        dis1.setBounds(200, 165, 600, 50);
        dis2.setForeground(Color.GREEN);
        dis2.setBounds(400, 200, 200, 50);
        dis3.setForeground(Color.GREEN);
        dis3.setBounds(250, 235, 400, 50);
        dis4.setForeground(Color.GREEN);
        dis4.setBounds(275, 270, 400, 50);
        dis.setFont(f);
        dis1.setFont(f);
        dis2.setFont(f);
        dis3.setFont(f);
        dis4.setFont(f);
        info.setOpaque(false);
        info.setBorder(new BasicBorders.MarginBorder());
        info.setSize(200, 50);
        info.setVerticalAlignment(JLabel.CENTER);
        info.setVerticalTextPosition(JLabel.TOP);
        info.setLocation(300, 35);
        info.setHorizontalTextPosition(JLabel.CENTER);
        info.setFont(f);
        info.setForeground(Color.GREEN);
        Bpan.add(dis);
        Bpan.add(dis1);
        Bpan.add(dis2);
        Bpan.add(dis3);
        Bpan.add(dis4);
        Bpan.add(info);
        this.add(Bpan);
    }

    public void StartGame() {
        this.setSize(1000, 800);
        this.setLocation((int) (screenSize.getWidth() / 2 - this.getSize().getWidth() / 2), (int) (screenSize.getHeight() / 2 - this.getSize().getHeight() / 2));
        pan = new GamePanel(this);
        pan.setLocation(0, 0);
        pan.setSize(this.getSize());
        pan.setBackground(new Color(12, 80, 255));
        pan.setVisible(true);
        this.add(pan);
        addKeyListener(new KeyChecker(pan));
    }

    //code for GAMEOVER
    public void gameOver() {
        int[] arr = pan.getInfo();
        this.setVisible(false);
        this.remove(pan);
        this.setSize(800, 600);
        this.setLocation((int) (screenSize.getWidth() / 2 - this.getSize().getWidth() / 2), (int) (screenSize.getHeight() / 2 - this.getSize().getHeight() / 2));
        btn = new JButton("Quit");
        Bpan = new JPanel();
        Font f = new Font("Mv Boli", Font.BOLD, 20);
        Bpan.setSize(this.getSize());
        Bpan.setOpaque(true);
        Bpan.setBackground(Color.BLACK);
        btn.setBounds(300, 400, 150, 40);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.GREEN);
        btn.setBorder(new BasicBorders.ButtonBorder(Color.GREEN, Color.RED, Color.GREEN, Color.RED));
        btn.setFont(f);
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setFocusable(false);
        btn.addActionListener(this);
        btn.setVisible(true);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        Bpan.add(btn);
        Bpan.setLayout(null);
        JLabel info = new JLabel();
        JLabel dis = new JLabel("Coins Collected: " + arr[1]);
        JLabel dis2 = new JLabel("Lives left: " + (arr[2]));
        dis.setFont(f);
        dis.setForeground(Color.GREEN);
        dis.setBounds(300, 250, 200, 50);
        JLabel dis3 = new JLabel();
        if (arr[3] == 0) dis3.setText("Flag Captured : NO");
        else dis3.setText("Flag Captured : YES");
        dis3.setFont(f);
        dis3.setForeground(Color.GREEN);
        dis3.setBounds(300, 300, 250, 50);
        dis2.setFont(f);
        dis2.setForeground(Color.GREEN);
        dis2.setBounds(300, 200, 200, 50);
        info.setOpaque(false);
        info.setBorder(new BasicBorders.MarginBorder());
        info.setSize(300, 50);
        info.setVerticalAlignment(JLabel.CENTER);
        info.setVerticalTextPosition(JLabel.TOP);
        info.setLocation(300, 75);
        info.setHorizontalTextPosition(JLabel.CENTER);
        info.setFont(new Font("Mv Boli", Font.BOLD, 50));
        info.setForeground(Color.GREEN);
        if (arr[0] == 1) {
            info.setText("VICTORY");
        } else {
            info.setText("DEFEAT");
        }
        Bpan.add(dis3);
        Bpan.add(dis2);
        Bpan.add(dis);
        Bpan.add(info);
        this.add(Bpan);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn && isStart) {
            this.setVisible(false);
            this.remove(Bpan);
            isStart = false;
            StartGame();
            this.setVisible(true);
        } else if (e.getSource() == btn && !isStart) {
            this.dispose();
        }
    }
}
