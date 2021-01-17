package com.company.Process_Mining;

import static com.company.Process_Mining.Relation_Type.None;

public class Relation_Count {

    private Relation_Type Relation_type;
    private int count;

    public Relation_Count(Relation_Type relation_type, int count) {
        Relation_type = relation_type;
        this.count = count;
    }

    public Relation_Count() {
        Relation_type = None;
        count = 0;
    }

    public Relation_Type getRelation_type() {
        return Relation_type;
    }

    public void setRelation_type(Relation_Type relation_type) {
        Relation_type = relation_type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void incrementCount() {
        count++;
    }
}
