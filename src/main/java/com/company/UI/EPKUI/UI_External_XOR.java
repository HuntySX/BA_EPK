package com.company.UI.EPKUI;

import com.company.EPK.EPK_Node;
import com.company.EPK.External_XOR_Split;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.List;


public class UI_External_XOR extends External_XOR_Split implements UI_Instantiable {

    private final VBox Box;
    private final VBox Rightbox;

    private final UI_EPK EPK;
    private final List<EPK_Node> nodelist;
    private EPK_Node UI_Timeout;
    private EPK_Node UI_Positive;
    private EPK_Node UI_Negative;
    private final IntegerProperty TIMEOUT_Workingtime_Hours = new SimpleIntegerProperty();
    private final IntegerProperty TIMEOUT_Workingtime_Minutes = new SimpleIntegerProperty();
    private final IntegerProperty TIMEOUT_Workingtime_Seconds = new SimpleIntegerProperty();
    private final IntegerField TIMEOUT_NONDET_WORKINGTIME_HOURS_FIELD;
    private final IntegerField TIMEOUT_NONDET_WORKINGTIME_MINUTES_FIELD;
    private final IntegerField TIMEOUT_NONDET_WORKINGTIME_SECONDS_FIELD;
    private final IntegerProperty UI_ID;
    private final IntegerField UI_ID_FIELD;
    private final IntegerProperty Chance_Pos_Neg;
    private SingleSelectionField<EPK_Node> UI_NEXT_ELEMENTS;
    private final FormRenderer ID_UI;
    private FormRenderer NEXT_ELEMS_UI;
    private final FormRenderer TIMEOUT_UI;
    private final IntegerField UI_CHANCE;
    private final FormRenderer CHANCE_UI;
    private SingleSelectionField<EPK_Node> UI_NEXT_ELEMENTS_FIELD;


    public UI_External_XOR(int id, UI_EPK epk, VBox rightbox) {

        super(id, null, null, null, 0);
        this.Box = new VBox();
        this.Rightbox = rightbox;
        this.EPK = epk;
        UI_ID = new SimpleIntegerProperty(id);
        UI_ID_FIELD = Field.ofIntegerType(UI_ID).label("ID").editable(false);
        this.nodelist = getNext_Elem();

        ID_UI = new FormRenderer(
                Form.of(
                        Group.of(
                                UI_ID_FIELD)));

        TIMEOUT_NONDET_WORKINGTIME_HOURS_FIELD = Field.ofIntegerType(TIMEOUT_Workingtime_Hours).label("Timeout - Hours").placeholder("0").tooltip("External Timeout(Hours)");
        TIMEOUT_NONDET_WORKINGTIME_MINUTES_FIELD = Field.ofIntegerType(TIMEOUT_Workingtime_Minutes).label("Timeout - Minutes").placeholder("0").tooltip("External Timeout(Minutes)");
        TIMEOUT_NONDET_WORKINGTIME_SECONDS_FIELD = Field.ofIntegerType(TIMEOUT_Workingtime_Seconds).label("Timeout - Seconds").placeholder("0").tooltip("External Timeout(Seconds)");

        TIMEOUT_UI = new FormRenderer(Form.of(Group.of(TIMEOUT_NONDET_WORKINGTIME_HOURS_FIELD,
                TIMEOUT_NONDET_WORKINGTIME_MINUTES_FIELD, TIMEOUT_NONDET_WORKINGTIME_SECONDS_FIELD)));

        this.Chance_Pos_Neg = new SimpleIntegerProperty(getChance_Pos_Neg());
        UI_CHANCE = Field.ofIntegerType(Chance_Pos_Neg).label("Chance for Positive / Negative");
        CHANCE_UI = new FormRenderer(Form.of(Group.of(UI_CHANCE)));
    }

