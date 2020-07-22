package com.company.EPK;

import java.util.List;

public class Gate_Waiting_Instance {
    List<EPK_Node> arrived;
    int Instance_ID;
    int Waiting_ID;

    public List<EPK_Node> getArrived() {
        return arrived;
    }

    public int getInstance_ID() {
        return Instance_ID;
    }

    public void setInstance_ID(int instance_ID) {
        Instance_ID = instance_ID;
    }

    public int getWaiting_ID() {
        return Waiting_ID;
    }

    public void setWaiting_ID(int waiting_ID) {
        Waiting_ID = waiting_ID;
    }
}
