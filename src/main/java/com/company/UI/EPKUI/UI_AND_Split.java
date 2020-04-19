package com.company.UI.EPKUI;

import com.company.EPK.Event_Con_Split;
import com.company.EPK.EPK_Node;
import com.company.Enums.Contype;
import com.company.Enums.Split_Decide_Type;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.controls.SimpleListViewControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.List;

public class UI_AND_Split extends Event_Con_Split implements UI_Instantiable {

    private VBox Box;
    private List<EPK_Node> nodelist;
    private IntegerProperty UI_ID = new SimpleIntegerProperty();
    private MultiSelectionField<EPK_Node> Next_Elems;
    private List<Split_Decide_Type> Decide_Types;
    private SingleSelectionField<Split_Decide_Type> Decide_Type;

    public UI_AND_Split(int ID, UI_EPK EPK) {

        super(null, ID, Contype.AND, Split_Decide_Type.FULL);
        this.Box = new VBox();
        UI_ID.set(ID);
        Box.getChildren().add(new FormRenderer(
                Form.of(
                        Group.of(
                                Field.ofIntegerType(UI_ID).label("ID").editable(false)))));
        Box.getChildren().add(new Separator());

        this.nodelist = EPK.getAll_Elems();
        Next_Elems = Field.ofMultiSelectionType(nodelist).label("Nachfolger");
        SimpleListViewControl<EPK_Node> NList = new SimpleListViewControl<>();
        NList.setField(Next_Elems);
        Box.getChildren().add(NList);
        Box.getChildren().add(new Separator());

        this.Decide_Types = EPK.getAll_Decide_Types();
        int counter = Decide_Types.indexOf(Split_Decide_Type.FULL);
        Decide_Type = Field.ofSingleSelectionType(Decide_Types, counter).editable(false).label("Aufteilungsoption").tooltip("Wahrschienlichkeitsverteilung zur Weitergabe der" +
                "Instanzen an die nachfolgenden Elemente");
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(Decide_Type))));
    }

    public UI_AND_Split(List<EPK_Node> Next_Elem, int ID, Contype contype, Split_Decide_Type decide_type) {
        super(Next_Elem, ID, contype, decide_type);
    }

    @Override
    public VBox Get_UI() {
        return Box;
    }

    @Override
    public void save_Settings() {

    }
}
