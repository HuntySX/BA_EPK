package com.company.UI.EPKUI;

import com.company.EPK.Activating_Function;
import com.company.EPK.Activating_Start_Event;
import com.company.EPK.EPK_Node;
import com.company.EPK.Workforce;
import com.company.Enums.Decide_Activation_Type;
import com.company.Enums.Function_Type;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Event_Calendar;
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

public class UI_Activate_Function extends Activating_Function implements UI_Instantiable {
    private VBox Box;
    int Selection = 0;
    private VBox Rightbox;
    private UI_EPK EPK;
    private List<EPK_Node> nodelist;
    private List<Workforce> Workforces;
    private List<Resource> Resources;
    private List<Resource> needed_resource_List;
    private List<Workforce> needed_workforces_List;
    private List<UI_Event_Activating_Starter> Activating_Start_Events;
    private List<Decide_Activation_Type> Activation_Types;
    private final Label Activating_Event_Label = new Label("Ausgewähltes Start Event: ");
    private StringProperty tag;
    private final BooleanProperty concurrently = new SimpleBooleanProperty(true);
    private final IntegerProperty Workingtime_Hours = new SimpleIntegerProperty();
    private final IntegerProperty Workingtime_Minutes = new SimpleIntegerProperty();
    private final IntegerProperty Workingtime_Seconds = new SimpleIntegerProperty();
    private IntegerProperty UI_ID;
    private IntegerProperty Chance;
    private final IntegerProperty Ordertime_Hours = new SimpleIntegerProperty();
    private final IntegerProperty Ordertime_Minutes = new SimpleIntegerProperty();
    private final IntegerProperty Ordertime_Seconds = new SimpleIntegerProperty();

    private final BooleanProperty isDeterministic_Workingtime = new SimpleBooleanProperty(true);
    private boolean ISDet_Workingtime_UI = true;
    private BooleanField UI_IS_DETERMINISTIC_WORKINGTIME;

    private final BooleanProperty isDeterministic_Ordertime = new SimpleBooleanProperty(true);
    private boolean ISDet_Ordertime_UI = true;
    private BooleanField UI_IS_DETERMINISTIC_ORDERTIME;

    private final IntegerProperty MIN_NONDET_Workingtime_Hours = new SimpleIntegerProperty();
    private final IntegerProperty MIN_NONDET_Workingtime_Minutes = new SimpleIntegerProperty();
    private final IntegerProperty MIN_NONDET_Workingtime_Seconds = new SimpleIntegerProperty();
    private final IntegerProperty MAX_NONDET_Workingtime_Hours = new SimpleIntegerProperty();
    private final IntegerProperty MAX_NONDET_Workingtime_Minutes = new SimpleIntegerProperty();
    private final IntegerProperty MAX_NONDET_Workingtime_Seconds = new SimpleIntegerProperty();
    private final IntegerProperty MEAN_NONDET_Workingtime_Hours = new SimpleIntegerProperty();
    private final IntegerProperty MEAN_NONDET_Workingtime_Minutes = new SimpleIntegerProperty();
    private final IntegerProperty MEAN_NONDET_Workingtime_Seconds = new SimpleIntegerProperty();
    private final IntegerProperty DEVIATION_NONDET_Workingtime_Hours = new SimpleIntegerProperty();
    private final IntegerProperty DEVIATION_NONDET_Workingtime_Minutes = new SimpleIntegerProperty();
    private final IntegerProperty DEVIATION_NONDET_Workingtime_Seconds = new SimpleIntegerProperty();
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

