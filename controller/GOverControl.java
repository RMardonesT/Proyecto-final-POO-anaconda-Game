package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import src.Simulator;

public class GOverControl {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button restart_but;

    @FXML
    private Button close_but;

    private Simulator simulator;

    public void oli (Simulator simulator){
        this.simulator = simulator;
    }

    @FXML
    void close_game(ActionEvent event) {
        Node boton = (Node) event.getSource();
        Stage stage = (Stage) boton.getScene().getWindow();
        simulator.primaryStage.close();
        stage.close();
    }

    @FXML
    void restart_game(ActionEvent event) {
        Node boton = (Node) event.getSource();
        Stage stage = (Stage) boton.getScene().getWindow();

        simulator.pause_flag = false;
        simulator.area.restart();
        simulator.animation.play();
        stage.close();
        simulator.description.close();

    }

    @FXML
    void initialize() {
        assert restart_but != null : "fx:id=\"restart_but\" was not injected: check your FXML file 'gameOver.fxml'.";
        assert close_but != null : "fx:id=\"close_but\" was not injected: check your FXML file 'gameOver.fxml'.";

    }
}





































