package com.company.UI.EPKUI;

import com.company.EPK.EPK_Node;
import com.company.EPK.Start_Event;
import com.company.Enums.Start_Event_Type;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.List;

import static com.company.Enums.Start_Event_Type.RANDOM;

public class UI_Event_Starter extends Start_Event implements UI_Instantiable {

    private VBox Box;
    private VBox Rightbox;
    private List<EPK_Node> nodelist;
    private UI_EPK EPK;
    private List<Start_Event_Type> Event_Types;
    private IntegerProperty UI_ID = new SimpleIntegerProperty();
    private IntegerProperty INSTANTIATE_COUNT = new SimpleIntegerProperty();
    private IntegerProperty to_Instantiate = new SimpleIntegerProperty();
    private StringProperty tag;
    private BooleanProperty is_Start = new SimpleBooleanProperty(true);
    private BooleanProperty is_End = new SimpleBooleanProperty(false);
    private StringField UI_TAGFIELD;
    private IntegerField UI_ID_FIELD;
    private StringField UI_NAMESTRING_FIELD;
    private IntegerField UI_INSTANTIATE_COUNT_FIELD;
    private SingleSelectionField<Start_Event_Type> Start_Event_Type;
    private SingleSelectionField<EPK_Node> Next_Elems;
    private FormRenderer ID_TAG_UI;
    private FormRenderer NEXT_ELEMS_UI;
    private FormRenderer UI_EVENT_TYPE_INSTANTIATE;

    public UI_Event_Starter(int ID, UI_EPK EPK, VBox Rightbox) {
        super(RANDOM, ID, null, 100, null, null, true);
        this.Box = new VBox();
        this.Rightbox = Rightbox;
        this.EPK = EPK;

        this.Event_Types = EPK.getStart_Event_Types();
        this.UI_ID = new SimpleIntegerProperty(ID);
        StringBuilder ID_Build = new StringBuilder("Start Event: ");
        ID_Build.append(ID);
        this.tag = new SimpleStringProperty(ID_Build.toString());
        UI_ID_FIELD = Field.ofIntegerType(UI_ID).label("ID").editable(false);
        UI_NAMESTRING_FIELD = Field.ofStringType(tag).label("Knotenname:");
        this.nodelist = getNext_Elem();
        this.INSTANTIATE_COUNT = new SimpleIntegerProperty(100);
        Start_Event_Type = Field.ofSingleSelectionType(Event_Types, 0).label("Starttyp")
                .tooltip("Die Auswahl des Starttyps entscheidet nach welcher " +
                        "Statistischen Verteilung die ausgewählte Anzahl an Instanzen " +
                        "innerhalb eines Arbeitstages verteilt werden.");
        UI_INSTANTIATE_COUNT_FIELD = Field.ofIntegerType(INSTANTIATE_COUNT)
                .label("Instanzanzahl").tooltip("Anzahl der Instanzen die nach der Statistischen" +
                        "Verteilung innerhalb eines Tages generiert werden");

        ID_TAG_UI = new FormRenderer(
                Form.of(
                        Group.of(
                                UI_ID_FIELD, UI_NAMESTRING_FIELD)));
        UI_EVENT_TYPE_INSTANTIATE = new FormRenderer(
                Form.of(
                        Group.of(
                                Start_Event_Type, UI_INSTANTIATE_COUNT_FIELD)));

    }


    public UI_Event_Starter(List<EPK_Node> Next_Elem, int ID, String Event_Tag) {
        super(RANDOM, ID, null, 100, Next_Elem, null, true);
    }

    @Override
    public List<EPK_Node> getNodelist() {
        return nodelist;
    }

    @Override
    public VBox Get_UI() {
        Box.getChildren().clear();
        Box.getChildren().add(ID_TAG_UI);
        Box.getChildren().add(new Separator());
        Next_Elems = Field.ofSingleSelectionType(nodelist).label("Nachfolger");
        NEXT_ELEMS_UI = new FormRenderer(Form.of(Group.of(Next_Elems)));
        Box.getChildren().add(NEXT_ELEMS_UI);
        Box.getChildren().add(new Separator());
        Button btn = new Button("Verbindung entfernen");
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
        Box.getChildren().add(btn);
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(
                Field.ofBooleanType(is_Start).label("Startevent").editable(false),
                Field.ofBooleanType(is_End).label("Endevent").editable(false)))));
        Box.getChildren().add(new Separator());
        Box.getChildren().add(UI_EVENT_TYPE_INSTANTIATE);
        return Box;
    }

    @Override
    public void save_Settings() {
        setEvent_Tag(UI_NAMESTRING_FIELD.getValue());
        setStart_event_type(Start_Event_Type.getSelection());
        setTo_Instantiate(UI_INSTANTIATE_COUNT_FIELD.getValue());
    }

    @Override
    public String toString() {
        return "Start-Event [" +
                "ID: " + UI_ID_FIELD.getValue() + ";"
                + " Tag: " + tag.getValue() +
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
