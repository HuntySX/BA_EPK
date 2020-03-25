package com.company.Simulation.Simulation_Base.Data.Discrete_Data.Bib;

import com.company.EPK.Function;
import com.company.Enums.Option_Event_Choosing;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.*;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Settings;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.util.List;

import static com.company.Enums.Option_Event_Choosing.*;

public class Event_Decider {
    private Settings settings;
    private Event_Calendar calendar;
    private List<User> Users;
    private List<Resource> Resources;

    public Event_Decider(Settings settings, List<User> Users, List<Resource> Resources, Event_Calendar calendar) {
        this.settings = settings;
        this.Users = Users;
        this.Resources = Resources;
        this.calendar = calendar;
    }

    public Instance_Workflow Decide_Event(List<Instance_Workflow> Upcoming, Simulation_Waiting_List Waiting) {

        Option_Event_Choosing Decide_Option = settings.getDecide_Event();
        if (Upcoming.isEmpty() && Waiting.getEvent_List().isEmpty()) {
            return null;
        }
        if (Decide_Option == FIFO || Decide_Option == DEFAULT) {
            //DECIDE EVENT FIFO, WAIT FOR RESOURCES AND USERS TO BE FREE FOR THE FIRST WAITING EVENT.
            //IF WAITINGLIST.isEMPTY() Decide for first Elem of Upcoming.

            if (Waiting.getEvent_List().isEmpty()) {
                if (Check_Condition_For_Event.Check_For_Condition(Users, Resources, Upcoming.get(0), calendar, settings)) {
                    Instance_Workflow result = Upcoming.get(0);
                    Upcoming.remove(0);
                    return result;
                } else {
                    return null;
                }
            } else {
                if (Check_Condition_For_Event.Check_For_Condition(Users, Resources, Waiting.getEvent_List().get(0), calendar, settings)) {
                    Instance_Workflow result = Waiting.getEvent_List().get(0);
                    Waiting.getEvent_List().remove(0);
                    return result;
                } else {
                    return null;
                }
            }

        } else if (Decide_Option == GREEDY) {

        } else if (Decide_Option == BY_CUSTOMER_RELATION) {

        } else if (Decide_Option == BY_LARGEST_INVEST) {

        } else {

        }
        return null;
    }
}
