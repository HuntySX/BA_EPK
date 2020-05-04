package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.EPK.Function;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.util.ArrayList;
import java.util.List;

public class Resource {
    private int r_ID;
    private String name;
    private int count;
    private List<User> Allowed_Users;
    private List<Function> Used_In;


    public Resource(String name, int count, int r_ID) {
        this.r_ID = r_ID;
        this.name = name;
        this.count = count;
        this.Used_In = new ArrayList<>();
    }

    public Resource(String name, int count, List<User> allowed_Users, int r_ID) {
        this.r_ID = r_ID;
        this.name = name;
        this.count = count;
        Allowed_Users = allowed_Users;
        this.Used_In = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<User> getAllowed_Users() {
        return Allowed_Users;
    }

    public void add_Used_In(Function Func) {
        if (!Used_In.contains(Func)) {
            Used_In.add(Func);
        }
        if (!Func.getNeeded_Resources().contains(this)) {
            Func.Add_Needed_Resource(this);
        }
    }

    public void setAllowed_Users(List<User> allowed_Users) {
        Allowed_Users = allowed_Users;
    }

    public List<Function> getUsed_In() {
        return Used_In;
    }

    public int getID() {
        return r_ID;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "r_ID=" + r_ID +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
