package com.company.UI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Borderpanecon implements Initializable {

    @FXML
    BorderPane Canvaspane;

    @FXML
    VBox Rightbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public BorderPane getCanvaspane() {
        return Canvaspane;
    }

    public VBox getLeftbox() {
        return Rightbox;
    }

    public void setLeftbox(VBox box) {
        Rightbox = box;
    }
}
