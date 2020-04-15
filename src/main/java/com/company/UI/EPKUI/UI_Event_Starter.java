package com.company.UI.EPKUI;

import com.company.EPK.Event;
import com.company.EPK.Node;
import com.company.Enums.Start_Event_Type;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.controls.SimpleListViewControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.*;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.List;

public class UI_Event_Starter extends Event implements UI_Instantiable {

    private VBox Box;
    private List<Node> Nodelist;
    private IntegerProperty UI_ID = new SimpleIntegerProperty();
    private StringProperty tag;
    private BooleanProperty is_Start = new SimpleBooleanProperty(true);
    private BooleanProperty is_End = new SimpleBooleanProperty(false);
    private MultiSelectionField<Node> Next_Elems;
    private SingleSelectionField<Start_Event_Type> Start_Event_Type;
    private IntegerProperty to_Instantiate;

    public UI_Event_Starter(int ID, UI_EPK EPK) {
        super(null, ID, null);
        this.Box = new VBox();
        UI_ID.set(ID);
        StringBuilder ID_Build = new StringBuilder("Function: ");
        ID_Build.append(ID);
        StringProperty tag = new SimpleStringProperty(ID_Build.toString());
        Box.getChildren().add(new FormRenderer(
                Form.of(
                        Group.of(
                                Field.ofIntegerType(UI_ID).label("ID").editable(false),
                                Field.ofStringType(tag).label("Knotenname")))));
        Box.getChildren().add(new Separator());

        this.Nodelist = EPK.getAll_Elems();
        Next_Elems = Field.ofMultiSelectionType(Nodelist).label("Nachfolger");
        SimpleListViewControl<Node> lv = new SimpleListViewControl<>();
        lv.setField(Next_Elems);
        Box.getChildren().add(lv);
        Box.getChildren().add(new Separator());
        List<Start_Event_Type> Start_Event_Types = EPK.getStart_Event_Types();

        Start_Event_Type = Field.ofSingleSelectionType(Start_Event_Types, 0).label("Starttyp").tooltip("Die Auswahl des Starttyps entscheidet nach welcher " +
                "Statistischen Verteilung die ausgewählte Anzahl an Instanzen innerhalb eines Arbeitstages verteilt werden.");
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(Field.ofIntegerType(to_Instantiate).label("Instanzanzahl").tooltip("Anzahl der Instanzen die nach der Statistischen" +
                "Verteilung innerhalb eines Tages generiert werden"), Start_Event_Type))));

        Box.getChildren().add(new FormRenderer(Form.of(Group.of(
                Field.ofBooleanType(is_Start).label("Startevent").editable(false),
                Field.ofBooleanType(is_End).label("Endevent").editable(false)))));
    }


    public UI_Event_Starter(List<Node> Next_Elem, int ID, String Event_Tag) {
        super(Next_Elem, ID, Event_Tag, true);
    }

    @Override
    public VBox Get_UI() {
        return Box;
    }

    @Override
    public void save_Settings() {

    }
}
