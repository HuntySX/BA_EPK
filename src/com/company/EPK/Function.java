package com.company.EPK;

import java.util.List;
import java.util.function.Consumer;

public class Function extends Node {
    private String Function_tag;
    private int successor = 1;
    private boolean concurrently = true;
    private Consumer<Void> ConsumableMethod;

    public Function(String function_tag) {
        Function_tag = function_tag;
    }

    public Function() {
        super();

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

    public Consumer<Void> getConsumableMethod() {
        return ConsumableMethod;
    }

    public void setConsumableMethod(Consumer<Void> consumableMethod) {
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
