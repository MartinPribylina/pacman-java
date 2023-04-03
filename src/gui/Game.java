package src.gui;

import src.MazePresenter;
import src.common.CommonMaze;
import src.common.ElementCreator;
import src.common.readers.maze.MazeFileReader;
import src.common.readers.maze.MazeFileReaderResult;
import src.game.MazeConfigure;
import src.game.core.FrameBasedGameLoop;
import src.game.core.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class Game extends JPanel{

    private final JButton menu;
    private JButton error;
    private final ActionListener parentListener;

    public Game(ActionListener parentListener, String filePath){
        this.parentListener = parentListener;

        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(Color.BLACK);
        this.add(header, BorderLayout.NORTH);
        header.setPreferredSize(new Dimension(100, 50));
        header.setLayout(new BorderLayout());

        JPanel sideBar = new JPanel();
        sideBar.setBackground(Color.BLACK);
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.PAGE_AXIS));
        this.add(sideBar, BorderLayout.WEST);

        menu = ElementCreator.CreateDefaultButton("Menu", 100, 50, parentListener);
        sideBar.add(menu, BorderLayout.WEST);

        JLabel score = ElementCreator.CreateDefaultLabel("Score: 0");
        score.setHorizontalTextPosition(JLabel.CENTER); // Horizontal text possition
        score.setVerticalAlignment(JLabel.CENTER);
        score.setHorizontalAlignment(JLabel.CENTER);
        header.add(score, BorderLayout.CENTER);

        MazeFileReaderResult result = MazeFileReader.ConfigureMaze(filePath);
        MazeConfigure mazeConfigure = result.getMazeConfigure();
        CommonMaze maze = mazeConfigure.createMaze();

        if (result.getErrorCode() != 0 || (mazeConfigure != null && !mazeConfigure.isReadingSuccess()) || maze == null)
        {
            JPanel errorPanel = new JPanel();
            errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.PAGE_AXIS));
            errorPanel.setBackground(Color.BLACK);
            this.add(errorPanel, BorderLayout.CENTER);

            JLabel errorLabel = ElementCreator.CreateDefaultLabel("Error: " + result.getErrorMessage());
            errorPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            errorPanel.add(errorLabel);

            JPanel wrap = new JPanel();
            wrap.setBackground(Color.BLACK);
            error = ElementCreator.CreateDefaultButton("OK", 100, 50, parentListener);
            error.setAlignmentX(Component.CENTER_ALIGNMENT);
            wrap.add(error);
            errorPanel.add(wrap);

            return;
        }


        MazePresenter presenter = new MazePresenter(maze);
        this.add(presenter, BorderLayout.CENTER);

        GameController gameController = new GameController(maze);

        FrameBasedGameLoop gameLoop = new FrameBasedGameLoop(gameController);

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('w'), "upAction");
        this.getActionMap().put("upAction", gameLoop.getPlayerActions().getUpAction());

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('s'), "downAction");
        this.getActionMap().put("downAction", gameLoop.getPlayerActions().getDownAction());

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('a'), "leftAction");
        this.getActionMap().put("leftAction", gameLoop.getPlayerActions().getLeftAction());

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('d'), "rightAction");
        this.getActionMap().put("rightAction", gameLoop.getPlayerActions().getRightAction());

        gameLoop.run();
    }

    public JButton getMenu() {
        return menu;
    }

    public JButton getError() {
        return error;
    }
}
