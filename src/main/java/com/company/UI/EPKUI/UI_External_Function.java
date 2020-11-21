package com.company.UI.EPKUI;

import com.company.EPK.EPK_Node;
import com.company.EPK.External_Function;
import com.company.EPK.External_XOR_Split;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class UI_External_Function extends External_Function implements UI_Instantiable {

    private VBox Box;
    private VBox Rightbox;

    private UI_EPK EPK;
    private List<EPK_Node> nodelist;
    private List<UI_External_XOR> XOR_Splits;
    private External_XOR_Split XOR_Split;
    private Label SelectionLabel = new Label();

    private IntegerProperty UI_ID;
    private StringProperty tag;


    private StringField UI_TAG_FIELD;
    private IntegerField UI_ID_FIELD;
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
    private SingleSelectionField<EPK_Node> UI_NEXT_ELEMENTS;
    private SingleSelectionField<UI_External_XOR> UI_EXTERNAL_XOR_SPLIT;
    private FormRenderer ID_TAG_UI;
    private FormRenderer NEXT_ELEMS_UI;


    public UI_External_Function(int ID, UI_EPK EPK, VBox Rightbox) {
        super(null, ID, null, null, null, null, null);
        this.Box = new VBox();
        this.Rightbox = Rightbox;
        this.EPK = EPK;
        UI_ID = new SimpleIntegerProperty(ID);
        StringBuilder ID_Build = new StringBuilder("External Function: ");
        ID_Build.append(ID);
        this.tag = new SimpleStringProperty(ID_Build.toString());
        this.nodelist = getNext_Elem();
        this.XOR_Split = getExternal_XOR();
        this.XOR_Splits = new ArrayList<>();

        UI_ID_FIELD = Field.ofIntegerType(UI_ID).label("ID").editable(false);
        UI_TAG_FIELD = Field.ofStringType(tag).label("Knotenname:");
        ID_TAG_UI = new FormRenderer(Form.of(Group.of(UI_ID_FIELD, UI_TAG_FIELD)));

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

        NONDET_WORKINGTIME_UI = new FormRenderer(Form.of(Group.of(
                UI_MIN_NONDET_WORKINGTIME_HOURS_FIELD, UI_MIN_NONDET_WORKINGTIME_MINUTES_FIELD, UI_MIN_NONDET_WORKINGTIME_SECONDS_FIELD,
                UI_MAX_NONDET_WORKINGTIME_HOURS_FIELD, UI_MAX_NONDET_WORKINGTIME_MINUTES_FIELD, UI_MAX_NONDET_WORKINGTIME_SECONDS_FIELD,
                UI_MEAN_NONDET_WORKINGTIME_HOURS_FIELD, UI_MEAN_NONDET_WORKINGTIME_MINUTES_FIELD, UI_MEAN_NONDET_WORKINGTIME_SECONDS_FIELD,
                UI_DEVIATION_NONDET_WORKINGTIME_HOURS_FIELD, UI_DEVIATION_NONDET_WORKINGTIME_MINUTES_FIELD, UI_DEVIATION_NONDET_WORKINGTIME_SECONDS_FIELD)));

    }


    @Override
    public VBox Get_UI() {

        XOR_Splits = EPK.getAll_External_XORs();
        UI_NEXT_ELEMENTS = Field.ofSingleSelectionType(nodelist).label("Nachfolger");
        NEXT_ELEMS_UI = new FormRenderer(Form.of(Group.of(UI_NEXT_ELEMENTS)));
        XOR_Split = getExternal_XOR();
        if (XOR_Split == null) {
            SelectionLabel.setText("");
        } else {
            SelectionLabel.setText(XOR_Split.toString());
        }
        UI_EXTERNAL_XOR_SPLIT = Field.ofSingleSelectionType(XOR_Splits).label("XOR-Split: ")
                .tooltip("Der Ausgewählte XOR-Split ist der Nachfolger der die Externe Funktion auswertet" +
                        "und je nach Ausgang und dauer prüft wie die Simulation einer Instanz weiter verläuft.");
        FormRenderer XOR_SPLIT_UI = new FormRenderer(Form.of
                (Group.of
                        (UI_EXTERNAL_XOR_SPLIT)));


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

        Button Save_Selection = new Button("Set XOR_Split");
        Save_Selection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                External_XOR_Split Chosen_XOR = UI_EXTERNAL_XOR_SPLIT.getSelection();
                if (Chosen_XOR != null && !Chosen_XOR.equals(XOR_Split)) {

                    XOR_Split = Chosen_XOR;
                    setExternal_XOR(Chosen_XOR);

                }
                StringBuilder LabelString = new StringBuilder("");
                LabelString.append(XOR_Split.toString());
                if (!LabelString.equals("")) {
                    SelectionLabel.setText(LabelString.toString());
                }
            }
        });
        Button Delete_Selection = new Button("Delete XOR-Split");
        Delete_Selection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                EPK_Node Chosen_XOR = UI_EXTERNAL_XOR_SPLIT.getSelection();
                if (Chosen_XOR != null && Chosen_XOR.equals(XOR_Split)) {
                    XOR_Split = null;
                    SelectionLabel.setText("");
                }
            }
        });


        ButtonBar Bar = new ButtonBar();
        Bar.getButtons().add(Save_Selection);
        Bar.getButtons().add(Delete_Selection);


        Box.getChildren().clear();
        Box.getChildren().add(ID_TAG_UI);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(NEXT_ELEMS_UI);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(btn);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(new Label("External Workingtime"));
        Box.getChildren().add(NONDET_WORKINGTIME_UI);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(SelectionLabel);
        Box.getChildren().add(XOR_SPLIT_UI);
        Box.getChildren().add(Bar);
        return Box;
    }

    @Override
    public void save_Settings() {
        setFunction_tag(UI_TAG_FIELD.getValue());

        setDeterministic(false);
        setWorkingHours(0);
        setWorkingMinutes(0);
        setWorkingSeconds(0);
        setMin_External_Time(new Workingtime(UI_MIN_NONDET_WORKINGTIME_HOURS_FIELD.getValue(),
                UI_MIN_NONDET_WORKINGTIME_MINUTES_FIELD.getValue(),
                UI_MIN_NONDET_WORKINGTIME_SECONDS_FIELD.getValue()));
        setMax_External_Time(new Workingtime(UI_MAX_NONDET_WORKINGTIME_HOURS_FIELD.getValue(),
                UI_MAX_NONDET_WORKINGTIME_MINUTES_FIELD.getValue(),
                UI_MAX_NONDET_WORKINGTIME_SECONDS_FIELD.getValue()));
        setMean_External_Time(new Workingtime(UI_MEAN_NONDET_WORKINGTIME_HOURS_FIELD.getValue(),
                UI_MEAN_NONDET_WORKINGTIME_MINUTES_FIELD.getValue(),
                UI_MEAN_NONDET_WORKINGTIME_SECONDS_FIELD.getValue()));
        setDeviation_External_Time(new Workingtime(UI_DEVIATION_NONDET_WORKINGTIME_HOURS_FIELD.getValue(),
                UI_DEVIATION_NONDET_WORKINGTIME_MINUTES_FIELD.getValue(),
                UI_DEVIATION_NONDET_WORKINGTIME_SECONDS_FIELD.getValue()));

    }

    @Override
    public List<EPK_Node> getNodelist() {
        return nodelist;
    }

    @Override
    public int get_Next_Elem_ID() {
        return 0;
    }

    @Override
    public EPK_Node getthis() {
        return super.returnUpperClass();
    }

    @Override
    public String toString() {
        return "Function [" +
                "ID: " + UI_ID_FIELD.getValue() + ";"
                + " Tag: " + tag.getValue() +
                ']';

    }
}
