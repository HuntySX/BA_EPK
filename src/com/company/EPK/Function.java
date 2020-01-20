package com.company.EPK;

import com.company.Simulation.User;

import java.util.List;
import java.util.function.Consumer;

//TODO USERLIST EVENTUELL WOANDERS HIN?

public class Function extends Node {
    private String Function_tag;
    private int successor = 1;
    private List<User> Allowed_Users;
    private boolean concurrently = true;
    private Consumer<Void> ConsumableMethod;

    public Function(String function_tag) {
        Function_tag = function_tag;
    }

    public Function() {
        super();

    }

    public Function(List<Node> Next_Elem, int ID, String Function_tag, List<User> Allowed_Users, boolean concurrently) {
        super(Next_Elem, ID);
        this.Function_tag = Function_tag;
        this.Allowed_Users = Allowed_Users;
        this.concurrently = concurrently;
        this.ConsumableMethod = null;
    }

    public String getFunction_tag() {
        return Function_tag;
    }

    public void setFunction_tag(String function_tag) {
        Function_tag = function_tag;
    }

    public List<User> getAllowed_Users() {
        return Allowed_Users;
    }

    public void setAllowed_Users(List<User> allowed_Users) {
        Allowed_Users = allowed_Users;
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
                ", Allowed_Users=" + Allowed_Users +
                ", concurrently=" + concurrently +
                ", ConsumableMethod=" + ConsumableMethod +
                ", Next_Elem=" + getNext_Elem() +
                ", ID=" + getID() +
                '}';
    }
}
