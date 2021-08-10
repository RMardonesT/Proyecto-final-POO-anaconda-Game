package src;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import controller.*;

import java.io.IOException;

public class Simulator {

    private int level1 = 200;
    private int level2 = 400;
    private int level3 = 9999999;

    public boolean dir_flag = false;
    public Timeline animation;
    public boolean pause_flag = false;
    public Area area;
    public Stage primaryStage, description;
    private Scene scene, sceneLvl;
    private double simulationSamplingTime;
    private double simulationTime;  // it goes along with real time, faster or slower than real time
    private double delta_t;   // precision of discrete simulation time
    public Stats stats;
    public StatsControl statsControl;
    static Media background = new Media(Simulator.class.getResource("audio/gametheme.wav").toExternalForm());
    MediaPlayer backPlayer = new MediaPlayer(background);

    public Simulator (double framePerSecond, double simulationTime2realTimeRate, Area area, Stage primaryStage, Stats stats, StatsControl statsControl) throws IOException {
        this.primaryStage = primaryStage;
        this.scene = primaryStage.getScene();
        this.area = area;
        double viewRefreshPeriod =  1 /  framePerSecond; // in [ms] real time used to display
        this.stats = stats;
        this.statsControl = statsControl;
        // a new view on application
        simulationSamplingTime = viewRefreshPeriod*simulationTime2realTimeRate/1000;
        delta_t = 0.2;  //delta_t
        simulationTime = 0;




        //FXMLLoader loadLvl3 = new FXMLLoader(getClass().getClassLoader().getResource("controller/lvl1.fxml"));
        //Parent rootLvl3 = loadLvl3.load();





        animation = new Timeline(new KeyFrame(Duration.millis(viewRefreshPeriod*1000), e-> {
            try {
                takeAction();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }));  //arregalr take action
        animation.setCycleCount(Timeline.INDEFINITE);










        double nextStop=simulationTime+simulationSamplingTime;

        this.scene.setOnKeyPressed(event -> {
            try {
                keyHandle(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });  //iniciar simulacion

    }


    public void takeAction() throws IOException {


        this.scene.setOnKeyPressed(event -> {
            try {
                keyHandle(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //animation.play();
        statsControl.setStats(stats);
        statsControl.updateName();
        backPlayer.setCycleCount(backPlayer.INDEFINITE);
        backPlayer.setVolume(0.5);
        backPlayer.play();



        double nextStop=simulationTime+simulationSamplingTime;
        for( ; simulationTime<nextStop; simulationTime+=delta_t) {
            dir_flag = false;
            statsControl.powerLabel.setText(String.valueOf(area.laserCounter));
            //if (area.scoreCounter == 0 && area.level == 1) animation.play();
            if (stats.score <= 100 && area.level == 1) statsControl.updateLevel(area.level);

            if (area.scoreCounter < level1 && area.level == 1) {


                this.area.moveAnaconda(this.area.getAnacDir());
                this.area.eatFood();
                stats.updateScore(this.area.scoreCounter); //acutaliza score de stats

                statsControl.updateScores(); //imprime scores de stats en interfaz


                if (this.area.getGameStatus()) {
                    stats.updateScoreArray();
                    statsControl.updateRanking();
                    animation.stop();
                }
                this.area.updateView();
            }
            //ACTUALIZACION DE NIVEL A 2
            if (stats.score == level1 && area.level == 1) {
                this.area.updateLevel();


                area.scoreCounter += 50;   //bonus por pasar de nivel
                stats.updateScore(area.scoreCounter);
                statsControl.updateScores();
                statsControl.updateLevel(area.level);
                animation.stop();

                FXMLLoader loadLvl2 = new FXMLLoader(getClass().getClassLoader().getResource("controller/Level2.fxml"));
                Parent rootLvl2 = loadLvl2.load();
                sceneLvl = new Scene(rootLvl2);

                sceneLvl.setOnKeyPressed(event -> {
                    try {
                        keyHandle(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                description = new Stage();
                description.initStyle(StageStyle.UNDECORATED);
                description.setScene(sceneLvl);
                description.show();






            }

            //COMIENZA NIVEL 2
            if (area.level == 2){
                this.area.moveAnaconda(this.area.getAnacDir());
                this.area.eatFood();

                stats.updateScore(this.area.scoreCounter); //acutaliza score de stats

                statsControl.updateScores(); //imprime scores de stats en interfaz

                this.area.scoreCounter--;
                if(area.scoreCounter <= 0){
                    area.GameOver = true;
                }
                if (this.area.getGameStatus()) {
                    stats.updateScoreArray();
                    statsControl.updateRanking();
                    animation.stop();
                }
                this.area.updateView();



                stats.updateScore(area.scoreCounter);
                statsControl.updateScores();

            }
            //ACTUALIZACION DE NIVEL A 3
            if (stats.score >= level2 && area.level == 2) {
                this.area.updateLevel();
                area.scoreCounter += 50;   //bonus por pasar de nivel
                stats.updateScore(area.scoreCounter);
                statsControl.updateScores();
                statsControl.updateLevel(area.level);
                area.createFL3();

                animation.stop();

                FXMLLoader loadLvl3 = new FXMLLoader(getClass().getClassLoader().getResource("controller/Level3.fxml"));
                Parent rootLvl3 = loadLvl3.load();

                sceneLvl = new Scene(rootLvl3);
                sceneLvl.setOnKeyPressed(event -> {
                    try {
                        keyHandle(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                description.setScene(sceneLvl);
                description.show();
            }

            //COMIENZA NIVEL 3
            if (area.level == 3){
                this.area.moveAnaconda(this.area.getAnacDir());
                this.area.eatFood();

                stats.updateScore(this.area.scoreCounter); //acutaliza score de stats
                statsControl.updateScores(); //imprime scores de stats en interfaz

                this.area.scoreCounter--;
                if(area.scoreCounter <= 0){
                    area.GameOver = true;
                }
                if (this.area.getGameStatus()) {
                    stats.updateScoreArray();
                    statsControl.updateRanking();
                    animation.stop();
                }
                this.area.updateView();

                if (area.laserON) {area.moveLaser(); this.area.laserEat();}

                stats.updateScore(area.scoreCounter);
                statsControl.updateScores();
                if(area.lvl3Counter == 7 && area.ronda < 3){
                    area.createFL3();
                    area.ronda++;
                    area.scoreCounter+=800;
                    stats.updateScore(this.area.scoreCounter); //acutaliza score de stats
                    statsControl.updateScores(); //imprime scores de stats en interfaz
                    area.lvl3Counter = 0;
                }
                if(area.ronda == 3 && area.lvl3Counter == 7){
                    FXMLLoader loaderEND = new FXMLLoader(getClass().getClassLoader().getResource("controller/endGame.fxml"));
                    if(!pause_flag){
                        area.scoreCounter += 1200;
                        stats.updateScore(area.scoreCounter);
                        stats.updateScoreArray();
                        statsControl.updateRanking();
                        statsControl.updateScores();

                        area.EndGamePlayer.seek(Duration.ZERO);
                        area.EndGamePlayer.setVolume(0.5);
                        area.EndGamePlayer.play();

                        animation.stop();
                        pause_flag = true;
                        Stage stage = loaderEND.load();
                        stage.initStyle(StageStyle.UNDECORATED);
                        GOverControl goverControl = loaderEND.getController();
                        goverControl.oli(this);
                        stage.show();
                    }
                }

            }
            if (area.getGameStatus()) {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("controller/gameOver.fxml"));
                if(!pause_flag){
                    animation.stop();
                    pause_flag = true;
                    Stage stage = loader.load();
                    stage.initStyle(StageStyle.UNDECORATED);
                    GOverControl goverControl = loader.getController();
                    goverControl.oli(this);
                    stage.show();
                }
            }
        }
    }

    private void keyHandle (KeyEvent e) throws IOException {

        if (e.getCode() == KeyCode.TAB && !area.laserON && area.level==3) {
            area.createLaser();

        }


        if (e.getCode() == KeyCode.SPACE) {

            if (area.level == 1) area.restart();
            animation.play(); //nice

            if (area.level > 1) {
                description.close();
            }

        }

        if (e.getCode() == KeyCode.LEFT && dir_flag==false) {
            this.area.setAnacDir(2);
            dir_flag = true;
        }
        else if (e.getCode() == KeyCode.RIGHT && dir_flag==false){
            this.area.setAnacDir(0);
            dir_flag = true;


        }
        else if (e.getCode() == KeyCode.UP && dir_flag==false){
            this.area.setAnacDir(1);
            dir_flag = true;
        }
        else if (e.getCode() == KeyCode.DOWN && dir_flag==false){
            this.area.setAnacDir(3);
            dir_flag = true;
        }


//        if (e.getCode() == KeyCode.ENTER){
//            Stage stage = new Stage();
//            stage.show();
//        }

        if (e.getCode() == KeyCode.ESCAPE){
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("controller/esc_menu.fxml"));
            if(!pause_flag){
                animation.stop();
                pause_flag = true;
                Stage stage = loader.load();
                stage.initStyle(StageStyle.UNDECORATED);
                PauseControl pause_controler = loader.getController();
                pause_controler.oli(this);
                pause_controler.SCORE_LABEL.setText("SCORE: " + stats.score);
                stage.show();
            }
        }
    }
}