    private final IntegerProperty MIN_NONDET_Ordertime_Hours = new SimpleIntegerProperty();
    private final IntegerProperty MIN_NONDET_Ordertime_Minutes = new SimpleIntegerProperty();
    private final IntegerProperty MIN_NONDET_Ordertime_Seconds = new SimpleIntegerProperty();
    private final IntegerProperty MAX_NONDET_Ordertime_Hours = new SimpleIntegerProperty();
    private final IntegerProperty MAX_NONDET_Ordertime_Minutes = new SimpleIntegerProperty();
    private final IntegerProperty MAX_NONDET_Ordertime_Seconds = new SimpleIntegerProperty();
    private final IntegerProperty MEAN_NONDET_Ordertime_Hours = new SimpleIntegerProperty();
    private final IntegerProperty MEAN_NONDET_Ordertime_Minutes = new SimpleIntegerProperty();
    private final IntegerProperty MEAN_NONDET_Ordertime_Seconds = new SimpleIntegerProperty();
    private final IntegerProperty DEVIATION_NONDET_Ordertime_Hours = new SimpleIntegerProperty();
    private final IntegerProperty DEVIATION_NONDET_Ordertime_Minutes = new SimpleIntegerProperty();
    private final IntegerProperty DEVIATION_NONDET_Ordertime_Seconds = new SimpleIntegerProperty();
    private IntegerField UI_MIN_NONDET_ORDERTIME_HOURS_FIELD;
    private IntegerField UI_MIN_NONDET_ORDERTIME_MINUTES_FIELD;
    private IntegerField UI_MIN_NONDET_ORDERTIME_SECONDS_FIELD;
    private IntegerField UI_MAX_NONDET_ORDERTIME_HOURS_FIELD;
    private IntegerField UI_MAX_NONDET_ORDERTIME_MINUTES_FIELD;
    private IntegerField UI_MAX_NONDET_ORDERTIME_SECONDS_FIELD;
    private IntegerField UI_MEAN_NONDET_ORDERTIME_HOURS_FIELD;
    private IntegerField UI_MEAN_NONDET_ORDERTIME_MINUTES_FIELD;
    private IntegerField UI_MEAN_NONDET_ORDERTIME_SECONDS_FIELD;
    private IntegerField UI_DEVIATION_NONDET_ORDERTIME_HOURS_FIELD;
    private IntegerField UI_DEVIATION_NONDET_ORDERTIME_MINUTES_FIELD;
    private IntegerField UI_DEVIATION_NONDET_ORDERTIME_SECONDS_FIELD;

    private UI_Event_Activating_Starter Chosen_Starter;
    private StringField UI_TAG_FIELD;
    private IntegerField UI_ID_FIELD;
    private IntegerField UI_WORKINGTIME_HOURS_FIELD;
    private IntegerField UI_WORKINGTIME_MINUTES_FIELD;
    private IntegerField UI_WORKINGTIME_SECONDS_FIELD;
    private IntegerField UI_ORDERTIME_HOURS_FIELD;
    private IntegerField UI_ORDERTIME_MINUTES_FIELD;
    private IntegerField UI_ORDERTIME_SECONDS_FIELD;
    private IntegerField UI_RESOURCE_COUNT;
    private IntegerField UI_CHANCE;
    private SingleSelectionField<Resource> UI_NEEDED_RESOURCES_FIELD;
    private SingleSelectionField<Workforce> UI_NEEDED_WORKFORCES_FIELD;
    private SingleSelectionField<EPK_Node> UI_NEXT_ELEMENTS;
    private FormRenderer RESOURCES_UI;
    private FormRenderer WORKFORCES_UI;
    private FormRenderer WORKINGTIME_UI;
    private FormRenderer CHANCE_UI;
    private FormRenderer ID_TAG_UI;
    private FormRenderer NEXT_ELEMS_UI;
    private FormRenderer UI_DECIDE_ACTIVATION_TYPE;
    private FormRenderer UI_ACTIVATING_EVENT;
    private FormRenderer ORDER_TIME_UI;
    private FormRenderer NONDET_WORKINGTIME_UI;
    private FormRenderer DETERMINISTIC_WORKINGTIME_UI;
    private FormRenderer NONDET_ORDERTIME_UI;
    private FormRenderer DETERMINISTIC_ORDERTIME_UI;
    private SingleSelectionField<Decide_Activation_Type> DECIDE_ACTIVATION_TYPE_FIELD;
    private SingleSelectionField<UI_Event_Activating_Starter> ACTIVATING_EVENT_FIELD;
    private SingleSelectionField<EPK_Node> UI_NEXT_ELEMENTS_FIELD;
    private Activating_Function self;

