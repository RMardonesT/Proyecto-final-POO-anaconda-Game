package src;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.util.concurrent.TimeUnit;
import controller.*;


//mediaplayer

public class Area {
    public int ronda;
    public int lvl3Counter;
    private ArrayList<Laser> lasers;
    public int level;
    public boolean GameOver = false;  //true-> lose, false = continue
    public  boolean laserON;
    private int anacDir = 0;
    private ArrayList<Eslabon> eslabones; //anaconda
    private Food food;
    private Laser laser;
    public int laserCounter;
    private ArrayList<Food>  foods;
    private AreaView areaView;
    private Rectangle2D territory;

    public int scoreCounter = 0;

    static Media error = new Media(Area.class.getResource("audio/error.wav").toExternalForm());
    static Media eat = new Media(Area.class.getResource("audio/eating.wav").toExternalForm());
    static Media over = new Media(Area.class.getResource("audio/gameover.wav").toExternalForm());
    static Media failLvl2 = new Media(Area.class.getResource("audio/fail_lvl2.wav").toExternalForm());
    static Media powerObtain = new Media(Area.class.getResource("audio/power2.wav").toExternalForm());
    static Media noBullet = new Media(Area.class.getResource("audio/noBullet.wav").toExternalForm());
    static Media shootPower = new Media(Area.class.getResource("audio/power1.wav").toExternalForm());
    static Media EndGame = new Media(Area.class.getResource("audio/EndGame.wav").toExternalForm());

    MediaPlayer overPlayer = new MediaPlayer(over);
    MediaPlayer  eatPlayer= new MediaPlayer(eat);
    MediaPlayer errorPlayer = new MediaPlayer(error);
    MediaPlayer failLvl2Player = new MediaPlayer(failLvl2);
    MediaPlayer powerObtainPlayer = new MediaPlayer(powerObtain);
    MediaPlayer noBulletPlayer = new MediaPlayer(noBullet);
    MediaPlayer shootPowerPlayer = new MediaPlayer(shootPower);
    MediaPlayer EndGamePlayer = new MediaPlayer(EndGame);


    //MediaPlayer mediaPlayer = new MediaPlayer(media);

    //CONSTRUCTOR DEL AREA
    public Area(){
        territory = new Rectangle2D(0, 0, 600, 600);
        areaView = new AreaView(this);
        food = new Food(this,0);
        eslabones = new ArrayList<Eslabon>();
        level = 1;
        ronda = 0;
        lvl3Counter = 0;
        laserON = false;
        laserCounter = 0;
        beginAnaconda();
        Eslabon head = new Eslabon(200,200, this);
        Eslabon tail = new Eslabon(this, 2, 0,175,200 );

        eslabones.add(head);
        eslabones.add(tail);

    }

    public double getWidth(){return territory.getWidth();}
    public double getHeight(){return territory.getWidth();}
    public Group getView(){return areaView;}
    public int getAnacDir(){return anacDir;}
    public boolean getGameStatus(){return GameOver;}  //true -> loser


    //actualiza el nivel del juego
    public void updateLevel(){

        if (level == 1) food.getFoodView().RemoveFood();

        level++;

        int ptje = scoreCounter;

        scoreCounter = ptje;

        //HAY UNA MANZANA FAKE Y UNA REAL, SE DESCUENTA PUNTAJE POR CADA INSTANTE QUE PASA
        if (level == 2){

            foods = new ArrayList<Food>();
            foods.add(new Food(this,0));
            foods.add(new Food(this, 0));
        }
        //SE GENERAN 8 MANZANAS UNA OTORGA UNA CARGA DE 3 LASERS DISPARABLES (ACUMULABLES HASTA 8)
        if (level == 3)
        {

            foods.get(0).getFoodView().RemoveFood();
            foods.get(1).getFoodView().RemoveFood();
            //createFL3();

        }

    }
    //Initialize Food at lvl 3
    public void createFL3(){
        for (int i=0; i < foods.size(); i++){
            foods.get(i).getFoodView().RemoveFood();
            foods.remove(i);
        }
        foods = new ArrayList<Food>();
        for (int i=0; i < 7; i++){
            Food comida = new Food(this, 0);
            foods.add(comida);
            while(verifyFoodPos()){
                foods.get(foods.indexOf(comida)).updatePos();
            }
        }
        Food poder = new Food(this, 1);
        foods.add(poder);
        while(verifyFoodPos()){
            foods.get(foods.indexOf(poder)).updatePos();
        }

    }

    public void createLaser(){
        if(laserCounter>0) {
            Eslabon head = eslabones.get(0);
            laser = new Laser(this, head.getDir(), head.getX(), head.getY());
            laserON = true;
            laserCounter--;
            shootPowerPlayer.seek(Duration.ZERO);
            shootPowerPlayer.setVolume(0.8);
            shootPowerPlayer.play();
        }
        else {
            noBulletPlayer.seek(Duration.ZERO);
            noBulletPlayer.setVolume(0.8);
            noBulletPlayer.play();
        }
    }

