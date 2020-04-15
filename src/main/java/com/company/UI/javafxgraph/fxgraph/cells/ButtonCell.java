package com.company.UI.javafxgraph.fxgraph.cells;

import com.company.UI.javafxgraph.fxgraph.graph.Cell;
import javafx.scene.control.Button;

public class ButtonCell extends Cell {

    public ButtonCell(String id) {
        super(id);

        Button view = new Button(id);

        setView(view);

    }

}