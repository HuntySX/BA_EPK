package com.company.UI.EPKUI;

import com.company.EPK.EPK_Node;
import com.company.EPK.Function;
import com.company.EPK.Workforce;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.List;

public class UI_Func extends Function implements UI_Instantiable {
    private VBox Box;
    private VBox Rightbox;

    private UI_EPK EPK;
    private List<EPK_Node> nodelist;
    private List<Workforce> Workforces;
    private List<Resource> Resources;
    private List<Resource> needed_resource_List;
    private List<Workforce> needed_workforces_List;

    private IntegerProperty UI_ID;
    private StringProperty tag;
    private BooleanProperty concurrently = new SimpleBooleanProperty(true);
    private IntegerProperty Workingtime_Hours = new SimpleIntegerProperty();
    private IntegerProperty Workingtime_Minutes = new SimpleIntegerProperty();
    private IntegerProperty Workingtime_Seconds = new SimpleIntegerProperty();
    private BooleanProperty isDeterministic = new SimpleBooleanProperty(true);
    private boolean ISDet_UI = true;


    private StringField UI_TAG_FIELD;
    private IntegerField UI_ID_FIELD;
    private IntegerField UI_WORKINGTIME_HOURS_FIELD;
    private IntegerField UI_WORKINGTIME_MINUTES_FIELD;
    private IntegerField UI_WORKINGTIME_SECONDS_FIELD;
    private IntegerProperty MIN_NONDET_Workingtime_Hours = new SimpleIntegerProperty();
    private IntegerProperty MIN_NONDET_Workingtime_Minutes = new SimpleIntegerProperty();
    private IntegerProperty MIN_NONDET_Workingtime_Seconds = new SimpleIntegerProperty();
    private IntegerProperty MAX_NONDET_Workingtime_Hours = new SimpleIntegerProperty();
    private IntegerProperty MAX_NONDET_Workingtime_Minutes = new SimpleIntegerProperty();
    private IntegerProperty MAX_NONDET_Workingtime_Seconds = new SimpleIntegerProperty();
    private IntegerProperty MEAN_NONDET_Workingtime_Hours = new SimpleIntegerProperty();
    private IntegerProperty MEAN_NONDET_Workingtime_Minutes = new SimpleIntegerProperty();
    private IntegerProperty MEAN_NONDET_Workingtime_Seconds = new SimpleIntegerProperty();
    private IntegerProperty DEVIATION_NONDET_Workingtime_Hours = new SimpleIntegerProperty();
    private IntegerProperty DEVIATION_NONDET_Workingtime_Minutes = new SimpleIntegerProperty();
    private IntegerProperty DEVIATION_NONDET_Workingtime_Seconds = new SimpleIntegerProperty();
    private IntegerField UI_MIN_NONDET_WORKINGTIME_HOURS_FIELD;
    private IntegerField UI_MIN_NONDET_WORKINGTIME_MINUTES_FIELD;
    private IntegerField UI_MIN_NONDET_WORKINGTIME_SECONDS_FIELD;
    private IntegerField UI_MAX_NONDET_WORKINGTIME_HOURS_FIELD;
    private IntegerField UI_MAX_NONDET_WORKINGTIME_MINUTES_FIELD;
    private IntegerField UI_MAX_NONDET_WORKINGTIME_SECONDS_FIELD;
    private IntegerField UI_MEAN_NONDET_WORKINGTIME_HOURS_FIELD;
    private IntegerField UI_MEAN_NONDET_WORKINGTIME_MINUTES_FIELD;
    private IntegerField UI_MEAN_NONDET_WORKINGTIME_SECONDS_FIELD;
    private IntegerField UI_DEVIATION_NONDET_WORKINGTIME_HOURS_FIELD;
    private IntegerField UI_DEVIATION_NONDET_WORKINGTIME_MINUTES_FIELD;
    private IntegerField UI_DEVIATION_NONDET_WORKINGTIME_SECONDS_FIELD;
    private FormRenderer NONDET_WORKINGTIME_UI;
    private FormRenderer DETERMINISTIC_UI;
    private BooleanField UI_IS_DETERMINISTIC;
    private IntegerField UI_RESOURCE_COUNT;
    private SingleSelectionField<Resource> UI_NEEDED_RESOURCES_FIELD;
    private SingleSelectionField<Workforce> UI_NEEDED_WORKFORCES_FIELD;
    private SingleSelectionField<EPK_Node> UI_NEXT_ELEMENTS;
    private Function self;
    private FormRenderer RESOURCES_UI;
    private FormRenderer WORKFORCES_UI;
    private FormRenderer WORKINGTIME_UI;
    private FormRenderer ID_TAG_UI;
    private FormRenderer NEXT_ELEMS_UI;


