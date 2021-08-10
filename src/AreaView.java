package src;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import controller.*;

public class AreaView extends Group{
    private final Area area;
    public AreaView(Area area){
        this.area = area;
        Rectangle territoryView = new Rectangle(area.getWidth(),area.getHeight(),Color.WHITE);
        territoryView.setStroke(Color.BROWN);
        getChildren().add(territoryView);
        setFocusTraversable(true);

    }



}