    public UI_Activate_Function(int ID, UI_EPK EPK, VBox Rightbox) {
        super(null, null, 0, null, null, ID, null, null, null);
        self = this;
        this.Box = new VBox();
        this.Rightbox = Rightbox;
        this.EPK = EPK;
        this.Resources = EPK.getAll_Resources();
        this.Workforces = EPK.getAll_Workforces();
        needed_resource_List = getNeeded_Resources();
        needed_workforces_List = getNeeded_Workforce();
        this.Activating_Start_Events = EPK.getAll_Activating_Start_Events();
        this.Activation_Types = EPK.getDecide_Activation_Types();
        this.UI_ID = new SimpleIntegerProperty(ID);
        Chosen_Starter = null;
        StringBuilder ID_Build = new StringBuilder("Activating-Function: ");
        ID_Build.append(ID);
        this.tag = new SimpleStringProperty(ID_Build.toString());
        UI_ID_FIELD = Field.ofIntegerType(UI_ID).label("ID").editable(false);
        UI_TAG_FIELD = Field.ofStringType(tag).label("Knotenname:");
        this.Chance = new SimpleIntegerProperty(getChance_for_instantiation());
        UI_CHANCE = Field.ofIntegerType(Chance).label("Chance for Instantiation");
        this.nodelist = getNext_Elem();

        DECIDE_ACTIVATION_TYPE_FIELD = Field.ofSingleSelectionType(Activation_Types, 0).label("Entscheidungstyp")
                .tooltip("Wahrscheinlichkeitsverteilung nach der Instanzen am ausgewählten" +
                        " Start Event generiert werden").editable(true);

        UI_ORDERTIME_HOURS_FIELD = Field.ofIntegerType(Ordertime_Hours).label("Stunden").placeholder("0").tooltip("Stunden bis Ankunft der Instanzierung");
        UI_ORDERTIME_MINUTES_FIELD = Field.ofIntegerType(Ordertime_Minutes).label("Minuten").placeholder("0").tooltip("Minuten bis Ankunft der Instanzierung");
        UI_ORDERTIME_SECONDS_FIELD = Field.ofIntegerType(Ordertime_Seconds).label("Sekunden").placeholder("60").tooltip("Sekunden bis Ankunft der Instanzierung");
        UI_WORKINGTIME_HOURS_FIELD = Field.ofIntegerType(Workingtime_Hours).label("Stunden").placeholder("0").tooltip("Arbeitszeitstunden");
        UI_WORKINGTIME_MINUTES_FIELD = Field.ofIntegerType(Workingtime_Minutes).label("Minuten").placeholder("0").tooltip("Arbeitszeitminuten");
        UI_WORKINGTIME_SECONDS_FIELD = Field.ofIntegerType(Workingtime_Seconds).label("Sekunden").placeholder("60").tooltip("Arbeitszeitsekunden");
        ID_TAG_UI = new FormRenderer(Form.of(Group.of(UI_ID_FIELD, UI_TAG_FIELD)));
        UI_DECIDE_ACTIVATION_TYPE = new FormRenderer(Form.of(Group.of(DECIDE_ACTIVATION_TYPE_FIELD)));
        CHANCE_UI = new FormRenderer(Form.of(Group.of(UI_CHANCE)));

        UI_IS_DETERMINISTIC_WORKINGTIME = Field.ofBooleanType(isDeterministic_Workingtime).label("Deterministic").placeholder("true").tooltip("Determisitic / Nondeterministic Workingtime");
        DETERMINISTIC_WORKINGTIME_UI = new FormRenderer(Form.of(Group.of(UI_IS_DETERMINISTIC_WORKINGTIME)));

        UI_IS_DETERMINISTIC_ORDERTIME = Field.ofBooleanType(isDeterministic_Ordertime).label("Deterministic").placeholder("true").tooltip("Determisitic / Nondeterministic Ordertime");
        DETERMINISTIC_ORDERTIME_UI = new FormRenderer(Form.of(Group.of(UI_IS_DETERMINISTIC_ORDERTIME)));

        UI_MIN_NONDET_WORKINGTIME_HOURS_FIELD = Field.ofIntegerType(MIN_NONDET_Workingtime_Hours).label("Min - Hours").placeholder("0").tooltip("Minimal Workingtime(Hours)");
        UI_MIN_NONDET_WORKINGTIME_MINUTES_FIELD = Field.ofIntegerType(MIN_NONDET_Workingtime_Minutes).label("Min - Minutes").placeholder("0").tooltip("Minimal Workingtime(Minutes)");
        UI_MIN_NONDET_WORKINGTIME_SECONDS_FIELD = Field.ofIntegerType(MIN_NONDET_Workingtime_Seconds).label("Min - Seconds").placeholder("0").tooltip("Minimal Workingtime(Seconds)");
        UI_MAX_NONDET_WORKINGTIME_HOURS_FIELD = Field.ofIntegerType(MAX_NONDET_Workingtime_Hours).label("Max - Hours").placeholder("0").tooltip("Maximum Workingtime(Hours)");
        UI_MAX_NONDET_WORKINGTIME_MINUTES_FIELD = Field.ofIntegerType(MAX_NONDET_Workingtime_Minutes).label("Max - Minutes").placeholder("0").tooltip("Maximum Workingtime(Minutes)");
        UI_MAX_NONDET_WORKINGTIME_SECONDS_FIELD = Field.ofIntegerType(MAX_NONDET_Workingtime_Seconds).label("Max - Seconds").placeholder("0").tooltip("Maximum Workingtime(Seconds)");
        UI_MEAN_NONDET_WORKINGTIME_HOURS_FIELD = Field.ofIntegerType(MEAN_NONDET_Workingtime_Hours).label("Mean - Hours").placeholder("0").tooltip("Mean Workingtime(Hours)");
        UI_MEAN_NONDET_WORKINGTIME_MINUTES_FIELD = Field.ofIntegerType(MEAN_NONDET_Workingtime_Minutes).label("Mean - Minutes").placeholder("0").tooltip("Mean Workingtime(Minutes)");
        UI_MEAN_NONDET_WORKINGTIME_SECONDS_FIELD = Field.ofIntegerType(MEAN_NONDET_Workingtime_Seconds).label("Mean - Seconds").placeholder("0").tooltip("Mean Workingtime(Seconds)");
        UI_DEVIATION_NONDET_WORKINGTIME_HOURS_FIELD = Field.ofIntegerType(DEVIATION_NONDET_Workingtime_Hours).label("Deviation - Stunden").placeholder("0").tooltip("Deviation Workingtime(Hours)");
        UI_DEVIATION_NONDET_WORKINGTIME_MINUTES_FIELD = Field.ofIntegerType(DEVIATION_NONDET_Workingtime_Minutes).label("Deviation - Minutes").placeholder("0").tooltip("Deviation Workingtime(Minutes)");
        UI_DEVIATION_NONDET_WORKINGTIME_SECONDS_FIELD = Field.ofIntegerType(DEVIATION_NONDET_Workingtime_Seconds).label("Deviation - Seconds").placeholder("0").tooltip("Deviation Workingtime(Seconds)");
        UI_MIN_NONDET_ORDERTIME_HOURS_FIELD = Field.ofIntegerType(MIN_NONDET_Ordertime_Hours).label("Min - Orderhours").placeholder("0").tooltip("Minimal Ordertime(Hours)");
        UI_MIN_NONDET_ORDERTIME_MINUTES_FIELD = Field.ofIntegerType(MIN_NONDET_Ordertime_Minutes).label("Min - Orderminutes").placeholder("0").tooltip("Minimal Ordertime(Minutes)");
        UI_MIN_NONDET_ORDERTIME_SECONDS_FIELD = Field.ofIntegerType(MIN_NONDET_Ordertime_Seconds).label("Min - Orderseconds").placeholder("0").tooltip("Minimal Ordertime(Seconds)");
        UI_MAX_NONDET_ORDERTIME_HOURS_FIELD = Field.ofIntegerType(MAX_NONDET_Ordertime_Hours).label("Max - Orderhours").placeholder("0").tooltip("Maximum Ordertime(Hours)");
        UI_MAX_NONDET_ORDERTIME_MINUTES_FIELD = Field.ofIntegerType(MAX_NONDET_Ordertime_Minutes).label("Max - Orderminutes").placeholder("0").tooltip("Maximum Ordertime(Minutes)");
        UI_MAX_NONDET_ORDERTIME_SECONDS_FIELD = Field.ofIntegerType(MAX_NONDET_Ordertime_Seconds).label("Max - Orderseconds").placeholder("0").tooltip("Maximum Ordertime(Seconds)");
        UI_MEAN_NONDET_ORDERTIME_HOURS_FIELD = Field.ofIntegerType(MEAN_NONDET_Ordertime_Hours).label("Mean - Orderhours").placeholder("0").tooltip("Mean Ordertime(Hours)");
        UI_MEAN_NONDET_ORDERTIME_MINUTES_FIELD = Field.ofIntegerType(MEAN_NONDET_Ordertime_Minutes).label("Mean - Orderminutes").placeholder("0").tooltip("Mean Ordertime(Minutes)");
        UI_MEAN_NONDET_ORDERTIME_SECONDS_FIELD = Field.ofIntegerType(MEAN_NONDET_Ordertime_Seconds).label("Mean - Orderseconds").placeholder("0").tooltip("Mean Ordertime(Seconds)");
        UI_DEVIATION_NONDET_ORDERTIME_HOURS_FIELD = Field.ofIntegerType(DEVIATION_NONDET_Ordertime_Hours).label("Deviation - Orderhours").placeholder("0").tooltip("Deviation Ordertime(Hours)");
        UI_DEVIATION_NONDET_ORDERTIME_MINUTES_FIELD = Field.ofIntegerType(DEVIATION_NONDET_Ordertime_Minutes).label("Deviation - Orderminutes").placeholder("0").tooltip("Deviation Ordertime(Minutes)");
        UI_DEVIATION_NONDET_ORDERTIME_SECONDS_FIELD = Field.ofIntegerType(DEVIATION_NONDET_Ordertime_Seconds).label("Deviation - Orderseconds").placeholder("0").tooltip("Deviation Ordertime(Seconds)");

        ORDER_TIME_UI = new FormRenderer(Form.of(Group.of(UI_ORDERTIME_HOURS_FIELD, UI_ORDERTIME_MINUTES_FIELD, UI_ORDERTIME_SECONDS_FIELD)));
        WORKINGTIME_UI = new FormRenderer(Form.of(Group.of(UI_WORKINGTIME_HOURS_FIELD, UI_WORKINGTIME_MINUTES_FIELD, UI_WORKINGTIME_SECONDS_FIELD)));
        NONDET_WORKINGTIME_UI = new FormRenderer(Form.of(Group.of(
                UI_MIN_NONDET_WORKINGTIME_HOURS_FIELD, UI_MIN_NONDET_WORKINGTIME_MINUTES_FIELD, UI_MIN_NONDET_WORKINGTIME_SECONDS_FIELD,
                UI_MAX_NONDET_WORKINGTIME_HOURS_FIELD, UI_MAX_NONDET_WORKINGTIME_MINUTES_FIELD, UI_MAX_NONDET_WORKINGTIME_SECONDS_FIELD,
                UI_MEAN_NONDET_WORKINGTIME_HOURS_FIELD, UI_MEAN_NONDET_WORKINGTIME_MINUTES_FIELD, UI_MEAN_NONDET_WORKINGTIME_SECONDS_FIELD,
                UI_DEVIATION_NONDET_WORKINGTIME_HOURS_FIELD, UI_DEVIATION_NONDET_WORKINGTIME_MINUTES_FIELD, UI_DEVIATION_NONDET_WORKINGTIME_SECONDS_FIELD)));

        NONDET_ORDERTIME_UI = new FormRenderer(Form.of(Group.of(
                UI_MIN_NONDET_ORDERTIME_HOURS_FIELD, UI_MIN_NONDET_ORDERTIME_MINUTES_FIELD, UI_MIN_NONDET_ORDERTIME_SECONDS_FIELD,
                UI_MAX_NONDET_ORDERTIME_HOURS_FIELD, UI_MAX_NONDET_ORDERTIME_MINUTES_FIELD, UI_MAX_NONDET_ORDERTIME_SECONDS_FIELD,
                UI_MEAN_NONDET_ORDERTIME_HOURS_FIELD, UI_MEAN_NONDET_ORDERTIME_MINUTES_FIELD, UI_MEAN_NONDET_ORDERTIME_SECONDS_FIELD,
                UI_DEVIATION_NONDET_ORDERTIME_HOURS_FIELD, UI_DEVIATION_NONDET_ORDERTIME_MINUTES_FIELD, UI_DEVIATION_NONDET_ORDERTIME_SECONDS_FIELD)));


    }

