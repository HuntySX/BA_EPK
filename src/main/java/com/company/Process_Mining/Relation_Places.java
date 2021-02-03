package com.company.Process_Mining;

import com.company.Process_Mining.Base_Data.Mining_Activity;

import java.util.ArrayList;
import java.util.List;

public class Relation_Places {
    List<Mining_Activity> From;
    List<Mining_Activity> To;
    boolean isStart;
    boolean isFinal;

    public Relation_Places() {
        From = new ArrayList<>();
        To = new ArrayList<>();
        isStart = false;
        isFinal = false;
    }

    public List<Mining_Activity> getFrom() {
        return From;
    }

    public List<Mining_Activity> getTo() {
        return To;
    }

    public boolean has_Same_From(Relation_Places other) {
        for (Mining_Activity From_This : From) {
            if (!other.getFrom().contains(From_This)) {
                return false;
            }
        }
        for (Mining_Activity From_Other : other.getFrom()) {
            if (!From.contains(From_Other)) {
                return false;
            }
        }
        return true;
    }

    public boolean has_Same_to(Relation_Places other) {
        for (Mining_Activity To_This : To) {
            if (!other.getTo().contains(To_This)) {
                return false;
            }
        }
        for (Mining_Activity To_Other : other.getTo()) {
            if (!To.contains(To_Other)) {
                return false;
            }
        }
        return true;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }
}
