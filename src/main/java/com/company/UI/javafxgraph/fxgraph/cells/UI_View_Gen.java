package com.company.UI.javafxgraph.fxgraph.cells;

import com.company.EPK.EPK_Node;
import com.company.UI.EPKUI.UI_AND_Join;
import com.company.UI.EPKUI.UI_Event;
import com.company.UI.EPKUI.UI_Func;
import com.company.UI.EPKUI.UI_OR_Join;
import com.company.UI.javafxgraph.fxgraph.graph.Cell;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class UI_View_Gen extends Cell {

    public UI_View_Gen(int cellId, EPK_Node EPKNode) {
        super(cellId, EPKNode);

        ImageView view = null;

        if (EPKNode instanceof UI_Event) {
            view = new ImageView("http://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Siberischer_tiger_de_edit02.jpg/800px-Siberischer_tiger_de_edit02.jpg");
        } else if (EPKNode instanceof UI_Func) {
            view = new ImageView("http://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Siberischer_tiger_de_edit02.jpg/800px-Siberischer_tiger_de_edit02.jpg");
        } else if (EPKNode instanceof UI_AND_Join) {
            view = new ImageView("http://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Siberischer_tiger_de_edit02.jpg/800px-Siberischer_tiger_de_edit02.jpg");
        } else if (EPKNode instanceof UI_OR_Join) {
            view = new ImageView("http://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Siberischer_tiger_de_edit02.jpg/800px-Siberischer_tiger_de_edit02.jpg");
        } else if (EPKNode instanceof UI_Func) {
            view = new ImageView("http://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Siberischer_tiger_de_edit02.jpg/800px-Siberischer_tiger_de_edit02.jpg");
        } else if (EPKNode instanceof UI_Func) {
            view = new ImageView("http://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Siberischer_tiger_de_edit02.jpg/800px-Siberischer_tiger_de_edit02.jpg");
        } else if (EPKNode instanceof UI_Func) {
            view = new ImageView("http://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Siberischer_tiger_de_edit02.jpg/800px-Siberischer_tiger_de_edit02.jpg");
        } else if (EPKNode instanceof UI_Func) {
            view = new ImageView("http://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Siberischer_tiger_de_edit02.jpg/800px-Siberischer_tiger_de_edit02.jpg");
        } else if (EPKNode instanceof UI_Func) {
            view = new ImageView("http://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Siberischer_tiger_de_edit02.jpg/800px-Siberischer_tiger_de_edit02.jpg");
        } else if (EPKNode instanceof UI_Func) {
            view = new ImageView("http://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Siberischer_tiger_de_edit02.jpg/800px-Siberischer_tiger_de_edit02.jpg");
        } else if (EPKNode instanceof UI_Func) {
            view = new ImageView("http://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Siberischer_tiger_de_edit02.jpg/800px-Siberischer_tiger_de_edit02.jpg");
        } else if (EPKNode instanceof UI_Func) {
            view = new ImageView("http://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Siberischer_tiger_de_edit02.jpg/800px-Siberischer_tiger_de_edit02.jpg");
        }

        view.setFitWidth(100);
        view.setFitHeight(80);

        Label label = new Label(Integer.toString(EPKNode.getID()));
        setView(view, label);
    }
}
