package com.company.UI.javafxgraph.fxgraph.cells;

import com.company.UI.javafxgraph.fxgraph.graph.Cell;
import javafx.scene.control.Button;

public class ButtonCell extends Cell {

    public ButtonCell(int id) {
        super(id);

        Button view = new Button(Integer.toString(id));
        setView(view);

    }

}