package com.company.UI.javafxgraph.fxgraph.cells;

import com.company.UI.javafxgraph.fxgraph.graph.Cell;
import javafx.scene.control.Label;

public class LabelCell extends Cell {

    public LabelCell(int id) {
        super(id);

        String s = Integer.toString(id);
        Label view = new Label(s);
        setView(view);
    }

}
