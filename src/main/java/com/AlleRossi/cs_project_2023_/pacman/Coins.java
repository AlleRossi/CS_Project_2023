package com.AlleRossi.cs_project_2023_.pacman;

import java.awt.*;

public class Coins {
    //coins position in the maze
    int xpos;
    int ypos;
    //coins dimensions
    int radius = 3;
    int diameter = radius * 2;
    //coins body; it is a rectangle but gets drawn as a circle
    Rectangle body;
    //constructor
    //@x and @y are the X and Y coordinates of each coin on the map
    public Coins(int x, int y) {
        this.xpos = x;
        this.ypos = y;

        body = new Rectangle(xpos, ypos, diameter, diameter);

    }
    //getter method for the coins body
    public Rectangle getCoinsBody() {
        return body;
    }
    //displays the coins on the panel
    public void drawCoin(Graphics2D gr) {
        gr.setColor(new Color(255, 215, 0));
        gr.drawOval(xpos - radius, ypos - radius, diameter, diameter);
        gr.fillOval(xpos - radius, ypos - radius, diameter, diameter);
    }
}
