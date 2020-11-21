package com.company.UI.javafxgraph.fxgraph.cells;

import com.company.EPK.EPK_Node;
import com.company.UI.EPKUI.*;
import com.company.UI.javafxgraph.fxgraph.graph.Cell;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class UI_View_Gen extends Cell {

    public UI_View_Gen(int cellId, EPK_Node EPKNode) {
        super(cellId, EPKNode);

        ImageView view = null;

        if (EPKNode instanceof UI_Event) {
            File file = new File("src/Icons/Event.png");
            Image image = new Image(file.toURI().toString());
            view = new ImageView(image);
        } else if (EPKNode instanceof UI_Func) {
            File file = new File("src/Icons/Function.png");
            Image image = new Image(file.toURI().toString());
            view = new ImageView(image);
        } else if (EPKNode instanceof UI_AND_Join) {
            File file = new File("src/Icons/AND_Join.png");
            Image image = new Image(file.toURI().toString());
            view = new ImageView(image);
        } else if (EPKNode instanceof UI_OR_Join) {
            File file = new File("src/Icons/OR_Join.png");
            Image image = new Image(file.toURI().toString());
            view = new ImageView(image);
        } else if (EPKNode instanceof UI_XOR_Join) {
            File file = new File("src/Icons/XOR_Join.png");
            Image image = new Image(file.toURI().toString());
            view = new ImageView(image);
        } else if (EPKNode instanceof UI_AND_Split) {
            File file = new File("src/Icons/AND_Split.png");
            Image image = new Image(file.toURI().toString());
            view = new ImageView(image);
        } else if (EPKNode instanceof UI_OR_Split) {
            File file = new File("src/Icons/OR_Split.png");
            Image image = new Image(file.toURI().toString());
            view = new ImageView(image);
        } else if (EPKNode instanceof UI_XOR_Split) {
            File file = new File("src/Icons/XOR_Split.png");
            Image image = new Image(file.toURI().toString());
            view = new ImageView(image);
        } else if (EPKNode instanceof UI_Event_Starter) {
            File file = new File("src/Icons/Start_Event.png");
            Image image = new Image(file.toURI().toString());
            view = new ImageView(image);
        } else if (EPKNode instanceof UI_Event_Ending) {
            File file = new File("src/Icons/End_Event.png");
            Image image = new Image(file.toURI().toString());
            view = new ImageView(image);
        } else if (EPKNode instanceof UI_Activate_Function) {
            File file = new File("src/Icons/Activating_Function.png");
            Image image = new Image(file.toURI().toString());
            view = new ImageView(image);
        } else if (EPKNode instanceof UI_Event_Activating_Starter) {
            File file = new File("src/Icons/Activating_Event.png");
            Image image = new Image(file.toURI().toString());
            view = new ImageView(image);
        } else if (EPKNode instanceof UI_External_Function) {
            File file = new File("src/Icons/External_Function.png");
            Image image = new Image(file.toURI().toString());
            view = new ImageView(image);
        } else if (EPKNode instanceof UI_External_XOR) {
            File file = new File("src/Icons/External_XOR.png");
            Image image = new Image(file.toURI().toString());
            view = new ImageView(image);
        }

        Label label = new Label(Integer.toString(EPKNode.getID()));
        setView(view, label);
    }

    public EPK_Node getNodeView() {
        return this.getEPKNode();
    }

}
