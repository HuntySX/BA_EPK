package com.company.Simulation.Simulation_Base.Data.Threading_Data;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;
import com.company.Simulation.Simulation_Base.Data.Threading_Data.Item;

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
