package src.game;


import src.common.AbstractObservableField;
import src.common.CommonField;
import src.common.CommonMazeObject;

public class WallField extends AbstractObservableField implements CommonField {
    private final int row;
    private final int col;
    private final Maze maze;
    public WallField(int row, int col, Maze maze)
    {
        this.row = row;
        this.col = col;
        this.maze = maze;
    }

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public boolean contains(CommonMazeObject commonMazeObject) {
        return false;
    }

    @Override
    public CommonMazeObject get() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public CommonField nextField(Direction dirs) {
        return switch (dirs) {
            case DOWN -> maze.getField(row - 1, col);
            case LEFT -> maze.getField(row, col - 1);
            case RIGHT -> maze.getField(row, col + 1);
            case UP -> maze.getField(row + 1, col);
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==this) return true;
        if (obj==null || obj.getClass()!=this.getClass()) return false;
        return ((WallField) obj).row == row && ((WallField) obj).col == col;
    }

    @Override
    public void addObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers() {

    }
}
