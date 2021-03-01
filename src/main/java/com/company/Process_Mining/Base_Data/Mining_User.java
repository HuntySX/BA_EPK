package com.company.Process_Mining.Base_Data;

public class Mining_User {

    private String name;
    private String lastname;
    private int P_ID;
    private float efficiency;

    public Mining_User(String name, String lastname, int p_ID) {
        this.name = name;
        this.lastname = lastname;
        P_ID = p_ID;
        this.efficiency = 1;
    }

    public Mining_User(String name, String lastname, int p_ID, float efficiency) {
        this.name = name;
        this.lastname = lastname;
        P_ID = p_ID;
        this.efficiency = efficiency;
    }

    public float getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(float efficiency) {
        this.efficiency = efficiency;
    }

    public String getName() {
        return name + " " + lastname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getP_ID() {
        return P_ID;
    }

    public void setP_ID(int p_ID) {
        P_ID = p_ID;
    }

    @Override
    public String toString() {
        return "P_ID=" + P_ID +
                "Name: " + name + " " +
                lastname
                ;

    }
}
