package com.company.Simulation.Simulation_Base.Data.Discrete_Data.Bib;

import com.company.EPK.Function;
import com.company.Enums.Option_Event_Choosing;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.*;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Settings;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.time.LocalTime;
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
        Instance_Workflow lightInstance = null;
        for (Instance_Workflow Upcoming_Workflow : Upcoming) {
            lightInstance = CheckForResourcefreeing(Upcoming_Workflow);
            if (lightInstance != null) {
                Upcoming.remove(lightInstance);
                return lightInstance;
            }
        }

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
            List<Instance_Workflow> Event_list = Waiting.getEvent_List();
            boolean found = false;
            Instance_Workflow mark_for_Del = null;
            for (Instance_Workflow w : Event_list) {
                if (Check_Condition_For_Event.Check_For_Condition(Users, Resources, w, calendar, settings)) {
                    mark_for_Del = w;
                    found = true;
                    break;
                }
            }
            if (found && mark_for_Del != null) {
                Event_list.remove(mark_for_Del);
                return mark_for_Del;
            } else {
                found = false;
                mark_for_Del = null;
                for (Instance_Workflow w : Upcoming) {
                    if (Check_Condition_For_Event.Check_For_Condition(Users, Resources, w, calendar, settings)) {
                        mark_for_Del = w;
                        found = true;
                        break;
                    }
                }

                if (found && mark_for_Del != null) {
                    Upcoming.remove(mark_for_Del);
                    return mark_for_Del;
                } else {
                    return null;
                }
            }


        } else if (Decide_Option == BY_CUSTOMER_RELATION) {

        } else if (Decide_Option == BY_LARGEST_INVEST) {

        } else {

        }
        return null;
    }

    private Instance_Workflow CheckForResourcefreeing(Instance_Workflow upcoming) {
        if (((upcoming.getEPKNode() instanceof Function) && upcoming.isWorking())) {
            return upcoming;
        } else {
            return null;
        }
    }

    public void updateWithExternalEvents() {
        LocalTime Runtime = calendar.getRuntime();
        int Day = calendar.getAct_runtimeDay();
        if (Day <= calendar.getRuntimeDays() - 1) {
            List<External_Event> External_Events = calendar.getExternal_Events().get(Day);
            {
                for (External_Event external_event : External_Events) {
                    if (external_event.getTime().get_Duration_to_Seconds() == Runtime.toSecondOfDay()) {
                        handleExternalEvent(external_event);
                    }
                }

            }
        }
    }

    private void handleExternalEvent(External_Event external_event) {
        if (external_event instanceof Resource_Activating_External_Event) {
            for (Resource res : Resources) {
                if (res.getID() == ((Resource_Activating_External_Event) external_event).getResource().getID()) {
                    res.setCount(res.getCount() - ((Resource_Activating_External_Event) external_event).getResource().getCount());
                    break;
                }
            }
        } else if (external_event instanceof Resource_Deactivating_External_Event) {
            for (Resource res : Resources) {
                if (res.getID() == ((Resource_Deactivating_External_Event) external_event).getResource().getID()) {
                    res.setCount(res.getCount() + ((Resource_Deactivating_External_Event) external_event).getResource().getCount());
                    break;
                }
            }

        } else if (external_event instanceof User_Activating_External_Event) {
            for (User user : Users) {
                if (user.getP_ID() == ((User_Activating_External_Event) external_event).getUser().getP_ID()) {
                    user.setDisabled(true);
                    break;
                }
            }
        } else if (external_event instanceof User_Deactivating_External_Event) {

            for (User user : Users) {
                if (user.getP_ID() == ((User_Deactivating_External_Event) external_event).getUser().getP_ID()) {
                    user.setDisabled(false);
                    break;
                }
            }
        }
    }
}
