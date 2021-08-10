package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class MenuControl {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public TextField NameField;

    public String name;
    @FXML
    public void getName(KeyEvent event) {
        name = this.NameField.getText();

    }

    @FXML
    void initialize() {
        assert NameField != null : "fx:id=\"NameField\" was not injected: check your FXML file 'MENUU.fxml'.";

    }
}
