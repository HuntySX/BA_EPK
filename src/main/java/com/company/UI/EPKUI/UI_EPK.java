package com.company.UI.EPKUI;

import com.company.EPK.Node;
import com.company.EPK.Workforce;
import com.company.Enums.Decide_Activation_Type;
import com.company.Enums.Split_Decide_Type;
import com.company.Enums.Start_Event_Type;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class UI_EPK {
    private Node Active_Elem;
    private List<Node> All_Elems;
    private List<UI_Event> Events;
    private List<UI_Event_Starter> Start_Events;
    private List<UI_Event_Ending> End_Events;
    private List<UI_Event_Activating_Starter> Activating_Start_Events;
    private List<UI_Activate_Function> Activate_Functions;
    private List<UI_Func> Functions;
    private List<UI_XOR_Split> XOR_Splits;
    private List<UI_OR_Split> OR_Splits;
    private List<UI_AND_Split> AND_Splits;
    private List<UI_XOR_Join> XOR_Joins;
    private List<UI_OR_Join> OR_Joins;
    private List<UI_AND_Join> AND_Joins;
    private List<Start_Event_Type> Start_Event_Types;
    private List<Decide_Activation_Type> Decide_Activation_Types;
    private List<Resource> All_Resources;
    private List<Workforce> All_Workforces;
    private VBox Box;


    public UI_EPK() {
        Active_Elem = null;
        All_Elems = new ArrayList<>();
        Events = new ArrayList<>();
        Start_Events = new ArrayList<>();
        End_Events = new ArrayList<>();
        Activating_Start_Events = new ArrayList<>();
        Activate_Functions = new ArrayList<>();
        Functions = new ArrayList<>();
        this.XOR_Splits = new ArrayList<>();
        this.OR_Splits = new ArrayList<>();
        this.AND_Splits = new ArrayList<>();
        this.XOR_Joins = new ArrayList<>();
        this.OR_Joins = new ArrayList<>();
        this.AND_Joins = new ArrayList<>();
        Start_Event_Types = new ArrayList<>();
        Decide_Activation_Types = new ArrayList<>();
        All_Resources = new ArrayList<>();
        All_Workforces = new ArrayList<>();
        Box = new VBox();
    }

    public List<Start_Event_Type> getStart_Event_Types() {
        return Start_Event_Types;
    }

    public List<Node> getAll_Elems() {
        return All_Elems;
    }

    public List<Resource> getAll_Resources() {
        return All_Resources;
    }

    public List<Workforce> getAll_Workforces() {
        return All_Workforces;
    }

    public List<Decide_Activation_Type> getDecide_Activation_Types() {
        return Decide_Activation_Types;
    }

    public List<UI_Activate_Function> getAll_Activating_Functions() {
        return Activate_Functions;
    }

    public List<UI_Event_Activating_Starter> getAll_Activating_Start_Events() {
        return Activating_Start_Events;
    }

    public List<UI_Con_Type> get_UI_Con_Type() {
        List<UI_Con_Type> List = new ArrayList<>();
        List.add(UI_Con_Type.EAGER);
        List.add(UI_Con_Type.LAZY);
        return List;
    }

    public List<Split_Decide_Type> getAll_Decide_Types() {
        List<Split_Decide_Type> List = new ArrayList<>();
        List.add(Split_Decide_Type.SINGLE_RANDOM);
        List.add(Split_Decide_Type.FULL_RANDOM);
        List.add(Split_Decide_Type.NORMAL);
        List.add(Split_Decide_Type.EXPONENTIAL);
        List.add(Split_Decide_Type.FULL);
        return List;
    }
}
