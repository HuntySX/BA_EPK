package com.company.UI.EPKUI;

import com.company.EPK.Activating_Function;
import com.company.EPK.Activating_Start_Event;
import com.company.EPK.Node;
import com.company.EPK.Workforce;
import com.company.Enums.Decide_Activation_Type;
import com.company.Enums.Function_Type;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Event_Calendar;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.controls.SimpleListViewControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.*;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

public class UI_Activate_Function extends Activating_Function implements UI_Instantiable {
    private VBox Box;
    private List<Node> Nodelist;
    private List<UI_Event_Activating_Starter> Activating_Start_Events;
    private SingleSelectionField<UI_Event_Activating_Starter> Activating_Start_Event;
    private IntegerProperty UI_ID = new SimpleIntegerProperty();
    private StringProperty tag;
    private MultiSelectionField<Node> Next_Elems;
    private BooleanProperty concurrently = new SimpleBooleanProperty(true);
    private List<Resource> Resources;
    private MultiSelectionField<Resource> Needed_Resources;
    private List<Workforce> Workforces;
    private MultiSelectionField<Workforce> Needed_Workforces;
    private IntegerProperty Workingtime_Hours = new SimpleIntegerProperty();
    private IntegerProperty Workingtime_Minutes = new SimpleIntegerProperty();
    private IntegerProperty Workingtime_Seconds = new SimpleIntegerProperty();

    private IntegerProperty Order_Hours = new SimpleIntegerProperty();
    private IntegerProperty Order_Minutes = new SimpleIntegerProperty();
    private IntegerProperty Order_Seconds = new SimpleIntegerProperty();

    private SingleSelectionField<Decide_Activation_Type> Decide_Activation_Type;

    public UI_Activate_Function(int ID, UI_EPK EPK) {
        super(null, null, null, ID, null, null, null);
        this.Box = new VBox();

        UI_ID.set(ID);
        StringBuilder ID_Build = new StringBuilder("Activating Function: ");
        ID_Build.append(ID);
        StringProperty tag = new SimpleStringProperty(ID_Build.toString());
        Box.getChildren().add(new FormRenderer(
                Form.of(
                        Group.of(
                                Field.ofIntegerType(UI_ID).label("ID").editable(false),
                                Field.ofStringType(tag).label("Knotenname")))));
        Box.getChildren().add(new Separator());

        this.Nodelist = EPK.getAll_Elems();
        this.Activating_Start_Events = EPK.getAll_Activating_Start_Events();
        MultiSelectionField<Node> Next_Elems = Field.ofMultiSelectionType(Nodelist).label("Nachfolger");
        SimpleListViewControl<Node> lv = new SimpleListViewControl<>();
        lv.setField(Next_Elems);
        Box.getChildren().add(lv);
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(Field.ofBooleanType(concurrently).label("Konkurrierbare Ausführung").tooltip("Bestimmt ob dieser Prozess Parallel zu einem anderen " +
                "Prozess laufen darf")))));
        Activating_Start_Event = Field.ofSingleSelectionType(Activating_Start_Events).label("AktivierungsEvent").tooltip("Bitte das " +
                "Event angeben an dem im Falle einer passenden Verteilung eine Instanz generiert werden soll.");
        Box.getChildren().add(new Separator());
        Box.getChildren().add(new Label("Arbeitsstunden"));
        Box.getChildren().add(new FormRenderer(Form.of(
                Group.of(
                        Field.ofIntegerType(Workingtime_Hours).label("Stunden").placeholder("0").tooltip("Arbeitszeitstunden"),
                        Field.ofIntegerType(Workingtime_Minutes).label("Minuten").placeholder("0").tooltip("Arbeitszeitminuten"),
                        Field.ofIntegerType(Workingtime_Seconds).label("Sekunden").placeholder("60").tooltip("Arbeitszeitsekunden")))));
        Box.getChildren().add(new Label("Instanzierungsoptionen"));
        List<Decide_Activation_Type> Decide_Activation_Types = EPK.getDecide_Activation_Types();
        Decide_Activation_Type = Field.ofSingleSelectionType(Decide_Activation_Types).editable(true).label("Entscheidungsverteilung").tooltip("Auswahl nach Welcher Statistischen Verteilung" +
                "Instanzen ein Event am Aktierungsevent in Auftrag geben.");
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(Decide_Activation_Type))));
        Box.getChildren().add(new FormRenderer(Form.of(
                Group.of(
                        Field.ofIntegerType(Order_Hours).label("Stunden").placeholder("0").tooltip("Stunden bis Ankunft der Instanzierung"),
                        Field.ofIntegerType(Order_Minutes).label("Minuten").placeholder("0").tooltip("Minuten bis Ankunft der Instanzierung"),
                        Field.ofIntegerType(Order_Seconds).label("Sekunden").placeholder("60").tooltip("Sekunden bis Ankunft der Instanzierung")))));
        this.Resources = EPK.getAll_Resources();
        this.Workforces = EPK.getAll_Workforces();
        Needed_Resources = Field.ofMultiSelectionType(Resources, Arrays.asList()).label("Benötigte Ressourcen").tooltip("Benötigte Ressourcen");
        Needed_Workforces = Field.ofMultiSelectionType(Workforces, Arrays.asList()).label("Benötigte Arbeitskraft").tooltip("Benötigte Arbeitskraft");
        Box.getChildren().add(new Label("Benötigte Ressourcen"));
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(Needed_Resources))));
        Box.getChildren().add(new Label("Benötigte Arbeitskraft"));
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(Needed_Workforces))));
    }

    public UI_Activate_Function(String function_tag, Workingtime instantiate_Time, Function_Type type, int ID, Activating_Start_Event start_Event, Event_Calendar calendar, Decide_Activation_Type decision) {
        super(function_tag, instantiate_Time, type, ID, start_Event, calendar, decision);
    }

    @Override
    public VBox Get_UI() {
        return Box;
    }

    @Override
    public void save_Settings() {

    }
}
