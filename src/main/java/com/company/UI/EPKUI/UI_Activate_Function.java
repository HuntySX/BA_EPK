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
import com.company.UI.javafxgraph.fxgraph.cells.UI_View_Gen;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.List;

public class UI_Activate_Function extends Activating_Function implements UI_Instantiable {
    private VBox Box;
    int Selection = 0;
    private VBox Rightbox;
    private UI_EPK EPK;
    private ObservableList<EPK_Node> nodelist;
    private List<Workforce> Workforces;
    private List<Resource> Resources;
    private ObservableList<Integer> needed_resource_List;
    private ObservableList<Workforce> needed_workforces_List;
    private List<UI_Event_Activating_Starter> Activating_Start_Events;
    private List<Decide_Activation_Type> Activation_Types;
    private Label Activating_Event_Label = new Label("Ausgewähltes Start Event: ");
    private StringProperty tag;
    private BooleanProperty concurrently = new SimpleBooleanProperty(true);
    private IntegerProperty Workingtime_Hours = new SimpleIntegerProperty();
    private IntegerProperty Workingtime_Minutes = new SimpleIntegerProperty();
    private IntegerProperty Workingtime_Seconds = new SimpleIntegerProperty();
    private IntegerProperty UI_ID;
    private IntegerProperty Order_Hours = new SimpleIntegerProperty();
    private IntegerProperty Order_Minutes = new SimpleIntegerProperty();
    private IntegerProperty Order_Seconds = new SimpleIntegerProperty();
    private UI_Event_Activating_Starter Chosen_Starter;
    private StringField UI_TAG_FIELD;
    private IntegerField UI_ID_FIELD;
    private IntegerField UI_WORKINGTIME_HOURS_FIELD;
    private IntegerField UI_WORKINGTIME_MINUTES_FIELD;
    private IntegerField UI_WORKINGTIME_SECONDS_FIELD;
    private IntegerField UI_ORDERTIME_HOURS_FIELD;
    private IntegerField UI_ORDERTIME_MINUTES_FIELD;
    private IntegerField UI_ORDERTIME_SECONDS_FIELD;
    private MultiSelectionField<Resource> UI_NEEDED_RESOURCES_FIELD;
    private MultiSelectionField<Workforce> UI_NEEDED_WORKFORCES_FIELD;
    private SingleSelectionField<EPK_Node> UI_NEXT_ELEMENTS;
    private FormRenderer RESOURCES_UI;
    private FormRenderer WORKFORCES_UI;
    private FormRenderer WORKINGTIME_UI;
    private FormRenderer ID_TAG_UI;
    private FormRenderer NEXT_ELEMS_UI;
    private FormRenderer UI_DECIDE_ACTIVATION_TYPE;
    private FormRenderer UI_ACTIVATING_EVENT;
    private FormRenderer ORDER_TIME_UI;
    private SingleSelectionField<Decide_Activation_Type> DECIDE_ACTIVATION_TYPE_FIELD;
    private SingleSelectionField<UI_Event_Activating_Starter> ACTIVATING_EVENT_FIELD;
    private SingleSelectionField<EPK_Node> UI_NEXT_ELEMENTS_FIELD;

    public UI_Activate_Function(int ID, UI_EPK EPK, VBox Rightbox) {
        super(null, null, null, ID, null, null, null);
        this.Box = new VBox();
        this.Rightbox = Rightbox;
        this.EPK = EPK;
        this.Activating_Start_Events = EPK.getAll_Activating_Start_Events();
        this.Activation_Types = EPK.getDecide_Activation_Types();
        this.UI_ID = new SimpleIntegerProperty(ID);
        Chosen_Starter = null;
        StringBuilder ID_Build = new StringBuilder("Event: ");
        ID_Build.append(ID);
        this.tag = new SimpleStringProperty(ID_Build.toString());
        UI_ID_FIELD = Field.ofIntegerType(UI_ID).label("ID").editable(false);
        UI_TAG_FIELD = Field.ofStringType(tag).label("Knotenname:");
        this.nodelist = FXCollections.observableArrayList();
        nodelist.addListener((ListChangeListener<EPK_Node>) e -> {
            while (e.next()) {
                if (e.wasRemoved()) {
                    Rightbox.getChildren().clear();
                    UI_Instantiable Nodeview = (UI_Instantiable) ((UI_View_Gen) EPK.getActive_Elem()).getNodeView();
                    Rightbox.getChildren().add(Nodeview.Get_UI());
                }
            }
        });


        DECIDE_ACTIVATION_TYPE_FIELD = Field.ofSingleSelectionType(Activation_Types, 0).label("Entscheidungstyp")
                .tooltip("Wahrscheinlichkeitsverteilung nach der Instanzen am ausgewählten" +
                        " Start Event generiert werden").editable(true);

        UI_ORDERTIME_HOURS_FIELD = Field.ofIntegerType(Order_Hours).label("Stunden").placeholder("0").tooltip("Stunden bis Ankunft der Instanzierung");
        UI_ORDERTIME_MINUTES_FIELD = Field.ofIntegerType(Order_Minutes).label("Minuten").placeholder("0").tooltip("Minuten bis Ankunft der Instanzierung");
        UI_ORDERTIME_SECONDS_FIELD = Field.ofIntegerType(Order_Seconds).label("Sekunden").placeholder("60").tooltip("Sekunden bis Ankunft der Instanzierung");
        ID_TAG_UI = new FormRenderer(Form.of(Group.of(UI_ID_FIELD, UI_TAG_FIELD)));
        UI_DECIDE_ACTIVATION_TYPE = new FormRenderer(Form.of(Group.of(DECIDE_ACTIVATION_TYPE_FIELD)));
        ORDER_TIME_UI = new FormRenderer(Form.of(Group.of(UI_ORDERTIME_HOURS_FIELD, UI_ORDERTIME_MINUTES_FIELD, UI_ORDERTIME_SECONDS_FIELD)));
    }

    public UI_Activate_Function(String function_tag, Workingtime instantiate_Time, Function_Type type, int ID, Activating_Start_Event start_Event, Event_Calendar calendar, Decide_Activation_Type decision) {
        super(function_tag, instantiate_Time, type, ID, start_Event, calendar, decision);
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
                    nodelist.remove(Node);
                    EPK.getActive_Elem().getEPKNode().getNext_Elem().remove(Node);
                    EPK.getModel().removeEdge(getID(), Node.getID());
                    EPK.getGraph().endUpdate();
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
                if (Chosen_Starter != null) {
                    Activating_Event_Label.setText("Ausgewähltes Start Event: \n" + Chosen_Starter.toString());
                }
            }
        });

        Box.getChildren().add(btn);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(UI_DECIDE_ACTIVATION_TYPE);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(Activating_Event_Label);
        Box.getChildren().add(UI_ACTIVATING_EVENT);
        Box.getChildren().add(Save_Selection);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(ORDER_TIME_UI);
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
    public void save_Settings() {

    }
}
