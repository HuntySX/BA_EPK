package com.company.UI.javafxgraph.fxgraph.graph;

import com.company.EPK.EPK_Node;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Cell extends Pane {

    int cellId;
    EPK_Node UI_Node;

    List<Cell> children = new ArrayList<>();
    List<Cell> parents = new ArrayList<>();

    Node view;
    Node label;

    public Cell(int cellId, EPK_Node node) {
        this.cellId = cellId;
        this.UI_Node = node;
    }

    public void addCellChild(Cell cell) {
        children.add(cell);
    }

    public List<Cell> getCellChildren() {
        return children;
    }

    public void addCellParent(Cell cell) {
        parents.add(cell);
    }

    public List<Cell> getCellParents() {
        return parents;
    }

    public void removeCellChild(Cell cell) {
        children.remove(cell);
    }

    public Node getView() {
        return this.view;
    }

    public Node getLabel() {
        return this.label;
    }

    public EPK_Node getEPKNode() {
        return UI_Node;
    }

    public void setView(Node view, Node label) {

        this.view = view;
        this.label = label;
        getChildren().add(view);
        getChildren().add(label);
    }

    public int getCellId() {
        return cellId;
    }

}