    public  void moveLaser(){
        laser.updateLaser();
    }


    //CREA LOS PRIMEROS DOS ESLABONES DE LA ANACONDA
    public void beginAnaconda(){
        Eslabon head = new Eslabon(200,200, this);
        Eslabon tail = new Eslabon(this, 2, 0,175,200 );

        eslabones.add(head);
        eslabones.add(tail);

    }

    //actualiza la dirección
    public void setAnacDir(int newDir){
        if (Math.abs(anacDir - newDir) != 2) anacDir = newDir;
        else {
            errorPlayer.seek(Duration.ZERO);
            errorPlayer.play();

        }
    }

    //Add eslabon
    public void updateAnaconda(){
        int index = eslabones.size()-1;
        Eslabon tail = eslabones.get(index);
        Eslabon newTail = new Eslabon(this, tail);
        //newTail.CopyEslab(tail);
        //newTail.setColor(Color.BLACK);
        eslabones.add(newTail);
}


    //Anaconda Moves
    public void moveAnaconda(int dir){
        Eslabon head = eslabones.get(0);
        int x,y;

        for (int i= eslabones.size() -1 ; i > 0; i--){
            eslabones.get(i).CopyEslab(eslabones.get(i-1));
        }
        switch (dir){

            case 0: {   x = head.getX() + 25 ; y = head.getY();
                        if (verifyBody(x,y)  && verifyBorders(x,y)) eslabones.get(0).updatePos(head.getX() + 25 , head.getY() ,dir);
                        else {

                            GameOver = true;
                            overPlayer.seek(Duration.ZERO);
                            overPlayer.play();

                        }
                        break;}

            case 1: {   x = head.getX() ; y = head.getY()-25;
                        if (verifyBody(x,y)  && verifyBorders(x,y)) eslabones.get(0).updatePos(head.getX() , head.getY() - 25  ,dir);
                        else {

                            GameOver = true;
                            overPlayer.seek(Duration.ZERO);
                            overPlayer.play();

                        }
                        break;}

            case 2: {   x = head.getX() -25 ; y = head.getY();
                        if (verifyBody(x,y)  && verifyBorders(x,y)) eslabones.get(0).updatePos(head.getX() - 25 , head.getY()  ,dir);
                        else {

                            GameOver = true;
                            overPlayer.seek(Duration.ZERO);
                            overPlayer.play();
                        }
                        break;}

            case 3: {   x = head.getX()  ; y = head.getY() + 25;
                        if (verifyBody(x,y)  && verifyBorders(x,y)) eslabones.get(0).updatePos(head.getX() , head.getY() + 25 ,dir);



                        else {

                            GameOver = true;
                            overPlayer.seek(Duration.ZERO);
                            overPlayer.play();
                        }
                        break;}


            default: break;
        }
    }

    public void laserEat(){

        if (laserON){

            for(int i = 0; i< foods.size(); i++) {
                if ((laser.getX() == (int) foods.get(i).getX()) && (laser.getY() == (int) foods.get(i).getY())) {
                    if(foods.get(i).getFoodView().power == 1){
                        laserCounter = Math.min(laserCounter+3, 8);
                        powerObtainPlayer.setVolume(0.3);
                        powerObtainPlayer.seek(Duration.ZERO);
                        powerObtainPlayer.play();
                    }
                    else {
                        updateAnaconda();
                        eatPlayer.setVolume(0.3);
                        eatPlayer.seek(Duration.ZERO);
                        eatPlayer.play();
                        lvl3Counter++;

                    }
                    foods.get(i).getFoodView().RemoveFood();
                    foods.remove(i);
                    break;

                }
            }


        }
    }


    //ANACONDA EATS FOOD
    public void eatFood(){

        Eslabon head = eslabones.get(0);
        if (level == 1){

            if ((head.getX() == (int)food.getX()) && (head.getY() == (int)food.getY())){
                scoreCounter += 100;
                updateAnaconda();
                while (verifyFoodPos()){
                    food.updatePos();
                }
                eatPlayer.setVolume(0.3);
                eatPlayer.seek(Duration.ZERO);
                eatPlayer.play();
            }
        }
        else if (level == 2){
            if ((head.getX() == (int)foods.get(0).getX()) && (head.getY() == (int)foods.get(0).getY())){
                scoreCounter += 100;
                updateAnaconda();
                while (verifyFoodPos()){

                    foods.get(0).updatePos();
                    foods.get(1).updatePos();
                }
                eatPlayer.setVolume(0.3);
                eatPlayer.seek(Duration.ZERO);
                eatPlayer.play();
            }
            else if ((head.getX() == (int)foods.get(1).getX()) && (head.getY() == (int)foods.get(1).getY())){
                while (verifyFoodPos()){
                    foods.get(0).updatePos();
                    foods.get(1).updatePos();
                }
                failLvl2Player.setVolume(0.3);
                failLvl2Player.seek(Duration.ZERO);
                failLvl2Player.play();
            }

        }
        else if (level == 3){
            for(int i = 0; i< foods.size(); i++) {
                if ((head.getX() == (int) foods.get(i).getX()) && (head.getY() == (int) foods.get(i).getY())) {
                    if(foods.get(i).getFoodView().power == 1){
                        laserCounter = Math.min(laserCounter+3, 8);
                        powerObtainPlayer.setVolume(0.3);
                        powerObtainPlayer.seek(Duration.ZERO);
                        powerObtainPlayer.play();
                    }
                    else {
                        updateAnaconda();
                        eatPlayer.setVolume(0.3);
                        eatPlayer.seek(Duration.ZERO);
                        eatPlayer.play();
                        lvl3Counter++;

                    }
                    foods.get(i).getFoodView().RemoveFood();
                    foods.remove(i);
                    break;

                }
            }
        }

    }

