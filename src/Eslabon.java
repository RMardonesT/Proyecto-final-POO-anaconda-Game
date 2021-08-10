package src;
import javafx.scene.paint.Color;
import controller.*;

public class Eslabon {

    private int type; // 0 = body, 1 = head, 2 = tail
    private  int dir; // 0 = right, 1 = up, 2 = left, 3 = down
    private  int X,Y;
    private Area area;
    private EslabonView  esbView;
    private  Color color;

    //HEAD
    public  Eslabon( int X,int Y ,Area area)
    {
        type = 1;
        dir = 0;  //direccion inicial derecha
        this.X = X;
        this.Y = Y;
        color = Color.RED;
        esbView = new EslabonView(area, this);

    }

    //BODY
    public   Eslabon(Area area, int type, int dir, int X, int Y){
        this.area = area;
        this.dir = dir;
        this.type = type;
        this.X = X;
        this.Y = Y;
        color = Color.BLACK;
        esbView = new EslabonView(area, this);
    }

    //NewTail
    public Eslabon(Area area, Eslabon eslabon){
        this.area = area;
        type = 2;  X = eslabon.getX(); Y=eslabon.getY(); dir = eslabon.getDir();
        color = Color.BLACK;
        esbView = new EslabonView(area, this);
    }



    // Copia el eslabon de enfrente para permitir movimiento de la anaconda
    public void CopyEslab( Eslabon orig){
        this.X = orig.getX();
        this.Y = orig.getY();
        this.dir = orig.getDir();
    }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color;}
    public EslabonView getEsbView(){return esbView;}
    public int getX(){return X;}
    public int getY(){return Y;}
    public int getDir(){return dir;}
    public int getType(){return type;}

    public void updatePos(int X , int Y , int newDir){this.X = X; this.Y = Y; dir = newDir;}





}
