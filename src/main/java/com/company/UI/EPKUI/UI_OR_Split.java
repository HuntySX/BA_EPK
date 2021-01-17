package com.company.UI.EPKUI;

import com.company.EPK.EPK_Node;
import com.company.EPK.Event_Con_Split;
import com.company.EPK.Split_Node_Chances;
import com.company.Enums.Contype;
import com.company.Enums.Split_Decide_Type;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

import static com.company.Enums.Split_Decide_Type.SINGLE_RANDOM;

public class UI_OR_Split extends Event_Con_Split implements UI_Instantiable {

    private final VBox Box;
    private final VBox Rightbox;
    private final List<EPK_Node> nodelist;
    private final UI_EPK EPK;
    private final IntegerProperty UI_ID;
    private final List<Split_Decide_Type> Decide_Types;
    private final IntegerField UI_ID_FIELD;
    private final SingleSelectionField<Split_Decide_Type> Decide_Type;
    private SingleSelectionField<EPK_Node> UI_NEXT_ELEMENTS_FIELD;
    private final FormRenderer ID_UI;
    private FormRenderer NEXT_ELEMS_UI;
    private final FormRenderer DECIDE_UI;
    private List<UI_Split_Node_Chances> UI_Chance_List;


    public UI_OR_Split(int ID, UI_EPK EPK, VBox Rightbox) {

        super(null, ID, Contype.EAGER_OR, SINGLE_RANDOM);
        this.Box = new VBox();
        this.Rightbox = Rightbox;
        this.EPK = EPK;
        UI_ID = new SimpleIntegerProperty(ID);
        UI_ID_FIELD = Field.ofIntegerType(UI_ID).label("ID").editable(false);
        this.nodelist = getNext_Elem();

        ID_UI = new FormRenderer(
                Form.of(
                        Group.of(
                                UI_ID_FIELD)));

        this.Decide_Types = EPK.getAll_Decide_Types();
        Decide_Type = Field.ofSingleSelectionType(Decide_Types, Decide_Types.indexOf(SINGLE_RANDOM)).editable(true).label("Aufteilungsoption").tooltip("Wahrschienlichkeitsverteilung zur Weitergabe der" +
                "Instanzen an die nachfolgenden Elemente");
        DECIDE_UI = new FormRenderer(Form.of(Group.of(Decide_Type)));

    }

    @Override
    public List<EPK_Node> getNodelist() {
        return nodelist;
    }

    @Override
    public VBox Get_UI() {

        UI_NEXT_ELEMENTS_FIELD = Field.ofSingleSelectionType(nodelist).label("Nachfolger");
        NEXT_ELEMS_UI = new FormRenderer(Form.of(Group.of(UI_NEXT_ELEMENTS_FIELD)));

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
        UI_Chance_List = new ArrayList<>();
        CleanupChances();
        if (!getChances_List().isEmpty()) {
            for (EPK_Node n : nodelist) {

                boolean found = false;
                for (Split_Node_Chances Chance : getChances_List()) {
                    if (Chance.getNode().equals(n)) {
                        SimpleStringProperty Node_Name = new SimpleStringProperty(n.toString());
                        StringField Node_Name_Field = Field.ofStringType(Node_Name).label("Node").editable(false);
                        IntegerProperty Chanceproperty = new SimpleIntegerProperty(Chance.getChance());
                        IntegerField Chancefield = Field.ofIntegerType(Chanceproperty).label("Chance").editable(true);
                        UI_Chance_List.add(new UI_Split_Node_Chances(Chance, Chanceproperty, Chancefield, Node_Name, Node_Name_Field));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Split_Node_Chances new_Chance = new Split_Node_Chances(n, 0);
                    SimpleStringProperty Node_Name = new SimpleStringProperty(n.toString());
                    StringField Node_Name_Field = Field.ofStringType(Node_Name).label("Node").editable(false);
                    IntegerProperty Chanceproperty = new SimpleIntegerProperty(new_Chance.getChance());
                    IntegerField Chancefield = Field.ofIntegerType(Chanceproperty).label("Chance").editable(true);
                    UI_Chance_List.add(new UI_Split_Node_Chances(new_Chance, Chanceproperty, Chancefield, Node_Name, Node_Name_Field));

                }
            }
        }

        Box.getChildren().clear();
        Box.getChildren().add(ID_UI);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(NEXT_ELEMS_UI);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(btn);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(new Separator());
        Box.getChildren().add(DECIDE_UI);

        for (UI_Split_Node_Chances to_Render_Chance : UI_Chance_List) {
            FormRenderer to_Render = new FormRenderer(Form.of(Group.of(to_Render_Chance.getNamefield(), to_Render_Chance.getChancefield())));
            Box.getChildren().add(to_Render);
        }

        Box.getChildren().add(new Separator());
        return Box;
    }

    @Override
    public void save_Settings() {
        setDecide_Type(Decide_Type.getSelection());
        for (UI_Split_Node_Chances to_Save_Chance : UI_Chance_List) {
            if (!to_Save_Chance.getChancefield().getValue().equals(to_Save_Chance.getChance().getChance())) {
                to_Save_Chance.getChance().setChance(to_Save_Chance.getChancefield().getValue());
            }
        }
    }

    @Override
    public int get_Next_Elem_ID() {
        return 0;
    }

    @Override
    public String toString() {
        return "OR-Split [" +
                "ID: " + UI_ID_FIELD.getValue() + "]";
    }

    @Override
    public EPK_Node getthis() {
        return super.returnUpperClass();
    }
}
