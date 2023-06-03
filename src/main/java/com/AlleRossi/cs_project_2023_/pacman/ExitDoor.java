package com.AlleRossi.cs_project_2023_.pacman;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ExitDoor {
    int xpos;
    int ypos;

    BufferedImage textureClosed;
    BufferedImage textureOpen;

    int Dwidth = 60;
    int Dheight = 55;

    Rectangle body;

    public ExitDoor(int x, int y) {
        this.xpos = x;
        this.ypos = y;

        body = new Rectangle(xpos, ypos, Dwidth, Dheight);
    }

    //method to get the door's body
    public Rectangle getDoorBody() {
        return body;
    }

    //load image for flag obj
    {
        try {
            textureOpen = ImageIO.read(new File("src/images/open_door.png"));
            textureClosed = ImageIO.read(new File("src/images/locked_doors.png"));
        } catch (IOException e) {
            System.out.print("Problem loading Doors Icons");
            throw new RuntimeException(e.getCause());
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
    ImageProducer filteredImgProdOpen = new FilteredImageSource(textureOpen.getSource(), filter);
    ImageProducer filteredImgProdClosed = new FilteredImageSource(textureClosed.getSource(), filter);
    Image DoorIconOpen = Toolkit.getDefaultToolkit().createImage(filteredImgProdOpen);
    Image DoorIconClosed = Toolkit.getDefaultToolkit().createImage(filteredImgProdClosed);

    //draw the doors
    public void drawDoor(Graphics2D gr, boolean open) {
        if (!open) gr.drawImage(DoorIconClosed, xpos, ypos, null);
        else gr.drawImage(DoorIconOpen, xpos, ypos, null);
    }
}
