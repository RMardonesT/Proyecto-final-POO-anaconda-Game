package src;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.util.Duration;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.sql.Time;


public class Laser {

    public Timeline animation;

    private Rectangle view, explodeView;
    private double size = 25;
    private Eslabon esbRef;
    private Image template, explode ,right,up,down;
    private Area area;
    private int X,Y, dir;
    public Laser(Area area, int dir, int x, int y){
        this.area = area;
        this.dir = dir;
        template = new Image(Laser.class.getResource("img/laser.png").toExternalForm());
        explode = new Image(Laser.class.getResource("img/exp.png").toExternalForm());

        view = new Rectangle(size, size);
        explodeView = new Rectangle(size,size);

        X = x; Y = y;
        view.setX(x);
        view.setY(y);

        view.setFill(new ImagePattern(template));

        area.getView().getChildren().add(view);


        SnapshotParameters params = new SnapshotParameters();

        ImageView uR = new ImageView(template);  uR.setRotate(90);
        up = uR.snapshot(params,null);


        ImageView rR = new ImageView(template);  rR.setRotate(180);
        right = rR.snapshot(params, null);

        ImageView dR = new ImageView(template);  dR.setRotate(270);
        down = dR.snapshot(params, null);


    }

    public void updateLaser(){



        switch (dir){
            //DERECHA
            case 0: {

                    view.setFill(new ImagePattern(right));

                    X = (int)view.getX() + 25;
                    view.setX(X );
                    if (view.getX()> area.getWidth()){ explode(); RemoveLaser(); area.laserON = false;}

                    break;}


            //ARRIBA
            case 1: {
                    view.setFill(new ImagePattern(up));

                    Y = (int)view.getY() - 25;
                    view.setY(Y);


                    if (view.getY()< 0) { explode(); RemoveLaser(); area.laserON = false;}
                    break;}
            //IZQUIERDA
            case 2: {

                    X = (int)view.getX() - 25;
                    view.setX(X);
                    if (view.getX() < 0){ RemoveLaser(); area.laserON = false;}

                    break;}
            //ABAJO
            case 3: {view.setFill(new ImagePattern(down));

                    Y = (int)view.getY() + 25;
                    view.setY( Y);
                    if (view.getY() > area.getHeight()){  RemoveLaser(); area.laserON = false;}

                    break;}

            default: view.setX(view.getX()); view.setY(view.getY());
        }

    }

    private void explode ( ){

        view.setFill(new ImagePattern(explode));
    }




    public void RemoveLaser(){

        area.getView().getChildren().remove(view);

    }

    public int getX(){return X;}
    public int getY(){return Y;}
    public int getDir(){return dir;}
}
