/*************************
 * Authors: Martin Pribylina
 *
 * Class that reads and saves stats data into a file
 ************************/
package src.game.save;

import java.io.*;

/**
 * StatsSaveManager is class for saving and loading stats data
 *
 * @author      Martin Pribylina
 */
public class StatsSaveManager {
    private static final String dataFilePath =  System.getProperty("user.dir") + "\\data\\Stats.bin";

    /**
     * Saves data
     * @param data Stats data to save
     */
    public static void SaveStats(StatsData data){
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(dataFilePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(data);
            objectOutputStream.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    /**
     * Loads stats data
     * @return loaded data
     */
    public static StatsData LoadStats(){
        try
        {
            FileInputStream fileInputStream = new FileInputStream(dataFilePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            StatsData statsData = (StatsData) objectInputStream.readObject();
            return  statsData;
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
            // File probably doesn't exist yet, return empty StatsData
        }
        return new StatsData(0,0, 0, 0, 0);
    }
}
