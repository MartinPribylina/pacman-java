package src.common.gfx;

import src.common.CommonField;
import src.common.ImageEditor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GhostGfx {

    public static Image GetImage(CommonField.Direction dir, int width, int ghostType){
        int startHorizontal = 228;
        int startVertical = 64;

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
            Image image = img.getSubimage(startHorizontal + y * 16 * 2, startVertical + ghostType * 16, 16, 16);
            return ImageEditor.ScaleImage(image, width);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