    public UI_Func(int ID, UI_EPK EPK, VBox Rightbox) {
        super(null, ID, null, false, null, null, 0, 0, 10);
        self = this;
        this.Box = new VBox();
        this.Rightbox = Rightbox;
        this.EPK = EPK;
        this.Resources = EPK.getAll_Resources();
        this.Workforces = EPK.getAll_Workforces();
        needed_resource_List = getNeeded_Resources();
        needed_workforces_List = getNeeded_Workforce();
        UI_ID = new SimpleIntegerProperty(ID);
        StringBuilder ID_Build = new StringBuilder("Function: ");
        ID_Build.append(ID);
        this.tag = new SimpleStringProperty(ID_Build.toString());
        this.nodelist = getNext_Elem();

        UI_ID_FIELD = Field.ofIntegerType(UI_ID).label("ID").editable(false);
        UI_TAG_FIELD = Field.ofStringType(tag).label("Knotenname:");
        UI_WORKINGTIME_HOURS_FIELD = Field.ofIntegerType(Workingtime_Hours).label("Stunden").placeholder("0").tooltip("Arbeitszeitstunden");
        UI_WORKINGTIME_MINUTES_FIELD = Field.ofIntegerType(Workingtime_Minutes).label("Minuten").placeholder("0").tooltip("Arbeitszeitminuten");
        UI_WORKINGTIME_SECONDS_FIELD = Field.ofIntegerType(Workingtime_Seconds).label("Sekunden").placeholder("60").tooltip("Arbeitszeitsekunden");
        ID_TAG_UI = new FormRenderer(Form.of(Group.of(UI_ID_FIELD, UI_TAG_FIELD)));


        UI_IS_DETERMINISTIC = Field.ofBooleanType(isDeterministic).label("Deterministic").placeholder("true").tooltip("Determisitic / Nondeterministic Workingtime");
        DETERMINISTIC_UI = new FormRenderer(Form.of(Group.of(UI_IS_DETERMINISTIC)));
        WORKINGTIME_UI = new FormRenderer(Form.of(Group.of(UI_WORKINGTIME_HOURS_FIELD, UI_WORKINGTIME_MINUTES_FIELD, UI_WORKINGTIME_SECONDS_FIELD)));

        UI_MIN_NONDET_WORKINGTIME_HOURS_FIELD = Field.ofIntegerType(MIN_NONDET_Workingtime_Hours).label("Min - Hours").placeholder("0").tooltip("Minimal Workingtime(Hours)");
        UI_MIN_NONDET_WORKINGTIME_MINUTES_FIELD = Field.ofIntegerType(MIN_NONDET_Workingtime_Minutes).label("Min - Minutes").placeholder("0").tooltip("Minimal Workingtime(Minutes)");
        ;
        UI_MIN_NONDET_WORKINGTIME_SECONDS_FIELD = Field.ofIntegerType(MIN_NONDET_Workingtime_Seconds).label("Min - Seconds").placeholder("0").tooltip("Minimal Workingtime(Seconds)");
        ;
        UI_MAX_NONDET_WORKINGTIME_HOURS_FIELD = Field.ofIntegerType(MAX_NONDET_Workingtime_Hours).label("Max - Hours").placeholder("0").tooltip("Maximum Workingtime(Hours)");
        ;
        UI_MAX_NONDET_WORKINGTIME_MINUTES_FIELD = Field.ofIntegerType(MAX_NONDET_Workingtime_Minutes).label("Max - Minutes").placeholder("0").tooltip("Maximum Workingtime(Minutes)");
        ;
        UI_MAX_NONDET_WORKINGTIME_SECONDS_FIELD = Field.ofIntegerType(MAX_NONDET_Workingtime_Seconds).label("Max - Seconds").placeholder("0").tooltip("Maximum Workingtime(Seconds)");
        ;
        UI_MEAN_NONDET_WORKINGTIME_HOURS_FIELD = Field.ofIntegerType(MEAN_NONDET_Workingtime_Hours).label("Mean - Hours").placeholder("0").tooltip("Mean Workingtime(Hours)");
        ;
        UI_MEAN_NONDET_WORKINGTIME_MINUTES_FIELD = Field.ofIntegerType(MEAN_NONDET_Workingtime_Minutes).label("Mean - Minutes").placeholder("0").tooltip("Mean Workingtime(Minutes)");
        ;
        UI_MEAN_NONDET_WORKINGTIME_SECONDS_FIELD = Field.ofIntegerType(MEAN_NONDET_Workingtime_Seconds).label("Mean - Seconds").placeholder("0").tooltip("Mean Workingtime(Seconds)");
        ;
        UI_DEVIATION_NONDET_WORKINGTIME_HOURS_FIELD = Field.ofIntegerType(DEVIATION_NONDET_Workingtime_Hours).label("Deviation - Stunden").placeholder("0").tooltip("Deviation Workingtime(Hours)");
        ;
        UI_DEVIATION_NONDET_WORKINGTIME_MINUTES_FIELD = Field.ofIntegerType(DEVIATION_NONDET_Workingtime_Minutes).label("Deviation - Minutes").placeholder("0").tooltip("Deviation Workingtime(Minutes)");
        ;
        UI_DEVIATION_NONDET_WORKINGTIME_SECONDS_FIELD = Field.ofIntegerType(DEVIATION_NONDET_Workingtime_Seconds).label("Deviation - Seconds").placeholder("0").tooltip("Deviation Workingtime(Seconds)");
        ;

        NONDET_WORKINGTIME_UI = new FormRenderer(Form.of(Group.of(
                UI_MIN_NONDET_WORKINGTIME_HOURS_FIELD, UI_MIN_NONDET_WORKINGTIME_MINUTES_FIELD, UI_MIN_NONDET_WORKINGTIME_SECONDS_FIELD,
                UI_MAX_NONDET_WORKINGTIME_HOURS_FIELD, UI_MAX_NONDET_WORKINGTIME_MINUTES_FIELD, UI_MAX_NONDET_WORKINGTIME_SECONDS_FIELD,
                UI_MEAN_NONDET_WORKINGTIME_HOURS_FIELD, UI_MEAN_NONDET_WORKINGTIME_MINUTES_FIELD, UI_MEAN_NONDET_WORKINGTIME_SECONDS_FIELD,
                UI_DEVIATION_NONDET_WORKINGTIME_HOURS_FIELD, UI_DEVIATION_NONDET_WORKINGTIME_MINUTES_FIELD, UI_DEVIATION_NONDET_WORKINGTIME_SECONDS_FIELD)));
    }

