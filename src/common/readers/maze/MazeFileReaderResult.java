/*************************
 * Authors: Martin Pribylina
 *
 * Utility class for maze file reader
 ************************/
package src.common.readers.maze;

import src.game.MazeConfigure;

public class MazeFileReaderResult {
    private int errorCode = 0;
    private String errorMessage = "";
    private MazeConfigure mazeConfigure = null;

    public MazeFileReaderResult(MazeConfigure _mazeConfigure){
        mazeConfigure = _mazeConfigure;
    }

    public MazeFileReaderResult(int _result, String _errorMessage){
        errorCode = _result;
        errorMessage = _errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public MazeConfigure getMazeConfigure() {
        return mazeConfigure;
    }
}
