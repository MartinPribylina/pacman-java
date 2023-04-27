package src.main.java.src.game.save;

import java.io.*;

public class StatsSaveManager {
    private static final String dataFilePath =  System.getProperty("user.dir") + "\\data\\Stats.bin";
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

    public static StatsData LoadStats(){
        try
        {
            FileInputStream fileInputStream = new FileInputStream(dataFilePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return  (StatsData) objectInputStream.readObject();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
            // File probably doesn't exist yet, return empty StatsData
        }
        return new StatsData(0,0, 0, 0, 0);
    }
}
