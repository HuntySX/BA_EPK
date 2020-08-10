package com.company.UI.EPKUI;

import com.company.EPK.Split_Node_Chances;
import com.dlsc.formsfx.model.structure.IntegerField;
import com.dlsc.formsfx.model.structure.StringField;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UI_Split_Node_Chances {
    private Split_Node_Chances Chance;
    private IntegerProperty Chanceprop;
    private IntegerField Chancefield;
    private SimpleStringProperty Nameprop;
    private StringField Namefield;

    public UI_Split_Node_Chances(Split_Node_Chances chance, IntegerProperty chanceprop, IntegerField chancefield, SimpleStringProperty nameprop, StringField namefield) {
        Chance = chance;
        Chanceprop = chanceprop;
        Chancefield = chancefield;
        Nameprop = nameprop;
        Namefield = namefield;
    }

    public Split_Node_Chances getChance() {
        return Chance;
    }

    public void setChance(Split_Node_Chances chance) {
        Chance = chance;
    }

    public String getNameprop() {
        return Nameprop.get();
    }

    public void setNameprop(String nameprop) {
        this.Nameprop.set(nameprop);
    }

    public SimpleStringProperty namepropProperty() {
        return Nameprop;
    }

    public StringField getNamefield() {
        return Namefield;
    }

    public void setNamefield(StringField namefield) {
        Namefield = namefield;
    }

    public int getChanceprop() {
        return Chanceprop.get();
    }

    public void setChanceprop(int chanceprop) {
        this.Chanceprop.set(chanceprop);
    }

    public IntegerProperty chancepropProperty() {
        return Chanceprop;
    }

    public IntegerField getChancefield() {
        return Chancefield;
    }

    public void setChancefield(IntegerField chancefield) {
        Chancefield = chancefield;
    }
}
