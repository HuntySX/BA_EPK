package com.company.EPK;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Con_Split extends Connector {

    private Consumer<List<Node>> Pathfinder;

    public Con_Split(List<Node> Next_Elem, int ID, Contype type, Consumer<List<Node>> Pathfinder) {
        super(Next_Elem, ID, type);
        this.Pathfinder = Pathfinder;
    }

    public List<Node> choose_Next() {
        List<Node> Path = new ArrayList<>();
        Pathfinder.accept(Path);
        return Path;
    }

    public Consumer<List<Node>> getPathfinder() {
        return Pathfinder;
    }

    public void setPathfinder(Consumer<List<Node>> pathfinder) {
        Pathfinder = pathfinder;
    }

}
