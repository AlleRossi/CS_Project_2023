package com.AlleRossi.cs_project_2023_.pacman;

import java.awt.*;

public class Coins {
    int xpos;
    int ypos;
    int radius = 3;
    int diameter = radius * 2;

    Rectangle body;

    public Coins(int x, int y) {
        this.xpos = x;
        this.ypos = y;

        body = new Rectangle(xpos, ypos, diameter, diameter);

    }

    public Rectangle getCoinsBody() {
        return body;
    }

    public void drawCoin(Graphics2D gr) {
        gr.setColor(new Color(255, 215, 0));
        gr.drawOval(xpos - radius, ypos - radius, diameter, diameter);
        gr.fillOval(xpos - radius, ypos - radius, diameter, diameter);
    }
}
