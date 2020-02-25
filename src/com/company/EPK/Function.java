package com.company.EPK;

import com.company.Enums.Function_Type;
import com.company.Simulation.Simulation_Base.Threading_Instance.Process_instance;

import java.util.List;
import java.util.function.Consumer;

public class Function extends Node {
    private String Function_tag;
    private Function_Type function_type;
    private int successor = 1;
    private boolean concurrently = true;
    private Consumer<Process_instance> ConsumableMethod;

    public Function(String function_tag, Function_Type type, int ID) {

        super(ID);
        this.function_type = type;
        Function_tag = function_tag;
    }

    public Function() {
        super();

    }

    public Function_Type getFunction_type() {
        return function_type;
    }

    public Function(List<Node> Next_Elem, int ID, String Function_tag, boolean concurrently) {
        super(Next_Elem, ID);
        this.Function_tag = Function_tag;
        this.concurrently = concurrently;
        this.ConsumableMethod = null;
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

    public void setConsumableMethod(Consumer<Process_instance> consumableMethod) {
        ConsumableMethod = consumableMethod;
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
