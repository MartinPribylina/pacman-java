/*************************
 * Authors: Martin Pribylina
 *
 * CommonMaze interface
 ************************/
package src.common;

import java.util.List;

/**
 * CommonMaze is interface for maze
 *
 * @author      Martin Pribylina
 */
public interface CommonMaze {
    CommonField getField(int var1, int var2);

    int numRows();

    int numCols();

    List<CommonMazeObject> ghosts();

    CommonMazeObject pacman();
}

