package com.company.UI.javafxgraph.fxgraph.graph;

import com.company.UI.javafxgraph.fxgraph.cells.RectangleCell;
import com.company.UI.javafxgraph.fxgraph.cells.TriangleCell;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class MouseGestures {

    final DragContext dragContext = new DragContext();

    SingleSelectionField<Integer> mf = Field.ofSingleSelectionType(Arrays.asList(1, 2, 3, 4)).label("Nachfolgende Elemente").tooltip("Nachfolgende Elemente auswählen");
    Graph graph;
    VBox Box;

    EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Node node = (Node) event.getSource();
            if (node instanceof RectangleCell) {
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

                Box.getChildren().add(new FormRenderer(
                        Form.of(
                                Group.of(Needed_Resources, Needed_Workforces))));

            } else if (node instanceof TriangleCell) {
                Box.getChildren().clear();
                Box.getChildren().add(new FormRenderer(
                        Form.of(
                                Group.of(
                                        Field.ofStringType("Banana").label("Username"),
                                        Field.ofMultiSelectionType(Arrays.asList("Zürich (ZH)", "Bern (BE)", "Gelsenkirchen (GE)"), Arrays.asList())
                                                .label("Biggest Cities")))));
            } else {
                Box.getChildren().clear();
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

    public MouseGestures(Graph graph, VBox box) {
        this.graph = graph;
        this.Box = box;
    }

    public void makeDraggable(final Node node) {


        node.setOnMousePressed(onMousePressedEventHandler);
        node.setOnMouseDragged(onMouseDraggedEventHandler);
        node.setOnMouseReleased(onMouseReleasedEventHandler);
        node.setOnMouseClicked(onMouseClickedEventHandler);

    }

    class DragContext {

        double x;
        double y;

    }
}