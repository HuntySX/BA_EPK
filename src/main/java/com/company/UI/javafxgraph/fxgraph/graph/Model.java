package com.company.UI.javafxgraph.fxgraph.graph;

import com.company.UI.EPKUI.*;
import com.company.UI.UI_Button_Active_Type;
import com.company.UI.javafxgraph.fxgraph.cells.UI_View_Gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    Map<Integer, Cell> cellMap; // <id,cell>
    private UI_EPK EPK;
    Cell graphParent;
    Graph graph;
    List<Cell> allCells;
    List<Cell> addedCells;
    List<Cell> removedCells;

    List<Edge> allEdges;
    List<Edge> addedEdges;
    List<Edge> removedEdges;
    private int UI_ID = 1;

    public Model(Graph graph) {
        EPK = new UI_EPK(this, graph);
        this.graph = graph;
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
        UI_View_Gen UI_Elem = null;

        switch (Btn_Type) {

            case EVENT:
                UI_Event Element = new UI_Event(id, EPK, graph.getBorderpanecontroller().getRightbox());
                UI_Elem = new UI_View_Gen(id, Element);
                getEPK().getAll_Elems().add(Element);
                getEPK().getEvents().add(Element);
                break;

            case FUNCTION:
                UI_Func Function = new UI_Func(id, EPK, graph.getBorderpanecontroller().getRightbox());
                UI_Elem = new UI_View_Gen(id, Function);
                getEPK().getAll_Elems().add(Function);
                getEPK().getFunctions().add(Function);
                break;

            case AND_JOIN:
                UI_AND_Join AND_Join = new UI_AND_Join(id, EPK, graph.getBorderpanecontroller().getRightbox());
                UI_Elem = new UI_View_Gen(id, AND_Join);
                getEPK().getAll_Elems().add(AND_Join);
                getEPK().getAND_Joins().add(AND_Join);
                break;

            case OR_JOIN:
                UI_OR_Join OR_Join = new UI_OR_Join(id, EPK, graph.getBorderpanecontroller().getRightbox());
                UI_Elem = new UI_View_Gen(id, OR_Join);
                getEPK().getAll_Elems().add(OR_Join);
                getEPK().getOR_Joins().add(OR_Join);
                break;

            case XOR_JOIN:
                UI_XOR_Join XOR_Join = new UI_XOR_Join(id, EPK, graph.getBorderpanecontroller().getRightbox());
                UI_Elem = new UI_View_Gen(id, XOR_Join);
                getEPK().getAll_Elems().add(XOR_Join);
                getEPK().getXOR_Joins().add(XOR_Join);
                break;

            case AND_SPLIT:
                UI_AND_Split AND_Split = new UI_AND_Split(id, EPK, graph.getBorderpanecontroller().getRightbox());
                UI_Elem = new UI_View_Gen(id, AND_Split);
                getEPK().getAll_Elems().add(AND_Split);
                getEPK().getAND_Splits().add(AND_Split);
                break;

            case OR_SPLIT:
                UI_OR_Split OR_Split = new UI_OR_Split(id, EPK, graph.getBorderpanecontroller().getRightbox());
                UI_Elem = new UI_View_Gen(id, OR_Split);
                getEPK().getAll_Elems().add(OR_Split);
                getEPK().getOR_Splits().add(OR_Split);
                break;

            case XOR_SPLIT:
                UI_XOR_Split XOR_Split = new UI_XOR_Split(id, EPK, graph.getBorderpanecontroller().getRightbox());
                UI_Elem = new UI_View_Gen(id, XOR_Split);
                getEPK().getAll_Elems().add(XOR_Split);
                getEPK().getXOR_Splits().add(XOR_Split);
                break;

            case START_EVENT:
                UI_Event_Starter Start_Event = new UI_Event_Starter(id, EPK, graph.getBorderpanecontroller().getRightbox());
                UI_Elem = new UI_View_Gen(id, Start_Event);
                getEPK().getAll_Elems().add(Start_Event);
                getEPK().getStart_Events().add(Start_Event);
                break;

            case END_EVENT:
                UI_Event_Ending Ending_Event = new UI_Event_Ending(id, EPK, graph.getBorderpanecontroller().getRightbox());
                UI_Elem = new UI_View_Gen(id, Ending_Event);
                getEPK().getAll_Elems().add(Ending_Event);
                getEPK().getEnd_Events().add(Ending_Event);
                break;

            case ACTIVATING_FUNCTION:
                UI_Activate_Function Activating_Func = new UI_Activate_Function(id, EPK, graph.getBorderpanecontroller().getRightbox());
                UI_Elem = new UI_View_Gen(id, Activating_Func);
                getEPK().getAll_Elems().add(Activating_Func);
                getEPK().getAll_Activating_Functions().add(Activating_Func);
                break;

            case ACTIVATING_START_EVENT:
                UI_Event_Activating_Starter Event_Activating_Starter = new UI_Event_Activating_Starter(id, EPK, graph.getBorderpanecontroller().getRightbox());
                UI_Elem = new UI_View_Gen(id, Event_Activating_Starter);
                getEPK().getAll_Elems().add(Event_Activating_Starter);
                getEPK().getAll_Activating_Start_Events().add(Event_Activating_Starter);
                break;
        }
        addCell(UI_Elem);

    }


    private void addCell(Cell cell) {

        addedCells.add(cell);

        cellMap.put(cell.getCellId(), cell);

    }

    public void addEdge(int sourceId, int targetId) {

        Cell sourceCell = cellMap.get(sourceId);
        Cell targetCell = cellMap.get(targetId);

        Edge edge = new Edge(sourceCell, targetCell);

        addedEdges.add(edge);

    }

    public Cell getCellByID(int ID) {
        return cellMap.get(ID);
    }

    public void removeEdge(int sourceID, int targetID) {


        Cell sourceCell = cellMap.get(sourceID);
        Cell targetCell = cellMap.get(targetID);
        Edge Search = null;
        for (Edge E : allEdges) {
            if (E.source.getCellId() == sourceID && E.target.getCellId() == targetID) {
                Search = E;
                break;
            }
        }
        if (Search != null) {
            removedEdges.add(Search);

        }
    }

    public void removebyTargetEdge(int targetID) {

        List<Edge> Searches = new ArrayList<>();
        for (Edge E : allEdges) {
            if (E.target.getCellId() == targetID) {
                Searches.add(E);
                break;
            }
        }
        if (!Searches.isEmpty()) {
            removedEdges.addAll(Searches);

        }
    }

    public void removebySourceEdge(int sourceID) {


        List<Edge> Searches = new ArrayList<>();
        for (Edge E : allEdges) {
            if (E.source.getCellId() == sourceID) {
                Searches.add(E);
                break;
            }
        }
        if (!Searches.isEmpty()) {
            removedEdges.addAll(Searches);

        }
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

    public UI_EPK getEPK() {
        return EPK;
    }

    public Graph getGraph() {
        return graph;
    }
}