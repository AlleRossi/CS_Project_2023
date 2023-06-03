package com.AlleRossi.cs_project_2023_.pacman;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Walls {
    BufferedImage texture;

    {
        try {
            texture = ImageIO.read(new File("src/images/walltxt.jpg"));
        } catch (IOException e) {
            System.out.print("Problem loading Walls Icons");
            throw new RuntimeException(e.getCause());
        }
    }

    int x;
    int y;
    int height;
    int width;
    Rectangle body;

    public Walls(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;

        body = new Rectangle(x, y, width, height);

    }

    public Rectangle getBody() {
        return body;
    }

    public void draw(Graphics2D gr) {
        TexturePaint tp = new TexturePaint(texture, new Rectangle(0, 0, texture.getWidth(), texture.getHeight()));
        gr.setPaint(tp);
        gr.fillRect(x, y, width, height);
    }
}
