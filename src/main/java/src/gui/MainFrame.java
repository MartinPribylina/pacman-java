package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

public class MainFrame extends JFrame implements ActionListener {

    private static final int width = 800;
    private static final int height = 600;
    private static final String title = "Pacman";

    private static final String mazeFolderPath = System.getProperty("user.dir") + "\\data\\maze";
    private static final String replayFolderPath = System.getProperty("user.dir") + "\\data\\replay";

    private final MainMenu menu;

    private GamePlay gamePlay;

    private GameReplay gameReplay;

    private Stats stats;

    private File lastSelectedFile;

    public MainFrame(){
        FrameSetup();

        menu = new MainMenu(this);
        this.add(menu);

        this.setVisible(true); // set frame visible, Calling last so everything gets displayed
    }

    private void FrameSetup(){
        this.setTitle(title); // set Title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close application
        this.setResizable(false); // prevent users from resizing window
        this.setSize(width, height); // set width and height
        this.setLocationRelativeTo(null); // display in the middle

        ImageIcon icon = new ImageIcon("lib/Pacman.png"); // create image icon
        this.setIconImage(icon.getImage()); // change icon
        this.getContentPane().setBackground(Color.BLACK); // change background color
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu.getPlay())
        {
            System.out.println("Play");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(mazeFolderPath));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                lastSelectedFile = selectedFile;
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                this.remove(menu);
                gamePlay = new GamePlay(this, selectedFile.getAbsolutePath());
                this.add(gamePlay);
                Refresh();
            }
        }else if(e.getSource() == menu.getReplay()){
            System.out.println("Replay");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(replayFolderPath));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                this.remove(menu);
                gameReplay = new GameReplay(this, selectedFile);
                this.add(gameReplay);
                Refresh();
            }

        } else if (e.getSource() == menu.getStats()) {
            stats = new Stats(this);
            this.remove(menu);
            this.add(stats);
            Refresh();
        } else if (stats != null && e.getSource() == stats.getBack()) {
            this.remove(stats);
            this.add(menu);
            Refresh();
        } else if(e.getSource() == menu.getExit()){
            System.out.println("Exit");
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }else if(gamePlay != null && (e.getSource() == gamePlay.getMenu() || e.getSource() == gamePlay.getError())){
            gamePlay.getGameLoop().stop();
            this.remove(gamePlay);
            gamePlay = null;
            this.add(menu);
            Refresh();
        }else if(gamePlay != null && e.getSource() == gamePlay.getPlayAgain()){
            this.remove(gamePlay);
            gamePlay = new GamePlay(this, lastSelectedFile.getAbsolutePath());
            this.add(gamePlay);
            Refresh();
        }else if(gameReplay != null && e.getSource() == gameReplay.getMenu()){
            this.remove(gameReplay);
            gameReplay = null;
            this.add(menu);
            Refresh();
        }
    }

    private void Refresh(){
        this.revalidate();
        this.repaint();
    }
}
