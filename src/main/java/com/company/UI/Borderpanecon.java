package com.company.UI;

import com.company.UI.EPKUI.*;
import com.company.UI.javafxgraph.fxgraph.graph.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.company.UI.UI_Button_Active_Type.*;


public class Borderpanecon implements Initializable {

    UI_Button_Active_Type Btn_Type;
    Model model;
    UI_EPK EPK;
    Stage primarystage;
    Stage ThisStage;

    @FXML
    Button SimulationMan;
    @FXML
    Button WorkforcesMan;
    @FXML
    Button ResourceMan;
    @FXML
    Button UserMan;
    @FXML
    Button Testing;
    @FXML
    BorderPane Canvaspane;
    @FXML
    VBox Rightbox;
    @FXML
    Button Normal;
    @FXML
    Button Connect;
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
    @FXML
    Button Delete;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Delete.setDisable(true);
        Btn_Type = NORMAL;
        Normal.setTooltip(new Tooltip("Auswahl"));
        Connect.setTooltip(new Tooltip("Verbindung"));
        Event.setTooltip(new Tooltip("Event"));
        Function.setTooltip(new Tooltip("Funktion"));
        OR_Join.setTooltip(new Tooltip("OR-Join"));
        OR_Split.setTooltip(new Tooltip("OR-Split"));
        AND_Join.setTooltip(new Tooltip("AND-Join"));
        AND_Split.setTooltip(new Tooltip("AND-Split"));
        XOR_Join.setTooltip(new Tooltip("XOR-Join"));
        XOR_Split.setTooltip(new Tooltip("XOR-Split"));
        Activating_Function.setTooltip(new Tooltip("Aktivierende Funktion"));
        Activating_Start_Event.setTooltip(new Tooltip("Aktivierendes Start Event"));
        Start_Event.setTooltip(new Tooltip("Start Event"));
        End_Event.setTooltip(new Tooltip("End Event"));

    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setThisStage(Stage stage) {
        this.ThisStage = stage;
    }

    public void setEPK(UI_EPK EPK) {
        this.EPK = EPK;
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

    public void actionPerformed(ActionEvent e) throws IOException {

        if (e.getSource() == Delete) {
            System.out.println("Palaber " + ((UI_Instantiable) EPK.getActive_Elem().getNodeView()).get_Next_Elem_ID());
        } else if (e.getSource() == Normal) {
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
        } else if (e.getSource() == Connect) {
            Btn_Type = CONNECT;
            System.out.println("Connect");
        } else if (e.getSource() == Testing) {

            URL url = new File("src/main/java/com/company/UI/javafxgraph/Testing_UI.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            Parent root = loader.load();
            Scene scene = new Scene(root, 700, 500);

            UI_TEST_EPK Testing_UI = (UI_TEST_EPK) loader.getController();


            Stage newWindow = new Stage();
            Testing_UI.initializeTest(EPK, newWindow);
            newWindow.setTitle("EPCSim Test");
            newWindow.setScene(scene);
            newWindow.setResizable(false);
            newWindow.initOwner(primarystage);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.setX(primarystage.getX() + 200);
            newWindow.setY(primarystage.getY() + 100);
            newWindow.show();

        } else if (e.getSource() == UserMan) {

            URL url = new File("src/main/java/com/company/UI/javafxgraph/User_UI.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 500);

            UI_USER_MANAGEMENT USER_UI = (UI_USER_MANAGEMENT) loader.getController();


            Stage newWindow = new Stage();
            USER_UI.setEPK(EPK);
            USER_UI.setMainStage(ThisStage);
            USER_UI.setThisstage(newWindow);
            newWindow.setTitle("EPCSim User-Management");
            newWindow.setScene(scene);
            newWindow.setResizable(false);
            newWindow.initOwner(primarystage);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.setX(primarystage.getX() + 200);
            newWindow.setY(primarystage.getY() + 100);
            USER_UI.generateUI();
            newWindow.show();

        } else if (e.getSource() == ResourceMan) {
            URL url = new File("src/main/java/com/company/UI/javafxgraph/Resource_UI.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 500);

            UI_RESOURCE_MANAGEMENT RESOURCE_UI = (UI_RESOURCE_MANAGEMENT) loader.getController();
            Stage newWindow = new Stage();
            RESOURCE_UI.setEPK(EPK);
            RESOURCE_UI.setMainStage(ThisStage);
            RESOURCE_UI.setThisstage(newWindow);
            newWindow.setTitle("EPCSim Resource-Management");
            newWindow.setScene(scene);
            newWindow.setResizable(false);
            newWindow.initOwner(primarystage);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.setX(primarystage.getX() + 200);
            newWindow.setY(primarystage.getY() + 100);
            RESOURCE_UI.generateUI();
            newWindow.show();
        } else if (e.getSource() == WorkforcesMan) {
            URL url = new File("src/main/java/com/company/UI/javafxgraph/Workforce_UI.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200, 800);

            UI_WORKFORCE_MANAGEMENT WORKFORCE_UI = (UI_WORKFORCE_MANAGEMENT) loader.getController();
            Stage newWindow = new Stage();
            WORKFORCE_UI.setEPK(EPK);
            WORKFORCE_UI.setMainStage(ThisStage);
            WORKFORCE_UI.setThisstage(newWindow);
            newWindow.setTitle("EPCSim Workforce-Management");
            newWindow.setScene(scene);
            newWindow.setResizable(false);
            newWindow.initOwner(primarystage);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.setX(primarystage.getX() + 200);
            newWindow.setY(primarystage.getY() + 100);
            WORKFORCE_UI.generateUI();
            newWindow.show();
        } else if (e.getSource() == SimulationMan) {
            UI_Settings Settings = EPK.getUI_Settings();
            if (Settings == null) {
                Settings = new UI_Settings();
                EPK.setUI_Settings(Settings);
            }
            URL url = new File("src/main/java/com/company/UI/javafxgraph/Simulation_UI.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200, 800);

            UI_SIMULATION_MANAGEMENT SIMULATION_UI = (UI_SIMULATION_MANAGEMENT) loader.getController();
            Stage newWindow = new Stage();
            SIMULATION_UI.setEPK(EPK);
            SIMULATION_UI.setSettings(Settings);
            SIMULATION_UI.setMainStage(ThisStage);
            SIMULATION_UI.setThisstage(newWindow);
            newWindow.setTitle("EPCSim Workforce-Management");
            newWindow.setScene(scene);
            newWindow.setResizable(false);
            newWindow.initOwner(primarystage);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.setX(primarystage.getX() + 200);
            newWindow.setY(primarystage.getY() + 100);
            SIMULATION_UI.generateUI();
            newWindow.show();
        }


    }

    public void activateDeleteButton() {
        Delete.setDisable(false);
    }

    public void deactivateDeleteButton() {
        Delete.setDisable(true);
    }

    public UI_Button_Active_Type getBtn_Type() {
        return Btn_Type;
    }

    public void setBtn_Type(UI_Button_Active_Type Type) {
        Btn_Type = Type;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primarystage = primaryStage;
    }
}

