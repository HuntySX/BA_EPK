package com.company.Simulation.Simulation_Base.Data.Shared_Data;

import com.company.EPK.Function;
import com.company.EPK.Workforce;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String first_Name;
    private String last_Name;
    private int p_ID;
    private List<Function> allowed_Processes;
    private boolean active;
    private float efficiency;
    private List<Workforce> Workforces;

    public User(String first_Name, String last_Name, int p_ID, float efficiency) {
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.p_ID = p_ID;
        this.active = false;
        Workforces = new ArrayList<>();
        this.efficiency = efficiency;
    }

    public User(String first_Name, String last_Name, int p_ID, List<Function> allowed_Processes) {
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.allowed_Processes = allowed_Processes;
        this.p_ID = p_ID;
        this.active = false;
    }

    //TODO Set Get Workforce ( Add, Construct bla)

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public void setLast_Name(String last_Name) {
        this.last_Name = last_Name;
    }

    public int getP_ID() {
        return p_ID;
    }

    public void setP_ID(int p_ID) {
        this.p_ID = p_ID;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Function> getAllowed_Processes() {
        return allowed_Processes;
    }

    public void setAllowed_Processes(List<Function> allowed_Processes) {
        this.allowed_Processes = allowed_Processes;
    }

    public float getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(float efficiency) {
        this.efficiency = efficiency;
    }

    public List<Workforce> getWorkforces() {
        return Workforces;
    }

    @Override
    public String toString() {
        return first_Name + '\'' +
                ", last_Name='" + last_Name + '\'' +
                ", [p_ID=" + p_ID +
                "]";
    }

    public void removeWorkforceByID(Workforce workforce) {
        List<Workforce> to_Delete = new ArrayList<>();
        for (Workforce w : Workforces) {
            if (w.getPermission().equals(workforce.getPermission())) {
                to_Delete.add(w);
            }
        }
        if (!to_Delete.isEmpty()) {
            Workforces.removeAll(to_Delete);
        }
    }
}
