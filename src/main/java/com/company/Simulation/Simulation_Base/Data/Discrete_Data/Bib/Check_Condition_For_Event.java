package com.company.Simulation.Simulation_Base.Data.Discrete_Data.Bib;

import com.company.EPK.*;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Event_Calendar;
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

    //TODO Check for Condition prüft aktuell immer Activating Functions auf möglichkeit durhczuführen.
    // Es Exisitieren jedoch fälle wo dies nicht nötig wäre
    public static boolean Check_For_Condition(List<User> Users, List<Resource> Resources, Instance_Workflow Instance, Event_Calendar calendar, Settings settings) {
        boolean rescheck = true;
        if (Instance.getEPKNode() instanceof Event || Instance.getEPKNode() instanceof Event_Con_Join || Instance.getEPKNode() instanceof Event_Con_Split
                || (Instance.getEPKNode() instanceof Function && Instance.isWorking() && !(Instance.getEPKNode() instanceof Activating_Function))) {
            return true;
        } else {
            for (Resource needing : ((Function) Instance.getEPKNode()).getNeeded_Resources()) {
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
                for (Workforce workforce : ((Function) Instance.getEPKNode()).getNeeded_Workforce()) {
                    boolean workforcefound = false;
                    for (User user : Users) {
                        if (!user.isActive()) {
                            for (Workforce work : user.getWorkforces()) {
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

                int lasting_Shifttime_in_Seconds = calendar.getEnd_Time().toSecondOfDay() - calendar.getRuntime().toSecondOfDay();
                int Workingtime_in_Seconds = ((Function) Instance.getEPKNode()).getWorkingTime().get_Duration_to_Seconds();
                if (Workingtime_in_Seconds <= lasting_Shifttime_in_Seconds) {
                    return true;
                } else {
                    Workingtime_in_Seconds = Workingtime_in_Seconds - lasting_Shifttime_in_Seconds;
                    int Shifttime_in_Seconds = calendar.getEnd_Time().toSecondOfDay() - calendar.getBegin_Time().toSecondOfDay();
                    int advanceday = 1;
                    while (Workingtime_in_Seconds > Shifttime_in_Seconds) {
                        Workingtime_in_Seconds = Workingtime_in_Seconds - Shifttime_in_Seconds;
                        advanceday++;
                    }
                    if (advanceday >= calendar.getRuntimeDays() - calendar.getAct_runtimeDay()) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}

