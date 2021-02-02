package com.company.Process_Mining;

import com.company.Process_Mining.Base_Data.Mining_User;

public class Mining_User_Relation {

    private Mining_User Related_To;
    private Relation_Type Relation_Type;

    public Mining_User_Relation() {
    }

    public Mining_User_Relation(Mining_User related_To, com.company.Process_Mining.Relation_Type relation_Type) {
        Related_To = related_To;
        Relation_Type = relation_Type;
    }

    public Mining_User getRelated_To() {
        return Related_To;
    }

    public void setRelated_To(Mining_User related_To) {
        Related_To = related_To;
    }

    public com.company.Process_Mining.Relation_Type getRelation_Type() {
        return Relation_Type;
    }

    public void setRelation_Type(com.company.Process_Mining.Relation_Type relation_Type) {
        Relation_Type = relation_Type;
    }
}
