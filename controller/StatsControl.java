package controller;

import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import src.Stats;

public class StatsControl {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public Label powerLabel;

    @FXML
    private Text levelText;

    @FXML
    private Text PlayerText;

    @FXML
    private Text ScoreText;

    @FXML
    private Text FirstScore;

    @FXML
    private Text SecondScore;

    @FXML
    private Text ThirdScore;

    Stats stats;
    public void setStats(Stats stats){
        this.stats = stats;
    }
    public void updateName(){
        this.PlayerText.setText(stats.name);
    }
    public void updateScores(){
        this.ScoreText.setText(String.valueOf(stats.score));
    }

    public void updateLevel(int level){levelText.setText(String.valueOf(level));}

    public void updateRanking(){
        this.FirstScore.setText(String.valueOf(stats.bestScores[3]));
        this.SecondScore.setText(String.valueOf(stats.bestScores[2]));
        this.ThirdScore.setText(String.valueOf(stats.bestScores[1]));
    }
    @FXML
    void initialize() {
        assert PlayerText != null : "fx:id=\"PlayerText\" was not injected: check your FXML file 'sample.fxml'.";
        assert ScoreText != null : "fx:id=\"ScoreText\" was not injected: check your FXML file 'sample.fxml'.";
        assert levelText != null : "fx:id=\"levelText\" was not injected: check your FXML file 'sample.fxml'.";
        assert FirstScore != null : "fx:id=\"FirstScore\" was not injected: check your FXML file 'sample.fxml'.";
        assert SecondScore != null : "fx:id=\"SecondScore\" was not injected: check your FXML file 'sample.fxml'.";
        assert ThirdScore != null : "fx:id=\"ThirdScore\" was not injected: check your FXML file 'sample.fxml'.";
        assert powerLabel != null : "fx:id=\"powerLabel\" was not injected: check your FXML file 'sample.fxml'.";
    }
}
