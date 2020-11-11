package com.company.EPK;

import com.company.Enums.Function_Type;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;
import com.company.Simulation.Simulation_Base.Data.Threading_Data.Process_instance;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Function extends EPK_Node implements Printable_Node, Is_Tagged {

    private String Function_tag;
    private Function_Type function_type;
    private int successor = 1;
    private boolean concurrently = true;
    private List<Resource> Needed_Resources;
    private List<Workforce> Needed_Workforce;
    private Consumer<Process_instance> ConsumableMethod;
    private boolean isDeterministic;
    private Workingtime DeterministicWorkingTime;
    private Workingtime Min_Workingtime;
    private Workingtime Max_Workingtime;
    private Workingtime Mean_Workingtime;
    private Workingtime Deviation_Workintime;

    public Function(String function_tag, Function_Type type, int ID, Workingtime workingtime) {
        super(ID);
        if (function_tag == null) {
            String a = "Function ";
            String b = Integer.toString(ID);
            a = a.concat(b);
            this.Function_tag = a;
        } else {
            this.Function_tag = function_tag;
        }
        this.function_type = type;
        Needed_Workforce = new ArrayList<>();
        Needed_Resources = new ArrayList<>();
        isDeterministic = true;
        if (workingtime != null) {
            DeterministicWorkingTime = workingtime;
        } else {
            DeterministicWorkingTime = new Workingtime();
        }
    }

    public Function(String function_tag, Function_Type type, int ID, Workingtime workingtime,
                    Workingtime Min, Workingtime Max, Workingtime Mean, Workingtime Deviation) {
        super(ID);
        if (function_tag == null) {
            String a = "Function ";
            String b = Integer.toString(ID);
            a = a.concat(b);
            this.Function_tag = a;
        } else {
            this.Function_tag = function_tag;
        }
        this.function_type = type;
        Needed_Workforce = new ArrayList<>();
        Needed_Resources = new ArrayList<>();
        DeterministicWorkingTime = null;
        isDeterministic = false;

        if (Min != null) {
            Min_Workingtime = Min;
        } else {
            Min_Workingtime = new Workingtime();
        }
        if (Max != null) {
            Max_Workingtime = Max;
        } else {
            Max_Workingtime = new Workingtime();
        }
        if (Mean != null) {
            Mean_Workingtime = Mean;
        } else {
            Mean_Workingtime = new Workingtime();
        }
        if (Deviation != null) {
            Deviation_Workintime = Deviation;
        } else {
            Deviation_Workintime = new Workingtime();
        }

    }

    public Function() {
        super();

    }

    public Function_Type getFunction_type() {
        return function_type;
    }

    public Function(List<EPK_Node> Next_Elem, int ID, String Function_tag, boolean concurrently, List<Resource> Needed_Resources,
                    List<Workforce> Needed_Workforce, Workingtime Min_Workingtime, Workingtime Max_Workingtime,
                    Workingtime Mean_Workingtime, Workingtime Deviation_Workingtime) {
        super(Next_Elem, ID);
        if (Function_tag == null) {
            String a = "Function ";
            String b = Integer.toString(ID);
            a = a.concat(b);
            this.Function_tag = a;
        } else {
            this.Function_tag = Function_tag;
        }
        this.concurrently = concurrently;
        this.ConsumableMethod = null;
        if (Needed_Resources == null) {
            this.Needed_Resources = new ArrayList<>();
        } else {
            this.Needed_Resources = Needed_Resources;
        }
        if (Needed_Workforce == null) {
            this.Needed_Workforce = new ArrayList<>();
        } else {
            this.Needed_Workforce = Needed_Workforce;
        }

        isDeterministic = false;
        this.DeterministicWorkingTime = new Workingtime();

        this.Min_Workingtime = Min_Workingtime;
        this.Max_Workingtime = Max_Workingtime;
        this.Mean_Workingtime = Mean_Workingtime;
        this.Deviation_Workintime = Deviation_Workingtime;

    }

    public Function(List<EPK_Node> Next_Elem, int ID, String Function_tag, boolean concurrently, List<Resource> Needed_Resources,
                    List<Workforce> Needed_Workforce, int Workinghours, int Workingminutes, int Workingseconds) {
        super(Next_Elem, ID);
        if (Function_tag == null) {
            String a = "Function ";
            String b = Integer.toString(ID);
            a = a.concat(b);
            this.Function_tag = a;
        } else {
            this.Function_tag = Function_tag;
        }
        this.concurrently = concurrently;
        this.ConsumableMethod = null;
        if (Needed_Resources == null) {
            this.Needed_Resources = new ArrayList<>();
        } else {
            this.Needed_Resources = Needed_Resources;
        }
        if (Needed_Workforce == null) {
            this.Needed_Workforce = new ArrayList<>();
        } else {
            this.Needed_Workforce = Needed_Workforce;
        }

        isDeterministic = true;
        this.DeterministicWorkingTime = new Workingtime(Workinghours, Workingminutes, Workingseconds);
        Min_Workingtime = new Workingtime();
        Max_Workingtime = new Workingtime();
        Mean_Workingtime = new Workingtime();
        Deviation_Workintime = new Workingtime();
    }

    public String getFunction_tag() {
        return Function_tag;
    }

    public void setFunction_tag(String function_tag) {
        Function_tag = function_tag;
    }

    public boolean isConcurrently() {
        return concurrently;
    }

    public void setConcurrently(boolean concurrently) {
        this.concurrently = concurrently;
    }

    public Consumer<Process_instance> getConsumableMethod() {
        return ConsumableMethod;
    }

    public List<Resource> getNeeded_Resources() {
        return Needed_Resources;
    }

    public void Add_Needed_Resource(Resource res) {
        if (!Needed_Resources.contains(res)) {
            Needed_Resources.add(res);
        }
        if (res.getUsed_In().contains(this)) {
            res.add_Used_In(this);
        }
    }

    public List<Workforce> getNeeded_Workforce() {
        return Needed_Workforce;
    }

    public void add_Needed_Workforce(Workforce work) {
        Needed_Workforce.add(work);
    }

    public void setConsumableMethod(Consumer<Process_instance> consumableMethod) {
        ConsumableMethod = consumableMethod;
    }


    public Workingtime getWorkingTime() {
        if (isDeterministic) {
            return DeterministicWorkingTime;
        } else {
            NormalDistribution Distribution = new NormalDistribution(Mean_Workingtime.get_Duration_to_Seconds(), Deviation_Workintime.get_Duration_to_Seconds());
            int ResultingNonDetSeconds = (int) Distribution.sample();
            Workingtime ResultingNonDetWorkingtime = new Workingtime(ResultingNonDetSeconds);

            if (Min_Workingtime.isBefore(ResultingNonDetWorkingtime)) {
                ResultingNonDetWorkingtime = Min_Workingtime;
            }
            if (Max_Workingtime.isAfter(ResultingNonDetWorkingtime)) {
                ResultingNonDetWorkingtime = Max_Workingtime;
            }

            return ResultingNonDetWorkingtime;
        }
    }

    public void setDeterministicWorkingTime(Workingtime deterministicWorkingTime) {
        DeterministicWorkingTime = deterministicWorkingTime;
    }

    public void setWorkingHours(int Hours) {
        DeterministicWorkingTime.setHours(Hours);
    }

    public void setWorkingMinutes(int Minutes) {
        DeterministicWorkingTime.setMinutes(Minutes);
    }

    public void setWorkingSeconds(int Seconds) {
        DeterministicWorkingTime.setSeconds(Seconds);
    }


    @Override
    public String toString() {
        return "Function{" +
                "Function_tag='" + Function_tag + '\'' +
                ", concurrently=" + concurrently +
                ", ConsumableMethod=" + ConsumableMethod +
                ", Next_Elem=" + getNext_Elem() +
                ", ID=" + getID() +
                '}';
    }

    @Override
    public boolean CheckSettings() {
        boolean Check = true;
        if (Needed_Resources == null || Needed_Resources.isEmpty()) {
            Check = false;
        }
        if (Needed_Workforce == null || Needed_Workforce.isEmpty()) {
            Check = false;
        }
        if (DeterministicWorkingTime == null) {
            Check = false;
        }
        if (Function_tag == null || Function_tag.equals("")) {
            Check = false;
        }
        return Check;
    }

    public void removeResourcebyID(Resource resource) {
        List<Resource> to_Delete = new ArrayList<>();
        for (Resource r : Needed_Resources) {
            if (r.getID() == resource.getID()) {
                to_Delete.add(r);
            }
        }
        if (!to_Delete.isEmpty()) {
            Needed_Resources.removeAll(to_Delete);
        }
    }

    public void removeWorkforceByID(Workforce workforce) {
        List<Workforce> to_Delete = new ArrayList<>();
        for (Workforce w : Needed_Workforce) {
            if (w.getW_ID() == workforce.getW_ID()) {
                to_Delete.add(w);
            }
        }
        if (!to_Delete.isEmpty()) {
            Needed_Workforce.removeAll(to_Delete);
        }
    }

    public Function returnUpperClass() {
        return this;
    }

    public void setFunction_type(Function_Type function_type) {
        this.function_type = function_type;
    }

    public String getTag() {
        return Function_tag;
    }

    public int getSuccessor() {
        return successor;
    }

    public void setSuccessor(int successor) {
        this.successor = successor;
    }

    public void setNeeded_Resources(List<Resource> needed_Resources) {
        Needed_Resources = needed_Resources;
    }

    public void setNeeded_Workforce(List<Workforce> needed_Workforce) {
        Needed_Workforce = needed_Workforce;
    }

    public boolean isDeterministic() {
        return isDeterministic;
    }

    public void setDeterministic(boolean deterministic) {
        isDeterministic = deterministic;
    }


    public Workingtime getMin_Workingtime() {
        return Min_Workingtime;
    }

    public Workingtime getDeterministicWorkingTime() {
        return DeterministicWorkingTime;
    }

    public Workingtime getMax_Workingtime() {
        return Max_Workingtime;
    }

    public Workingtime getMean_Workingtime() {
        return Mean_Workingtime;
    }

    public Workingtime getDeviation_Workintime() {
        return Deviation_Workintime;
    }

    public void setMin_Workingtime(Workingtime min_Workingtime) {
        Min_Workingtime = min_Workingtime;
    }

    public void setMax_Workingtime(Workingtime max_Workingtime) {
        Max_Workingtime = max_Workingtime;
    }

    public void setMean_Workingtime(Workingtime mean_Workingtime) {
        Mean_Workingtime = mean_Workingtime;
    }

    public void setDeviation_Workintime(Workingtime deviation_Workintime) {
        Deviation_Workintime = deviation_Workintime;
    }
}
