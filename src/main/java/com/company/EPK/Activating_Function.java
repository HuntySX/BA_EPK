package com.company.EPK;

import com.company.Enums.Decide_Activation_Type;
import com.company.Enums.Function_Type;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Event_Calendar;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Instance_Workflow;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.company.Enums.Decide_Activation_Type.*;

public class Activating_Function extends Function implements Printable_Node, Is_Tagged {

    private Activating_Start_Event Start_Event;
    private Workingtime Instantiate_Time;
    private int waiting_Ticket;
    private List<Instance_Workflow> Waiting_For_Activation_Instances;
    private Event_Calendar calendar;
    private Decide_Activation_Type DecisionType;

    public Activating_Function(String function_tag, Workingtime instantiate_Time, Workingtime working_time, Function_Type type, int ID, Activating_Start_Event start_Event, Event_Calendar calendar, Decide_Activation_Type decision) {
        super(function_tag, type, ID, working_time);
        Start_Event = start_Event;
        if (instantiate_Time == null) {
            Instantiate_Time = new Workingtime();
        } else {
            Instantiate_Time = instantiate_Time;
        }
        this.Waiting_For_Activation_Instances = new ArrayList<>();
        this.calendar = calendar;
        this.DecisionType = decision;

        waiting_Ticket = 0;
    }

    public void Instantiate_Activation(Instance_Workflow instance) {

        calendar.instantiate_new_Activation_Event(Start_Event, this, instance.getInstance().getCase_ID(),
                instance.getWaiting_Ticket(), Instantiate_Time);
        add_to_Waiting_For_Activation(instance);
    }

    /*@Deprecated
    public void Activate_Waiting_Instance(Instance_Workflow instance){
        if(instance.getInstance() instanceof Activating_Event_Instance){
            if(((Activating_Event_Instance) instance.getInstance()).getEnd_Function().getID() == this.getID()){
                for (Instance_Workflow waiting: Waiting_For_Activation_Instances) {
                    if(waiting.getInstance().getCase_ID() == ((Activating_Event_Instance) instance.getInstance()).getFor_case_ID())
                    {
                        LocalTime instantiateTime = calendar.getRuntime();
                        instantiateTime.plusSeconds(this.getWorkingTime().getSecond());
                        instantiateTime.plusMinutes(this.getWorkingTime().getMinute());
                        instantiateTime.plusHours(this.getWorkingTime().getHour());
                        Node n = this.getNext_Elem().get(0);
                        if(instantiateTime.isBefore(calendar.getEnd_Time())){

                        }
                        Instance_Workflow new_Instance = new Instance_Workflow(waiting.getInstance(), instantiateTime, n);
                        calendar.Add_To_Upcoming_List(new_Instance, calendar.getRuntimeDays());

                    }
                }
            }
        }
    }*/

    public Activating_Start_Event getStart_Event() {
        return Start_Event;
    }

    public void setStart_Event(Activating_Start_Event start_Event) {
        Start_Event = start_Event;
    }

    public List<Instance_Workflow> getWaiting_For_Activation_Instances() {
        return Waiting_For_Activation_Instances;
    }

    public void setWaiting_For_Activation_Instances(List<Instance_Workflow> waiting_For_Activation_Instances) {
        Waiting_For_Activation_Instances = waiting_For_Activation_Instances;
    }

    public void add_to_Waiting_For_Activation(Instance_Workflow instance) {
        if (!Waiting_For_Activation_Instances.contains(instance)) {
            Waiting_For_Activation_Instances.add(instance);
        }
    }

    public Decide_Activation_Type getDecisionType() {
        return DecisionType;
    }

    public void setDecisionType(Decide_Activation_Type decisionType) {
        DecisionType = decisionType;
    }

    public int get_Unique_Waiting_Ticket() {
        int id = waiting_Ticket;
        waiting_Ticket++;
        return id;
    }

    public boolean Decide() {
        if (DecisionType == RANDOM) {
            Random rand = new Random();
            int randomcheck = rand.nextInt(2);
            if (randomcheck == 0) {
                return true;
            } else {
                return false;
            }
        } else if (DecisionType == NORMAL) {
            return true;
            //TODO NORMAL ACTIVATION DISTRIBUTION
        } else if (DecisionType == EXPONENTIAL) {
            return false;
            //TODO NORMAL ACTIVATION DISTRIBUTION
        }
        return false; //TODO Delete;
    }

    @Override
    public boolean CheckSettings() {
        boolean Check = true;
        if (Start_Event == null) {
            Check = false;
        }
        if (Instantiate_Time == null) {
            Check = false;
        }
        if (DecisionType == null) {
            Check = false;
        }
        return Check;
    }

    public void setInstantiateHours(int Hours) {
        Instantiate_Time.setHours(Hours);
    }

    public void setInstantiateMinutes(int Minutes) {
        Instantiate_Time.setMinutes(Minutes);
    }

    public void setInstantiateSeconds(int Seconds) {
        Instantiate_Time.setMinutes(Seconds);
    }

    public Workingtime getInstantiate_Time() {
        return Instantiate_Time;
    }

    public void setInstantiate_Time(Workingtime InstantiateTime) {
        this.Instantiate_Time = InstantiateTime;
    }

    public String getTag() {
        return getFunction_tag();
    }

    public Activating_Function returnUpperClass() {
        return this;
    }

    public Event_Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Event_Calendar calendar) {
        this.calendar = calendar;
    }
}
