package com.company.UI.EPKUI;

import com.company.EPK.EPK_Node;
import com.company.EPK.Workforce;
import com.company.Enums.Decide_Activation_Type;
import com.company.Enums.Option_Event_Choosing;
import com.company.Enums.Split_Decide_Type;
import com.company.Enums.Start_Event_Type;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.External_Event;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;
import com.company.UI.javafxgraph.fxgraph.cells.UI_View_Gen;
import com.company.UI.javafxgraph.fxgraph.graph.Graph;
import com.company.UI.javafxgraph.fxgraph.graph.Model;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.company.Enums.Option_Event_Choosing.*;
import static com.company.Enums.Start_Event_Type.*;

public class UI_EPK {
    private UI_View_Gen Active_Elem_For_Connection;
    private List<EPK_Node> All_Elems;
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
    private List<User> All_Users;
    private List<List<External_Event>> External_Events_by_Day;
    private Model model;
    private VBox Box;
    private Graph graph;
    private int UniqueUserID;
    private int UniqueResourceID;
    private int UniqueWorkforceID;
    private int UniqueExternalEventID;
    private UI_Settings UI_Settings;
    private UI_EXTERNAL_EVENT_MANAGER UI_EXTERNAL_EVENT_MANAGER;

    public UI_EPK(Model model, Graph graph) {
        this.model = model;
        Active_Elem_For_Connection = null;
        All_Elems = new ArrayList<>();
        Events = new ArrayList<>();
        Start_Events = new ArrayList<>();
        End_Events = new ArrayList<>();
        Activating_Start_Events = new ArrayList<>();
        Activate_Functions = new ArrayList<>();
        Functions = new ArrayList<>();
        External_Events_by_Day = new ArrayList<>();
        List<External_Event> Events_Day_0 = new ArrayList<>();
        External_Events_by_Day.add(Events_Day_0);
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
        this.graph = graph;
        this.All_Workforces = new ArrayList<>();
        this.All_Resources = new ArrayList<>();
        this.All_Users = new ArrayList<>();
        this.UniqueUserID = 1;
        this.UniqueResourceID = 1;
        this.UniqueWorkforceID = 1;
        this.UniqueExternalEventID = 1;
        this.UI_Settings = new UI_Settings();
    }

    public List<Start_Event_Type> getStart_Event_Types() {
        List<Start_Event_Type> Types = new ArrayList<>();
        Types.addAll(Arrays.asList(NORMAL, RANDOM, EXPONENTIAL));
        return Types;
    }

    public List<EPK_Node> getAll_Elems() {
        return All_Elems;
    }

    public List<Resource> getAll_Resources() {
        return All_Resources;
    }

    public List<User> getAll_Users() {
        return All_Users;
    }

    public void AddUser(User user) {
        All_Users.add(user);
    }

    public void RemoveUser(User user) {
        if (All_Users.contains(user)) {
            All_Users.remove(user);
        }
    }

    public void AddResource(Resource resource) {
        if (!All_Resources.contains(resource)) {
            All_Resources.add(resource);
        }
    }

    public void RemoveResource(Resource resource) {
        if (All_Resources.contains(resource)) {
            All_Resources.remove(resource);
        }
    }

    public void AddWorkforce(Workforce workforce) {
        if (!All_Workforces.contains(workforce)) {
            All_Workforces.add(workforce);
        }
    }

    public void RemoveWorkforce(Workforce workforce) {
        if (All_Workforces.contains(workforce)) {
            All_Workforces.remove(workforce);
        }
    }

    public List<Workforce> getAll_Workforces() {
        return All_Workforces;
    }

    public List<Decide_Activation_Type> getDecide_Activation_Types() {
        List<Decide_Activation_Type> List = new ArrayList<>();
        List.addAll(Arrays.asList(com.company.Enums.Decide_Activation_Type.CUSTOM,
                com.company.Enums.Decide_Activation_Type.RANDOM));
        return List;
    }

