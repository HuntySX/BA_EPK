package com.company.EPK;

import com.company.Enums.Decide_Activation_Type;
import com.company.Enums.Function_Type;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Activating_Event_Instance;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Event_Calendar;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Instance_Workflow;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;
import jdk.vm.ci.meta.Local;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.company.Enums.Decide_Activation_Type.*;

public class Activating_Function extends Function {

    private Activating_Start_Event Start_Event;
    private Workingtime Instantiate_Time;
    private int waiting_Ticket;
    private List<Instance_Workflow> Waiting_For_Activation_Instances;
    private Event_Calendar calendar;
    private Decide_Activation_Type DecisionType;

    public Activating_Function(String function_tag, Workingtime instantiate_Time, Function_Type type, int ID, Activating_Start_Event start_Event, Event_Calendar calendar, Decide_Activation_Type decision) {
        super(function_tag, type, ID);
        Start_Event = start_Event;
        Instantiate_Time = instantiate_Time;
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
}
