package src.main.java.src.common.readers.maze;

import src.main.java.src.game.MazeConfigure;

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
