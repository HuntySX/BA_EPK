package com.company.UI.EPKUI;

import com.company.EPK.Function;
import com.company.EPK.Node;
import com.company.EPK.Workforce;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.MultiSelectionField;
import com.dlsc.formsfx.view.controls.SimpleListViewControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.*;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

public class UI_Func extends Function implements UI_Instantiable {
    private VBox Box;
    private List<Node> Nodelist;
    private IntegerProperty UI_ID = new SimpleIntegerProperty();
    private StringProperty tag;
    private MultiSelectionField<Node> Next_Elems;
    private BooleanProperty concurrently = new SimpleBooleanProperty(true);
    private List<Resource> Resources;
    private MultiSelectionField<Resource> Needed_Resources;
    private List<Workforce> Workforces;
    private MultiSelectionField<Workforce> Needed_Workforces;
    private IntegerProperty Workingtime_Hours = new SimpleIntegerProperty();
    private IntegerProperty Workingtime_Minutes = new SimpleIntegerProperty();
    private IntegerProperty Workingtime_Seconds = new SimpleIntegerProperty();


    public UI_Func(int ID, UI_EPK EPK) {
        super(null, ID, null, false, null, null, 0, 0, 10);
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
        MultiSelectionField<Node> Next_Elems = Field.ofMultiSelectionType(Nodelist).label("Nachfolger");
        SimpleListViewControl<Node> lv = new SimpleListViewControl<>();
        lv.setField(Next_Elems);
        Box.getChildren().add(lv);
        Box.getChildren().add(new Separator());
        Box.getChildren().add(new Label("Arbeitsstunden"));
        Box.getChildren().add(new FormRenderer(Form.of(
                Group.of(
                        Field.ofIntegerType(Workingtime_Hours).label("Stunden").placeholder("0").tooltip("Arbeitszeitstunden"),
                        Field.ofIntegerType(Workingtime_Minutes).label("Minuten").placeholder("0").tooltip("Arbeitszeitminuten"),
                        Field.ofIntegerType(Workingtime_Seconds).label("Sekunden").placeholder("60").tooltip("Arbeitszeitsekunden")))));
        this.Resources = EPK.getAll_Resources();
        this.Workforces = EPK.getAll_Workforces();
        Needed_Resources = Field.ofMultiSelectionType(Resources, Arrays.asList()).label("Benötigte Ressourcen").tooltip("Benötigte Ressourcen");
        Needed_Workforces = Field.ofMultiSelectionType(Workforces, Arrays.asList()).label("Benötigte Arbeitskraft").tooltip("Benötigte Arbeitskraft");
        Box.getChildren().add(new Label("Benötigte Ressourcen"));
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(Needed_Resources))));
        Box.getChildren().add(new Label("Benötigte Arbeitskraft"));
        Box.getChildren().add(new FormRenderer(Form.of(Group.of(Needed_Workforces))));
    }

    @Override
    public VBox Get_UI() {
        return Box;
    }

    @Override
    public void save_Settings() {

    }

}
