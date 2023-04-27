/*************************
 * Authors: Martin Pribylina
 *
 * Graphics for misc
 ************************/
package src.common.gfx;

import src.common.ImageEditor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MiscGfx {

    public static Image GetKey(int width){
        int startHorizontal = 228 + 10 * 16;
        int startVertical = 48;

        File file = new File("lib/PacManSprites.png");

        try {
            BufferedImage img = ImageIO.read(file);
            Image image = img.getSubimage(startHorizontal, startVertical, 16, 16);
            return ImageEditor.ScaleImage(image, width);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image GetTarget(int width){
        int startHorizontal = 228 + 13 * 16;
        int startVertical = 0;

        File file = new File("lib/PacManSprites.png");

        try {
            BufferedImage img = ImageIO.read(file);
            Image image = img.getSubimage(startHorizontal, startVertical, 16, 16);
            return ImageEditor.ScaleImage(image, width);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
