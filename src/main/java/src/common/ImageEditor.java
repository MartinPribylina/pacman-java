/*************************
 * Authors: Martin Pribylina
 *
 * Image scale editor
 ************************/
package src.common;

import javax.swing.*;
import java.awt.*;

/**
 * ImageEditor is utility class for easier manipulation with images
 *
 * @author      Martin Pribylina
 */
public class ImageEditor {

    public static Image ScaleImage(String filename, int width){
        ImageIcon imageIcon = new ImageIcon(filename);
        Image image = imageIcon.getImage(); // transform it
        double ratio = (double) imageIcon.getIconWidth() / (double) imageIcon.getIconHeight();
        int newImageHeight = (int)(width / ratio);
        return image.getScaledInstance(width, newImageHeight,  Image.SCALE_AREA_AVERAGING); // scale it the smooth way
    }

    public static Image ScaleImage(Image img, int width){
        ImageIcon imageIcon = new ImageIcon(img);
        Image image = imageIcon.getImage(); // transform it
        double ratio = (double) imageIcon.getIconWidth() / (double) imageIcon.getIconHeight();
        int newImageHeight = (int)(width / ratio);
        return image.getScaledInstance(width, newImageHeight,  Image.SCALE_AREA_AVERAGING); // scale it the smooth way
    }
}