    @Override
    public VBox Get_UI() {
        UI_Timeout = getTimeout();
        UI_Positive = getPositive();
        UI_Negative = getNegative();
        UI_NEXT_ELEMENTS_FIELD = Field.ofSingleSelectionType(nodelist).label("Nachfolger");
        NEXT_ELEMS_UI = new FormRenderer(Form.of(Group.of(UI_NEXT_ELEMENTS_FIELD)));

        Button Delete_Con = new Button("Verbindung entfernen");
        Delete_Con.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                EPK_Node Node = UI_NEXT_ELEMENTS_FIELD.getSelection();
                if (Node != null) {
                    EPK.getActive_Elem().getEPKNode().getNext_Elem().remove(Node);
                    if (UI_Timeout.equals(Node)) {
                        UI_Timeout = null;
                        setTimeout(null);
                    }
                    if (UI_Positive.equals(Node)) {
                        UI_Positive = null;
                        setPositive(null);
                    }
                    if (UI_Negative.equals(Node)) {
                        UI_Negative = null;
                        setNegative(null);
                    }
                    EPK.getModel().removeEdge(getID(), Node.getID());
                    EPK.getGraph().endUpdate();
                    EPK.activate();
                }
            }
        });
        Button setAsTimeout = new Button("Set Timeout Node");
        setAsTimeout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                EPK_Node Node = UI_NEXT_ELEMENTS_FIELD.getSelection();
                if (Node != null) {
                    if (UI_Timeout == null || !UI_Timeout.equals(Node)) {
                        UI_Timeout = Node;
                        setTimeout(Node);
                        EPK.activate();
                    }
                }
            }
        });
        Button setAsPositive = new Button("Set Positive Node");
        setAsPositive.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                EPK_Node Node = UI_NEXT_ELEMENTS_FIELD.getSelection();
                if (Node != null) {
                    if (UI_Positive == null || !UI_Positive.equals(Node)) {
                        UI_Positive = Node;
                        setPositive(Node);
                        EPK.activate();
                    }
                }
            }
        });
        Button setAsNegative = new Button("Set Negative Node");
        setAsNegative.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                EPK_Node Node = UI_NEXT_ELEMENTS_FIELD.getSelection();
                if (Node != null) {
                    if (UI_Negative == null || !UI_Negative.equals(Node)) {
                        UI_Negative = Node;
                        setNegative(Node);
                        EPK.activate();
                    }
                }
            }
        });
        ButtonBar setStatus = new ButtonBar();
        setStatus.getButtons().add(setAsPositive);
        setStatus.getButtons().add(setAsNegative);
        setStatus.getButtons().add(setAsTimeout);

        Button deleteTimeout = new Button("delete Timeout Node");
        deleteTimeout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                EPK_Node Node = UI_NEXT_ELEMENTS_FIELD.getSelection();
                if (UI_Timeout != null) {
                    UI_Timeout = null;
                    setTimeout(null);
                    EPK.activate();
                }

            }
        });
        Button deletePositive = new Button("delete Positive Node");
        deletePositive.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (UI_Positive != null) {
                    UI_Positive = null;
                    setPositive(null);
                    EPK.activate();
                }
            }
        });
        Button deleteNegative = new Button("delete Negative Node");
        deleteNegative.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                EPK_Node Node = UI_NEXT_ELEMENTS_FIELD.getSelection();
                if (UI_Negative != null) {
                    UI_Negative = null;
                    setNegative(null);
                    EPK.activate();
                }

            }
        });

        ButtonBar deleteStatus = new ButtonBar();
        deleteStatus.getButtons().add(deletePositive);
        deleteStatus.getButtons().add(deleteNegative);
        deleteStatus.getButtons().add(deleteTimeout);

        Label TimeoutLabel = new Label("Timeout-Node: N/A");
        Label PositiveLabel = new Label("Positive-Node: N/A");
        Label NegativeLabel = new Label("Negative-Node: N/A");
        if (UI_Timeout != null) {
            TimeoutLabel.setText("Timeout-Node: " + UI_Timeout.toString());
        }
        if (UI_Positive != null) {
            PositiveLabel.setText("Positive-Node: " + UI_Positive.toString());
        }
        if (UI_Negative != null) {
            NegativeLabel.setText("Negative-Node: " + UI_Negative.toString());
        }
        VBox LabelBox = new VBox();
        LabelBox.getChildren().add(PositiveLabel);
        LabelBox.getChildren().add(NegativeLabel);
        LabelBox.getChildren().add(TimeoutLabel);

        Box.getChildren().clear();
        Box.getChildren().add(ID_UI);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(NEXT_ELEMS_UI);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(Delete_Con);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(LabelBox);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(setStatus);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(deleteStatus);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(CHANCE_UI);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(TIMEOUT_UI);
        Box.getChildren().add(new Separator());
        return Box;
    }

    @Override
    public void save_Settings() {
        setChance_Pos_Neg(UI_CHANCE.getValue());
        setNegative(UI_Negative);
        setPositive(UI_Positive);
        setTimeout(UI_Timeout);
        Workingtime Timeout = new Workingtime(TIMEOUT_NONDET_WORKINGTIME_HOURS_FIELD.getValue(),
                TIMEOUT_NONDET_WORKINGTIME_MINUTES_FIELD.getValue(),
                TIMEOUT_NONDET_WORKINGTIME_SECONDS_FIELD.getValue());
        setTimeout_Time(Timeout);

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
        return "External_XOR [" +
                "ID: " + UI_ID_FIELD.getValue() + ']';

    }
}
