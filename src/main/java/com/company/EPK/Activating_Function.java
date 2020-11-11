package com.company.EPK;

import com.company.Enums.Decide_Activation_Type;
import com.company.Enums.Function_Type;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Event_Calendar;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Instance_Workflow;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.company.Enums.Decide_Activation_Type.CUSTOM;
import static com.company.Enums.Decide_Activation_Type.RANDOM;

public class Activating_Function extends Function implements Printable_Node, Is_Tagged {

    private Activating_Start_Event Start_Event;
    private Workingtime Instantiate_Time;
    private Workingtime Min_Instantiate_Time;
    private Workingtime Max_Instantiate_Time;
    private Workingtime Mean_Instantiate_Time;
    private Workingtime Deviation_Instantiate_Time;
    private boolean is_Deterministic_Ordertime;
    private int chance_for_instantiation;
    private int waiting_Ticket;
    private List<Instance_Workflow> Waiting_For_Activation_Instances;
    private Event_Calendar calendar;
    private Decide_Activation_Type DecisionType;

    public Activating_Function(String function_tag, Workingtime instantiate_Time, int chance_for_instantiation, Workingtime working_time, Function_Type type, int ID, Activating_Start_Event start_Event, Event_Calendar calendar, Decide_Activation_Type decision) {
        super(function_tag, type, ID, working_time);
        Start_Event = start_Event;
        if (instantiate_Time == null) {
            Instantiate_Time = new Workingtime();
        } else {
            Instantiate_Time = instantiate_Time;
        }
        this.chance_for_instantiation = chance_for_instantiation;
        this.Waiting_For_Activation_Instances = new ArrayList<>();
        this.calendar = calendar;
        this.DecisionType = decision;
        is_Deterministic_Ordertime = true;
        waiting_Ticket = 0;
    }

    public Activating_Function(String function_tag, Workingtime instantiate_Time, boolean concurrently, List<Resource> Needed_Resources,
                               int chance_for_instantiation, Workingtime Min_Workingtime,
                               Workingtime Max_Workingtime, Workingtime Mean_Workingtime, Workingtime Deviation_Workingtime,
                               Workingtime Min_Instantiate_time, Workingtime Max_Instantiate_time,
                               Workingtime Mean_Instantiate_time, Workingtime Deviation_Instantiate_time,
                               Function_Type type, int ID, Activating_Start_Event start_Event, Event_Calendar calendar,
                               Decide_Activation_Type decision
    ) {

        super(null, ID, function_tag, concurrently, Needed_Resources,
                null, Min_Workingtime, Max_Workingtime, Mean_Workingtime, Deviation_Workingtime);
        Start_Event = start_Event;
        this.Min_Instantiate_Time = Min_Instantiate_time;
        this.Max_Instantiate_Time = Max_Instantiate_time;
        this.Mean_Instantiate_Time = Mean_Instantiate_time;
        this.Deviation_Instantiate_Time = Deviation_Instantiate_time;
        this.chance_for_instantiation = chance_for_instantiation;
        this.Waiting_For_Activation_Instances = new ArrayList<>();
        this.calendar = calendar;
        this.DecisionType = decision;
        is_Deterministic_Ordertime = true;

        waiting_Ticket = 0;
    }

    public void Instantiate_Activation(Instance_Workflow instance) {
        Workingtime to_Instantiate = null;
        if (is_Deterministic_Ordertime()) {
            to_Instantiate = Instantiate_Time;
        } else {
            NormalDistribution Distribution = new NormalDistribution(Mean_Instantiate_Time.get_Duration_to_Seconds(), Deviation_Instantiate_Time.get_Duration_to_Seconds());
            int Seconds_to_Instantiate = (int) Distribution.sample();
            to_Instantiate = new Workingtime(Seconds_to_Instantiate);
            if (Min_Instantiate_Time.isBefore(to_Instantiate)) {
                to_Instantiate = Min_Instantiate_Time;
            } else if (Max_Instantiate_Time.isAfter(to_Instantiate)) {
                to_Instantiate = Max_Instantiate_Time;
            }
        }
        calendar.instantiate_new_Activation_Event(Start_Event, this, instance, to_Instantiate);
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
        } else if (DecisionType == CUSTOM) {
            System.out.println("Chance: " + chance_for_instantiation);
            Random rand = new Random();
            int randomcheck = rand.nextInt(100);
            randomcheck++;
            return randomcheck <= chance_for_instantiation;
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

    public Workingtime getMin_Instantiate_Time() {
        return Min_Instantiate_Time;
    }

    public void setMin_Instantiate_Time(Workingtime min_Instantiate_Time) {
        Min_Instantiate_Time = min_Instantiate_Time;
    }

    public Workingtime getMax_Instantiate_Time() {
        return Max_Instantiate_Time;
    }

    public void setMax_Instantiate_Time(Workingtime max_Instantiate_Time) {
        Max_Instantiate_Time = max_Instantiate_Time;
    }

    public Workingtime getInstantiate_Time() {
        return Instantiate_Time;
    }

    public boolean is_Deterministic_Ordertime() {
        return is_Deterministic_Ordertime;
    }

    public void set_Deterministic_Ordertime(boolean is_Deterministic_Ordertime) {
        this.is_Deterministic_Ordertime = is_Deterministic_Ordertime;
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

    public int getChance_for_instantiation() {
        return chance_for_instantiation;
    }

    public void setChance_for_instantiation(int chance_for_instantiation) {
        this.chance_for_instantiation = chance_for_instantiation;
    }

    public Workingtime getMean_Instantiate_Time() {
        return Mean_Instantiate_Time;
    }

    public void setMean_Instantiate_Time(Workingtime mean_Instantiate_Time) {
        Mean_Instantiate_Time = mean_Instantiate_Time;
    }

    public Workingtime getDeviation_Instantiate_Time() {
        return Deviation_Instantiate_Time;
    }

    public void setDeviation_Instantiate_Time(Workingtime deviation_Instantiate_Time) {
        Deviation_Instantiate_Time = deviation_Instantiate_Time;
    }
}