    public List<UI_Activate_Function> getAll_Activating_Functions() {
        return Activate_Functions;
    }

    public List<UI_Event_Activating_Starter> getAll_Activating_Start_Events() {
        return Activating_Start_Events;
    }

    public UI_View_Gen getActive_Elem() {
        return Active_Elem_For_Connection;
    }

    public void setActive_Elem_For_Connection(UI_View_Gen active_Elem_For_Connection) {
        Active_Elem_For_Connection = active_Elem_For_Connection;
    }

    public List<UI_Event> getEvents() {
        return Events;
    }

    public List<UI_Event_Starter> getStart_Events() {
        return Start_Events;
    }

    public List<UI_Event_Ending> getEnd_Events() {
        return End_Events;
    }

    public List<UI_Func> getFunctions() {
        return Functions;
    }

    public List<UI_XOR_Split> getXOR_Splits() {
        return XOR_Splits;
    }

    public List<UI_OR_Split> getOR_Splits() {
        return OR_Splits;
    }

    public List<UI_AND_Split> getAND_Splits() {
        return AND_Splits;
    }

    public List<UI_XOR_Join> getXOR_Joins() {
        return XOR_Joins;
    }

    public List<UI_OR_Join> getOR_Joins() {
        return OR_Joins;
    }

    public List<UI_AND_Join> getAND_Joins() {
        return AND_Joins;
    }

    public VBox getBox() {
        return Box;
    }

    public Model getModel() {
        return model;
    }

    public Graph getGraph() {
        return graph;
    }

    public List<List<External_Event>> getExternal_Events_by_Day() {
        return External_Events_by_Day;
    }

    public void setExternal_Events_by_Day(List<List<External_Event>> external_Events_by_Day) {
        External_Events_by_Day = external_Events_by_Day;
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
        List.add(Split_Decide_Type.CUSTOM);
        List.add(Split_Decide_Type.FULL);
        return List;
    }

    public int getUniqueUserID() {
        int id = UniqueUserID;
        UniqueUserID++;
        return id;
    }

    public int getUniqueResourceID() {
        int id = UniqueResourceID;
        UniqueResourceID++;
        return id;
    }

    public int getUniqueWorkforceID() {
        int id = UniqueWorkforceID;
        UniqueWorkforceID++;
        return id;
    }

    public int getUniqueExternalEventID() {
        int id = UniqueExternalEventID;
        UniqueExternalEventID++;
        return id;
    }

    public com.company.UI.EPKUI.UI_Settings getUI_Settings() {
        return UI_Settings;
    }

    public void setUI_Settings(com.company.UI.EPKUI.UI_Settings UI_Settings) {
        this.UI_Settings = UI_Settings;
    }

    public List<Option_Event_Choosing> getAll_Event_Choosings() {
        List<Option_Event_Choosing> List = new ArrayList<>();
        List.add(FIFO);
        List.add(GREEDY);
        List.add(BY_CUSTOMER_RELATION);
        List.add(BY_LARGEST_MARGIN);
        List.add(BY_LARGEST_INVEST);
        List.add(DEFAULT);
        return List;
    }

    public void activate() {
        Box.getChildren().clear();
        Box = (((UI_Instantiable) getActive_Elem().getEPKNode()).Get_UI());
    }

    public UI_EXTERNAL_EVENT_MANAGER getUI_External_Events() {
        return UI_EXTERNAL_EVENT_MANAGER;
    }

    public void deleteEE_List_for_days(Integer value) {
        while (value > 0) {
            if (External_Events_by_Day.isEmpty()) {
                break;
            }
            List<External_Event> pointer = null;
            for (List<External_Event> to_Point : External_Events_by_Day) {
                pointer = to_Point;
            }
            if (pointer != null) {
                External_Events_by_Day.remove(pointer);
            }
            value--;
        }
    }

    public void addNewDayForExternalEvent(List<External_Event> new_event_list) {
        External_Events_by_Day.add((new_event_list));
    }
}
