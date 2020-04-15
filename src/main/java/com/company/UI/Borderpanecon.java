package com.company.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class Borderpanecon implements Initializable {

    @FXML
    BorderPane Canvaspane;
    @FXML
    VBox Rightbox;
    @FXML
    Button Normal;
    @FXML
    Button Event;
    @FXML
    Button Function;
    @FXML
    Button AND_Join;
    @FXML
    Button OR_Join;
    @FXML
    Button XOR_Join;
    @FXML
    Button AND_Split;
    @FXML
    Button OR_Split;
    @FXML
    Button XOR_Split;
    @FXML
    Button Start_Event;
    @FXML
    Button End_Event;
    @FXML
    Button Activating_Start_Event;
    @FXML
    Button Activating_Function;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public BorderPane getCanvaspane() {
        return Canvaspane;
    }

    public VBox getRightbox() {
        return Rightbox;
    }

    public void setRightbox(VBox box) {
        Rightbox = box;
    }

    public void initializeButtons() {

    }
    
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == Normal) {
            System.out.println("Normal");
        } else if (e.getSource() == Event) {
            System.out.println("Event");
        } else if (e.getSource() == Function) {
            System.out.println("Function");
        } else if (e.getSource() == AND_Join) {
            System.out.println("AND_Join");
        } else if (e.getSource() == OR_Join) {
            System.out.println("OR_Join");
        } else if (e.getSource() == XOR_Join) {
            System.out.println("XOR_Join");
        } else if (e.getSource() == AND_Split) {
            System.out.println("AND_Split");
        } else if (e.getSource() == OR_Split) {
            System.out.println("OR_Split");
        } else if (e.getSource() == XOR_Split) {
            System.out.println("XOR_Split");
        } else if (e.getSource() == Start_Event) {
            System.out.println("Start_Event");
        } else if (e.getSource() == End_Event) {
            System.out.println("End_Event");
        } else if (e.getSource() == Activating_Start_Event) {
            System.out.println("Activating_Start_Event");
        } else if (e.getSource() == Activating_Function) {
            System.out.println("Activating_Function");
        }
    }

}

