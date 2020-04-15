package com.company.UI.EPKUI;

import com.company.EPK.Event_Con_Join;
import com.company.EPK.Node;
import com.company.Enums.Contype;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.controls.SimpleListViewControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.List;

public class UI_OR_Join extends Event_Con_Join implements UI_Instantiable {

    private VBox Box;
    private SingleSelectionField<UI_Con_Type> UI_Con_Type;
    private List<Node> Nodelist;
    private IntegerProperty UI_ID = new SimpleIntegerProperty();
    private MultiSelectionField<Node> Next_Elems;
    private MultiSelectionField<Node> Mapped_Branched_Elements;

    public UI_OR_Join(int ID, UI_EPK EPK) {
        super(null, ID, Contype.LAZY_OR);
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
        List<UI_Con_Type> UI_Con_Type_List = EPK.get_UI_Con_Type();
        UI_Con_Type = Field.ofSingleSelectionType(UI_Con_Type_List, 0).editable(true).label("Weiterleitungsoption").tooltip("Eager: Warte darauf das Instanz " +
                "abgeschlossen mit allen potenziellen Vorgängern ist. \n Lazy: Gebe Instanz weiter sobald Bedingung des Gates erfüllt.");
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(UI_Con_Type))));
        Button Refresh_VBox = new Button();
        Refresh_VBox.setText("Aktualisiere Ansicht");
        Refresh_VBox.setOnAction(null); //TODO EVENT HANDLER!
        Box.getChildren().add(Refresh_VBox);
        Box.getChildren().add(new Separator());
        VBox Con_Type_Box = new VBox();
        Mapped_Branched_Elements = Field.ofMultiSelectionType(Nodelist).label("Mögliche Vorgänger").tooltip("Bitte Alle möglichen Nachfolger auswählen " +
                "die eine Instanz zu diesem Knoten führen können.");
        SimpleListViewControl<Node> MList = new SimpleListViewControl<>();
        MList.setField(Mapped_Branched_Elements);
        Con_Type_Box.getChildren().add(MList);
        Box.getChildren().add(Con_Type_Box);
        Box.getChildren().add(new Separator());
    }

    public UI_OR_Join(List<Node> Next_Elem, int ID, Contype contype) {
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
