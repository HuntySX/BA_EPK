package com.company.Process_Mining;

import com.company.Process_Mining.Base_Data.Mining_Activity;

import java.util.ArrayList;
import java.util.List;

public class Relation_Places {
    List<Mining_Activity> From;
    List<Mining_Activity> To;

    public Relation_Places() {
        From = new ArrayList<>();
        To = new ArrayList<>();
    }

    public List<Mining_Activity> getFrom() {
        return From;
    }

    public List<Mining_Activity> getTo() {
        return To;
    }
}
