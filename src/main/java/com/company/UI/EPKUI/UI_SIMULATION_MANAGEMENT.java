package com.company.UI.EPKUI;

import com.company.Enums.Option_Event_Choosing;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import static com.company.Enums.Option_Event_Choosing.*;

public class UI_SIMULATION_MANAGEMENT implements Initializable {

    @FXML
    Button OK_Button;
    @FXML
    Button CANCEL_Button;
    @FXML
    VBox Choosebox;
    @FXML
    VBox Editbox;
    @FXML
    VBox Showbox;


    private UI_EPK EPK;
    private Stage Mainstage;
    private Stage this_stage;
    private UI_Settings settings;
    private UI_Settings saveForCancel;
    private BooleanField Only_Start_Finishable_Functions;
    private BooleanField Print_Only_Functions;
    private BooleanField Optimal_User_Layout;
    private SingleSelectionField<Option_Event_Choosing> Event_Choosing;
    private IntegerField BeginTimeHour;
    private IntegerField BeginTimeMinute;
    private IntegerField BeginTimeSecond;
    private IntegerField EndTimeHour;
    private IntegerField EndTimeMinute;
    private IntegerField EndTimeSecond;
    private IntegerField RuntimeDays;
    private FormRenderer BooleanSettings;
    private FormRenderer Begintime;
    private FormRenderer Endingtime;
    private FormRenderer RunningDays;
    private FormRenderer Choose_UI;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setEPK(UI_EPK EPK) {
        this.EPK = EPK;
    }

    public void setMainStage(Stage stage) {
        this.Mainstage = stage;
    }

    public void setThisstage(Stage stage) {
        this_stage = stage;
    }

    public void setSettings(UI_Settings settings) {
        this.settings = settings;
        saveForCancel = new UI_Settings();
        saveForCancel.setOptimal_User_Layout(settings.isOptimal_User_Layout());
        saveForCancel.setPrint_Only_Function(settings.isPrint_Only_Function());
        saveForCancel.setDecide_Event_choosing(settings.getDecide_Event_choosing());
        saveForCancel.setOnly_Start_Finishable_Functions(settings.isOnly_Start_Finishable_Functions());
        saveForCancel.setMax_RuntimeDays(settings.getMax_RuntimeDays());
        saveForCancel.setBeginTime(settings.getBeginTime());
        saveForCancel.setEndTime(settings.getEndTime());
    }

    public void generateUI() {

        Choosebox.getChildren().clear();
        List<Option_Event_Choosing> Choosing = EPK.getAll_Event_Choosings();

        Option_Event_Choosing chosen = settings.getDecide_Event_choosing();
        int choose = 0;
        if (chosen == BY_CUSTOMER_RELATION) {
            choose = 1;
        } else if (chosen == BY_LARGEST_MARGIN) {
            choose = 2;
        } else if (chosen == BY_LARGEST_INVEST) {
            choose = 3;
        } else if (chosen == DEFAULT) {
            choose = 4;
        }
        Event_Choosing = Field.ofSingleSelectionType(Choosing, choose)
                .label("Instance decision type: ")
                .tooltip("Choose by wich setting the simulation should choose the instance that should be prioritized");

        Only_Start_Finishable_Functions = Field.ofBooleanType(settings.isOnly_Start_Finishable_Functions())
                .label("Only Start Finishabled: ")
                .tooltip("Only start functions that are finishable inside the given workingtime per day");
        Optimal_User_Layout = Field.ofBooleanType(settings.isOptimal_User_Layout())
                .label("Optimal User Layout: ")
                .tooltip("Not yet implemented")
                .editable(false);
        Print_Only_Functions = Field.ofBooleanType(settings.isPrint_Only_Function())
                .label("Only print functions: ")
                .tooltip("Only print informations of functions to the simulation Log without" +
                        " events nor gates");

        BeginTimeHour = Field.ofIntegerType(settings.getBeginTime().getHour())
                .label("Begintime (Hour)").tooltip("sets the begintime each Day in Hours");
        BeginTimeMinute = Field.ofIntegerType(settings.getBeginTime().getMinute())
                .label("Begintime (Minute)").tooltip("sets the begintime each Day in Minutes");
        BeginTimeSecond = Field.ofIntegerType(settings.getBeginTime().getSecond())
                .label("Begintime (Second)").tooltip("sets the begintime each Day in Seconds");

        EndTimeHour = Field.ofIntegerType(settings.getEndTime().getHour())
                .label("Endingtime (Hour)").tooltip("sets the Endingtime each Day in Hours");
        EndTimeMinute = Field.ofIntegerType(settings.getEndTime().getMinute())
                .label("Endingtime (Minute)").tooltip("sets the Endingtime each Day in Minutes");
        EndTimeSecond = Field.ofIntegerType(settings.getEndTime().getSecond())
                .label("Endingtime (Second)").tooltip("sets the Endingtime each Day in Seconds");

        RuntimeDays = Field.ofIntegerType(settings.getMax_RuntimeDays())
                .label("Days to simulate").tooltip("Maximum number of days the simulation should simulate");

        BooleanSettings = new FormRenderer(Form.of(Group.of
                (Only_Start_Finishable_Functions, Optimal_User_Layout, Print_Only_Functions)));
        Begintime = new FormRenderer(Form.of(Group.of
                (BeginTimeHour, BeginTimeMinute, BeginTimeSecond)));
        Endingtime = new FormRenderer(Form.of(Group.of
                (EndTimeHour, EndTimeMinute, EndTimeSecond)));
        RunningDays = new FormRenderer(Form.of(Group.of(RuntimeDays)));
        Choose_UI = new FormRenderer(Form.of(Group.of(Event_Choosing)));


        Choosebox.getChildren().clear();
        Showbox.getChildren().clear();
        Editbox.getChildren().clear();

        Choosebox.getChildren().add(Choose_UI);
        Showbox.getChildren().add(BooleanSettings);
        Editbox.getChildren().add(RunningDays);
        Editbox.getChildren().add(Begintime);
        Editbox.getChildren().add(Endingtime);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == OK_Button) {
            settings.setOnly_Start_Finishable_Functions(Only_Start_Finishable_Functions.getValue());
            settings.setBeginTime(LocalTime.of
                    (BeginTimeHour.getValue(), BeginTimeMinute.getValue(), BeginTimeSecond.getValue()));
            settings.setEndTime(LocalTime.of
                    (EndTimeHour.getValue(), EndTimeMinute.getValue(), EndTimeSecond.getValue()));
            settings.setDecide_Event_choosing(Event_Choosing.getSelection());
            settings.setMax_RuntimeDays(RuntimeDays.getValue());
            settings.setPrint_Only_Function(Print_Only_Functions.getValue());
            settings.setOptimal_User_Layout(Optimal_User_Layout.getValue());
            this_stage.close();
        } else if (e.getSource() == CANCEL_Button) {
            settings.setEndTime(saveForCancel.getEndTime());
            settings.setBeginTime(saveForCancel.getBeginTime());
            settings.setMax_RuntimeDays(saveForCancel.getMax_RuntimeDays());
            settings.setOnly_Start_Finishable_Functions(saveForCancel.isOnly_Start_Finishable_Functions());
            settings.setDecide_Event_choosing(saveForCancel.getDecide_Event_choosing());
            settings.setPrint_Only_Function(saveForCancel.isPrint_Only_Function());
            settings.setOptimal_User_Layout(saveForCancel.isOptimal_User_Layout());
            this_stage.close();
        }
    }

}
