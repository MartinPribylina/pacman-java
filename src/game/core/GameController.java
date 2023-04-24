package src.game.core;

import src.common.CommonField;
import src.common.CommonMaze;
import src.common.CommonMazeObject;
import src.game.save.GameLogging;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class GameController {

    private final CommonMaze maze;
    private CommonField.Direction pacmanDirection;
    private CommonField.Direction pacmanFutureDirection;
    private GameLogging gameLogging;
    private JLabel steps, lives, score;
    int scoreCounter = 0;

    public GameController(CommonMaze _maze, GameLogging gameLogging)
    {
        maze = _maze;
        this.gameLogging = gameLogging;
        gameLogging.setMaze(_maze);
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

    public void Update(){
        MovePlayer();
        MoveGhosts();
        UpdateGameStats();
        gameLogging.frameTick();
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
        if (pacmanDirection == null) return;
        CommonMazeObject pacman = maze.pacman();
        if (pacman.canMove(pacmanDirection)){
            pacman.move(pacmanDirection);
        }else if(pacmanFutureDirection != null && pacmanDirection != pacmanFutureDirection){
            if(pacman.canMove(pacmanFutureDirection)){
                pacmanDirection = pacmanFutureDirection;
                pacman.move(pacmanDirection);
            }
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
                ghost.move(RandomDirection(ghost));
            }
        }
    }

    private CommonField.Direction RandomDirection(CommonMazeObject object){
        Random rand = new Random();
        int r = rand.nextInt(4);
        switch (r){
            case 0 -> {
                if (object.canMove(CommonField.Direction.UP))
                    return CommonField.Direction.UP;
                else return RandomDirection(object);
            }
            case 1 -> {
                if (object.canMove(CommonField.Direction.DOWN))
                    return CommonField.Direction.DOWN;
                else return RandomDirection(object);
            }
            case 2 -> {
                if (object.canMove(CommonField.Direction.LEFT))
                    return CommonField.Direction.LEFT;
                else return RandomDirection(object);
            }
            case 3 -> {
                if (object.canMove(CommonField.Direction.RIGHT))
                    return CommonField.Direction.RIGHT;
                else return RandomDirection(object);
            }
            default -> throw new IllegalStateException("Unexpected value: " + r);
        }
    }

    public void saveIntoFile(){
        System.out.println("Writing into file");

        String replayPath = System.getProperty("user.dir") + "\\data\\replay";

        try {
            Files.createDirectories(Paths.get(replayPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("-yyyyMMdd-HHmm");
        LocalDateTime now = LocalDateTime.now();
        try(FileOutputStream fs = new FileOutputStream(replayPath + "\\Replay" + dtf.format(now) + ".txt")) {
            ObjectOutputStream os = new ObjectOutputStream(fs);
            try {
                os.writeObject(gameLogging);
            }catch (Exception e){
                System.out.println("Failed to save");
            }
            os.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
}
