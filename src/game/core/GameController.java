package src.game.core;

import src.common.CommonField;
import src.common.CommonMaze;
import src.common.CommonMazeObject;
import src.game.objects.Pacman;
import src.game.objects.PathField;
import src.game.save.GameLogging;
import src.game.utility.LogicHelper;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController implements Serializable {

    private final CommonMaze maze;
    private CommonField.Direction pacmanDirection;
    private CommonField.Direction pacmanFutureDirection;
    private GameLogging gameLogging;
    private JLabel steps, lives, score;
    int scoreCounter = 0;
    private CommonField pacmanGoalDestination;

    public GameController(CommonMaze _maze, GameLogging gameLogging)
    {
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

    public List<CommonField.Direction> FindShortestPath(){
        if (pacmanGoalDestination == null)
            return null;
        if (!pacmanGoalDestination.canMove())
            return null;

        Pacman pacman = (Pacman)maze.pacman();
        PathField pacmanField = (PathField)pacman.getField();

        PathField goalField = (PathField) pacmanGoalDestination;
        PathField currentField = (PathField) pacmanField;

        if (pacmanGoalDestination == currentField)
            return null;

        List<List<CommonField.Direction>> paths = new ArrayList<>();

        for (CommonField.Direction dir : CommonField.Direction.values()) {
            CommonField nextField = currentField.nextField(dir);
            if (nextField == null || !nextField.canMove()){
                continue;
            }
            List<CommonField.Direction> path = new ArrayList<>();
            path.add(dir);
            if (nextField == goalField) {
                return path;
            }
            paths.add(path);
        }

        while (true) {

            List<List<CommonField.Direction>> newPaths = new ArrayList<>();

            for (int i = 0; i < paths.size(); i++) {

                for (CommonField.Direction dir : CommonField.Direction.values()) {
                    var path = paths.get(i);
                    currentField = pacmanField;
                    for(int j = 0; j < path.size(); j++){
                        currentField = (PathField) currentField.nextField(path.get(j));
                    }
                    CommonField nextField = currentField.nextField(dir);

                    if (nextField == null || !nextField.canMove() || path.get(path.size() - 1).Reverse() == dir) {
                        continue;
                    }
                    var newPath = new ArrayList<>(path);
                    newPath.add(dir);

                    if (nextField == goalField) {
                        return newPath;
                    }

                    if (!newPaths.contains(newPath))
                        newPaths.add(newPath);
                }
            }

            paths = newPaths;
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
        if (pacmanGoalDestination != null) {
            var shortestPath = FindShortestPath();
            if (shortestPath != null)
            {
                CommonField.Direction next = FindShortestPath().get(0);
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
                ghost.move(LogicHelper.RandomDirection(ghost));
            }
        }
    }

    //Presunút tak aby ukladal Game Logger samého seba a volalo sa to pri ukončení hry
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
                e.printStackTrace();
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
