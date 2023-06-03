package com.AlleRossi.cs_project_2023_.pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {
    //create a player instance
    Player Pac;
    //creates the door instance
    ExitDoor doors;
    //creates the flag
    Flag Fl;
    //stores all our walls in here
    ArrayList<Walls> walls = new ArrayList<>();
    //store the ghosts in here
    ArrayList<Ghost> ghosts = new ArrayList<>();
    //stores our coins in here
    ArrayList<Coins> coins = new ArrayList<>();
    //generates a game timer
    Timer gameTimer;
    //Frame to witch the panel belongs
    MyFrame Frame;
    //constructor
    //@frame where our panel is displayed
    public GamePanel(MyFrame frame) {
        this.Frame = frame;
        Pac = new Player(50, 650, this);
        reset();
        makeWalls();
        addCoins();
        addFlag();
        addDoors();
        deployGhosts();
        gameTimer = new Timer();
        //code to start the game timer--> aka our game loop
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Pac.set();
                for (Ghost ghost : ghosts) {
                    ghost.set();
                }
                repaint();
            }
        }, 0, 17);
    }

    //code for drawing on the panel
    //@g stands for graphics and gets cast to graphiscs2D to improve textures and possible delays
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D gr = (Graphics2D) g;

        doors.drawDoor(gr, Pac.FlagCollected);
        Pac.draw(gr);
        if (!Pac.FlagCollected) Fl.drawFlag(gr);
        for (Coins cn : coins) cn.drawCoin(gr);
        for (Walls walls : walls) walls.draw(gr);
        for (Ghost ghost : ghosts) ghost.drawGhost(gr);
    }


    //code for adding the flag
    public void addFlag() {
        Fl = new Flag(800, 165);
    }

    //code for creating the doors
    public void addDoors() {
        doors = new ExitDoor(60, 655);
    }

    //code for deploying the coins
    public void addCoins() {

        coins.add(new Coins(75, 75));
        coins.add(new Coins(75, 535));
        coins.add(new Coins(175, 135));
        coins.add(new Coins(175, 285));
        coins.add(new Coins(325, 75));
        coins.add(new Coins(275, 285));
        coins.add(new Coins(325, 685));
        coins.add(new Coins(425, 485));
        coins.add(new Coins(475, 335));
        coins.add(new Coins(575, 435));
        coins.add(new Coins(475, 235));
        coins.add(new Coins(425, 75));
        coins.add(new Coins(845, 75));
        coins.add(new Coins(660, 235));
        coins.add(new Coins(820, 330));
        coins.add(new Coins(910, 685));
        coins.add(new Coins(775, 485));
        coins.add(new Coins(525, 585));
    }

    //code to deploy ghosts
    public void deployGhosts() {
        ghosts.add(new Ghost(750, 660, 0, 1, this));
        ghosts.add(new Ghost(800, 660, 2, 2, this));
        ghosts.add(new Ghost(850, 660, 3, 3, this));
    }

    //code for making the walls of the maze
    public void makeWalls() {
        //creating outer walls
        for (int i = 0; i < 1000; i += 50) {
            walls.add(new Walls(i, 710, 50, 50));
            walls.add(new Walls(i, 0, 50, 50));

        }
        for (int i = 0; i < 750; i += 50) {
            walls.add(new Walls(0, i, 50, 50));
            walls.add(new Walls(935, i, 50, 50));

        }
        //creating inner maze walls
        //good old hardcoding

        //maze walls group 1
        walls.add(new Walls(200, 660, 100, 50));
        walls.add(new Walls(250, 460, 50, 200));
        walls.add(new Walls(300, 460, 50, 50));

        //maze walls group 2
        walls.add(new Walls(50, 560, 100, 50));
        walls.add(new Walls(100, 460, 50, 50));

        //maze walls group 3
        walls.add(new Walls(150, 360, 50, 50));
        walls.add(new Walls(100, 310, 200, 50));
        walls.add(new Walls(100, 50, 50, 110));
        walls.add(new Walls(150, 50, 50, 60));
        walls.add(new Walls(200, 210, 50, 100));
        walls.add(new Walls(200, 160, 200, 50));
        walls.add(new Walls(350, 50, 50, 110));

        //maze walls group 4
        walls.add(new Walls(350, 560, 150, 100));
        walls.add(new Walls(450, 510, 50, 50));
        walls.add(new Walls(450, 360, 100, 150));
        walls.add(new Walls(350, 360, 300, 50));
        walls.add(new Walls(350, 260, 100, 100));
        walls.add(new Walls(450, 260, 100, 50));
        walls.add(new Walls(500, 110, 50, 150));

        //maze walls group 5
        walls.add(new Walls(550, 660, 150, 50));
        walls.add(new Walls(600, 560, 150, 50));
        walls.add(new Walls(700, 510, 185, 50));

        //maze walls group 6
        walls.add(new Walls(750, 310, 50, 150));
        walls.add(new Walls(635, 260, 250, 50));
        walls.add(new Walls(685, 160, 100, 100));
        walls.add(new Walls(735, 110, 50, 50));
        walls.add(new Walls(875, 50, 60, 60));


    }

    //code to reset player position and speed on death
    public void reset() {
        Pac.setXPac(50);
        Pac.setYPac(650);
        Pac.setXspeedP(0);
        Pac.setYspeedP(0);
        walls.clear();
        makeWalls();
    }

    //key listeners for user input
    void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'a') Pac.setLeftP(true);
        if (e.getKeyChar() == 'w') Pac.setUpP(true);
        if (e.getKeyChar() == 'd') Pac.setRightP(true);
        if (e.getKeyChar() == 's') Pac.setDownP(true);
    }

    //key listeners
    void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'a') Pac.setLeftP(false);
        if (e.getKeyChar() == 'w') Pac.setUpP(false);
        if (e.getKeyChar() == 'd') Pac.setRightP(false);
        if (e.getKeyChar() == 's') Pac.setDownP(false);
    }

    //code to return game stats
    public int[] getInfo() {
        int[] arr = new int[4];
        arr[0] = Pac.getVictory();
        arr[1] = Pac.getCoins();
        arr[2] = Pac.getLives();
        if (Pac.getFlagStatus()) arr[3] = 1;
        else arr[3] = 0;
        return arr;
    }

    //code for GAMEOVER
    public void gameOver() {
        gameTimer.cancel();
        this.Frame.gameOver();
    }
}
