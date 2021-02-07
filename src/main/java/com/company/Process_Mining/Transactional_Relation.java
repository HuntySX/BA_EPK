package com.company.Process_Mining;

public class Transactional_Relation {

    private Integer ActivityID;
    private Relation_Places Connected_To_Place;
    private boolean FromTransaction;

    public Transactional_Relation() {
        ActivityID = 0;
        Connected_To_Place = new Relation_Places();
        FromTransaction = true;
    }

    public Transactional_Relation(Integer activityID, Relation_Places connected_To_Place, boolean fromTransaction) {
        ActivityID = activityID;
        Connected_To_Place = connected_To_Place;
        FromTransaction = fromTransaction;
    }

    public boolean isFromTransaction() {
        return FromTransaction;
    }

    public void setFromTransaction(boolean fromTransaction) {
        FromTransaction = fromTransaction;
    }

    public Integer getActivityID() {
        return ActivityID;
    }

    public void setActivityID(Integer activityID) {
        ActivityID = activityID;
    }

    public Relation_Places getConnected_To_Place() {
        return Connected_To_Place;
    }

    public void setConnected_To_Place(Relation_Places connected_To_Place) {
        Connected_To_Place = connected_To_Place;
    }

    public boolean isSame(Transactional_Relation other) {
        if (this.getActivityID().equals(other.getActivityID())) {
            if (this.getConnected_To_Place().has_Same_From(other.getConnected_To_Place())) {
                if (this.getConnected_To_Place().has_Same_to(other.getConnected_To_Place())) {
                    if (this.isFromTransaction() == other.isFromTransaction()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
