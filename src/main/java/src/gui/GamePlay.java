/*************************
 * Authors: Martin Pribylina
 *
 * Class that creates a game GUI and starts the game loop
 ************************/
package src.gui;

import src.MazePresenter;
import src.common.CommonField;
import src.common.CommonMaze;
import src.common.ElementCreator;
import src.common.readers.maze.MazeFileReader;
import src.common.readers.maze.MazeFileReaderResult;
import src.game.MazeConfigure;
import src.game.core.FrameBasedGameLoop;
import src.game.core.GameController;
import src.game.core.GameLoop;
import src.game.save.GameLogging;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GamePlay is a class displaying and handling Play functions
 *
 * @author      Martin Pribylina
 */
public class GamePlay extends AbstractGame implements ActionListener{

    private JButton menu;
    private JButton pause;
    private JButton error;
    private JButton playAgain;
    private final ActionListener parentListener;
    private FrameBasedGameLoop gameLoop;
    private GameController gameController;
    private MazePresenter presenter;

    private JLabel score, steps, lives;

    /**
     *
     * @param parentListener used for handling button actions outside this class in MainFrame
     * @param filePath Maze Map File Path
     */
    public GamePlay(ActionListener parentListener, String filePath){
        this.parentListener = parentListener;

        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        SetupHeader();

        SetupSideBar();

        MazeFileReaderResult result = MazeFileReader.ConfigureMaze(filePath, null);
        MazeConfigure mazeConfigure = result.getMazeConfigure();

        if (result.getErrorCode() != 0 || (mazeConfigure != null && !mazeConfigure.isReadingSuccess()))
        {
            ShowError(result);
            return;
        }

        CommonMaze maze = mazeConfigure.createMaze();

        presenter = new MazePresenter(maze, this);
        this.add(presenter, BorderLayout.CENTER);

        GameLogging gameLogging = new GameLogging(filePath);

        gameController = new GameController(maze, gameLogging, this);
        gameController.setLives(lives);
        gameController.setScore(score);
        gameController.setSteps(steps);

        gameLoop = new FrameBasedGameLoop(gameController);

        SetupKeyActions();

        gameLoop.run();
    }

    private void ShowError(MazeFileReaderResult result){
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.PAGE_AXIS));
        errorPanel.setBackground(Color.BLACK);
        this.add(errorPanel, BorderLayout.CENTER);

        JLabel errorLabel = ElementCreator.CreateDefaultLabel("Error: " + result.getErrorMessage());
        errorPanel.setAlignmentX(CENTER_ALIGNMENT);
        errorPanel.add(errorLabel);

        JPanel wrap = new JPanel();
        wrap.setBackground(Color.BLACK);
        error = ElementCreator.CreateDefaultButton("OK", 100, 50, parentListener);
        error.setAlignmentX(CENTER_ALIGNMENT);
        wrap.add(error);
        errorPanel.add(wrap);
    }

    public void SetupHeader(){
        JPanel header = new JPanel();
        header.setBackground(Color.BLACK);
        header.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.add(header, BorderLayout.NORTH);
        header.setPreferredSize(new Dimension(100, 50));
        header.setLayout(new BorderLayout());

        score = ElementCreator.CreateDefaultLabel("Score: 0");
        score.setHorizontalTextPosition(JLabel.CENTER); // Horizontal text possition
        score.setVerticalAlignment(JLabel.CENTER);
        score.setHorizontalAlignment(JLabel.CENTER);
        header.add(score, BorderLayout.CENTER);

        steps = ElementCreator.CreateDefaultLabel("Steps: 0");
        header.add(steps, BorderLayout.EAST);

        lives = ElementCreator.CreateDefaultLabel("Lives: 0");
        header.add(lives, BorderLayout.WEST);
    }

    public void SetupSideBar(){
        JPanel sideBar = new JPanel();
        sideBar.setBackground(Color.BLACK);
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.PAGE_AXIS));
        this.add(sideBar, BorderLayout.WEST);

        menu = ElementCreator.CreateDefaultButton("Menu", 100, 50, parentListener);
        pause = ElementCreator.CreateDefaultButton("Pause", 100, 50, this);
        sideBar.add(menu);
        sideBar.add(pause);
    }

    public void SetupKeyActions(){
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('w'), "upAction");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "upAction");
        this.getActionMap().put("upAction", gameLoop.getPlayerActions().getUpAction());

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('s'), "downAction");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        this.getActionMap().put("downAction", gameLoop.getPlayerActions().getDownAction());

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('a'), "leftAction");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        this.getActionMap().put("leftAction", gameLoop.getPlayerActions().getLeftAction());

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('d'), "rightAction");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        this.getActionMap().put("rightAction", gameLoop.getPlayerActions().getRightAction());
    }

    @Override
    public void endGame(boolean isVictory) {

        JPanel endGame = new JPanel();
        endGame.setLayout(new BoxLayout(endGame, BoxLayout.PAGE_AXIS));
        endGame.setBackground(Color.BLACK);
        this.add(endGame, BorderLayout.SOUTH);

        JLabel errorLabel = ElementCreator.CreateDefaultLabel(isVictory ? "You won!" : "You lost!");
        errorLabel.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
        errorLabel.setAlignmentX(CENTER_ALIGNMENT);
        errorLabel.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        endGame.add(errorLabel);

        JPanel wrap = new JPanel();
        wrap.setBackground(Color.BLACK);
        playAgain = ElementCreator.CreateDefaultButton("Play Again!", 250, 50, parentListener);
        playAgain.setAlignmentX(CENTER_ALIGNMENT);
        wrap.add(playAgain);
        wrap.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        endGame.add(wrap);

        pause.setEnabled(false);
    }

    public JButton getMenu() {
        return menu;
    }

    public JButton getError() {
        return error;
    }
    public GameLoop getGameLoop() { return gameLoop; }

    public JButton getPlayAgain() {
        return playAgain;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pause){
            if (gameLoop.isGameRunning()){
                gameLoop.stop();
            }else{
                gameLoop.run();
            }
        }
    }

    @Override
    public void setPacmanGoalDestinationClick(CommonField field) {
        if (!field.canMove()) {
            return;
        }
        gameController.setPacmanGoalDestination(field);
    }
}
