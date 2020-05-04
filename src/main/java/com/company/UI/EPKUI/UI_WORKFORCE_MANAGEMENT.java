package com.company.UI.EPKUI;

import com.company.EPK.Function;
import com.company.EPK.Workforce;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UI_WORKFORCE_MANAGEMENT implements Initializable {

    @FXML
    Button OK_Button;
    @FXML
    VBox Choosebox;
    @FXML
    VBox Editbox;
    @FXML
    VBox Showbox;

    private UI_EPK EPK;
    private List<Workforce> Workforces;
    private Stage Mainstage;
    private Stage this_stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.Workforces = new ArrayList<>();
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

    public void generateUI() {
        Choosebox.getChildren().clear();
        Workforces = EPK.getAll_Workforces();
        generateShowUI();
        Label label = new Label("Choose Workforce to edit: ");
        SingleSelectionField<Workforce> UI_Workforces = Field.ofSingleSelectionType(Workforces).label("Workforces");
        FormRenderer RESOURCE_UI = new FormRenderer(Form.of(Group.of(UI_Workforces)));
        Choosebox.getChildren().add(RESOURCE_UI);
        Button button1 = new Button("Edit selected workforce");

        button1.setOnAction(actionEvent -> {
            Workforce workforce = UI_Workforces.getSelection();
            if (workforce != null) {
                showEditWorkforceUI(workforce);
            }
        });
        Button button2 = new Button("New workforce");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showNewWorkforceUI();
            }
        });
        ButtonBar bar = new ButtonBar();
        bar.getButtons().add(button1);
        bar.getButtons().add(button2);
        Choosebox.getChildren().add(bar);
    }

    private void showNewWorkforceUI() {
        Editbox.getChildren().clear();
        Workforce workforce = new Workforce(EPK.getUniqueWorkforceID(), "");
        IntegerField W_ID = Field.ofIntegerType(workforce.getW_ID()).label("ID: ").editable(false);
        StringField Permission = Field.ofStringType(workforce.getPermission()).label("Permissiontag: ");

        ButtonBar Save = new ButtonBar();
        Button Save_Button = new Button("Save workforce");
        Save_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                workforce.setPermission(Permission.getValue());
                EPK.AddWorkforce(workforce);
                generateUI();
            }
        });
        Save.getButtons().add(Save_Button);
        Label ManageUserFunctionLabel = new Label();
        String Managementlabel = new String("Users: [ ");
        for (User granted_User : workforce.getGranted_to()) {
            Managementlabel.concat("; " + granted_User.toString());
        }
        Managementlabel.concat("] / Functions: [ ");

        for (Function used_in : workforce.getUsed_in()) {
            Managementlabel.concat("; " + used_in.toString());
        }
        Managementlabel.concat("]");
        ManageUserFunctionLabel.setText(Managementlabel);
        List<User> AddedUserlist = workforce.getGranted_to();
        List<Function> AddedFunctionlist = workforce.getUsed_in();

        Button Delete_Workforce_Btn = new Button("Delete workforce");
        Delete_Workforce_Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (AddedUserlist != null && !AddedUserlist.isEmpty()) {
                    for (User u : AddedUserlist) {
                        u.removeWorkforceByID(workforce);
                    }
                    for (Function F : AddedFunctionlist) {
                        F.removeWorkforceByID(workforce);
                    }
                    EPK.RemoveWorkforce(workforce);
                    String Userlabel = "Users: [ ] / Functions: [ ]";
                    ManageUserFunctionLabel.setText(Userlabel);
                }
            }
        });

        Delete_Workforce_Btn.setDisable(true);
        ButtonBar Functionbar = new ButtonBar();
        Functionbar.getButtons().add(Delete_Workforce_Btn);

        FormRenderer EDIT_UI = new FormRenderer(Form.of(Group.of(W_ID, Permission)));
        Editbox.getChildren().add(EDIT_UI);
        Editbox.getChildren().add(ManageUserFunctionLabel);
        Editbox.getChildren().add(Functionbar);
        Editbox.getChildren().add(new Separator());
        Editbox.getChildren().add(Save);
    }

    private void showEditWorkforceUI(Workforce workforce) {

        Editbox.getChildren().clear();
        IntegerField W_ID = Field.ofIntegerType(workforce.getW_ID()).label("ID: ").editable(false);
        StringField Permission = Field.ofStringType(workforce.getPermission()).label("Permissiontag: ");

        ButtonBar Save = new ButtonBar();
        Button Save_Button = new Button("Save workforce");
        Save_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                workforce.setPermission(Permission.getValue());
                EPK.AddWorkforce(workforce);
                generateUI();
            }
        });
        Save.getButtons().add(Save_Button);
        Label ManageUserFunctionLabel = new Label();
        String Managementlabel = new String("Users: [ ");
        for (User granted_User : workforce.getGranted_to()) {
            Managementlabel.concat("; " + granted_User.toString());
        }
        Managementlabel.concat("] / Functions: [ ");

        for (Function used_in : workforce.getUsed_in()) {
            Managementlabel.concat("; " + used_in.toString());
        }
        Managementlabel.concat("]");
        ManageUserFunctionLabel.setText(Managementlabel);
        List<User> AddedUserlist = workforce.getGranted_to();
        List<Function> AddedFunctionlist = workforce.getUsed_in();

        Button Delete_Workforce_Btn = new Button("Delete workforce");
        Delete_Workforce_Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (AddedUserlist != null && AddedFunctionlist != null) {
                    for (User u : AddedUserlist) {
                        u.removeWorkforceByID(workforce);
                    }
                    for (Function F : AddedFunctionlist) {
                        F.removeWorkforceByID(workforce);
                    }
                    EPK.RemoveWorkforce(workforce);
                    String Userlabel = "Users: [ ] / Functions: [ ]";
                    ManageUserFunctionLabel.setText(Userlabel);
                    generateUI();
                }
            }
        });

        Delete_Workforce_Btn.setDisable(false);
        ButtonBar Functionbar = new ButtonBar();
        Functionbar.getButtons().add(Delete_Workforce_Btn);

        FormRenderer EDIT_UI = new FormRenderer(Form.of(Group.of(W_ID, Permission)));
        Editbox.getChildren().add(EDIT_UI);
        Editbox.getChildren().add(ManageUserFunctionLabel);
        Editbox.getChildren().add(Functionbar);
        Editbox.getChildren().add(new Separator());
        Editbox.getChildren().add(Save);
    }

    private void generateShowUI() {
        Editbox.getChildren().clear();
        StringField W_ID = Field.ofStringType("").label("ID: ").editable(false);
        StringField Permission = Field.ofStringType("").label("Permissiontag: ");

        ButtonBar Save = new ButtonBar();
        Button Save_Button = new Button("Save workforce");
        Save_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        Save.getButtons().add(Save_Button);
        Save_Button.setDisable(true);
        Label ManageUserFunctionLabel = new Label();
        String Managementlabel = new String("Users: [ ");
        Managementlabel.concat("] / Functions: [ ");
        Managementlabel.concat("]");
        ManageUserFunctionLabel.setText(Managementlabel);

        Button Delete_Workforce_Btn = new Button("Delete workforce");
        Delete_Workforce_Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        Delete_Workforce_Btn.setDisable(true);
        ButtonBar Functionbar = new ButtonBar();
        Functionbar.getButtons().add(Delete_Workforce_Btn);

        FormRenderer EDIT_UI = new FormRenderer(Form.of(Group.of(W_ID, Permission)));
        Editbox.getChildren().add(EDIT_UI);
        Editbox.getChildren().add(ManageUserFunctionLabel);
        Editbox.getChildren().add(Functionbar);
        Editbox.getChildren().add(new Separator());
        Editbox.getChildren().add(Save);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == OK_Button) {
            this_stage.close();
        }
    }

}
