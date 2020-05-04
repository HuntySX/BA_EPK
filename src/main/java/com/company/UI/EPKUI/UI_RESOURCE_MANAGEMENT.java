package com.company.UI.EPKUI;

import com.company.EPK.Function;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
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

public class UI_RESOURCE_MANAGEMENT implements Initializable {

    @FXML
    Button OK_Button;
    @FXML
    VBox Choosebox;
    @FXML
    VBox Editbox;
    @FXML
    VBox Showbox;

    private UI_EPK EPK;
    private List<Resource> Resources;
    private Stage Mainstage;
    private Stage this_stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.Resources = new ArrayList<>();
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
        Resources = EPK.getAll_Resources();
        generateShowUI();
        Label label = new Label("Choose resource to edit: ");
        SingleSelectionField<Resource> UI_Resources = Field.ofSingleSelectionType(Resources).label("Resources");
        FormRenderer RESOURCE_UI = new FormRenderer(Form.of(Group.of(UI_Resources)));
        Choosebox.getChildren().add(RESOURCE_UI);
        Button button1 = new Button("Edit selected resource");
        button1.setOnAction(actionEvent -> {
            Resource resource = UI_Resources.getSelection();
            if (resource != null) {
                showEditResourceUI(resource);
            }
        });
        Button button2 = new Button("New resource");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showNewResourceUI();
            }
        });
        ButtonBar bar = new ButtonBar();
        bar.getButtons().add(button1);
        bar.getButtons().add(button2);
        Choosebox.getChildren().add(bar);
    }

    private void showNewResourceUI() {
        Editbox.getChildren().clear();
        Resource resource = new Resource("", 0, EPK.getUniqueResourceID());
        IntegerField R_ID = Field.ofIntegerType(resource.getID()).label("ID: ").editable(false);
        StringField Resourcename = Field.ofStringType(resource.getName()).label("Resourcename:");
        IntegerField Count = Field.ofIntegerType(resource.getCount()).label("Quantity: ");

        ButtonBar Save = new ButtonBar();
        Button Save_Button = new Button("Save resource");
        Save_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resource.setCount(Count.getValue());
                resource.setName(Resourcename.getValue());
                EPK.AddResource(resource);
                generateUI();
            }
        });
        Save.getButtons().add(Save_Button);
        Label AddedFunctions = new Label();
        String Functionlabel = new String("Added functions: [");
        for (Function added_function : resource.getUsed_In()) {
            Functionlabel.concat("; " + added_function.toString());
        }
        Functionlabel.concat("]");
        AddedFunctions.setText(Functionlabel);
        List<Function> AddedFunctionlist = resource.getUsed_In();
        SingleSelectionField<Function> Functionlist = Field.ofSingleSelectionType(AddedFunctionlist).label("Used in: ");
        Button Clear_functions = new Button("Clear functions");
        Clear_functions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (AddedFunctionlist != null && !AddedFunctionlist.isEmpty()) {
                    for (Function f : AddedFunctionlist) {
                        f.removeResourcebyID(resource);
                    }
                    AddedFunctionlist.clear();
                    String Functionlabel = "Added functions: [ ";
                    Functionlabel.concat("]");
                    AddedFunctions.setText(Functionlabel);
                }
            }
        });

        Clear_functions.setDisable(true);
        ButtonBar Functionbar = new ButtonBar();
        Functionbar.getButtons().add(Clear_functions);

        FormRenderer EDIT_UI = new FormRenderer(Form.of(Group.of(R_ID, Resourcename, Count)));
        FormRenderer FUNCTION_UI = new FormRenderer(Form.of(Group.of(Functionlist)));
        Editbox.getChildren().add(EDIT_UI);
        Editbox.getChildren().add(AddedFunctions);
        Editbox.getChildren().add(FUNCTION_UI);
        Editbox.getChildren().add(Functionbar);
        Editbox.getChildren().add(new Separator());
        Editbox.getChildren().add(Save);
    }

    private void showEditResourceUI(Resource resource) {

        Editbox.getChildren().clear();
        IntegerField R_ID = Field.ofIntegerType(resource.getID()).label("ID: ").editable(false);
        StringField Resourcename = Field.ofStringType(resource.getName()).label("Resourcename:");
        IntegerField Count = Field.ofIntegerType(resource.getCount()).label("Quantity: ");

        ButtonBar Save = new ButtonBar();
        Button Save_Button = new Button("Save resource");
        Save_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resource.setCount(Count.getValue());
                resource.setName(Resourcename.getValue());
                EPK.AddResource(resource);
                generateUI();
            }
        });
        Save.getButtons().add(Save_Button);

        Label AddedFunctions = new Label();
        String Functionlabel = new String("Added functions: [");
        for (Function added_function : resource.getUsed_In()) {
            Functionlabel.concat("; " + added_function.toString());
        }
        Functionlabel.concat("]");
        AddedFunctions.setText(Functionlabel);
        List<Function> AddedFunctionlist = resource.getUsed_In();
        SingleSelectionField<Function> Functionlist = Field.ofSingleSelectionType(AddedFunctionlist).label("Used in: ");
        Button Clear_functions = new Button("Clear functions");
        Clear_functions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (AddedFunctionlist != null && !AddedFunctionlist.isEmpty()) {
                    for (Function f : AddedFunctionlist) {
                        f.removeResourcebyID(resource);
                    }
                    AddedFunctionlist.clear();
                    String Functionlabel = "Added functions: [ ";
                    Functionlabel.concat("]");
                    AddedFunctions.setText(Functionlabel);
                }
            }
        });

        Clear_functions.setDisable(false);
        ButtonBar Functionbar = new ButtonBar();
        Functionbar.getButtons().add(Clear_functions);

        FormRenderer EDIT_UI = new FormRenderer(Form.of(Group.of(R_ID, Resourcename, Count)));
        FormRenderer FUNCTION_UI = new FormRenderer(Form.of(Group.of(Functionlist)));
        Editbox.getChildren().add(EDIT_UI);
        Editbox.getChildren().add(AddedFunctions);
        Editbox.getChildren().add(FUNCTION_UI);
        Editbox.getChildren().add(Functionbar);
        Editbox.getChildren().add(new Separator());
        Editbox.getChildren().add(Save);
    }

    private void generateShowUI() {
        Editbox.getChildren().clear();
        StringField R_ID = Field.ofStringType("").label("ID: ").editable(false);
        StringField Resourcename = Field.ofStringType("").label("Resourcename:");
        StringField Count = Field.ofStringType("").label("Quantity: ");

        ButtonBar Save = new ButtonBar();
        Button Save_Button = new Button("Save resource");
        Save_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        Save.getButtons().add(Save_Button);
        Save_Button.setDisable(true);

        Label AddedFunctions = new Label();
        String Functionlabel = new String("Added functions: [ ");
        Functionlabel.concat("]");
        AddedFunctions.setText(Functionlabel);
        List<Function> emptylist = new ArrayList<>();
        SingleSelectionField<Function> Functionlist = Field.ofSingleSelectionType(emptylist).label("Used in: ");
        Button Clear_functions = new Button("Clear functions");
        Clear_functions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });
        Save_Button.setDisable(true);
        Clear_functions.setDisable(true);
        ButtonBar Functionbar = new ButtonBar();
        Functionbar.getButtons().add(Clear_functions);

        R_ID.editable(false);
        Resourcename.editable(false);
        Count.editable(false);
        FormRenderer EDIT_UI = new FormRenderer(Form.of(Group.of(R_ID, Resourcename, Count)));
        FormRenderer FUNCTION_UI = new FormRenderer(Form.of(Group.of(Functionlist)));
        Editbox.getChildren().add(EDIT_UI);
        Editbox.getChildren().add(AddedFunctions);
        Editbox.getChildren().add(FUNCTION_UI);
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
