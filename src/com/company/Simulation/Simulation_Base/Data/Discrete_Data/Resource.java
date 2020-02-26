package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.util.List;

public class Resource {
    private String name;
    private int count;
    private List<User> Allowed_Users;

    public Resource(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public Resource(String name, int count, List<User> allowed_Users) {
        this.name = name;
        this.count = count;
        Allowed_Users = allowed_Users;
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

    public void setAllowed_Users(List<User> allowed_Users) {
        Allowed_Users = allowed_Users;
    }
}
