package com.AlleRossi.cs_project_2023_.pacman;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Ghost {
    //variables for random directions
    int newDir;
    Random rand = new Random();
    //variables to decide where to make the ghosts go
    private static final int UP = 0;
    boolean isUp = false;
    private static final int DOWN = 1;
    boolean isDown = false;
    private static final int LEFT = 2;
    boolean isLeft = false;
    private static final int RIGHT = 3;
    boolean isRight = false;
    //ghosts position, dimensions and texture
    int xpos;
    int ypos;
    //passing the game panel to access other objects displayed
    GamePanel panel;

    //set the textures for the ghosts
    ArrayList<BufferedImage> textures = new ArrayList<>();// create an ArrayList of the different textures for ghosts
    ArrayList<String> paths = new ArrayList<>(); // Create an ArrayList object of paths
    ArrayList<Image> TransparentImages = new ArrayList<>();

    Image GhostIcon;
    int Gwidth = 50;
    int Gheight = 50;
    //variable to make unstuck the ghosts every once in a while
    int counter = 0;

    static final int maxSpeed = 4;
    double xspeed;
    double yspeed;
    //key variables for movement and a body to check collisions


    Rectangle body;

    public Ghost(int x, int y, int dir, int type, GamePanel panel) {
        this.xpos = x;
        this.ypos = y;
        switch (dir) {
            case RIGHT -> {
                isRight = true;
                xspeed = maxSpeed;
            }
            case LEFT -> {
                isLeft = true;
                xspeed = -maxSpeed;
            }
            case UP -> {
                isUp = true;
                yspeed = -maxSpeed;
            }
            case DOWN -> {
                isDown = true;
                yspeed = maxSpeed;
            }
        }
        this.panel = panel;
        loadIcons();
        GhostIcon = TransparentImages.get(type - 1);
        body = new Rectangle(xpos, ypos, Gwidth, Gheight);
    }

    //getter methods to obtain the ghosts info
    public Rectangle getBody() {
        return body;
    }

    public int getYposG() {
        return ypos;
    }

    public int getXposG() {
        return xpos;
    }

    public double getXspeedG() {
        return xspeed;
    }

    public double getYspeedG() {
        return yspeed;
    }

    //setter methods to set the ghosts stats
    public void setXspeesG(double Xspeed) {
        this.xspeed = Xspeed;
    }

    public void setYspeedG(double Yspeed) {
        this.yspeed = Yspeed;
    }

    public void setXposG(int x) {
        this.xpos = x;
    }

    public void setYposG(int y) {
        this.ypos = y;
    }

    public void updateBodyPosG() {
        this.body.x = xpos;
        this.body.y = ypos;
    }

    //method that randomly chooses where to go
    public int chooseDir(int old) {
        newDir = rand.nextInt(4);
        if (newDir == old) newDir = chooseDir(newDir);
        return newDir;
    }

    //method to control the ghosts
    public void set() {
        //collisions on the x-axis
        this.getBody().x += this.getXspeedG();
        for (Walls walls : panel.walls) {
            if (this.getBody().intersects(walls.getBody())) {
                this.getBody().x -= this.getXspeedG();
                while (!walls.getBody().intersects(this.getBody())) this.getBody().x += Math.signum(getXspeedG());
                this.getBody().x -= Math.signum(getXspeedG());
                this.setXspeesG(0);
                this.setXposG(getBody().x);
                changeDirection();
            }
        }

        // collisions on the y-axis
        this.getBody().y += this.getYspeedG();
        for (Walls walls : panel.walls) {
            if (this.getBody().intersects(walls.getBody())) {
                this.getBody().y -= getYspeedG();
                while (!walls.getBody().intersects(this.getBody())) this.getBody().y += Math.signum(getYspeedG());
                this.getBody().y -= Math.signum(getYspeedG());
                this.setYspeedG(0);
                this.setYposG(getBody().y);
                changeDirection();
            }
        }
        counter++;
        if (counter % 300 == 0) {
            counter = 0;
            changeDirection();
        }
        if (getXspeedG() > maxSpeed) setXspeesG(maxSpeed);
        if (getXspeedG() < -maxSpeed) setXspeesG(-maxSpeed);
        setXposG(getXposG() + (int) getXspeedG());
        setYposG(getYposG() + (int) getYspeedG());
        updateBodyPosG();
    }

    public boolean checkIfOpen(int way) {
        switch (way) {
            case UP -> {
                body.y -= 4;
                for (Walls walls : panel.walls) {
                    if (this.getBody().intersects(walls.getBody())) {
                        body.y += 4;
                        return false;
                    }
                }
            }
            case DOWN -> {
                body.y += 4;
                for (Walls walls : panel.walls) {
                    if (this.getBody().intersects(walls.getBody())) {
                        body.y -= 4;
                        return false;
                    }
                }
            }
            case RIGHT -> {
                body.x += 4;
                for (Walls walls : panel.walls) {
                    if (this.getBody().intersects(walls.getBody())) {
                        body.x -= 4;
                        return false;
                    }
                }
            }
            case LEFT -> {
                body.x -= 4;
                for (Walls walls : panel.walls) {
                    if (this.getBody().intersects(walls.getBody())) {
                        body.x += 4;
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //change direction after collision
    public void changeDirection() {
        int tmp;
        if (isDown || isUp) {
            if (isDown) {
                tmp = DOWN;
            } else {
                tmp = UP;
            }
            setYspeedG(0);
        } else {
            if (isLeft) {
                tmp = LEFT;
            } else {
                tmp = RIGHT;
            }
            setXspeesG(0);
        }
        int x = chooseDir(tmp);
        switch (x) {
            case DOWN -> {
                if (checkIfOpen(DOWN)) {
                    setYspeedG(maxSpeed);
                    isDown = true;
                } else changeDirection();
            }
            case UP -> {
                if (checkIfOpen(UP)) {
                    setYspeedG(-maxSpeed);
                    isUp = true;
                } else changeDirection();
            }
            case LEFT -> {
                if (checkIfOpen(LEFT)) {
                    setXspeesG(-maxSpeed);
                    isLeft = true;
                } else changeDirection();
            }
            case RIGHT -> {
                if (checkIfOpen(RIGHT)) {
                    setXspeesG(maxSpeed);
                    isRight = true;
                } else changeDirection();
            }
        }
        switch (tmp) {
            case DOWN -> isDown = false;
            case UP -> isUp = false;
            case LEFT -> isLeft = false;
            case RIGHT -> isRight = false;

        }
    }

    //method to create the ghosts icons with transparent background
    public void loadIcons() {
        ImageProducer filteredImgProd;
        Image transparentImg;
        paths.add("src/images/ghost1.png");
        paths.add("src/images/ghost2.png");
        paths.add("src/images/ghost3.png");
        for (String pat : paths) {
            {
                try {
                    textures.add(ImageIO.read(new File(pat)));
                } catch (IOException e) {
                    System.out.print("Problem loading Ghost Icons");
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

    // crate a filter to make the icon's background transparent
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

    //draw the ghost
    public void drawGhost(Graphics2D gr) {
        gr.drawImage(GhostIcon, xpos, ypos, null);
    }
}
