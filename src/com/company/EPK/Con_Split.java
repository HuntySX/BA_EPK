package com.company.EPK;

import com.company.Enums.Contype;
import com.company.Simulation.Instance.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Con_Split extends Connector {

    private Consumer<List<Node>> Pathfinder;

    public Con_Split(List<Node> Next_Elem, int ID, Contype contype, Consumer<List<Node>> Pathfinder) {
        super(Next_Elem, ID, contype);
        this.Pathfinder = Pathfinder;
    }

    public List<Node> choose_Next(Simulation_Instance instance) {
        List<Node> Path = new ArrayList<>();
        Pathfinder.accept(Path);
        //TODO Instance bewerten und danach den Path entscheiden.
        return Path;
    }

    public Consumer<List<Node>> getPathfinder() {
        return Pathfinder;
    }

    public void setPathfinder(Consumer<List<Node>> pathfinder) {
        Pathfinder = pathfinder;
    }

}
