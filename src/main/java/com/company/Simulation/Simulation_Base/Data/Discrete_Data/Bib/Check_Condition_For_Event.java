package com.company.Simulation.Simulation_Base.Data.Discrete_Data.Bib;

import com.company.EPK.*;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Event_Instance;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Instance_Workflow;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Settings;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.util.List;

public class Check_Condition_For_Event {
/*
    private Settings settings;
    private List<User> Users;
    private List<Resource> Resources;

    //TODO Very Late: Diese Klasse evtl Static machen und Settings übergeben (Wenn überhaupt nötig?)

    public Check_Condition_For_Event(Settings settings, List<User> users, List<Resource> resources) {
        this.settings = settings;
        Users = users;
        Resources = resources;
    }
    */

    public static boolean Check_For_Condition(List<User> Users, List<Resource> Resources, Instance_Workflow Instance) {
        boolean rescheck = true;
        if (Instance.getNode() instanceof Event || Instance.getNode() instanceof Con_Join || Instance.getNode() instanceof Con_Split
                || (Instance.getNode() instanceof Function && Instance.isWorking())) {
            return true;
        } else {
            for (Resource needing : ((Function) Instance.getNode()).getNeeded_Resources()) {
                if (!rescheck) {
                    return false;
                }
                for (Resource having : Resources) {
                    if (needing.getID() == having.getID() && having.getCount() < needing.getCount()) {
                        rescheck = false;
                        break;
                    } else if (needing.getID() == having.getID() && having.getCount() >= needing.getCount()) {
                        break;
                    }
                }
            }
            if (!rescheck) {
                return false;
            } else {
                for (Workforce workforce : ((Function) Instance.getNode()).getNeeded_Workforce()) {
                    boolean workforcefound = false;
                    for (User user : Users) {
                        if (!user.isActive()) {
                            for (Workforce work : user.getWorkforce()) {
                                if (work.getW_ID() == workforce.getW_ID()) {
                                    workforcefound = true;
                                    break;
                                }
                            }
                            if (workforcefound) {
                                break;
                            }
                        }
                    }
                    if (!workforcefound) {
                        return false;
                    }
                }
                return true;
            }
        }
    }
    }

