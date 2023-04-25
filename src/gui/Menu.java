package src.gui;

import src.common.ElementCreator;
import src.common.ImageEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Menu extends JPanel {

    private JButton play;
    private JButton replay;
    private JButton stats;
    private JButton exit;
    private final ActionListener parentListener;
    public Menu(ActionListener parentListener){
        this.parentListener = parentListener;

        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        SetupHeader();
        SetupButtons();

    }

    private void SetupHeader()
    {
        JLabel header = new JLabel();
        ImageIcon imageIcon = new ImageIcon(ImageEditor.ScaleImage("lib/pac-man-logo.png", 300));  // transform it back
        header.setIcon(imageIcon);
        header.setPreferredSize(new Dimension(300,150));
        this.add(header, BorderLayout.NORTH);

//        label.setHorizontalTextPosition(JLabel.CENTER); // Horizontal text possition
//        label.setVerticalTextPosition(JLabel.TOP); // Vertical text position
//        label.setIconTextGap(10); // Gap between text and image
//
//        label.setOpaque(true); // Set to true to show Background color
//        label.setBackground(Color.BLACK); // Background Color
//        label.setForeground(Color.white); // Text color
//
        header.setVerticalAlignment(JLabel.CENTER); // Set Vertical position within label
        header.setHorizontalAlignment(JLabel.CENTER); // Set Horizontal position within label
//
//        label.setFont(new Font("MV Boli", Font.PLAIN, 20));

    }

    private void SetupButtons(){
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.PAGE_AXIS));
        middlePanel.setBackground(Color.BLACK);

        middlePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        play = ElementCreator.CreateDefaultButton("Play", 150, 50, parentListener);
        middlePanel.add(play);

        middlePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        replay = ElementCreator.CreateDefaultButton("Replay", 150, 50, parentListener);
        middlePanel.add(replay);

        middlePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        stats = ElementCreator.CreateDefaultButton("Stats", 150, 50, parentListener);
        middlePanel.add(stats);

        middlePanel.add(Box.createRigidArea(new Dimension(0, 40)));

        exit = ElementCreator.CreateDefaultButton("Exit", 150, 50, parentListener);
        middlePanel.add(exit);

        this.add(middlePanel, BorderLayout.CENTER);
    }

    public JButton getPlay() {
        return play;
    }

    public JButton getReplay() {
        return replay;
    }

    public JButton getStats() {
        return stats;
    }

    public JButton getExit() {
        return exit;
    }
}
