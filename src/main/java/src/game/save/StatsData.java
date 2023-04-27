package src.main.java.src.game.save;

import java.io.Serializable;

public class StatsData implements Serializable {
    public int timesPlayed;
    public int timesWon;
    public int stepsTaken;
    public int maxScore;
    public int livesLost;

    public StatsData(int timesPlayed, int timesWon, int stepsTaken, int maxScore, int livesLost) {
        this.timesPlayed = timesPlayed;
        this.timesWon = timesWon;
        this.stepsTaken = stepsTaken;
        this.maxScore = maxScore;
        this.livesLost = livesLost;
    }
}
