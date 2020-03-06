package com.company.EPK;

public class Workforce {
    private int W_ID;
    private String permission;

    public Workforce(int w_ID, String permission) {
        W_ID = w_ID;
        this.permission = permission;
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

}
