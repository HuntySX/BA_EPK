package com.company.UI.javafxgraph.fxgraph.graph;

import com.company.UI.UI_Controller;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Graph {

    MouseGestures mouseGestures;
    /**
     * the pane wrapper is necessary or else the scrollpane would always align
     * the top-most and left-most child to the top and left eg when you drag the
     * top child down, the entire scrollpane would move down
     */
    CellLayer cellLayer;
    private final Model model;
    private Node Active_Element;
    private final VBox VBox;
    private final Group canvas;
    private final UI_Controller controller;
    private final ZoomableScrollPane scrollPane;

    public Graph(VBox Box, UI_Controller controller) {

        this.model = new Model(this);
        this.VBox = Box;
        this.controller = controller;

        canvas = new Group();
        cellLayer = new CellLayer();

        canvas.getChildren().add(cellLayer);

        mouseGestures = new MouseGestures(this, Box, model, controller);

        scrollPane = new ZoomableScrollPane(canvas);

        mouseGestures.makeCanvasClickable(scrollPane);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

    }

    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public Pane getCellLayer() {
        return this.cellLayer;
    }

    public Model getModel() {
        return model;
    }

    public Node getActive_Element() {
        return Active_Element;
    }

    public void setActive_Element(Node active_Element) {
        Active_Element = active_Element;
    }

    public void beginUpdate() {
    }

    public void endUpdate() {

        // add components to graph pane
        getCellLayer().getChildren().addAll(model.getAddedEdges());
        getCellLayer().getChildren().addAll(model.getAddedCells());

        // remove components from graph pane
        getCellLayer().getChildren().removeAll(model.getRemovedCells());
        getCellLayer().getChildren().removeAll(model.getRemovedEdges());

        // enable dragging of cells
        for (Cell cell : model.getAddedCells()) {
            mouseGestures.makeDraggable(cell);
        }

        // every cell must have a parent, if it doesn't, then the graphParent is
        // the parent
        getModel().attachOrphansToGraphParent(model.getAddedCells());

        // remove reference to graphParent
        getModel().disconnectFromGraphParent(model.getRemovedCells());

        // merge added & removed cells with all cells
        getModel().merge();

    }

    public double getScale() {
        return this.scrollPane.getScaleValue();
    }

    public UI_Controller getBorderpanecontroller() {

        return controller;
    }
}