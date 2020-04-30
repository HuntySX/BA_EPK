package com.company.UI.EPKUI;

import com.company.EPK.EPK_Node;
import com.company.EPK.Function;
import com.company.EPK.Workforce;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
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

import java.util.Arrays;
import java.util.List;

public class UI_Func extends Function implements UI_Instantiable {
    private VBox Box;
    private VBox Rightbox;

    private UI_EPK EPK;
    private ObservableList<EPK_Node> nodelist;
    private List<Workforce> Workforces;
    private List<Resource> Resources;
    private ObservableList<Integer> needed_resource_List;
    private ObservableList<Workforce> needed_workforces_List;

    private IntegerProperty UI_ID;
    private StringProperty tag;
    private BooleanProperty concurrently = new SimpleBooleanProperty(true);
    private IntegerProperty Workingtime_Hours = new SimpleIntegerProperty();
    private IntegerProperty Workingtime_Minutes = new SimpleIntegerProperty();
    private IntegerProperty Workingtime_Seconds = new SimpleIntegerProperty();

    private StringField UI_TAG_FIELD;
    private IntegerField UI_ID_FIELD;
    private IntegerField UI_WORKINGTIME_HOURS_FIELD;
    private IntegerField UI_WORKINGTIME_MINUTES_FIELD;
    private IntegerField UI_WORKINGTIME_SECONDS_FIELD;
    private MultiSelectionField<Resource> UI_NEEDED_RESOURCES_FIELD;
    private MultiSelectionField<Workforce> UI_NEEDED_WORKFORCES_FIELD;
    private SingleSelectionField<EPK_Node> UI_NEXT_ELEMENTS;

    private FormRenderer RESOURCES_UI;
    private FormRenderer WORKFORCES_UI;
    private FormRenderer WORKINGTIME_UI;
    private FormRenderer ID_TAG_UI;
    private FormRenderer NEXT_ELEMS_UI;


    public UI_Func(int ID, UI_EPK EPK, VBox Rightbox) {
        super(null, ID, null, false, null, null, 0, 0, 10);
        this.Box = new VBox();
        this.Rightbox = Rightbox;
        this.EPK = EPK;
        this.Resources = EPK.getAll_Resources();
        this.Workforces = EPK.getAll_Workforces();
        needed_resource_List = FXCollections.observableArrayList();
        needed_resource_List.addListener((ListChangeListener<Integer>) e -> {
            while (e.next()) {
                if (e.wasAdded()) {
                    System.out.println("changed to: " + needed_resource_List.get(e.getFrom()).toString());
                }
            }
        });
        UI_ID = new SimpleIntegerProperty(ID);
        StringBuilder ID_Build = new StringBuilder("Function: ");
        ID_Build.append(ID);
        this.tag = new SimpleStringProperty(ID_Build.toString());
        this.nodelist = FXCollections.observableArrayList();

        //TEST
        Resource R1 = new Resource("A", 10, 1);
        Resource R2 = new Resource("B", 10, 2);

        Resources.add(R1);
        Resources.add(R2);

        nodelist.addListener((ListChangeListener<EPK_Node>) e -> {
            while (e.next()) {
                if (e.wasRemoved()) {
                    Rightbox.getChildren().clear();
                    UI_Instantiable Nodeview = (UI_Instantiable) ((UI_View_Gen) EPK.getActive_Elem()).getNodeView();
                    Rightbox.getChildren().add(Nodeview.Get_UI());
                }
            }
        });

        UI_ID_FIELD = Field.ofIntegerType(UI_ID).label("ID").editable(false);
        UI_TAG_FIELD = Field.ofStringType(tag).label("Knotenname:");
        UI_WORKINGTIME_HOURS_FIELD = Field.ofIntegerType(Workingtime_Hours).label("Stunden").placeholder("0").tooltip("Arbeitszeitstunden");
        UI_WORKINGTIME_MINUTES_FIELD = Field.ofIntegerType(Workingtime_Minutes).label("Minuten").placeholder("0").tooltip("Arbeitszeitminuten");
        UI_WORKINGTIME_SECONDS_FIELD = Field.ofIntegerType(Workingtime_Seconds).label("Sekunden").placeholder("60").tooltip("Arbeitszeitsekunden");

        UI_NEEDED_RESOURCES_FIELD = Field.ofMultiSelectionType(Resources, Arrays.asList()).label("Benötigte Ressourcen").tooltip("Benötigte Ressourcen");
        UI_NEEDED_WORKFORCES_FIELD = Field.ofMultiSelectionType(Workforces, Arrays.asList()).label("Benötigte Arbeitskraft").tooltip("Benötigte Arbeitskraft");

        RESOURCES_UI = new FormRenderer(Form.of(Group.of(UI_NEEDED_RESOURCES_FIELD)));
        WORKFORCES_UI = new FormRenderer(Form.of(Group.of(UI_NEEDED_WORKFORCES_FIELD)));
        WORKINGTIME_UI = new FormRenderer(Form.of(Group.of(UI_WORKINGTIME_HOURS_FIELD, UI_WORKINGTIME_MINUTES_FIELD, UI_WORKINGTIME_SECONDS_FIELD)));
        ID_TAG_UI = new FormRenderer(Form.of(Group.of(UI_ID_FIELD, UI_TAG_FIELD)));
    }

    @Override
    public VBox Get_UI() {
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
                    nodelist.remove(Node);
                    EPK.getActive_Elem().getEPKNode().getNext_Elem().remove(Node);
                    EPK.getModel().removeEdge(getID(), Node.getID());
                    EPK.getGraph().endUpdate();
                }
            }
        });
        Box.getChildren().add(btn);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(new Label("Arbeitsstunden"));
        Box.getChildren().add(WORKINGTIME_UI);
        Box.getChildren().add(new Label("Benötigte Ressourcen"));
        Box.getChildren().add(RESOURCES_UI);
        Box.getChildren().add(new Label("Benötigte Arbeitskraft"));
        Box.getChildren().add(WORKFORCES_UI);

        return Box;
    }

    @Override
    public List<EPK_Node> getNodelist() {
        return nodelist;
    }

    @Override
    public void save_Settings() {

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
}
