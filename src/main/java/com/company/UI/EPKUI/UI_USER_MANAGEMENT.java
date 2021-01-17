package com.company.UI.EPKUI;

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

public class UI_USER_MANAGEMENT implements Initializable {

    @FXML
    Button OK_Button;
    @FXML
    VBox Choosebox;
    @FXML
    VBox Editbox;
    @FXML
    VBox Showbox;

    private UI_EPK EPK;
    private List<User> Users;
    private List<Workforce> Workforces;
    private Stage Mainstage;
    private Stage thisstage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.Users = new ArrayList<>();
        this.Workforces = new ArrayList<>();
    }

    public void setEPK(UI_EPK EPK) {
        this.EPK = EPK;
    }

    public void setMainStage(Stage stage) {
        this.Mainstage = stage;
    }

    public void setThisstage(Stage stage) {
        thisstage = stage;
    }

    public void generateUI() {
        Choosebox.getChildren().clear();
        Users = EPK.getAll_Users();
        Workforces = EPK.getAll_Workforces();
        generateShowUI();
        Label label = new Label("Choose User: ");
        SingleSelectionField<User> UI_USERS = Field.ofSingleSelectionType(Users).label("User");
        FormRenderer USERS_UI = new FormRenderer(Form.of(Group.of(UI_USERS)));
        Choosebox.getChildren().add(USERS_UI);
        Button button1 = new Button("Edit selected User");
        button1.setOnAction(actionEvent -> {
            User user = UI_USERS.getSelection();
            if (user != null) {
                showEditUserUI(user);
            }
        });
        Button button2 = new Button("New User");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showNewUserUI();
            }
        });
        ButtonBar bar = new ButtonBar();
        bar.getButtons().add(button1);
        bar.getButtons().add(button2);
        Choosebox.getChildren().add(bar);
    }

    private void showNewUserUI() {
        Editbox.getChildren().clear();
        User user = new User("", "", EPK.getUniqueUserID(), 1);
        IntegerField P_ID = Field.ofIntegerType(user.getP_ID()).label("ID: ").editable(false);
        List<Workforce> Workforces_to_Add = new ArrayList<>();
        StringField FirstName = Field.ofStringType(user.getFirst_Name()).label("Firstname:");
        StringField LastName = Field.ofStringType(user.getLast_Name()).label("Lastname: ");
        DoubleField Efficiency = Field.ofDoubleType(user.getEfficiency()).label("Efficiency: ");

        ButtonBar Save = new ButtonBar();
        Button Save_Button = new Button("Save User");
        Save_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                user.setEfficiency(Efficiency.getValue().floatValue());
                user.setFirst_Name(FirstName.getValue());
                user.setLast_Name(LastName.getValue());
                for (Workforce w : Workforces_to_Add) {
                    if (!user.getWorkforces().contains(w)) {
                        user.getWorkforces().add(w);
                    }
                    if (!w.getGranted_to().contains(user)) {
                        w.getGranted_to().add(user);
                    }
                }
                EPK.AddUser(user);
                generateUI();
            }
        });

        Button Remove_Button = new Button("Remove user");
        Remove_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                List<Workforce> User_Workforce_List = user.getWorkforces();
                for (Workforce w : Workforces) {
                    w.getGranted_to().remove(user);
                }
                user.getWorkforces().clear();
                EPK.getAll_Users().remove(user);
                generateUI();
            }
        });
        Remove_Button.setDisable(true);
        Save.getButtons().add(Remove_Button);
        Save.getButtons().add(Save_Button);

        Label AddedWorkforces = new Label();
        String Workforcelabel = "Added Workforces: [";
        for (Workforce added_force : user.getWorkforces()) {
            Workforcelabel = Workforcelabel.concat("; " + added_force.toString());
        }
        Workforcelabel = Workforcelabel.concat("]");
        AddedWorkforces.setText(Workforcelabel);

        SingleSelectionField<Workforce> Workforcelist = Field.ofSingleSelectionType(Workforces).label("Workforces: ");
        Button Add_Workforce = new Button("Add Workforce");
        Add_Workforce.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Workforce force = Workforcelist.getSelection();
                if (force != null) {
                    if (!Workforces_to_Add.contains(force)) {
                        Workforces_to_Add.add(force);
                        String Workforcelabel = "Added Workforces: [";
                        for (Workforce added_force : Workforces_to_Add) {
                            Workforcelabel = Workforcelabel.concat("; " + added_force.toString());
                        }
                        Workforcelabel = Workforcelabel.concat("]");
                        AddedWorkforces.setText(Workforcelabel);
                    }
                }
            }
        });
        Button Remove_Workforce = new Button("Remove Workforce");
        Remove_Workforce.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Workforce force = Workforcelist.getSelection();
                if (force != null) {
                    if (Workforces_to_Add.contains(force)) {
                        Workforces_to_Add.remove(force);
                        String Workforcelabel = "Added Workforces: [ ";
                        for (Workforce added_force : Workforces_to_Add) {
                            Workforcelabel = Workforcelabel.concat("; " + added_force.toString());
                        }
                        Workforcelabel = Workforcelabel.concat(" ]");
                        AddedWorkforces.setText(Workforcelabel);
                    }
                }
            }
        });
        ButtonBar WorkforcesBar = new ButtonBar();
        WorkforcesBar.getButtons().add(Add_Workforce);
        WorkforcesBar.getButtons().add(Remove_Workforce);

        FormRenderer EDIT_UI = new FormRenderer(Form.of(Group.of(P_ID, FirstName, LastName, Efficiency)));
        FormRenderer WORKFORCE_SELECTION_UI = new FormRenderer(Form.of(Group.of(Workforcelist)));
        Editbox.getChildren().add(EDIT_UI);
        Editbox.getChildren().add(AddedWorkforces);
        Editbox.getChildren().add(WORKFORCE_SELECTION_UI);
        Editbox.getChildren().add(WorkforcesBar);
        Editbox.getChildren().add(new Separator());
        Editbox.getChildren().add(Save);
    }

    private void showEditUserUI(User user) {

        Editbox.getChildren().clear();
        IntegerField P_ID = Field.ofIntegerType(user.getP_ID()).label("ID: ").editable(false);
        StringField FirstName = Field.ofStringType(user.getFirst_Name()).label("Firstname:");
        StringField LastName = Field.ofStringType(user.getLast_Name()).label("Lastname: ");
        DoubleField Efficiency = Field.ofDoubleType(user.getEfficiency()).label("Efficiency: ");
        List<Workforce> Workforces_to_Add = new ArrayList<>();
        List<Workforce> Workforces_to_Remove = new ArrayList<>();
        ButtonBar Save = new ButtonBar();
        Button Save_Button = new Button("Save User");
        Save_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                user.setEfficiency(Efficiency.getValue().floatValue());
                user.setFirst_Name(FirstName.getValue());
                user.setLast_Name(LastName.getValue());
                for (Workforce w : Workforces_to_Add) {
                    if (!user.getWorkforces().contains(w)) {
                        user.getWorkforces().add(w);
                    }
                    if (!w.getGranted_to().contains(user)) {
                        w.getGranted_to().add(user);
                    }
                }
                for (Workforce w : Workforces_to_Remove) {
                    user.getWorkforces().remove(w);
                    w.getGranted_to().remove(user);
                }
                generateUI();
            }
        });

        Button Remove_Button = new Button("Remove user");
        Remove_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                List<Workforce> User_Workforce_List = user.getWorkforces();
                for (Workforce w : Workforces) {
                    w.getGranted_to().remove(user);
                }
                user.getWorkforces().clear();
                EPK.getAll_Users().remove(user);
                generateUI();
            }
        });
        Remove_Button.setDisable(false);
        Save.getButtons().add(Remove_Button);
        Save.getButtons().add(Save_Button);

        Label AddedWorkforces = new Label();
        String Workforcelabel = "Added Workforces: [";
        for (Workforce added_force : user.getWorkforces()) {
            Workforcelabel = Workforcelabel.concat("; " + added_force.toString());
        }
        Workforcelabel = Workforcelabel.concat("]");
        AddedWorkforces.setText(Workforcelabel);

        SingleSelectionField<Workforce> Workforcelist = Field.ofSingleSelectionType(Workforces).label("Workforces: ");
        Button Add_Workforce = new Button("Add Workforce");
        Add_Workforce.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Workforce force = Workforcelist.getSelection();
                if (force != null) {
                    if (!user.getWorkforces().contains(force) && !Workforces_to_Add.contains(force)) {
                        Workforces_to_Add.add(force);
                        String Workforcelabel = "Added Workforces: [";
                        for (Workforce added_force : user.getWorkforces()) {
                            Workforcelabel = Workforcelabel.concat("; " + added_force.toString());
                        }
                        for (Workforce added_force : Workforces_to_Add) {
                            Workforcelabel = Workforcelabel.concat("; " + added_force.toString());
                        }
                        Workforcelabel = Workforcelabel.concat("]");
                        AddedWorkforces.setText(Workforcelabel);
                    }
                }
            }
        });

        Button Remove_Workforce = new Button("Remove Workforce");
        Remove_Workforce.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Workforce force = Workforcelist.getSelection();
                if (force != null) {
                    if (user.getWorkforces().contains(force) || Workforces_to_Add.contains(force)) {
                        Workforces_to_Remove.add(force);
                    }

                    String Workforcelabel = "Added Workforces: [ ";
                    for (Workforce added_force : user.getWorkforces()) {
                        if (!Workforces_to_Remove.contains(added_force)) {
                            Workforcelabel = Workforcelabel.concat("; " + added_force.toString());
                        }
                    }
                    Workforcelabel = Workforcelabel.concat(" ]");
                    AddedWorkforces.setText(Workforcelabel);
                }
            }
        });
        ButtonBar WorkforcesBar = new ButtonBar();
        WorkforcesBar.getButtons().add(Add_Workforce);
        WorkforcesBar.getButtons().add(Remove_Workforce);

        FormRenderer EDIT_UI = new FormRenderer(Form.of(Group.of(P_ID, FirstName, LastName, Efficiency)));
        FormRenderer WORKFORCE_SELECTION_UI = new FormRenderer(Form.of(Group.of(Workforcelist)));
        Editbox.getChildren().add(EDIT_UI);
        Editbox.getChildren().add(AddedWorkforces);
        Editbox.getChildren().add(WORKFORCE_SELECTION_UI);
        Editbox.getChildren().add(WorkforcesBar);
        Editbox.getChildren().add(new Separator());
        Editbox.getChildren().add(Save);
    }

    private void generateShowUI() {
        Editbox.getChildren().clear();
        StringField P_ID = Field.ofStringType("").label("ID: ").editable(false);
        StringField FirstName = Field.ofStringType("").label("Firstname: ");
        StringField LastName = Field.ofStringType("").label("Lastname: ");
        StringField Efficiency = Field.ofStringType("").label("Efficiency: ");

        ButtonBar Save = new ButtonBar();
        Button Save_Button = new Button("Save User");
        Save_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });

        Button Remove_Button = new Button("Remove user");
        Remove_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });
        Remove_Button.setDisable(true);
        Save.getButtons().add(Remove_Button);
        Save.getButtons().add(Save_Button);
        List<Workforce> emptyList = new ArrayList<>();
        Label AddedWorkforces = new Label(" ");
        SingleSelectionField<Workforce> Workforcelist = Field.ofSingleSelectionType(emptyList).label("Workforces: ");
        Button Add_Workforce = new Button("Add Workforce");
        Add_Workforce.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });

        Button Remove_Workforce = new Button("Remove Workforce");
        Remove_Workforce.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });

        ButtonBar WorkforcesBar = new ButtonBar();
        WorkforcesBar.getButtons().add(Add_Workforce);
        WorkforcesBar.getButtons().add(Remove_Workforce);

        P_ID.editable(false);
        FirstName.editable(false);
        LastName.editable(false);
        Efficiency.editable(false);
        Workforcelist.editable(true);
        Add_Workforce.setDisable(true);
        Remove_Workforce.setDisable(true);
        Save_Button.setDisable(true);
        FormRenderer EDIT_UI = new FormRenderer(Form.of(Group.of(P_ID, FirstName, LastName, Efficiency)));
        FormRenderer WORKFORCE_SELECTION_UI = new FormRenderer(Form.of(Group.of(Workforcelist)));
        Editbox.getChildren().add(EDIT_UI);
        Editbox.getChildren().add(AddedWorkforces);
        Editbox.getChildren().add(WORKFORCE_SELECTION_UI);
        Editbox.getChildren().add(WorkforcesBar);
        Editbox.getChildren().add(new Separator());
        Editbox.getChildren().add(Save);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == OK_Button) {
            thisstage.close();
        }
    }
}
