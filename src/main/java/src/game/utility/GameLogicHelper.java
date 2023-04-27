package src.main.java.src.game.utility;

import src.main.java.src.common.CommonField;
import src.main.java.src.common.CommonMazeObject;
import src.main.java.src.game.objects.PathField;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogicHelper {

    public static List<CommonField.Direction> FindShortestPath(CommonField currentField, CommonField goalField){
        if (goalField == null)
            return null;
        if (!goalField.canMove())
            return null;

        PathField goalPathField = (PathField) goalField;
        PathField currentPathField = (PathField) currentField;

        if (goalPathField == currentPathField)
            return null;

        List<List<CommonField.Direction>> paths = new ArrayList<>();

        for (CommonField.Direction dir : CommonField.Direction.values()) {
            CommonField nextField = currentPathField.nextField(dir);
            if (nextField == null || !nextField.canMove()){
                continue;
            }
            List<CommonField.Direction> path = new ArrayList<>();
            path.add(dir);
            if (nextField == goalPathField) {
                return path;
            }
            paths.add(path);
        }

        while (true) {

            List<List<CommonField.Direction>> newPaths = new ArrayList<>();

            for (int i = 0; i < paths.size(); i++) {

                for (CommonField.Direction dir : CommonField.Direction.values()) {
                    var path = paths.get(i);
                    currentPathField = (PathField) currentField;
                    for(int j = 0; j < path.size(); j++){
                        currentPathField = (PathField) currentPathField.nextField(path.get(j));
                    }
                    CommonField nextField = currentPathField.nextField(dir);

                    if (nextField == null || !nextField.canMove() || path.get(path.size() - 1).Reverse() == dir) {
                        continue;
                    }
                    var newPath = new ArrayList<>(path);
                    newPath.add(dir);

                    if (nextField == goalPathField) {
                        return newPath;
                    }

                    if (!newPaths.contains(newPath))
                        newPaths.add(newPath);
                }
            }

            paths = newPaths;
        }
    }
    public static CommonField.Direction RandomDirection(CommonMazeObject object){
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
}
