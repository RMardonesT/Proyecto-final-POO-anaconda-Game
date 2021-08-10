package src;
import java.util.Random;
import controller.*;

public class Food {
    private double x,y;
    private FoodView foodView;
    private Area area;
    private double size;
    //private    int powerUp; //1 si corresponde a powerup




    //coinstructor level 1


    public Food(Area area, int power){
        this.area = area;
        this.size = 25;
        this.x = Math.floor(Math.random()*area.getWidth()/size)*size;
        this.y = Math.floor(Math.random()*area.getHeight()/size)*size;

        foodView = new FoodView(area, this, power);

    }


    public double getX() {return x;}
    public double getY() {return y;}
    public double getSize() {return size;}
    public FoodView getFoodView(){return foodView;}


    public void updatePos(){
        this.x = Math.floor(Math.random()*area.getWidth()/size)*size;
        this.y = Math.floor(Math.random()*area.getHeight()/size)*size;
        foodView.update();
    }

    //public  int getPowerUp(){return powerUp;}
}
