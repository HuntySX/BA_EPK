package com.company.UI.EPKUI;

import com.company.EPK.Activating_Function;
import com.company.EPK.Activating_Start_Event;
import com.company.EPK.Node;
import com.company.Enums.Start_Event_Type;
import com.company.Run.Discrete_Event_Generator;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.controls.SimpleListViewControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.*;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.List;

public class UI_Event_Activating_Starter extends Activating_Start_Event implements UI_Instantiable {

    private VBox Box;
    private List<Node> Nodelist;
    private IntegerProperty UI_ID = new SimpleIntegerProperty();
    private StringProperty tag;
    private BooleanProperty is_Start = new SimpleBooleanProperty(true);
    private BooleanProperty is_End = new SimpleBooleanProperty(false);
    private MultiSelectionField<Node> Next_Elems;
    private SingleSelectionField<Start_Event_Type> Start_Event_Type;
    private SingleSelectionField<UI_Activate_Function> Activating_Function;
    private IntegerProperty to_Instantiate;
    private List<UI_Activate_Function> Activating_Functions;

    public UI_Event_Activating_Starter(int ID, UI_EPK EPK) {

        super(null, ID, null, null, null, true);
        this.Box = new VBox();

        UI_ID.set(ID);
        StringBuilder ID_Build = new StringBuilder("Activating Start Event: ");
        ID_Build.append(ID);
        StringProperty tag = new SimpleStringProperty(ID_Build.toString());
        Box.getChildren().add(new FormRenderer(
                Form.of(
                        Group.of(
                                Field.ofIntegerType(UI_ID).label("ID").editable(false),
                                Field.ofStringType(tag).label("Knotenname")))));
        Box.getChildren().add(new Separator());

        this.Nodelist = EPK.getAll_Elems();
        this.Activating_Functions = EPK.getAll_Activating_Functions();
        Next_Elems = Field.ofMultiSelectionType(Nodelist).label("Nachfolger");
        SimpleListViewControl<Node> lv = new SimpleListViewControl<>();
        lv.setField(Next_Elems);
        Box.getChildren().add(lv);
        Activating_Function = Field.ofSingleSelectionType(Activating_Functions).label("Aktivierungsfunktion").tooltip("Bitte die " +
                "Funktion die eine Instanzierung an dieser Stelle" + "Auslösen würde, auswählen.");
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(Activating_Function))));
        Box.getChildren().add(new Separator());
        List<Start_Event_Type> Start_Event_Types = EPK.getStart_Event_Types();
        Start_Event_Type = Field.ofSingleSelectionType(Start_Event_Types, Start_Event_Types.size() - 1).editable(false)
                .label("Starttyp")
                .tooltip("Die Auswahl des Starttyps entscheidet nach welcher " +
                        "Statistischen Verteilung die ausgewählte Anzahl an Instanzen innerhalb eines Arbeitstages verteilt werden.");
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(Start_Event_Type))));

        Box.getChildren().add(new FormRenderer(Form.of(Group.of(
                Field.ofBooleanType(is_Start).label("Startevent").editable(false),
                Field.ofBooleanType(is_End).label("Endevent").editable(false)))));
    }

    public UI_Event_Activating_Starter(Activating_Function function, int ID, Discrete_Event_Generator generator, List<Node> Next_Elem, String Event_Tag, boolean is_Start_Event) {
        super(function, ID, generator, Next_Elem, Event_Tag, is_Start_Event);
    }

    @Override
    public VBox Get_UI() {
        return Box;
    }

    @Override
    public void save_Settings() {

    }
}
