package com.company.UI.EPKUI;

import com.company.EPK.Activating_Function;
import com.company.EPK.Activating_Start_Event;
import com.company.EPK.EPK_Node;
import com.company.Enums.Start_Event_Type;
import com.company.Run.Discrete_Event_Generator;
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

import static com.company.Enums.Start_Event_Type.INSTANTIATED;

public class UI_Event_Activating_Starter extends Activating_Start_Event implements UI_Instantiable {

    private VBox Box;
    private VBox Rightbox;
    private List<EPK_Node> nodelist;
    private IntegerProperty UI_ID;
    private IntegerProperty INSTANTIATE_COUNT;
    private StringProperty tag;
    private IntegerField UI_ID_FIELD;
    private final Label Activating_Event_Label = new Label("Ausgewählte Aktivierende Funktion: ");
    private final BooleanProperty is_Start = new SimpleBooleanProperty(true);
    private final BooleanProperty is_End = new SimpleBooleanProperty(false);
    private SingleSelectionField<EPK_Node> Next_Elems;
    private UI_EPK EPK;
    private UI_Activate_Function Chosen_Starter;
    private FormRenderer ID_TAG_UI;
    private FormRenderer NEXT_ELEMS_UI;
    private FormRenderer ACTIVATING_FUNCTION_UI;
    private StringField UI_NAMESTRING_FIELD;
    private IntegerField UI_INSTANTIATE_COUNT_FIELD;
    private SingleSelectionField<Start_Event_Type> Start_Event_Type;
    private FormRenderer UI_EVENT_TYPE_INSTANTIATE;
    private List<Start_Event_Type> Event_Types;
    private final IntegerProperty to_Instantiate = new SimpleIntegerProperty();
    private SingleSelectionField<UI_Activate_Function> Activating_Function;
    private List<UI_Activate_Function> Activating_Functions;

    public UI_Event_Activating_Starter(int ID, UI_EPK EPK, VBox Rightbox) {

        super(null, ID, null, null, null, true);
        this.Box = new VBox();
        this.Rightbox = Rightbox;
        this.EPK = EPK;
        this.Activating_Functions = EPK.getAll_Activating_Functions();
        this.Event_Types = EPK.getStart_Event_Types();
        this.UI_ID = new SimpleIntegerProperty(ID);
        Chosen_Starter = null;
        StringBuilder ID_Build = new StringBuilder("Activating Start-Event: ");
        ID_Build.append(ID);
        this.tag = new SimpleStringProperty(ID_Build.toString());
        UI_ID_FIELD = Field.ofIntegerType(UI_ID).label("ID").editable(false);
        UI_NAMESTRING_FIELD = Field.ofStringType(tag).label("Knotenname:");
        this.nodelist = getNext_Elem();

        Start_Event_Type = Field.ofSingleSelectionType(Event_Types, Event_Types.indexOf(INSTANTIATED)).label("Starttyp")
                .tooltip("Aktivierte Start Events sind immer vom Typ \" Instantiated\"").editable(false);
        ID_TAG_UI = new FormRenderer(Form.of(Group.of(UI_ID_FIELD, UI_NAMESTRING_FIELD)));
        UI_EVENT_TYPE_INSTANTIATE = new FormRenderer(Form.of(Group.of(Start_Event_Type)));
    }

    public UI_Event_Activating_Starter(Activating_Function function, int ID, Discrete_Event_Generator generator, List<EPK_Node> Next_Elem, String Event_Tag, boolean is_Start_Event) {
        super(function, ID, generator, Next_Elem, Event_Tag, is_Start_Event);
    }

    @Override
    public List<EPK_Node> getNodelist() {
        return nodelist;
    }

    @Override
    public VBox Get_UI() {
        Box.getChildren().clear();
        Box.getChildren().add(ID_TAG_UI);
        this.Activating_Functions = EPK.getAll_Activating_Functions();
        Box.getChildren().add(new Separator());
        Next_Elems = Field.ofSingleSelectionType(nodelist).label("Nachfolger");
        NEXT_ELEMS_UI = new FormRenderer(Form.of(Group.of(Next_Elems)));
        Box.getChildren().add(NEXT_ELEMS_UI);
        Box.getChildren().add(new Separator());
        Button btn = new Button("Verbindung entfernen");
        if (Chosen_Starter != null) {
            Activating_Event_Label.setText("Ausgewählte Aktivierende Funktion: " + Chosen_Starter.toString());
        }
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                EPK_Node Node = Next_Elems.getSelection();
                if (Node != null) {
                    EPK.getActive_Elem().getEPKNode().getNext_Elem().remove(Node);
                    EPK.getModel().removeEdge(getID(), Node.getID());
                    EPK.getGraph().endUpdate();
                    EPK.activate();
                }
            }
        });
        Button Save_Selection = new Button("Auswahl Speichern");
        Save_Selection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Chosen_Starter = Activating_Function.getSelection();
                if (Chosen_Starter != null) {
                    Activating_Event_Label.setText("Ausgewählte Aktivierende Funktion: " + Chosen_Starter.toString());
                    setFunction(Chosen_Starter);
                }
            }
        });
        Button Remove_Selection = new Button("Delete Activating");
        Remove_Selection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Chosen_Starter = Activating_Function.getSelection();
                if (Chosen_Starter != null && getActivating_Function() != null && getActivating_Function().equals(Chosen_Starter)) {
                    Chosen_Starter = null;
                    setFunction(null);
                    Activating_Event_Label.setText("Ausgewählte Aktivierende Funktion: ");
                }
            }
        });

        ButtonBar Selection = new ButtonBar();
        Selection.getButtons().add(Save_Selection);
        Selection.getButtons().add(Remove_Selection);
        Activating_Function = Field.ofSingleSelectionType(Activating_Functions).label("Aktivierende" +
                "Funktion").tooltip("Bitte wählen sie Die Funktion hier aus, die eine Instanz" +
                "der Simulation an dieser Stelle auslösen würde.");
        ACTIVATING_FUNCTION_UI = new FormRenderer(Form.of(Group.of(Activating_Function)));

        Box.getChildren().add(btn);
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(
                Field.ofBooleanType(is_Start).label("Startevent").editable(false),
                Field.ofBooleanType(is_End).label("Endevent").editable(false)))));
        Box.getChildren().add(new Separator());
        Box.getChildren().add(UI_EVENT_TYPE_INSTANTIATE);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(Activating_Event_Label);
        Box.getChildren().add(ACTIVATING_FUNCTION_UI);
        Box.getChildren().add(Selection);

        return Box;
    }

    @Override
    public void save_Settings() {
        setEvent_Tag(UI_NAMESTRING_FIELD.getValue());
    }

    @Override
    public String toString() {
        return "Aktiviertes Start Event [" +
                "ID: " + UI_ID_FIELD.getValue() + ";"
                + " Tag: " + UI_NAMESTRING_FIELD.getValue() +
                ']';
    }

    @Override
    public int get_Next_Elem_ID() {
        return 0;
    }

    @Override
    public EPK_Node getthis() {
        return super.returnUpperClass();
    }
}
