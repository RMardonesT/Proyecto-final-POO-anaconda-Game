import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import src.*;
import controller.*;

import java.io.IOException;


public class Main extends Application {
    Stats stats;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("controller/MENUU.fxml"));
        Parent root = loader.load();

        Scene initScene = new Scene(root);

        primaryStage.setTitle("anaCOndaVID");

        primaryStage.setScene(initScene);

        MenuControl menuControl = loader.getController();
        primaryStage.show();


        initScene.setOnKeyPressed(event -> {
            try {
                keyHandle(event, primaryStage, menuControl.name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    private void keyHandle (KeyEvent e , Stage primaryStage, String name) throws IOException {


        if (e.getCode() == KeyCode.ENTER) {
            stats = new Stats(name);


            //CARGA DE LA ESCENA LVL1 DESCRIPTION

            FXMLLoader levelLoad = new FXMLLoader(getClass().getClassLoader().getResource("controller/level1.fxml"));
            Parent root = levelLoad.load();

            Scene levelScn = new Scene(root);
            primaryStage.setScene(levelScn);

            levelScn.setOnKeyPressed(event -> {
                try {
                    keyHandle(event, primaryStage, stats.name);
                } catch (IOException ev) {
                    ev.printStackTrace();
                }
            });
        }
       if (e.getCode() == KeyCode.SPACE){

            //INICIO DEL JUEGO
            primaryStage.setTitle("AnaCOndaVID");   //title
            primaryStage.setMaxHeight(640);
            primaryStage.setMaxWidth(850);
            primaryStage.setMinHeight(640);
            primaryStage.setMinWidth(850);
            BorderPane borderPane = new BorderPane(); //border Pane
            Scene scene = new Scene(borderPane, 800, 600);
            primaryStage.setScene(scene);


            Area area = new Area();
            stats = new Stats(name);

            FXMLLoader loaderControl = new FXMLLoader(getClass().getClassLoader().getResource("controller/sample.fxml"));
            VBox vboxS =  loaderControl.load();
            StatsControl statsControl = loaderControl.getController();
            Simulator simulator = new Simulator(10,1,area, primaryStage, stats, statsControl);


            Pane pane = new Pane();//pane
            pane.getChildren().add(area.getView());//
            borderPane.setCenter(pane);
            borderPane.setRight(vboxS); //hacer lo que dice el pepo de hacer lo del loader :p
            primaryStage.show();

        }

    }



        public static void main(String[] args) {
        launch(args);


    }
}
