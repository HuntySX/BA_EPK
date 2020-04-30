package com.company.EPK;

import com.company.Enums.Contype;
import com.company.Enums.Split_Decide_Type;
import com.company.Enums.Split_Status;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Con_Split extends Connector {

    private BiFunction<Simulation_Instance, List<EPK_Node>, List<EPK_Node>> Pathfinder;
    private Supplier<List<EPK_Node>> Pathfi;
    private List<EPK_Node> Single_Elem;
    private Split_Status Status;
    private Split_Decide_Type Decide_Type;
    private boolean is_Event_Driven;

    public Con_Split(List<EPK_Node> Next_Elem, int ID, Contype contype, Split_Status status, Supplier<List<EPK_Node>> Pathfi, BiFunction<Simulation_Instance, List<EPK_Node>, List<EPK_Node>> Pathfinder, boolean is_Event_Driven) {
        super(Next_Elem, ID, contype);
        this.Pathfinder = Pathfinder;
        this.Single_Elem = new ArrayList<>();
        this.Pathfi = Pathfi;
        this.Status = status;
        this.is_Event_Driven = is_Event_Driven;
    }

    public Con_Split(List<EPK_Node> Next_Elem, int ID, Contype contype, Split_Decide_Type decide_type, boolean is_Event_Driven) {
        super(Next_Elem, ID, contype);
        this.Decide_Type = decide_type;
        this.is_Event_Driven = is_Event_Driven;
    }

    public void setSingle_Elem(List<EPK_Node> Elem) {
        Single_Elem = Elem;
    }

    public List<EPK_Node> choose_Next(Simulation_Instance instance) {
        List<EPK_Node> Path = new ArrayList<>();
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

    public List<EPK_Node> get_Single_Elem() {
        return Single_Elem;
    }

    public BiFunction<Simulation_Instance, List<EPK_Node>, List<EPK_Node>> getPathfinder() {
        return Pathfinder;
    }

    public void setPathfinder(BiFunction<Simulation_Instance, List<EPK_Node>, List<EPK_Node>> pathfinder) {
        Pathfinder = pathfinder;
    }

    public void setPathfi(Supplier<List<EPK_Node>> pathfi) {
        Pathfi = pathfi;
    }

    public Split_Status getStatus() {
        return Status;
    }

    public List<EPK_Node> getRandom(Function f1, Function f2) {
        List<EPK_Node> result = new ArrayList<>();
        Random random = new Random();
        int i = random.nextInt(2);
        if (i == 0) {
            result.add(f1);
        } else {
            result.add(f2);
        }
        return result;
    }

    @Override
    public boolean CheckSettings() {
        boolean Check = true;
        if (Pathfi == null) {
            Check = false;
        }
        if (Pathfinder == null) {
            Check = false;
        }
        if (Single_Elem == null || Single_Elem.isEmpty()) {
            Check = false;
        }
        if (Status == null) {
            Check = false;
        }
        if (Decide_Type == null) {
            Check = false;
        }
        return Check;
    }
}
