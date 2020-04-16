package com.company.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static com.company.UI.UI_Button_Active_Type.*;


public class Borderpanecon implements Initializable {

    UI_Button_Active_Type Btn_Type;
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
            Btn_Type = NORMAL;
            System.out.println("Normal");
        } else if (e.getSource() == Event) {
            Btn_Type = EVENT;
            System.out.println("Event");
        } else if (e.getSource() == Function) {
            Btn_Type = FUNCTION;
            System.out.println("Function");
        } else if (e.getSource() == AND_Join) {
            Btn_Type = AND_JOIN;
            System.out.println("AND_Join");
        } else if (e.getSource() == OR_Join) {
            Btn_Type = OR_JOIN;
            System.out.println("OR_Join");
        } else if (e.getSource() == XOR_Join) {
            Btn_Type = XOR_JOIN;
            System.out.println("XOR_Join");
        } else if (e.getSource() == AND_Split) {
            Btn_Type = AND_SPLIT;
            System.out.println("AND_Split");
        } else if (e.getSource() == OR_Split) {
            Btn_Type = OR_SPLIT;
            System.out.println("OR_Split");
        } else if (e.getSource() == XOR_Split) {
            Btn_Type = XOR_SPLIT;
            System.out.println("XOR_Split");
        } else if (e.getSource() == Start_Event) {
            Btn_Type = START_EVENT;
            System.out.println("Start_Event");
        } else if (e.getSource() == End_Event) {
            Btn_Type = END_EVENT;
            System.out.println("End_Event");
        } else if (e.getSource() == Activating_Start_Event) {
            Btn_Type = ACTIVATING_START_EVENT;
            System.out.println("Activating_Start_Event");
        } else if (e.getSource() == Activating_Function) {
            Btn_Type = ACTIVATING_FUNCTION;
            System.out.println("Activating_Function");
        }
    }

    public UI_Button_Active_Type getBtn_Type() {
        return Btn_Type;
    }
}

