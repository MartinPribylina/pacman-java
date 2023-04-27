package src.main.java.src.common.gfx;

import src.main.java.src.common.CommonField;
import src.main.java.src.common.ImageEditor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PacmanGfx {

    public static Image GetImage(CommonField.Direction dir, int width, int pos){
        int startHorizontal = 229;
        int startVertical = 0;

        int y = 0;

        if (dir == null)
            dir = CommonField.Direction.RIGHT;

        switch (dir){

            case LEFT -> y = 1;
            case UP -> y = 2;
            case RIGHT -> y = 0;
            case DOWN -> y = 3;
        }

        File file = new File("lib/PacManSprites.png");

        try {
            BufferedImage img = ImageIO.read(file);
            Image image = img.getSubimage(startHorizontal + pos * 16, startVertical + y * 16, 15, 15);
            return ImageEditor.ScaleImage(image, width);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
