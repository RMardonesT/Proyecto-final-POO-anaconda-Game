package src;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import controller.*;

public class FoodView {
    private Rectangle view;
    private double size;
    private Food food;
    private Image template, powerUPTemplate;
    private Area area;
    public int power;

    public FoodView(Area area, Food food, int power){

        template = new Image(FoodView.class.getResource("img/food.png").toExternalForm());
        powerUPTemplate = new Image(FoodView.class.getResource("img/powerUP.png").toExternalForm());
        this.area = area;
        this.food = food;
        this.power = power;
        size = food.getSize();
        view = new Rectangle(size, size, Color.GREEN);
        view.setX(food.getX());
        view.setY(food.getY());

        if (power == 1) {
            view.setFill(new ImagePattern(powerUPTemplate));
            }
        else view.setFill(new ImagePattern(template));

        area.getView().getChildren().add(view);
    }

    public void update(){


        view.setX(food.getX());
        view.setY(food.getY());

    }

    public void RemoveFood(){
        area.getView().getChildren().remove(view);
    }
}
