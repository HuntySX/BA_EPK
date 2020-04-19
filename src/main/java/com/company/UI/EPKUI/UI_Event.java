package com.company.UI.EPKUI;

import com.company.EPK.Event;
import com.company.EPK.EPK_Node;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.MultiSelectionField;
import com.dlsc.formsfx.view.controls.SimpleListViewControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.*;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

public class UI_Event extends Event implements UI_Instantiable {
    private VBox Box;
    private List<EPK_Node> nodelist;
    private IntegerProperty UI_ID = new SimpleIntegerProperty();
    private StringProperty tag;
    private BooleanProperty is_Start = new SimpleBooleanProperty(false);
    private BooleanProperty is_End = new SimpleBooleanProperty(false);
    private MultiSelectionField<EPK_Node> Next_Elems;

    public UI_Event(int ID, UI_EPK EPK) {
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

        this.nodelist = EPK.getAll_Elems();
        MultiSelectionField<EPK_Node> Next_Elems = Field.ofMultiSelectionType(nodelist, Arrays.asList(1, 2, 3)).label("Nachfolger");
        SimpleListViewControl<EPK_Node> lv = new SimpleListViewControl<>();
        lv.setField(Next_Elems);
        Box.getChildren().add(lv);
        Box.getChildren().add(new Separator());

        Box.getChildren().add(new FormRenderer(Form.of(Group.of(
                Field.ofBooleanType(is_Start).label("Startevent").editable(false),
                Field.ofBooleanType(is_End).label("Endevent").editable(false)))));
    }

    public UI_Event(List<EPK_Node> Next_Elem, int ID, String Event_Tag) {
        super(Next_Elem, ID, Event_Tag);
    }

    @Override
    public VBox Get_UI() {
        return Box;
    }

    @Override
    public void save_Settings() {

    }

}
