/*************************
 * Authors: Martin Pribylina
 *
 * GUI element creator
 ************************/
package src.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * ElementCreator is utility class for fast and generalized creation of GUI elements such as Buttons and Labels
 *
 * @author      Martin Pribylina
 */
public class ElementCreator {

    private static final Font font = new Font(Font.SERIF, Font.BOLD, 20);
    public static JButton CreateDefaultButton(String text, int width, int height, ActionListener actionListener)
    {
        JButton button = new JButton();
        button.setText(text);
        Dimension size = new Dimension(width, height);
        button.setMinimumSize(size);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.addActionListener(actionListener);
        button.setFocusable(false);
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }
    public static JButton CreateButton(String text, int width, int height, ActionListener actionListener)
    {
        JButton button = new JButton();
        button.setText(text);
        Dimension size = new Dimension(width, height);
        button.setMinimumSize(size);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.addActionListener(actionListener);
        button.setFocusable(false);
        button.setFont(font);

        return button;
    }

    public static JLabel CreateDefaultLabel(String text)
    {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.WHITE);

        return label;
    }
}
