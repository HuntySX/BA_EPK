package com.company.UI.javafxgraph.fxgraph.cells;

import com.company.UI.javafxgraph.fxgraph.graph.Cell;
import javafx.scene.control.Label;

public class LabelCell extends Cell {

    public LabelCell(String id) {
        super(id);

        Label view = new Label(id);

        setView(view);

    }

}
