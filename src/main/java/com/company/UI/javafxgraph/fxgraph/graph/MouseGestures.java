package com.company.UI.javafxgraph.fxgraph.graph;

import com.company.UI.Borderpanecon;
import com.company.UI.UI_Button_Active_Type;
import com.company.UI.javafxgraph.fxgraph.cells.RectangleCell;
import com.company.UI.javafxgraph.fxgraph.cells.TriangleCell;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

import static com.company.UI.UI_Button_Active_Type.EVENT;
import static com.company.UI.UI_Button_Active_Type.NORMAL;

public class MouseGestures {

    final DragContext dragContext = new DragContext();

    SingleSelectionField<Integer> mf = Field.ofSingleSelectionType(Arrays.asList(1, 2, 3, 4)).label("Nachfolgende Elemente").tooltip("Nachfolgende Elemente auswählen");
    Graph graph;
    VBox Box;
    Model model;
    Borderpanecon controller;

    EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Node node = (Node) event.getSource();

            if (node instanceof RectangleCell && controller.getBtn_Type() == NORMAL) {
                Box.getChildren().clear();
                Box.getChildren().add(new Label("Nächste Elemente"));
                Box.getChildren().add(new FormRenderer(Form.of(Group.of(mf))));
                Box.getChildren().add(new Separator());
                Box.getChildren().add(new FormRenderer(
                        Form.of(
                                Group.of(
                                        Field.ofStringType("Apple").label("Username"),
                                        Field.ofStringType("Banana").label("Typ")))));
                MultiSelectionField<Integer> Needed_Resources;
                MultiSelectionField<Integer> Needed_Workforces;
                Needed_Resources = Field.ofMultiSelectionType(Arrays.asList(1, 2, 3), Arrays.asList()).label("Benötigte Ressourcen").tooltip("Benötigte Ressourcen");
                Needed_Workforces = Field.ofMultiSelectionType(Arrays.asList(3, 4, 5), Arrays.asList()).label("Benötigte Arbeitskraft").tooltip("Benötigte Arbeitskraft");

                List<Cell> Cells = model.getAllCells();
                int index = 0;
                Cell cell = new Cell(null);
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
                                Group.of(Needed_Resources, Needed_Workforces))));

            } else if (node instanceof TriangleCell && controller.getBtn_Type() == NORMAL) {

                UI_Button_Active_Type Type = controller.getBtn_Type();

                switch (Type) {
                    case EVENT:
                    case FUNCTION:
                    case AND_JOIN:
                    case OR_JOIN:
                    case XOR_JOIN:
                    case AND_SPLIT:
                    case OR_SPLIT:
                    case XOR_SPLIT:
                    case START_EVENT:
                    case END_EVENT:
                    case ACTIVATING_START_EVENT:
                    case ACTIVATING_FUNCTION:
                }

                Box.getChildren().clear();
                Box.getChildren().add(new FormRenderer(
                        Form.of(
                                Group.of(
                                        Field.ofStringType("Banana").label("Username"),
                                        Field.ofMultiSelectionType(Arrays.asList("Zürich (ZH)", "Bern (BE)", "Gelsenkirchen (GE)"), Arrays.asList())
                                                .label("Biggest Cities")))));
                System.out.println("Cell ID of clicked Elem: " + ((TriangleCell) event.getSource()).getCellId());


            } else if (node instanceof ZoomableScrollPane && controller.getBtn_Type() != NORMAL) {
                Box.getChildren().clear();
                double x = event.getX();
                double y = event.getY();
                model.addCell("Cell H", CellType.TRIANGLE);
                graph.endUpdate();
                Cell cell = new Cell(null);
                for (Cell c : model.getAllCells()) {
                    if (c.getCellId().equals("Cell H")) {
                        cell = c;
                        break;
                    }
                }

                double scale = graph.getScale();

                x /= scale;
                y /= scale;

                cell.relocate(x, y);

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