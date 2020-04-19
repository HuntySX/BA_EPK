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
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.List;

import static com.company.Enums.Split_Decide_Type.SINGLE_RANDOM;

public class UI_XOR_Split extends Event_Con_Split implements UI_Instantiable {

    private VBox Box;
    private List<EPK_Node> nodelist;
    private IntegerProperty UI_ID = new SimpleIntegerProperty();
    private MultiSelectionField<EPK_Node> Next_Elems;
    private List<Split_Decide_Type> Decide_Types;
    private SingleSelectionField<Split_Decide_Type> Decide_Type;
    private VBox Decide_Type_Box;

    public UI_XOR_Split(int ID, UI_EPK EPK) {

        super(null, ID, Contype.EAGER_XOR, SINGLE_RANDOM);
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
        Decide_Type = Field.ofSingleSelectionType(Decide_Types, 0).editable(true).label("Aufteilungsoption").tooltip("Wahrschienlichkeitsverteilung zur Weitergabe der" +
                "Instanzen an die nachfolgenden Elemente");
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(Decide_Type))));
        Button Refresh_VBox = new Button();
        Refresh_VBox.setText("Aktualisiere Ansicht");
        Refresh_VBox.setOnAction(null); //TODO EVENT HANDLER!
        Box.getChildren().add(Refresh_VBox);
        Box.getChildren().add(new Separator());
        Decide_Type_Box = new VBox();
        Box.getChildren().add(Decide_Type_Box);
        Box.getChildren().add(new Separator());

        //TODO Wahrschienlichkeitsoptionen in Decide Type Box einfügen und bei Update Instanzieren.
    }


    public UI_XOR_Split(List<EPK_Node> Next_Elem, int ID, Contype contype, Split_Decide_Type decide_type) {
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
