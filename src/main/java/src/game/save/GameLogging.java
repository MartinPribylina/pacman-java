package src.main.java.src.game.save;

import src.main.java.src.common.CommonField;
import src.main.java.src.common.CommonMaze;
import src.main.java.src.common.CommonMazeObject;
import src.main.java.src.game.objects.Ghost;
import src.main.java.src.game.objects.PathField;
import src.main.java.src.game.replay.GhostData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GameLogging implements Serializable {
    private String filepath;
    private CommonMaze maze;
    private int time;
    private List<CommonField.Direction> pacmanPath = new ArrayList<>();
    private Dictionary<PathField, GhostData> ghostsData = new Hashtable<>();

    public GameLogging(String filepath){
        this.filepath  = filepath;
        this.time = 0;
    }

    public void frameTick() {
        ghostsMove();
        this.time++;
    }

    public void pacmanMove(CommonField.Direction dir){
            pacmanPath.add(dir);
    }
    public void ghostsMove(){
        for (CommonMazeObject CMOGhost : maze.ghosts())
        {
            Ghost ghost = (Ghost) CMOGhost;
            PathField startPos = (PathField) ghost.start;
            if (ghostsData.get(startPos) == null) {
                ghostsData.put((PathField) ghost.start, new GhostData(ghost.ghostType(), startPos.getRow(), startPos.getCol()));
            }
            ghostsData.get(ghost.start).path.add(ghost.lastMove());
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
        try {
            File file = new File(replayPath + "\\Replay" + dtf.format(now) + ".txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter fileWriter = new FileWriter(file.getAbsolutePath());

            File mazeFile = new File(filepath);
            Scanner fileReader = new Scanner(mazeFile);
            while (fileReader.hasNextLine()) {
                fileWriter.write(fileReader.nextLine() + '\n');
            }
            fileReader.close();
            fileWriter.write("*\n");

            fileWriter.write(filepath + '\n');
            fileWriter.write(String.valueOf(time) + '\n');
            fileWriter.write(pacmanPath.toString() + '\n');

            for (CommonMazeObject CMOGhost : maze.ghosts()){
                Ghost ghost = (Ghost) CMOGhost;
                PathField startField = (PathField) ghost.start;
                fileWriter.write(ghost.ghostType() + ",");
                fileWriter.write("X" + startField.getRow() + "Y" + startField.getCol() + ",");
                fileWriter.write(ghostsData.get(startField).path.toString() + '\n');
            }

            fileWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Fail in writing to file.");
            e.printStackTrace();
        }
    }

    public CommonMaze getMaze() {
        return maze;
    }

    public void setMaze(CommonMaze maze) {
        this.maze = maze;
    }

    public int getTime() {
        return time;
    }

    public List<CommonField.Direction> getPacmanPath() {
        return pacmanPath;
    }

    public String getFilepath() {
        return filepath;
    }

    public Dictionary<PathField, GhostData> getGhostsData() {
        return ghostsData;
    }
}
