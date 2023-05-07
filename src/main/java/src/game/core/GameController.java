/*************************
 * Authors: Martin Pribylina, Samuel Gall
 *
 * Updates game status based on player input
 ************************/
package src.game.core;

import src.common.CommonField;
import src.common.CommonMaze;
import src.common.CommonMazeObject;
import src.game.objects.Pacman;
import src.game.save.GameLogging;
import src.game.save.StatsData;
import src.game.save.StatsSaveManager;
import src.game.utility.GameLogicHelper;
import src.gui.IGame;

import javax.swing.*;
import java.util.List;

/**
 * GameController is class that handles ghost logic and player's inputs, updates game each frame
 *
 * @author      Martin Pribylina
 */
public class GameController {

    private final CommonMaze maze;
    private CommonField.Direction pacmanDirection;
    private CommonField.Direction pacmanFutureDirection;
    private GameLogging gameLogging;
    private GameLoop gameLoop;
    private IGame game;
    private JLabel steps, lives, score;
    int scoreCounter = 0;
    private CommonField pacmanGoalDestination;

    public GameController(CommonMaze _maze, GameLogging gameLogging, IGame game)
    {
        this.game = game;
        maze = _maze;
        this.gameLogging = gameLogging;
        gameLogging.setMaze(_maze);
    }

    public void ChangePacmanDirectionKeyboardInput(CommonField.Direction direction){
        ChangePacmanDirection(direction);
        pacmanGoalDestination = null;
    }

    public void ChangePacmanDirection(CommonField.Direction direction){
        CommonMazeObject pacman = maze.pacman();
        if (pacman.canMove(direction)){
            pacmanDirection = direction;
            pacmanFutureDirection = null;
        }else{
            pacmanFutureDirection = direction;
        }
    }

    public void setPacmanGoalDestination(CommonField field){
        pacmanGoalDestination = field;
    }

    public void Update(){
        MovePlayer();
        MoveGhosts();
        UpdateGameStats();
        CheckGameEnd();
        gameLogging.frameTick();
        gameLogging.pacmanMove(pacmanDirection);
    }

    private void CheckGameEnd(){
        Pacman pacman = (Pacman) maze.pacman();

        boolean endGame = false;
        boolean isVictory = false;

        if (pacman.getLives() <= 0){
            endGame = true;
        }

        if (pacman.isWon()){
            endGame = true;
            isVictory = true;
        }

        if (endGame){
            game.endGame(isVictory);
            gameLoop.stop();


            StatsData stats = StatsSaveManager.LoadStats();
            stats.livesLost += 3 - pacman.getLives();
            stats.stepsTaken += pacman.getStepCounter();
            stats.timesPlayed++;
            if (isVictory){
                stats.timesWon++;
            }
            if (stats.maxScore < scoreCounter)
            {
                stats.maxScore = scoreCounter;
            }

            StatsSaveManager.SaveStats(stats);
        }
    }

    private void UpdateGameStats() {
        CommonMazeObject pacman = maze.pacman();
        int pacmanLives = pacman.getLives();
        scoreCounter += pacmanLives;
        lives.setText("Lives: " + pacmanLives);
        steps.setText("Steps: " + pacman.getStepCounter());
        score.setText("Score: " + scoreCounter);
    }

    private void MovePlayer(){
        if (pacmanGoalDestination != null) {
            var shortestPath = GameLogicHelper.FindShortestPath(maze.pacman().getField(), pacmanGoalDestination);
            if (shortestPath != null)
            {
                CommonField.Direction next = shortestPath.get(0);
                if (next != null)
                    ChangePacmanDirection(next);
            }else{
                pacmanDirection = null;
            }
        }
        if (pacmanDirection == null) return;
        CommonMazeObject pacman = maze.pacman();
        if (pacman.canMove(pacmanDirection)){
            pacman.move(pacmanDirection);
        }else if(pacmanFutureDirection != null && pacmanDirection != pacmanFutureDirection && pacman.canMove(pacmanFutureDirection)){
            pacmanDirection = pacmanFutureDirection;
            pacman.move(pacmanDirection);
        }else {
            pacmanDirection = null;
        }
    }

    private void MoveGhosts(){
        List<CommonMazeObject> ghosts = maze.ghosts();
        for (CommonMazeObject ghost :
                ghosts) {
            if(ghost.lastMove() != null && ghost.canMove(ghost.lastMove()))
            {
                ghost.move(ghost.lastMove());
            }else{
                ghost.move(GameLogicHelper.RandomDirection(ghost));
            }
        }
    }

    public void setSteps(JLabel steps) {
        this.steps = steps;
    }

    public void setLives(JLabel lives) {
        this.lives = lives;
    }

    public void setScore(JLabel score) {
        this.score = score;
    }

    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public GameLogging getGameLogging() {
        return gameLogging;
    }
}
