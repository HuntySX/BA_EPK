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
        Button Cancel_Changes = new Button("Cancel");
        Cancel_Changes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                generateUI();
            }
        });
        Button Remove_Resource = new Button("Remove Resource");
        Remove_Resource.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                {
                    for (Function f : resource.getUsed_In()) {
                        f.removeResourcebyID(resource);
                    }
                    resource.getUsed_In().clear();
                    EPK.RemoveResource(resource);
                    generateUI();
                }
            }
        });

        Save.getButtons().add(Remove_Resource);
        Save.getButtons().add(Save_Button);
        Save.getButtons().add(Cancel_Changes);

        Remove_Resource.setDisable(true);
        Label AddedFunctions = new Label();
        String Functionlabel = new String("Added functions: [");
        for (Function added_function : resource.getUsed_In()) {
            Functionlabel = Functionlabel.concat("; " + added_function.toString());
        }
        Functionlabel = Functionlabel.concat("]");
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
                    Functionlabel = Functionlabel.concat("]");
                    AddedFunctions.setText(Functionlabel);
                }
            }
        });
        Button Remove_from_functions = new Button("Remove function");
        Remove_from_functions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });

        Remove_from_functions.setDisable(true);
        Clear_functions.setDisable(true);
        ButtonBar Functionbar = new ButtonBar();
        Functionbar.getButtons().add(Clear_functions);
        Functionbar.getButtons().add(Remove_from_functions);

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
        List<Function> Original_Res = resource.getUsed_In();
        List<Function> to_Remove = new ArrayList<>();
        ButtonBar Save = new ButtonBar();
        Button Save_Button = new Button("Save changes");
        Save_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resource.setCount(Count.getValue());
                resource.setName(Resourcename.getValue());
                for (Function remove_res : to_Remove) {
                    remove_res.removeResourcebyID(resource);
                }
                resource.getUsed_In().removeAll(to_Remove);
                EPK.AddResource(resource);
                generateUI();
            }
        });
        Button Cancel_Changes = new Button("Cancel");
        Cancel_Changes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                generateUI();
            }
        });
        Button Remove_Resource = new Button("Remove Resource");
        Remove_Resource.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                {
                    for (Function f : resource.getUsed_In()) {
                        f.removeResourcebyID(resource);
                    }
                    resource.getUsed_In().clear();
                    EPK.RemoveResource(resource);
                    generateUI();
                }
            }
        });

        Save.getButtons().add(Remove_Resource);
        Save.getButtons().add(Save_Button);
        Save.getButtons().add(Cancel_Changes);

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
                        if (!to_Remove.contains(f)) {
                            to_Remove.add(f);
                        }
                    }
                    String Functionlabel = "Added functions: [ ";
                    Functionlabel.concat("]");
                    AddedFunctions.setText(Functionlabel);
                }
            }
        });
        Button Remove_from_functions = new Button("Remove function");
        Remove_from_functions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (Functionlist.getSelection() != null) {
                    Function removeFunction = Functionlist.getSelection();
                    if (removeFunction != null) {
                        if (!to_Remove.contains(removeFunction)) {
                            to_Remove.add(removeFunction);
                        }
                    }

                    String Functionlabel = new String("Added functions: [");
                    for (Function added_function : resource.getUsed_In()) {
                        if (!to_Remove.contains(added_function)) {
                            Functionlabel = Functionlabel.concat("; " + added_function.toString());
                        }
                    }
                    Functionlabel = Functionlabel.concat("]");
                    AddedFunctions.setText(Functionlabel);
                }
            }
        });

        Clear_functions.setDisable(false);
        ButtonBar Functionbar = new ButtonBar();
        Functionbar.getButtons().add(Clear_functions);
        Functionbar.getButtons().add(Remove_from_functions);

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
        Button Save_Button = new Button("Save changes");
        Save_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        Button Cancel_Changes = new Button("Cancel");
        Cancel_Changes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        Button Remove_Resource = new Button("Remove Resource");
        Remove_Resource.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });
        Save.getButtons().add(Remove_Resource);
        Save.getButtons().add(Save_Button);
        Save.getButtons().add(Cancel_Changes);
        Remove_Resource.setDisable(true);
        Cancel_Changes.setDisable(true);
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

        Button Remove_from_functions = new Button("Remove function");
        Remove_from_functions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });

        Clear_functions.setDisable(true);
        Remove_from_functions.setDisable(true);
        ButtonBar Functionbar = new ButtonBar();
        Functionbar.getButtons().add(Clear_functions);
        Functionbar.getButtons().add(Remove_from_functions);

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
