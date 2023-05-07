/*************************
 * Authors: Martin Pribylina
 *
 * Class for a field, that represents a path, objects can be placed on it
 ************************/
package src.game.objects;


import src.common.AbstractObservableField;
import src.common.CommonField;
import src.common.CommonMazeObject;

import java.util.ArrayList;
import java.util.List;

/**
 * PathField is class for a field, that represents a path, objects can be placed on it
 *
 * @author      Martin Pribylina
 */
public class PathField extends AbstractObservableField implements CommonField {
    private final int row;
    private final int col;

    private boolean target = false;
    public PathField(int row, int col, Maze maze)
    {
        this.row = row;
        this.col = col;
        this.maze = maze;
    }
    private final List<Observer> observers = new ArrayList<>();
    private final Maze maze;
    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public boolean contains(CommonMazeObject commonMazeObject) {
        return observers.contains(commonMazeObject);
    }

    @Override
    public CommonMazeObject get() {

        for (Observer observer :
                observers) {
            if(observer instanceof CommonMazeObject)
            {
                return (CommonMazeObject) observer;
            }
        }
        return null;
    }

    @Override
    public List<CommonMazeObject> getAll() {
        List<CommonMazeObject> commonMazeObjects = new ArrayList<>();

        for (Observer observer :
                observers) {
            if(observer instanceof Key)
            {
                commonMazeObjects.add ((CommonMazeObject) observer);
            }
        }

        for (Observer observer :
                observers) {
            if(observer instanceof Ghost)
            {
                commonMazeObjects.add ((CommonMazeObject) observer);
            }
        }

        for (Observer observer :
                observers) {
            if(observer instanceof Pacman)
            {
                commonMazeObjects.add ((CommonMazeObject) observer);
            }
        }
        return commonMazeObjects;
    }

    public Pacman getPacman() {

        for (Observer observer :
                observers) {
            if(observer instanceof Pacman)
            {
                return (Pacman) observer;
            }
        }
        return null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean isEmpty() {
        return get() == null;
    }

    @Override
    public CommonField nextField(Direction dirs) {
        return switch (dirs) {
            case DOWN -> maze.getField(row + 1, col);
            case LEFT -> maze.getField(row, col - 1);
            case RIGHT -> maze.getField(row, col + 1);
            case UP -> maze.getField(row - 1, col);
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==this) return true;
        if (obj==null || obj.getClass()!=this.getClass()) return false;
        return ((PathField) obj).row == row && ((PathField) obj).col == col;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++){
            if (observers.get(i) != null)
                observers.get(i).update(this);
        }
    }

    public void setTarget(boolean target) {
        this.target = target;
    }

    public boolean isTarget() {
        return target;
    }
}
