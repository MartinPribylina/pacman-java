package src.gui;

import src.game.save.GameLogging;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;

public class MainFrame extends JFrame implements ActionListener {

    private static final int width = 800;
    private static final int height = 600;
    private static final String title = "Pacman";

    private static final String folderPath = System.getProperty("user.dir") + "\\data";

    private final Menu menu;

    private Game game;

    private GameReplay gameReplay;

    public MainFrame(){
        FrameSetup();

        menu = new Menu(this);
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
            fileChooser.setCurrentDirectory(new File(folderPath));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                this.remove(menu);
                game = new Game(this, selectedFile.getAbsolutePath());
                this.add(game);
                Refresh();
            }
        }else if(e.getSource() == menu.getReplay()){
            System.out.println("Replay");
            try(FileInputStream fi = new FileInputStream("replay.txt")) {
                ObjectInputStream oi = new ObjectInputStream(fi);
                GameLogging gameLogging = (GameLogging) oi.readObject();
                oi.close();
                System.out.println(gameLogging.getTime());
                System.out.println(gameLogging.getMaze());
                this.remove(menu);
                gameReplay = new GameReplay(this, gameLogging);
                this.add(gameReplay);
                Refresh();
            } catch (FileNotFoundException ex){
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        }else if(e.getSource() == menu.getExit()){
            System.out.println("Exit");
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }else if(game != null && (e.getSource() == game.getMenu() || e.getSource() == game.getError())){
            game.getGameLoop().stop();
            this.remove(game);
            game = null;
            this.add(menu);
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
