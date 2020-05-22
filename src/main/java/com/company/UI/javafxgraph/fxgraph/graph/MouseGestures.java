package com.company.UI.javafxgraph.fxgraph.graph;

import com.company.EPK.EPK_Node;
import com.company.UI.Borderpanecon;
import com.company.UI.EPKUI.UI_Instantiable;
import com.company.UI.UI_Button_Active_Type;
import com.company.UI.javafxgraph.fxgraph.cells.UI_View_Gen;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import static com.company.UI.UI_Button_Active_Type.CONNECT;
import static com.company.UI.UI_Button_Active_Type.NORMAL;

public class MouseGestures {

    final DragContext dragContext = new DragContext();

    Graph graph;
    VBox Box;
    Model model;
    Borderpanecon controller;

    EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
                System.out.println("Test123");
                Box.getChildren().clear();
                model.getEPK().setActive_Elem_For_Connection(null);
                controller.setBtn_Type(NORMAL);
                graph.getBorderpanecontroller().deactivateDeleteButton();
            } else {
                Node node = (Node) event.getSource();

                if (node instanceof UI_View_Gen && controller.getBtn_Type() == CONNECT) {
                    Box.getChildren().clear();
                    graph.getBorderpanecontroller().deactivateDeleteButton();
                    if (model.getEPK().getActive_Elem() != null) {
                        model.getEPK().getActive_Elem().getEPKNode().add_Next_Elem(((UI_View_Gen) node).getEPKNode());
                        model.addEdge(model.getEPK().getActive_Elem().getCellId(), ((UI_View_Gen) node).getCellId());
                        graph.endUpdate();
                        model.getEPK().setActive_Elem_For_Connection(null);
                        controller.setBtn_Type(NORMAL);
                    } else {
                        model.getEPK().setActive_Elem_For_Connection((UI_View_Gen) node);
                    }
                } else if (node instanceof UI_View_Gen && controller.getBtn_Type() == NORMAL) {
                    graph.getBorderpanecontroller().deactivateDeleteButton();
                    model.getEPK().setActive_Elem_For_Connection((UI_View_Gen) node);
                    EPK_Node UI_Node = ((UI_View_Gen) node).getEPKNode();
                    if (UI_Node instanceof UI_Instantiable) {
                        VBox new_side_View = ((UI_Instantiable) UI_Node).Get_UI();
                        Box.getChildren().clear();
                        Box.getChildren().add(new_side_View);
                        graph.getBorderpanecontroller().activateDeleteButton();
                    }

                } /*else if (node instanceof ZoomableScrollPane && controller.getBtn_Type() == NORMAL) {

                if(!Box.getChildren().isEmpty()) {
                    Box.getChildren().clear();
                }
            }*/ else if (node instanceof ZoomableScrollPane && controller.getBtn_Type() != NORMAL && controller.getBtn_Type() != CONNECT) {

                    graph.getBorderpanecontroller().deactivateDeleteButton();
                    model.getEPK().setActive_Elem_For_Connection(null);
                    UI_Button_Active_Type Btn_Type = controller.getBtn_Type();
                    Box.getChildren().clear();

                    double x = event.getX();
                    double y = event.getY();

                    int Unique_ID_for_Cell = model.getUniqueCellID();

                    model.addCell(Unique_ID_for_Cell, Btn_Type);
                    graph.endUpdate();
                    Cell cell = new Cell(Unique_ID_for_Cell, null);
                    for (Cell c : model.getAllCells()) {
                        if (c.getCellId() == cell.getCellId()) {
                            cell = c;
                            break;
                        }
                    }


                    double scale = graph.getScale();
                    x /= scale;
                    y /= scale;
                    cell.relocate(x, y);
                    controller.setBtn_Type(NORMAL);

                    VBox new_side_View = ((UI_Instantiable) cell.getEPKNode()).Get_UI();
                    Box.getChildren().clear();
                    Box.getChildren().add(new_side_View);
                    graph.getBorderpanecontroller().activateDeleteButton();

                }
            }
        }
    };
    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            Node node = (Node) event.getSource();

            double scale = graph.getScale();

            dragContext.x = node.getBoundsInParent().getMinX() * scale - event.getScreenX();
            dragContext.y = node.getBoundsInParent().getMinY() * scale - event.getScreenY();

        }
    };
    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            Node node = (Node) event.getSource();

            double offsetX = event.getScreenX() + dragContext.x;
            double offsetY = event.getScreenY() + dragContext.y;

            // adjust the offset in case we are zoomed
            double scale = graph.getScale();

            offsetX /= scale;
            offsetY /= scale;

            node.relocate(offsetX, offsetY);

        }
    };

    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

        }
    };

    public MouseGestures(Graph graph, VBox box, Model model, Borderpanecon controller) {
        this.graph = graph;
        this.Box = box;
        this.model = model;
        this.controller = controller;
    }

    public void makeDraggable(final Node node) {

        node.setOnMousePressed(onMousePressedEventHandler);
        node.setOnMouseDragged(onMouseDraggedEventHandler);
        node.setOnMouseReleased(onMouseReleasedEventHandler);
        node.setOnMouseClicked(onMouseClickedEventHandler);
    }

    public void makeCanvasClickable(final Node node) {
        node.setOnMouseClicked(onMouseClickedEventHandler);
    }

    class DragContext {

        double x;
        double y;

    }
}

 /*List<Cell> Cells = model.getAllCells();
                int index = 0;
                Cell cell = new Cell(model.getUniqueCellID(),);
                for (Cell c : Cells) {
                    if (c.getCellId().equals("Cell A")) {
                        cell = c;
                        index = Cells.indexOf(c);
                        break;
                    }
                }
                if (cell != null) {
                    Cells.remove(cell);
                    model.getRemovedCells().add(cell);
                    graph.endUpdate();
                }


                Box.getChildren().add(new FormRenderer(
                        Form.of(
                                Group.of(Needed_Resources, Needed_Workforces))));*/