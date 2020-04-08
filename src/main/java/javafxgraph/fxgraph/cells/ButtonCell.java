package javafxgraph.fxgraph.cells;

import javafx.scene.control.Button;

import javafxgraph.fxgraph.graph.Cell;

public class ButtonCell extends Cell {

    public ButtonCell(String id) {
        super(id);

        Button view = new Button(id);

        setView(view);

    }

}