package javafxgraph.fxgraph.graph;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafxgraph.fxgraph.cells.RectangleCell;
import javafxgraph.fxgraph.cells.TriangleCell;

import java.util.Arrays;

public class MouseGestures {

    final DragContext dragContext = new DragContext();

    Graph graph;
    VBox Box;
    EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Node node = (Node) event.getSource();
            if (node instanceof RectangleCell) {
                Box.getChildren().clear();
                Box.getChildren().add(new FormRenderer(
                        Form.of(
                                Group.of(
                                        Field.ofStringType("Apple").label("Username"),
                                        Field.ofStringType("").label("Typ")))));
            } else if (node instanceof TriangleCell) {
                Box.getChildren().clear();
                Box.getChildren().add(new FormRenderer(
                        Form.of(
                                Group.of(
                                        Field.ofStringType("Banana").label("Username"),
                                        Field.ofMultiSelectionType(Arrays.asList("Zürich (ZH)", "Bern (BE)", "Gelsenkirchen (GE)"), Arrays.asList(0, 1, 2))
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