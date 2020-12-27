package com.company.Process_Mining.Base_Data;

public class Mining_Resource {
    private String name;
    private int R_ID;

    public Mining_Resource(String name, int r_ID) {
        this.name = name;
        R_ID = r_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getR_ID() {
        return R_ID;
    }

    public void setR_ID(int r_ID) {
        R_ID = r_ID;
    }

    @Override
    public String toString() {
        return "R_ID=" + R_ID +
                "Name: " + name;

    }
}
