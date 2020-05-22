package com.company.Print.EventDriven;

public class Connected_Workforce_Print {
    private int ID;
    private String Tag;

    public Connected_Workforce_Print(int ID, String tag) {
        this.ID = ID;
        Tag = tag;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }
}
