package com.company.EPK;

import com.company.Enums.Contype;
import com.company.Enums.Split_Status;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Con_Split extends Connector {

    private BiFunction<Simulation_Instance, List<Node>, List<Node>> Pathfinder;
    private Supplier<List<Node>> Pathfi;
    private List<Node> Single_Elem;
    private Split_Status Status;

    public Con_Split(List<Node> Next_Elem, int ID, Contype contype, Split_Status status, Supplier<List<Node>> Pathfi, BiFunction<Simulation_Instance, List<Node>, List<Node>> Pathfinder) {
        super(Next_Elem, ID, contype);
        this.Pathfinder = Pathfinder;
        this.Single_Elem = new ArrayList<>();
        this.Pathfi = Pathfi;
        this.Status = status;
    }

    public void setSingle_Elem(List<Node> Elem) {
        Single_Elem = Elem;
    }

    public List<Node> choose_Next(Simulation_Instance instance) {
        List<Node> Path = new ArrayList<>();
        if (Status == Split_Status.ChooseFirst) {
            Path = Pathfinder.apply(instance, get_Single_Elem());
        } else if (Status == Split_Status.General) {
            Path = Pathfinder.apply(instance, getNext_Elem());
        } else {
            Path = Pathfi.get();
        }
        return Path;
    }

    /*public List<Node> choose_Next(Simulation_Instance instance) {
        List<Node> Path = new ArrayList<>();
        Path = Pathfi.get();
        return Path;
    }*/

    public List<Node> get_Single_Elem() {
        return Single_Elem;
    }

    public BiFunction<Simulation_Instance, List<Node>, List<Node>> getPathfinder() {
        return Pathfinder;
    }

    public void setPathfinder(BiFunction<Simulation_Instance, List<Node>, List<Node>> pathfinder) {
        Pathfinder = pathfinder;
    }

    public void setPathfi(Supplier<List<Node>> pathfi) {
        Pathfi = pathfi;
    }

    public Split_Status getStatus() {
        return Status;
    }

    public List<Node> getRandom(Function f1, Function f2) {
        List<Node> result = new ArrayList<>();
        Random random = new Random();
        int i = random.nextInt(2);
        if (i == 0) {
            result.add(f1);
        } else {
            result.add(f2);
        }
        return result;
    }
}
