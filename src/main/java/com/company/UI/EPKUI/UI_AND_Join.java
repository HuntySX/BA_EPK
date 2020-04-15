package com.company.UI.EPKUI;

import com.company.EPK.Event_Con_Join;
import com.company.EPK.Node;
import com.company.Enums.Contype;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.MultiSelectionField;
import com.dlsc.formsfx.view.controls.SimpleListViewControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.List;

public class UI_AND_Join extends Event_Con_Join implements UI_Instantiable {

    private VBox Box;
    private List<Node> Nodelist;
    private IntegerProperty UI_ID = new SimpleIntegerProperty();
    private MultiSelectionField<Node> Next_Elems;

    public UI_AND_Join(int ID, UI_EPK EPK) {
        super(null, ID, Contype.AND);
        this.Box = new VBox();
        UI_ID.set(ID);
        Box.getChildren().add(new FormRenderer(
                Form.of(
                        Group.of(
                                Field.ofIntegerType(UI_ID).label("ID").editable(false)))));
        Box.getChildren().add(new Separator());

        this.Nodelist = EPK.getAll_Elems();
        Next_Elems = Field.ofMultiSelectionType(Nodelist).label("Nachfolger");
        SimpleListViewControl<Node> NList = new SimpleListViewControl<>();
        NList.setField(Next_Elems);
        Box.getChildren().add(NList);
        Box.getChildren().add(new Separator());

    }

    public UI_AND_Join(List<Node> Next_Elem, int ID, Contype contype) {
        super(Next_Elem, ID, contype);
    }

    @Override
    public VBox Get_UI() {
        return Box;
    }

    @Override
    public void save_Settings() {

    }
}
