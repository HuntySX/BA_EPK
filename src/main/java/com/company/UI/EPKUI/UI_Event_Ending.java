package com.company.UI.EPKUI;

import com.company.EPK.EPK_Node;
import com.company.EPK.Event;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.List;

public class UI_Event_Ending extends Event implements UI_Instantiable {

    private VBox Box;
    private VBox Rightbox;
    private List<EPK_Node> nodelist;
    private UI_EPK EPK;

    private IntegerProperty UI_ID;
    private StringProperty tag;
    private BooleanProperty is_Start = new SimpleBooleanProperty(false);
    private BooleanProperty is_End = new SimpleBooleanProperty(true);

    private StringField UI_NAMESTRING_FIELD;
    private StringField UI_TAG_FIELD;
    private IntegerField UI_ID_FIELD;
    private SingleSelectionField<EPK_Node> UI_NEXT_ELEMENTS_FIELD;

    private FormRenderer ID_TAG_UI;
    private FormRenderer NEXT_ELEMS_UI;

    public UI_Event_Ending(int ID, UI_EPK EPK, VBox Rightbox) {
        super(null, ID, null);
        this.Box = new VBox();
        this.Rightbox = Rightbox;
        this.EPK = EPK;
        UI_ID = new SimpleIntegerProperty(ID);
        StringBuilder ID_Build = new StringBuilder("End-Event: ");
        ID_Build.append(ID);
        this.tag = new SimpleStringProperty(ID_Build.toString());
        UI_ID_FIELD = Field.ofIntegerType(UI_ID).label("ID").editable(false);
        UI_NAMESTRING_FIELD = Field.ofStringType(tag).label("Knotenname:");
        this.nodelist = getNext_Elem();

        ID_TAG_UI = new FormRenderer(
                Form.of(
                        Group.of(
                                UI_ID_FIELD, UI_NAMESTRING_FIELD)));
    }

    public UI_Event_Ending(int ID, String Event_Tag, boolean is_End_Event) {
        super(ID, Event_Tag, is_End_Event);
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
        Box.getChildren().add(btn);
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(
                Field.ofBooleanType(is_Start).label("Startevent").editable(false),
                Field.ofBooleanType(is_End).label("Endevent").editable(false)))));
        return Box;
    }

    @Override
    public void save_Settings() {
        setEvent_Tag(UI_NAMESTRING_FIELD.getValue());
    }

    @Override
    public String toString() {
        return "End-Event [" +
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
