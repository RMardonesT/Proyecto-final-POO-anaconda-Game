package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Stats {
    public static String name;
    public static int score;

    public static int bestScores[];
    public Stats(String name){
        this.name = name;
        score = 0;
        bestScores = new int[]{0, 0, 0, 0};
    }


    //actualiza score actual.
    public void updateScore(int score){
        this.score = score;

    }

    public void updateScoreArray(){
        bestScores[0] = score;
        Arrays.sort(bestScores);
    }
    class Score{
        String pos;
        int puntaje;
        public Score(int puntaje, String pos){
            this.pos = pos;
            this.puntaje = puntaje;
        }
    }
}
