package com.AlleRossi.cs_project_2023_.pacman;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Player {
    //variable to check if just respawned
    boolean justResp = false;
    int counter = 0;
    //variable for victory;
    int victory = 0;
    //random variable to choose a respawn site
    Random rand = new Random();
    int site;
    //set the textures for the player
    ArrayList<BufferedImage> textures = new ArrayList<>();// create an ArrayList of the different textures for Pacman
    ArrayList<String> paths = new ArrayList<>(); // Create an ArrayList object of paths
    ArrayList<Image> TransparentImages = new ArrayList<>();

    //set the background colour of Pacman's  icon to transparent using this "magic" trick
    ImageFilter filter = new RGBImageFilter() {
        final int transparentColor = Color.white.getRGB() | 0xFF000000;

        public int filterRGB(int x, int y, int rgb) {
            if ((rgb | 0xFF000000) == transparentColor) {
                return 0x00FFFFFF & rgb;
            } else {
                return rgb;
            }
        }
    };

    Image mask;

    //lives left
    int lives = 3;
    //coins collected
    int Ccollected = 0;
    // flag collected
    boolean FlagCollected = false;
    //Player's position, dimensions and speed
    GamePanel panel;
    int x;
    int y;
    int Pheigh;
    int Pwidth;

    double xspeed;
    double yspeed;
    //key variables for movement and a body top check collisions
    boolean up;
    boolean down;
    boolean left;
    boolean right;
    Rectangle solid;

    //constructor for the PLayer obj
    public Player(int x, int y, GamePanel panel) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        Pwidth = 50;
        Pheigh = 50;
        solid = new Rectangle(x, y, Pwidth, Pheigh);
        loadIcons();
        mask = TransparentImages.get(0);
    }

    //getter methods for Player's stats
    public Rectangle getBodyP() {
        return solid;
    }

    public int getCoins() {
        return Ccollected;
    }

    public int getLives() {
        return lives;
    }

    public boolean getFlagStatus() {
        return FlagCollected;
    }

    public int getVictory() {
        return victory;
    }

    //setter methods to change the Player's stats
    public void setXPac(int X) {
        this.x = X;
    }

    public void setYPac(int Y) {
        this.y = Y;
    }

    public void setXspeedP(double Xs) {
        this.xspeed = Xs;
    }

    public void setYspeedP(double Ys) {
        this.yspeed = Ys;
    }

    public void setLeftP(boolean bool) {
        this.left = bool;
    }

    public void setRightP(boolean bool) {
        this.right = bool;
    }

    public void setUpP(boolean bool) {
        this.up = bool;
    }

    public void setDownP(boolean bool) {
        this.down = bool;
    }

    public void loadIcons() {
        ImageProducer filteredImgProd;
        Image transparentImg;
        paths.add("src/images/pacman.png");
        paths.add("src/images/pacmanDown.png");
        paths.add("src/images/pacmanLeft.png");
        paths.add("src/images/pacmanUp.png");
        for (String pat : paths) {
            {
                try {
                    textures.add(ImageIO.read(new File(pat)));
                } catch (IOException e) {
                    System.out.print("Problem loading Pacman's game Icons");
                    throw new RuntimeException(e.getCause());
                }
            }
        }
        for (BufferedImage img : textures) {
            filteredImgProd = new FilteredImageSource(img.getSource(), filter);
            transparentImg = Toolkit.getDefaultToolkit().createImage(filteredImgProd);
            TransparentImages.add(transparentImg);
        }
    }

    //set method that allows us to move around the panel/maze
    public void set() {
        //controls on the x-axis
        if (left && right || !left && !right) xspeed *= 0.8;
        else if (left) {
            xspeed--;
            mask = TransparentImages.get(2);
        } else {
            xspeed++;
            mask = TransparentImages.get(0);
        }
        if (xspeed > 0 && xspeed < 0.75) xspeed = 0;
        if (xspeed < 0 && xspeed > -0.75) xspeed = 0;
        if (xspeed > 4) xspeed = 4;
        if (xspeed < -4) xspeed = -4;
        //controls on teh y-axis
        if (up && down || !up && !down) yspeed *= 0.8;
        else if (up) {
            yspeed--;
            mask = TransparentImages.get(3);
        } else {
            yspeed++;
            mask = TransparentImages.get(1);
        }
        if (yspeed > 0 && yspeed < 0.75) yspeed = 0;
        if (yspeed < 0 && yspeed > -0.75) yspeed = 0;
        if (yspeed > 4) yspeed = 4;
        if (yspeed < -4) yspeed = -4;

        //collisions on the x-axis
        solid.x += xspeed;
        for (Walls walls : panel.walls) {
            if (this.getBodyP().intersects(walls.getBody())) {
                solid.x -= xspeed;
                while (!walls.getBody().intersects(this.getBodyP())) solid.x += Math.signum(xspeed);
                solid.x -= Math.signum(xspeed);
                xspeed = 0;
                x = solid.x;
            }
        }

        // collisions on the y-axis
        solid.y += yspeed;
        for (Walls walls : panel.walls) {
            if (this.getBodyP().intersects(walls.getBody())) {
                solid.y -= yspeed;
                while (!walls.body.intersects(solid)) solid.y += Math.signum(yspeed);
                solid.y -= Math.signum(yspeed);
                yspeed = 0;
                y = solid.y;
            }
        }
        //checks for collision with coins and collects them
        for (int i = 0; i < panel.coins.size(); i++) {
            if (solid.intersects(panel.coins.get(i).getCoinsBody())) {
                Ccollected++;
                //noinspection SuspiciousListRemoveInLoop
                panel.coins.remove(i);
            }
        }
        //checks for the flag capture
        if (Ccollected >= 12 && this.getBodyP().intersects(panel.Fl.getFlgBody()) && !FlagCollected) {
            FlagCollected = true;
        }


        x += xspeed;
        y += yspeed;

        solid.x = x;
        solid.y = y;

        if (!justResp && lives != 0) {
            //code for death
            for (Ghost ghost : panel.ghosts) {
                if (this.getBodyP().intersects(ghost.getBody())) {
                    lives--;
                    respawn();
                }
            }
        } else {
            if (counter >= 65) {
                justResp = false;
                counter = 0;
            } else counter++;
        }
        //code to end the game on either victory or lack of lives left
        if (lives == 0 || (this.getBodyP().intersects(panel.doors.getDoorBody()) && FlagCollected)) {
            if ((solid.intersects(panel.doors.getDoorBody()) && FlagCollected)) victory = 1;
            panel.gameOver();
        }
    }

    public void respawn() {
        xspeed = 0;
        yspeed = 0;
        justResp = true;
        site = rand.nextInt(3);
        switch (site) {
            case 0 -> {
                x = 450;
                y = 310;
                solid.x = 450;
                solid.y = 310;
            }
            case 1 -> {
                x = 200;
                y = 60;
                solid.x = 200;
                solid.y = 60;
            }
            case 2 -> {
                x = 700;
                y = 660;
                solid.x = 700;
                solid.y = 660;
            }
        }
    }

    //code for drawing our objects
    public void draw(Graphics2D gr) {

        gr.drawImage(mask, x, y, null);
    }

}
