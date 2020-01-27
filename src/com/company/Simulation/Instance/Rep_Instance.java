package com.company.Simulation.Instance;

import com.company.Simulation.Data.Item;

public class Rep_Instance extends Simulation_Instance {

    private Item rep_Item;
    private float Quality;

    public Rep_Instance(int case_ID, Item rep_Item) {
        super(case_ID);
        this.rep_Item = rep_Item;
    }

    public Item getRep_Item() {
        return rep_Item;
    }
    public void setRep_Item(Item rep_Item) {
        this.rep_Item = rep_Item;
    }

}
