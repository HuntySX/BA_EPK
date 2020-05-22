package com.company.Print.EventDriven;

public class Connected_Resource_Print {
    private int ID;
    private int count;
    private String Tag;

    public Connected_Resource_Print(int ID, int count, String tag) {
        this.ID = ID;
        this.count = count;
        Tag = tag;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }
}
