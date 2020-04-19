package com.company.EPK;

import com.company.Enums.Function_Type;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;
import com.company.Simulation.Simulation_Base.Data.Threading_Data.Process_instance;

import java.util.List;
import java.util.function.Consumer;

public class Function extends EPK_Node {
    private String Function_tag;
    private Function_Type function_type;
    private int successor = 1;
    private boolean concurrently = true;
    private List<Resource> Needed_Resources;
    private List<Workforce> Needed_Workforce;
    private Consumer<Process_instance> ConsumableMethod;
    private Workingtime WorkingTime;

    public Function(String function_tag, Function_Type type, int ID) {
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
    }

    public Function() {
        super();

    }

    public Function_Type getFunction_type() {
        return function_type;
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
        this.Needed_Resources = Needed_Resources;
        this.Needed_Workforce = Needed_Workforce;
        this.WorkingTime = new Workingtime(Workinghours, Workingminutes, Workingseconds);
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
        return WorkingTime;
    }

    public void setWorkingTime(Workingtime workingTime) {
        WorkingTime = workingTime;
    }

    public void setWorkingHours(int Hours) {
        WorkingTime.setHours(Hours);
    }

    public void setWorkingMinutes(int Minutes) {
        WorkingTime.setMinutes(Minutes);
    }

    public void setWorkingSeconds(int Seconds) {
        WorkingTime.setSeconds(Seconds);
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
}
