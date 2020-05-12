package com.company.UI.EPKUI;

import com.company.EPK.EPK_Node;
import com.company.EPK.Event_Con_Join;
import com.company.EPK.Nodemap;
import com.company.Enums.Contype;
import com.company.UI.javafxgraph.fxgraph.cells.UI_View_Gen;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class UI_XOR_Join extends Event_Con_Join implements UI_Instantiable {

    private VBox Box;
    private VBox Rightbox;
    private ObservableList<EPK_Node> nodelist;
    private List<EPK_Node> prev_nodelist;
    private UI_EPK EPK;
    private IntegerField UI_ID_FIELD;
    private IntegerProperty UI_ID;
    private SingleSelectionField<EPK_Node> UI_NEXT_ELEMENTS_FIELD;
    private SingleSelectionField<EPK_Node> UI_PREVIOUS_ELEMS_START;
    private SingleSelectionField<EPK_Node> UI_PREVIOUS_ELEMS_END;
    private BooleanProperty IS_EAGER = new SimpleBooleanProperty(true);
    private BooleanField UI_IS_EAGER;
    private Label SelectionLabel = new Label();
    private FormRenderer ID_TAG_UI;
    private FormRenderer NEXT_ELEMS_UI;
    private FormRenderer IS_EAGER_UI;
    private List<Nodemap> Chosen_Previous_List;


    public UI_XOR_Join(int ID, UI_EPK EPK, VBox Rightbox) {
        super(null, ID, null);
        this.Box = new VBox();
        this.Rightbox = Rightbox;
        this.EPK = EPK;
        this.UI_ID = new SimpleIntegerProperty(ID);
        this.prev_nodelist = new ArrayList<>();
        this.Chosen_Previous_List = getMapped_Branch_Elements();
        StringBuilder ID_Build = new StringBuilder("Event: ");
        ID_Build.append(ID);
        UI_ID_FIELD = Field.ofIntegerType(UI_ID).label("ID").editable(false);
        this.nodelist = FXCollections.observableArrayList();
        nodelist.addListener((ListChangeListener<EPK_Node>) e -> {
            while (e.next()) {
                if (e.wasRemoved()) {
                    Rightbox.getChildren().clear();
                    UI_Instantiable Nodeview = (UI_Instantiable) ((UI_View_Gen) EPK.getActive_Elem()).getNodeView();
                    Rightbox.getChildren().add(Nodeview.Get_UI());
                }
            }
        });
        ID_TAG_UI = new FormRenderer(
                Form.of(
                        Group.of(
                                UI_ID_FIELD)));
        UI_IS_EAGER = Field.ofBooleanType(IS_EAGER).label("XOR-Typ:").tooltip("Wenn das Häckchen gesetzt ist," +
                "arbeitet der XOR-Join Eager (Alle vorgänger werden auf potentielles Abarbeiten" +
                "überprüft. Ansonsten arbeitet der XOR-Join Lazy(Sobald eine Instanz ankommt wird" +
                "auf die gültige Or Bedingung geprüft)").editable(true);

        IS_EAGER_UI = new FormRenderer(Form.of(Group.of(UI_IS_EAGER)));
    }

    public UI_XOR_Join(List<EPK_Node> Next_Elem, int ID, Contype contype) {
        super(Next_Elem, ID, contype);
    }

    @Override
    public List<EPK_Node> getNodelist() {
        return nodelist;
    }

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
                    nodelist.remove(Node);
                    EPK.getActive_Elem().getEPKNode().getNext_Elem().remove(Node);
                    EPK.getModel().removeEdge(getID(), Node.getID());
                    EPK.getGraph().endUpdate();
                }
            }
        });
        Box.getChildren().add(btn);
        ButtonBar Next_ElemBar = new ButtonBar();
        Next_ElemBar.getButtons().add(btn);
        Box.getChildren().add(Next_ElemBar);
        Button Save_Selection = new Button("Add Path");
        Save_Selection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                EPK_Node Chosen_Previous_Start = UI_PREVIOUS_ELEMS_START.getSelection();
                EPK_Node Chosen_Previous_End = UI_PREVIOUS_ELEMS_END.getSelection();
                if (Chosen_Previous_Start != null && Chosen_Previous_End != null) {
                    boolean found = false;
                    for (Nodemap m : Chosen_Previous_List) {
                        if (m.containsboth(Chosen_Previous_Start, Chosen_Previous_End)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        Nodemap new_Map = new Nodemap(Chosen_Previous_Start, Chosen_Previous_End);
                        getMapped_Branch_Elements().add(new_Map);
                    }
                    StringBuilder LabelString = new StringBuilder("");
                    for (Nodemap map : Chosen_Previous_List) {
                        LabelString.append(map.toString());
                        LabelString.append("\n");
                    }
                    if (!LabelString.equals("")) {
                        SelectionLabel.setText(LabelString.toString());
                    }
                }
            }
        });
        Button Delete_Selection = new Button("Delete Path");
        Delete_Selection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                EPK_Node Chosen_to_Delete_Start = UI_PREVIOUS_ELEMS_START.getSelection();
                EPK_Node Chosen_to_Delete_End = UI_PREVIOUS_ELEMS_END.getSelection();
                Nodemap mark_for_deletion = null;
                if (Chosen_to_Delete_End != null && Chosen_to_Delete_Start != null) {
                    for (Nodemap m : Chosen_Previous_List) {
                        if (m.containsboth(Chosen_to_Delete_Start, Chosen_to_Delete_End)) {
                            mark_for_deletion = m;
                            break;
                        }
                    }
                    if (mark_for_deletion != null) {
                        Chosen_Previous_List.remove(mark_for_deletion);
                    }

                    StringBuilder LabelString = new StringBuilder("");
                    for (Nodemap m : Chosen_Previous_List) {
                        LabelString.append(m.toString());
                        LabelString.append("\n");
                    }
                    if (!LabelString.equals("")) {
                        SelectionLabel.setText(LabelString.toString());
                    }
                }
            }
        });
        prev_nodelist = EPK.getAll_Elems();
        UI_PREVIOUS_ELEMS_START = Field.ofSingleSelectionType(prev_nodelist).label("Path-start: ")
                .tooltip("Vorgänger werden in der Überprüfung der Instanz zur weitergabe am Gate auf" +
                        "Beendigung geprüft (je nach Gate Regel)");
        UI_PREVIOUS_ELEMS_END = Field.ofSingleSelectionType(prev_nodelist).label("Path-end: ")
                .tooltip("Vorgänger werden in der Überprüfung der Instanz zur weitergabe am Gate auf" +
                        "Beendigung geprüft (je nach Gate Regel)");

        FormRenderer PREVIOUS_ELEMS_UI = new FormRenderer(Form.of
                (Group.of
                        (UI_PREVIOUS_ELEMS_START, UI_PREVIOUS_ELEMS_END)));

        Box.getChildren().add(new Separator());
        Box.getChildren().add(IS_EAGER_UI);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(SelectionLabel);
        Box.getChildren().add(PREVIOUS_ELEMS_UI);
        ButtonBar Bar = new ButtonBar();
        Bar.getButtons().add(Save_Selection);
        Bar.getButtons().add(Delete_Selection);
        Box.getChildren().add(Bar);


        return Box;
    }

    @Override
    public void save_Settings() {

    }

    @Override
    public int get_Next_Elem_ID() {
        return 0;
    }

    @Override
    public String toString() {
        return "XOR-Join [" +
                "ID: " + UI_ID_FIELD.getValue() + "]";
    }
}