    @Override
    public VBox Get_UI() {

        if (UI_IS_DETERMINISTIC.getValue() != ISDet_UI) {
            isDeterministic.setValue(ISDet_UI);
            UI_IS_DETERMINISTIC.reset();
        }

        UI_NEEDED_RESOURCES_FIELD = Field.ofSingleSelectionType(Resources).label("Benötigte Ressourcen").tooltip("Benötigte Ressourcen");
        UI_RESOURCE_COUNT = Field.ofIntegerType(0).label("Count").tooltip("Choose number of selected resource to add to the function");
        UI_NEEDED_WORKFORCES_FIELD = Field.ofSingleSelectionType(Workforces).label("Benötigte Arbeitskraft").tooltip("Benötigte Arbeitskraft");

        RESOURCES_UI = new FormRenderer(Form.of(Group.of(UI_NEEDED_RESOURCES_FIELD, UI_RESOURCE_COUNT)));
        WORKFORCES_UI = new FormRenderer(Form.of(Group.of(UI_NEEDED_WORKFORCES_FIELD)));


        Box.getChildren().clear();
        Box.getChildren().add(ID_TAG_UI);
        Box.getChildren().add(new Separator());
        UI_NEXT_ELEMENTS = Field.ofSingleSelectionType(nodelist).label("Nachfolger");
        NEXT_ELEMS_UI = new FormRenderer(Form.of(Group.of(UI_NEXT_ELEMENTS)));
        Box.getChildren().add(NEXT_ELEMS_UI);
        Box.getChildren().add(new Separator());
        Button btn = new Button("Verbindung entfernen");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                EPK_Node Node = UI_NEXT_ELEMENTS.getSelection();
                if (Node != null) {
                    EPK.getActive_Elem().getEPKNode().getNext_Elem().remove(Node);
                    EPK.getModel().removeEdge(getID(), Node.getID());
                    EPK.getGraph().endUpdate();
                    EPK.activate();
                }
            }
        });
        Label AddedResources = new Label();
        String Resourcelabel = "Added Resources: [ ";
        for (Resource added_res : needed_resource_List) {
            Resourcelabel = Resourcelabel.concat("; " + added_res.toString());
        }
        Resourcelabel = Resourcelabel.concat("]");
        AddedResources.setText(Resourcelabel);

        Button Add_Resource = new Button("Add resource");
        Add_Resource.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Resource resource = UI_NEEDED_RESOURCES_FIELD.getSelection();
                Integer count = UI_RESOURCE_COUNT.getValue();
                if (resource != null && count != 0) {
                    boolean found = false;
                    for (Resource in_List : needed_resource_List) {
                        if (in_List.getID() == resource.getID()) {
                            found = true;
                            in_List.setCount(in_List.getCount() + count);
                            break;
                        }
                    }

                    if (!found) {
                        Resource to_Add = new Resource(resource.getName(), count, resource.getID());
                        needed_resource_List.add(to_Add);
                        resource.add_Used_In(self);
                    }
                    String Resourcelabel = "Added Resources: [";
                    for (Resource added_res : needed_resource_List) {
                        Resourcelabel = Resourcelabel.concat("; " + added_res.toString());
                    }
                    Resourcelabel = Resourcelabel.concat("]");
                    AddedResources.setText(Resourcelabel);
                }
            }
        });

        Button Remove_Resource = new Button("Remove resource");
        Remove_Resource.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Resource resource = UI_NEEDED_RESOURCES_FIELD.getSelection();
                Resource to_Delete = null;
                if (resource != null) {
                    for (Resource in_List : needed_resource_List) {
                        if (in_List.getID() == resource.getID()) {
                            to_Delete = in_List;
                            break;
                        }
                    }
                    if (to_Delete != null) {
                        needed_resource_List.remove(to_Delete);
                        to_Delete = null;
                        resource.removeResourcesUsed(self);
                        String Resourcelabel = "Added Resources: [";
                        for (Resource added_res : needed_resource_List) {
                            Resourcelabel = Resourcelabel.concat("; " + added_res.toString());
                        }
                        Resourcelabel = Resourcelabel.concat("]");
                        AddedResources.setText(Resourcelabel);
                    }
                }
            }
        });
        ButtonBar Resourcebar = new ButtonBar();
        Resourcebar.getButtons().add(Add_Resource);
        Resourcebar.getButtons().add(Remove_Resource);

        Label AddedWorkforces = new Label();
        String WorkforceLabel = "Added Workforces: [";
        for (Workforce added_force : needed_workforces_List) {
            WorkforceLabel = WorkforceLabel.concat("; " + added_force.toString());
        }
        WorkforceLabel = WorkforceLabel.concat("]");
        AddedWorkforces.setText(WorkforceLabel);

        Button Add_Workforce = new Button("Add Workforce");
        Add_Workforce.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Workforce force = UI_NEEDED_WORKFORCES_FIELD.getSelection();
                if (force != null) {
                    if (!needed_workforces_List.contains(force)) {
                        needed_workforces_List.add(force);
                        force.AddUsedIn(self);
                        String Workforcelabel = "Added Workforces: [ ";
                        for (Workforce added_force : needed_workforces_List) {
                            Workforcelabel = Workforcelabel.concat("; " + added_force.toString());
                        }
                        Workforcelabel = Workforcelabel.concat(" ]");
                        AddedWorkforces.setText(Workforcelabel);
                    }
                }
            }
        });
        Button Remove_Workforce = new Button("Remove Workforce");
        Remove_Workforce.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Workforce force = UI_NEEDED_WORKFORCES_FIELD.getSelection();
                if (force != null) {
                    if (needed_workforces_List.contains(force)) {
                        needed_workforces_List.remove(force);
                        force.removeUsedIn(self);
                        String Workforcelabel = new String("Added Workforces: [ ");
                        for (Workforce added_force : needed_workforces_List) {
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

        Button Change_Det = new Button("Change");
        Change_Det.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (UI_IS_DETERMINISTIC.getValue() != ISDet_UI) {
                    if (UI_IS_DETERMINISTIC.getValue()) {
                        MIN_NONDET_Workingtime_Hours.setValue(0);
                        MIN_NONDET_Workingtime_Minutes.setValue(0);
                        MIN_NONDET_Workingtime_Seconds.setValue(0);
                        MAX_NONDET_Workingtime_Hours.setValue(0);
                        MAX_NONDET_Workingtime_Minutes.setValue(0);
                        MAX_NONDET_Workingtime_Seconds.setValue(0);
                        MEAN_NONDET_Workingtime_Hours.setValue(0);
                        MEAN_NONDET_Workingtime_Minutes.setValue(0);
                        MEAN_NONDET_Workingtime_Seconds.setValue(0);
                        DEVIATION_NONDET_Workingtime_Hours.setValue(0);
                        DEVIATION_NONDET_Workingtime_Minutes.setValue(0);
                        DEVIATION_NONDET_Workingtime_Seconds.setValue(0);
                    } else {
                        Workingtime_Hours.setValue(0);
                        Workingtime_Minutes.setValue(0);
                        Workingtime_Seconds.setValue(0);
                    }
                    ISDet_UI = UI_IS_DETERMINISTIC.getValue();
                    EPK.activate();
                }
            }
        });
        ButtonBar Changebar = new ButtonBar();
        Changebar.getButtons().add(Change_Det);


        Box.getChildren().add(btn);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(new Label("Det/NonDet Workingtime"));
        Box.getChildren().add(DETERMINISTIC_UI);
        Box.getChildren().add(Changebar);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(new Label("Arbeitsstunden"));
        if (!ISDet_UI) {
            Box.getChildren().add(NONDET_WORKINGTIME_UI);
        } else {
            Box.getChildren().add(WORKINGTIME_UI);
        }
        Box.getChildren().add(new Label("Benötigte Ressourcen"));
        Box.getChildren().add(AddedResources);
        Box.getChildren().add(RESOURCES_UI);
        Box.getChildren().add(Resourcebar);
        Box.getChildren().add(new Label("Benötigte Arbeitskraft"));
        Box.getChildren().add(AddedWorkforces);
        Box.getChildren().add(WORKFORCES_UI);
        Box.getChildren().add(WorkforcesBar);

        return Box;
    }

    @Override
    public List<EPK_Node> getNodelist() {
        return nodelist;
    }

    @Override
    public void save_Settings() {
        setFunction_tag(UI_TAG_FIELD.getValue());

        if (ISDet_UI) {
            setDeterministic(true);
            setWorkingHours(UI_WORKINGTIME_HOURS_FIELD.getValue());
            setWorkingMinutes(UI_WORKINGTIME_MINUTES_FIELD.getValue());
            setWorkingSeconds(UI_WORKINGTIME_SECONDS_FIELD.getValue());
            setMin_Workingtime(new Workingtime());
            setMax_Workingtime(new Workingtime());
            setMean_Workingtime(new Workingtime());
            setDeviation_Workintime(new Workingtime());
        } else {
            setDeterministic(false);
            setWorkingHours(0);
            setWorkingMinutes(0);
            setWorkingSeconds(0);
            setMin_Workingtime(new Workingtime(UI_MIN_NONDET_WORKINGTIME_HOURS_FIELD.getValue(),
                    UI_MIN_NONDET_WORKINGTIME_MINUTES_FIELD.getValue(),
                    UI_MIN_NONDET_WORKINGTIME_SECONDS_FIELD.getValue()));
            setMax_Workingtime(new Workingtime(UI_MAX_NONDET_WORKINGTIME_HOURS_FIELD.getValue(),
                    UI_MAX_NONDET_WORKINGTIME_MINUTES_FIELD.getValue(),
                    UI_MAX_NONDET_WORKINGTIME_SECONDS_FIELD.getValue()));
            setMean_Workingtime(new Workingtime(UI_MEAN_NONDET_WORKINGTIME_HOURS_FIELD.getValue(),
                    UI_MEAN_NONDET_WORKINGTIME_MINUTES_FIELD.getValue(),
                    UI_MEAN_NONDET_WORKINGTIME_SECONDS_FIELD.getValue()));
            setDeviation_Workintime(new Workingtime(UI_DEVIATION_NONDET_WORKINGTIME_HOURS_FIELD.getValue(),
                    UI_DEVIATION_NONDET_WORKINGTIME_MINUTES_FIELD.getValue(),
                    UI_DEVIATION_NONDET_WORKINGTIME_SECONDS_FIELD.getValue()));
        }

    }

    @Override
    public int get_Next_Elem_ID() {
        return 0;
    }

    @Override
    public String toString() {
        return "Function [" +
                "ID: " + UI_ID_FIELD.getValue() + ";"
                + " Tag: " + tag.getValue() +
                ']';

    }

    @Override
    public EPK_Node getthis() {
        return super.returnUpperClass();
    }
}
