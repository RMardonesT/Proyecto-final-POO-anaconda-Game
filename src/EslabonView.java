package src;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.awt.*;
import controller.*;

public class EslabonView {

    private Eslabon eslabon;
    private Area area;
    private Rectangle esbView;
    private Image templateHead, templateBody, rotatedBody;


    private int SIZE = 25;

    public EslabonView(Area area, Eslabon eslabon){
        templateHead = new Image(EslabonView.class.getResource("img/head.png").toExternalForm());
        templateBody = new Image(EslabonView.class.getResource("img/body.png").toExternalForm());
        this.area = area;
        this.eslabon = eslabon;

        ImageView iv = new ImageView(templateBody);
        iv.setRotate(90);
        SnapshotParameters params = new SnapshotParameters();
        rotatedBody = iv.snapshot(params, null);


        esbView = new Rectangle(SIZE,SIZE, eslabon.getColor());
        esbView.setX(eslabon.getX());
        esbView.setY(eslabon.getY());

        if (eslabon.getType() ==1) {
            esbView.setFill(new ImagePattern(templateHead));

        }
        else esbView.setFill(new ImagePattern(templateBody));


        area.getView().getChildren().add(esbView);

    }

    public void update(){
        esbView.setX(eslabon.getX());
        esbView.setY(eslabon.getY());

        if (eslabon.getDir() == 1 || eslabon.getDir() == 3  && (eslabon.getType() != 1))   esbView.setFill(new ImagePattern(rotatedBody));
        
        else if (eslabon.getDir() == 0 || eslabon.getDir() == 2  && (eslabon.getType() != 1))   esbView.setFill(new ImagePattern(templateBody));

        if (eslabon.getType() ==1) esbView.setFill(new ImagePattern(templateHead));


    }

}