    //verifica que la nueva posicion de la comida no coincida con la posicion de cualquier
    //elemento de la anaconda.
    public boolean verifyFoodPos(){
        boolean flag = false;

        if (level == 1){
            for (int i = eslabones.size()-1; i>=0; i--) {
                int X_e = eslabones.get(i).getX();
                int Y_e = eslabones.get(i).getY();
                if ((int) food.getX() == X_e && (int) food.getY() == Y_e) {
                    flag = true;
                }
            }

        }
        else if (level == 2){
            for (int i = eslabones.size()-1; i>=0; i--) {
                int X_e = eslabones.get(i).getX();
                int Y_e = eslabones.get(i).getY();
                if (((int) foods.get(0).getX() == X_e && (int) foods.get(0).getY() == Y_e) ||
                        ((int) foods.get(1).getX() == X_e && (int) foods.get(1).getY() == Y_e)
                ||((int) foods.get(0).getX() ==(int)foods.get(1).getX() && (int)foods.get(0).getY() ==(int)foods.get(1).getY())){
                    flag = true;
                }

            }
            if (laserON) {
                if (((int) foods.get(0).getX() == laser.getX() && (int) foods.get(0).getY() == laser.getY()) ||
                        ((int) foods.get(1).getX() == laser.getX() && (int) foods.get(1).getY() == laser.getY())){
                    flag = true;
                }

            }
        }
        else if (level == 3){
            for (int i = eslabones.size()-1; i>=0; i--) {
                int X_e = eslabones.get(i).getX();
                int Y_e = eslabones.get(i).getY();
                for (int j = 0; j < foods.size(); j++) {
                    if (((int) foods.get(j).getX() == X_e && (int) foods.get(j).getY() == Y_e)) {
                        flag = true;
                        break;
                    }
                    for(int k = j+1; k < foods.size(); k++){
                        if((int) foods.get(j).getX() == (int)foods.get(k).getX() && (int)foods.get(j).getY() == (int)foods.get(k).getY()){
                            flag = true;
                            break;
                        }
                    }
                }
            }
            if (laserON) {
                for (int j = 0; j < foods.size(); j++) {
                    if (((int) foods.get(j).getX() == laser.getX() && (int) foods.get(j).getY() == laser.getY())) {
                        flag = true;
                    }
                    for(int k = j+1; k < foods.size(); k++){
                        if((int) foods.get(j).getX() == (int)foods.get(k).getX() && (int)foods.get(j).getY() == (int)foods.get(k).getY()){
                            flag = true;
                            break;
                        }
                    }
                }
            }
        }
        return flag;
    }

    //Verifica que la anaconda no coincida con su cuerpo
    public boolean verifyBody(int headX, int headY){
        boolean flag = true;  //true ->enable, false->lose game
        for (int i=1; i< eslabones.size() ; i++){
            if (headX == eslabones.get(i).getX()  && headY == eslabones.get(i).getY() )  flag = false;
        }
        return flag;

    }

    //verifica que se encuentre dentro de los bordes
    public boolean verifyBorders(int headX, int headY) {
        boolean flag = false;
        if ((0 <= headX && headX < this.getWidth()) && (0 <= headY && headY < this.getHeight()))  {flag = true;}

        return flag;
    }


    public void restart(){
        GameOver = false;
        eslabones = new ArrayList<Eslabon>();
        this.getView().getChildren().remove(1, this.getView().getChildren().size());
        beginAnaconda();

        food = new Food(this, 0);
        lvl3Counter = 0;
        ronda = 0;
        scoreCounter = 0;
        laserCounter = 0;
        level = 1;
    }

    //Update items on area
    public void updateArea(){}


    //update area view
    public void updateView(){
        //areaView.update();
        for (int i= eslabones.size()-1;i >=0; i--){
            eslabones.get(i).getEsbView().update();
        }
    }

}
