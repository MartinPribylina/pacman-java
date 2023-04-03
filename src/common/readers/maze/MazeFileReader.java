package src.common.readers.maze;

import src.game.MazeConfigure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MazeFileReader {

    public static MazeFileReaderResult ConfigureMaze(String filePath){
        MazeConfigure mazeConfigure = new MazeConfigure();

        boolean firstLine = true;
        int colsCount = 0;
        int rowsCount = 0;

        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(firstLine)
                {
                    colsCount = data.length();
                    firstLine = false;
                }
                //Check lineLength is same for every row
                if (colsCount != data.length())
                {
                    return new MazeFileReaderResult(4, "File row lengths do not match");
                }
                rowsCount++;
            }
            myReader.close();

            if(rowsCount <= 0 || colsCount <= 0){
                return new MazeFileReaderResult(3, "File is empty");
            }

            mazeConfigure.startReading(rowsCount, colsCount);

            myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
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
            System.out.println("An error occurred.");
            e.printStackTrace();
            return new MazeFileReaderResult(1, e.getMessage());
        }catch (Exception e){
            return new MazeFileReaderResult(2, e.getMessage());
        }

        return new MazeFileReaderResult(mazeConfigure);
    }
}
