/*************************
 * Authors: Martin Pribylina
 *
 * Reads maze from txt files
 ************************/
package src.common.readers.maze;

import src.game.MazeConfigure;
import src.game.replay.GhostData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;

/**
 * MazeFileReader is class which reads maze from txt files
 *
 * @author      Martin Pribylina
 */
public class MazeFileReader {

    public static MazeFileReaderResult ConfigureMaze(String filePath, List<GhostData> ghostsData){
        MazeConfigure mazeConfigure = null;
        if (ghostsData != null){
            mazeConfigure = new MazeConfigure(ghostsData);
        }else {
            mazeConfigure = new MazeConfigure();
        }

        int colsCount = 0;
        int rowsCount = 0;

        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);

            String data = myReader.nextLine();

            var firstLine = data.split(" ");

            rowsCount = Integer.parseInt(firstLine[0]);
            colsCount = Integer.parseInt(firstLine[1]);

            mazeConfigure.startReading(rowsCount, colsCount);


            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                boolean readSuccess = mazeConfigure.processLine(data);
                if (!readSuccess){
                    return new MazeFileReaderResult(4, "Map is corrupt");
                }
            }
            myReader.close();
            boolean readSuccess = mazeConfigure.stopReading();
            if (!readSuccess){
                return new MazeFileReaderResult(4, "Map is corrupt");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new MazeFileReaderResult(1, e.getMessage());
        }catch (Exception e){
            return new MazeFileReaderResult(2, e.getMessage());
        }

        return new MazeFileReaderResult(mazeConfigure);
    }
}