    public UI_Activate_Function(String function_tag, Workingtime workingtime, int chance_for_instantiation, Workingtime instantiate_Time, Function_Type type, int ID, Activating_Start_Event start_Event, Event_Calendar calendar, Decide_Activation_Type decision) {
        super(function_tag, instantiate_Time, chance_for_instantiation, workingtime, type, ID, start_Event, calendar, decision);
    }

    @Override
    public int get_Next_Elem_ID() {
        return 0;
    }

    @Override
    public List<EPK_Node> getNodelist() {
        return nodelist;
    }

    @Override
    public VBox Get_UI() {


        if (UI_IS_DETERMINISTIC_WORKINGTIME.getValue() != ISDet_Workingtime_UI) {
            isDeterministic_Workingtime.setValue(ISDet_Workingtime_UI);
            UI_IS_DETERMINISTIC_WORKINGTIME.reset();
        }
        if (UI_IS_DETERMINISTIC_ORDERTIME.getValue() != ISDet_Ordertime_UI) {
            isDeterministic_Ordertime.setValue(ISDet_Ordertime_UI);
            UI_IS_DETERMINISTIC_ORDERTIME.reset();
        }

        UI_NEEDED_RESOURCES_FIELD = Field.ofSingleSelectionType(Resources).label("Benötigte Ressourcen").tooltip("Benötigte Ressourcen");
        UI_RESOURCE_COUNT = Field.ofIntegerType(0).label("Count").tooltip("Choose number of selected resource to add to the function");
        UI_NEEDED_WORKFORCES_FIELD = Field.ofSingleSelectionType(Workforces).label("Benötigte Arbeitskraft").tooltip("Benötigte Arbeitskraft");

        RESOURCES_UI = new FormRenderer(Form.of(Group.of(UI_NEEDED_RESOURCES_FIELD, UI_RESOURCE_COUNT)));
        WORKFORCES_UI = new FormRenderer(Form.of(Group.of(UI_NEEDED_WORKFORCES_FIELD)));

        Box.getChildren().clear();
        Box.getChildren().add(ID_TAG_UI);
        this.Activating_Start_Events = EPK.getAll_Activating_Start_Events();
        Box.getChildren().add(new Separator());
        UI_NEXT_ELEMENTS_FIELD = Field.ofSingleSelectionType(nodelist).label("Nachfolger");
        NEXT_ELEMS_UI = new FormRenderer(Form.of(Group.of(UI_NEXT_ELEMENTS_FIELD)));
        Box.getChildren().add(NEXT_ELEMS_UI);
        Box.getChildren().add(new Separator());
        Button btn = new Button("Verbindung entfernen");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                EPK_Node Node = UI_NEXT_ELEMENTS_FIELD.getSelection();
                if (Node != null) {
                    EPK.getActive_Elem().getEPKNode().getNext_Elem().remove(Node);
                    EPK.getModel().removeEdge(getID(), Node.getID());
                    EPK.getGraph().endUpdate();
                    EPK.activate();
                }
            }
        });
        ACTIVATING_EVENT_FIELD = Field.ofSingleSelectionType(Activating_Start_Events, Selection).label("Aktivierendes Event:")
                .tooltip("Wählen sie hier das Event aus das im Falle einer Instanzierung die Simulation für die Instanz startet.");
        UI_ACTIVATING_EVENT = new FormRenderer(Form.of(Group.of(ACTIVATING_EVENT_FIELD)));

        Button Save_Selection = new Button("Auswahl Speichern");
        Save_Selection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Chosen_Starter = ACTIVATING_EVENT_FIELD.getSelection();

                if (Chosen_Starter != null && UI_CHANCE.getValue() != getChance_for_instantiation()) {
                    Activating_Event_Label.setText("Ausgewähltes Start Event: " + Chosen_Starter.toString());
                    setStart_Event(Chosen_Starter);
                    setChance_for_instantiation(UI_CHANCE.getValue());
                }
            }
        });

        Button Remove_Selection = new Button("Delete Activating");
        Remove_Selection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Chosen_Starter = ACTIVATING_EVENT_FIELD.getSelection();
                if (Chosen_Starter != null && getStart_Event() != null && getStart_Event().equals(Chosen_Starter)) {
                    Chosen_Starter = null;
                    setStart_Event(null);
                    Activating_Event_Label.setText("Ausgewählte Aktivierende Funktion: ");
                }
            }
        });

        ButtonBar Selection = new ButtonBar();
        Selection.getButtons().add(Save_Selection);
        Selection.getButtons().add(Remove_Selection);

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
        ButtonBar WorkforcesBar = new ButtonBar();
        WorkforcesBar.getButtons().add(Add_Workforce);
        WorkforcesBar.getButtons().add(Remove_Workforce);

        Button Change_Det_Workingtime = new Button("Change");
        Change_Det_Workingtime.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (UI_IS_DETERMINISTIC_WORKINGTIME.getValue() != ISDet_Workingtime_UI) {
                    if (UI_IS_DETERMINISTIC_WORKINGTIME.getValue()) {
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
                    ISDet_Workingtime_UI = UI_IS_DETERMINISTIC_WORKINGTIME.getValue();
                    EPK.activate();
                }
            }
        });
        ButtonBar Changebar = new ButtonBar();
        Changebar.getButtons().add(Change_Det_Workingtime);

        Button Change_Det_Ordertime = new Button("Change");
        Change_Det_Ordertime.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (UI_IS_DETERMINISTIC_ORDERTIME.getValue() != ISDet_Ordertime_UI) {
                    if (UI_IS_DETERMINISTIC_ORDERTIME.getValue()) {
                        MIN_NONDET_Ordertime_Hours.setValue(0);
                        MIN_NONDET_Ordertime_Minutes.setValue(0);
                        MIN_NONDET_Ordertime_Seconds.setValue(0);
                        MAX_NONDET_Ordertime_Hours.setValue(0);
                        MAX_NONDET_Ordertime_Minutes.setValue(0);
                        MAX_NONDET_Ordertime_Seconds.setValue(0);
                        MEAN_NONDET_Ordertime_Hours.setValue(0);
                        MEAN_NONDET_Ordertime_Minutes.setValue(0);
                        MEAN_NONDET_Ordertime_Seconds.setValue(0);
                        DEVIATION_NONDET_Ordertime_Hours.setValue(0);
                        DEVIATION_NONDET_Ordertime_Minutes.setValue(0);
                        DEVIATION_NONDET_Ordertime_Seconds.setValue(0);
                    } else {
                        Ordertime_Hours.setValue(0);
                        Ordertime_Minutes.setValue(0);
                        Ordertime_Seconds.setValue(0);
                    }
                    ISDet_Ordertime_UI = UI_IS_DETERMINISTIC_ORDERTIME.getValue();
                    EPK.activate();
                }
            }
        });
        ButtonBar Changebar_Ordertime = new ButtonBar();
        Changebar_Ordertime.getButtons().add(Change_Det_Ordertime);


        Box.getChildren().add(btn);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(new Label("Det/NonDet Workingtime"));
        Box.getChildren().add(DETERMINISTIC_WORKINGTIME_UI);
        Box.getChildren().add(Changebar);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(new Label("Arbeitsstunden"));
        if (!ISDet_Workingtime_UI) {
            Box.getChildren().add(NONDET_WORKINGTIME_UI);
        } else {
            Box.getChildren().add(WORKINGTIME_UI);
        }
        Box.getChildren().add(new Separator());
        Box.getChildren().add(new Label("Benötigte Ressourcen"));
        Box.getChildren().add(AddedResources);
        Box.getChildren().add(RESOURCES_UI);
        Box.getChildren().add(Resourcebar);
        Box.getChildren().add(new Label("Benötigte Arbeitskraft"));
        Box.getChildren().add(AddedWorkforces);
        Box.getChildren().add(WORKFORCES_UI);
        Box.getChildren().add(WorkforcesBar);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(UI_DECIDE_ACTIVATION_TYPE);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(Activating_Event_Label);
        Box.getChildren().add(UI_ACTIVATING_EVENT);
        Box.getChildren().add(CHANCE_UI);
        Box.getChildren().add(Selection);
        Box.getChildren().add(new Label("Det/NonDet Ordertime"));
        Box.getChildren().add(DETERMINISTIC_ORDERTIME_UI);
        Box.getChildren().add(Changebar_Ordertime);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(new Label("Ordertime"));
        if (!ISDet_Ordertime_UI) {
            Box.getChildren().add(NONDET_ORDERTIME_UI);
        } else {
            Box.getChildren().add(ORDER_TIME_UI);
        }
        Box.getChildren().add(new Separator());


        return Box;
    }

    @Override
    public String toString() {
        return "Aktivierende Funktion [" +
                "ID :" + UI_ID_FIELD.getValue() + ";"
                + " Tag: " + UI_TAG_FIELD.getValue() +
                ']';
    }

    @Override
    public EPK_Node getthis() {
        return super.returnUpperClass();
    }

    @Override
    public void save_Settings() {
        setDecisionType(DECIDE_ACTIVATION_TYPE_FIELD.getSelection());
        setChance_for_instantiation(UI_CHANCE.getValue());
        setFunction_tag(UI_TAG_FIELD.getValue());

        if (ISDet_Workingtime_UI) {
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

        if (ISDet_Ordertime_UI) {
            set_Deterministic_Ordertime(true);
            getInstantiate_Time().setSeconds(UI_ORDERTIME_HOURS_FIELD.getValue());
            getInstantiate_Time().setMinutes(UI_ORDERTIME_MINUTES_FIELD.getValue());
            getInstantiate_Time().setHours(UI_ORDERTIME_SECONDS_FIELD.getValue());
            setMin_Instantiate_Time(new Workingtime());
            setMax_Instantiate_Time(new Workingtime());
            setMean_Instantiate_Time(new Workingtime());
            setDeviation_Instantiate_Time(new Workingtime());
        } else {
            set_Deterministic_Ordertime(false);
            getInstantiate_Time().setSeconds(0);
            getInstantiate_Time().setMinutes(0);
            getInstantiate_Time().setHours(0);
            setMin_Instantiate_Time(new Workingtime(UI_MIN_NONDET_ORDERTIME_HOURS_FIELD.getValue(),
                    UI_MIN_NONDET_ORDERTIME_MINUTES_FIELD.getValue(),
                    UI_MIN_NONDET_ORDERTIME_SECONDS_FIELD.getValue()));
            setMax_Instantiate_Time(new Workingtime(UI_MAX_NONDET_ORDERTIME_HOURS_FIELD.getValue(),
                    UI_MAX_NONDET_ORDERTIME_MINUTES_FIELD.getValue(),
                    UI_MAX_NONDET_ORDERTIME_SECONDS_FIELD.getValue()));
            setMean_Instantiate_Time(new Workingtime(UI_MEAN_NONDET_ORDERTIME_HOURS_FIELD.getValue(),
                    UI_MEAN_NONDET_ORDERTIME_MINUTES_FIELD.getValue(),
                    UI_MEAN_NONDET_ORDERTIME_SECONDS_FIELD.getValue()));
            setDeviation_Instantiate_Time(new Workingtime(UI_DEVIATION_NONDET_ORDERTIME_HOURS_FIELD.getValue(),
                    UI_DEVIATION_NONDET_ORDERTIME_MINUTES_FIELD.getValue(),
                    UI_DEVIATION_NONDET_ORDERTIME_SECONDS_FIELD.getValue()));
        }

    }
}
