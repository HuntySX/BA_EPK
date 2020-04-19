package com.company.UI.javafxgraph.fxgraph.graph;

import com.company.UI.EPKUI.UI_EPK;
import com.company.UI.EPKUI.UI_Event;
import com.company.UI.UI_Button_Active_Type;
import com.company.UI.javafxgraph.fxgraph.cells.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    Map<Integer, Cell> cellMap; // <id,cell>
    private UI_EPK EPK;
    Cell graphParent;

    List<Cell> allCells;
    List<Cell> addedCells;
    List<Cell> removedCells;

    List<Edge> allEdges;
    List<Edge> addedEdges;
    List<Edge> removedEdges;
    private int UI_ID = 1;

    public Model() {
        EPK = new UI_EPK();
        graphParent = new Cell(0, null);

        // clear model, create lists
        clear();
    }

    public void clear() {

        allCells = new ArrayList<>();
        addedCells = new ArrayList<>();
        removedCells = new ArrayList<>();

        allEdges = new ArrayList<>();
        addedEdges = new ArrayList<>();
        removedEdges = new ArrayList<>();

        cellMap = new HashMap<Integer, Cell>(); // <id,cell>

    }

    public void clearAddedLists() {
        addedCells.clear();
        addedEdges.clear();
    }

    public List<Cell> getAddedCells() {
        return addedCells;
    }

    public List<Cell> getRemovedCells() {
        return removedCells;
    }

    public List<Cell> getAllCells() {
        return allCells;
    }

    public List<Edge> getAddedEdges() {
        return addedEdges;
    }

    public List<Edge> getRemovedEdges() {
        return removedEdges;
    }

    public List<Edge> getAllEdges() {
        return allEdges;
    }

    public void addCell(int id, UI_Button_Active_Type Btn_Type) {

        switch (Btn_Type) {

            case EVENT:
                int unique_ID = getUniqueCellID();
                UI_Event Event = new UI_Event(unique_ID, EPK);
                addCell();
                break;

            case FUNCTION:
                TriangleCell circleCell = new TriangleCell(id);
                addCell(circleCell);
                break;

            case AND_JOIN:
                LabelCell labelCell = new LabelCell(id);
                addCell(labelCell);
                break;

            case OR_JOIN:
                ImageCell imageCell = new ImageCell(id);
                addCell(imageCell);
                break;

            case XOR_JOIN:
                ButtonCell buttonCell = new ButtonCell(id);
                addCell(buttonCell);
                break;

            case AND_SPLIT:
                TitledPaneCell titledPaneCell = new TitledPaneCell(id);
                addCell(titledPaneCell);
                break;

            case OR_SPLIT:
                ImageCell imageCell = new ImageCell(id);
                addCell(imageCell);
                break;

            case XOR_SPLIT:
                ButtonCell buttonCell = new ButtonCell(id);
                addCell(buttonCell);
                break;

            case START_EVENT:
                TitledPaneCell titledPaneCell = new TitledPaneCell(id);
                addCell(titledPaneCell);
                break;

            case END_EVENT:
                ImageCell imageCell = new ImageCell(id);
                addCell(imageCell);
                break;

            case ACTIVATING_FUNCTION:
                ButtonCell buttonCell = new ButtonCell(id);
                addCell(buttonCell);
                break;

            case ACTIVATING_START_EVENT:
                TitledPaneCell titledPaneCell = new TitledPaneCell(id);
                addCell(titledPaneCell);
                break;
        }

    }

    @Deprecated
    public void addCell(int id, CellType type) {

        switch (type) {

            case RECTANGLE:
                RectangleCell rectangleCell = new RectangleCell(id);
                addCell(rectangleCell);
                break;

            case TRIANGLE:
                TriangleCell circleCell = new TriangleCell(id);
                addCell(circleCell);
                break;

            case LABEL:
                LabelCell labelCell = new LabelCell(id);
                addCell(labelCell);
                break;

            case IMAGE:
                ImageCell imageCell = new ImageCell(id);
                addCell(imageCell);
                break;

            case BUTTON:
                ButtonCell buttonCell = new ButtonCell(id);
                addCell(buttonCell);
                break;

            case TITLEDPANE:
                TitledPaneCell titledPaneCell = new TitledPaneCell(id);
                addCell(titledPaneCell);
                break;
        }
    }

    private void addCell(Cell cell) {

        addedCells.add(cell);

        cellMap.put(cell.getCellId(), cell);

    }

    public void addEdge(String sourceId, String targetId) {

        Cell sourceCell = cellMap.get(sourceId);
        Cell targetCell = cellMap.get(targetId);

        Edge edge = new Edge(sourceCell, targetCell);

        addedEdges.add(edge);

    }

    /**
     * Attach all cells which don't have a parent to graphParent
     *
     * @param cellList
     */
    public void attachOrphansToGraphParent(List<Cell> cellList) {

        for (Cell cell : cellList) {
            if (cell.getCellParents().size() == 0) {
                graphParent.addCellChild(cell);
            }
        }

    }

    /**
     * Remove the graphParent reference if it is set
     *
     * @param cellList
     */
    public void disconnectFromGraphParent(List<Cell> cellList) {

        for (Cell cell : cellList) {
            graphParent.removeCellChild(cell);
        }
    }

    public void merge() {

        // cells
        allCells.addAll(addedCells);
        allCells.removeAll(removedCells);

        addedCells.clear();
        removedCells.clear();

        // edges
        allEdges.addAll(addedEdges);
        allEdges.removeAll(removedEdges);

        addedEdges.clear();
        removedEdges.clear();

    }

    public int getUniqueCellID() {
        int id = UI_ID;
        UI_ID++;
        return id;
    }
}