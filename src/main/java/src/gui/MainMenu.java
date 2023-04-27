package src.main.java.src.gui;

import src.main.java.src.common.ElementCreator;
import src.main.java.src.common.ImageEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    private JButton play;
    private JButton replay;
    private JButton stats;
    private JButton exit;
    private final ActionListener parentListener;
    public MainMenu(ActionListener parentListener){
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

        header.setVerticalAlignment(JLabel.CENTER); // Set Vertical position within label
        header.setHorizontalAlignment(JLabel.CENTER); // Set Horizontal position within label

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

        JPanel wrap = new JPanel();
        wrap.setBackground(Color.BLACK);
        exit = ElementCreator.CreateDefaultButton("Exit", 150, 50, parentListener);
        wrap.add(exit);
        wrap.setBorder(BorderFactory.createEmptyBorder(0,0,50,0));
        this.add(wrap, BorderLayout.SOUTH);

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
