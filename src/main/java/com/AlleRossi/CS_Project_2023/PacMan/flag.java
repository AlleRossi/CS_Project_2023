package com.AlleRossi.CS_Project_2023.PacMan;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class flag {
    int xpos;
    int ypos;

    BufferedImage texture;

    int Fwidth = 50;
    int Fheight = 50;

    Rectangle body;

    public flag(int x, int y) {
        this.xpos = x;
        this.ypos = y;

        body = new Rectangle(xpos, ypos, Fwidth, Fheight);
    }

    //get method to retrieve flag's body
    public Rectangle getFlgBody() {
        return body;
    }

    //load image for flag obj
    {
        try {
            texture = ImageIO.read(new File("src/images/flag.png"));
        } catch (IOException e) {
            System.out.print("Problem loading Flag Icons");
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
    ImageProducer filteredImgProd = new FilteredImageSource(texture.getSource(), filter);
    Image FlagIcon = Toolkit.getDefaultToolkit().createImage(filteredImgProd);

    //draw the flag
    public void drawFlag(Graphics2D gr) {
        gr.drawImage(FlagIcon, xpos, ypos, null);
    }
}
