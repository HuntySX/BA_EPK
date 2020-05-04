package com.company.EPK;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.util.ArrayList;
import java.util.List;

public class Workforce {
    private int W_ID;
    private String permission;
    private List<User> granted_to;
    private List<Function> used_In;

    public Workforce(int w_ID, String permission) {
        W_ID = w_ID;
        this.permission = permission;
        granted_to = new ArrayList<>();
        used_In = new ArrayList<>();
    }

    public int getW_ID() {
        return W_ID;
    }

    public void setW_ID(int w_ID) {
        W_ID = w_ID;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<User> getGranted_to() {
        return granted_to;
    }

    public void AddUser(User user) {
        if (!granted_to.contains(user)) {
            granted_to.add(user);
        }
    }

    public void RemoveUser(User user) {
        granted_to.remove(user);
    }

    public List<Function> getUsed_in() {
        return used_In;
    }

    @Override
    public String toString() {
        return "[ " +
                "W_ID=" + W_ID +
                ", permission='" + permission + '\'' +
                " ]";
    }
}
