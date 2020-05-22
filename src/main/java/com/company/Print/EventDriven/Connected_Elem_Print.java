package com.company.Print.EventDriven;

public class Connected_Elem_Print {
    private int id;
    private String Tag;

    public Connected_Elem_Print(int id, String tag) {
        this.id = id;
        Tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }
}
