package com.company.EPK;

import com.company.Enums.Contype;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;
import com.company.Simulation.Simulation_Threading.Event_Gate;

import java.util.List;

import static com.company.Enums.Process_Status.Active;
import static com.company.Enums.Process_Status.Scheduled;

public class Con_Join extends Connector {

    private List<EPK_Node> Pre_Elem;
    private Event_Gate event_gate;

    public Con_Join(List<EPK_Node> Next_Elem, int ID, Contype contype) {
        super(Next_Elem, ID, contype);
        //event_gate = Event_Gate.get_Event_Gate();
    }

    public List<EPK_Node> getPre_Elem() {
        return Pre_Elem;
    }

    public void setPre_Elem(List<EPK_Node> pre_Elem) {
        Pre_Elem = pre_Elem;
    }

    public void addPre_Elem(EPK_Node pre_Elem) {
        Pre_Elem.add(pre_Elem);
    }

    public boolean check_Previous_Elem(Simulation_Instance instance) {

        for (EPK_Node n : Pre_Elem) {
            if (instance.getWorkflowMonitor().get_Elements().contains(n) &&
                    (instance.getWorkflowMonitor().getProcess_Status().get(instance.getWorkflowMonitor().get_Elements().indexOf(n)) == Active ||
                            instance.getWorkflowMonitor().getProcess_Status().get(instance.getWorkflowMonitor().get_Elements().indexOf(n)) == Scheduled)) {
                return false;
            }
        }
        return true;

       /* if(instance.getScheduled_Processes().isEmpty()) {
            Event_Gate.getEvent_List().add_transport_Process(instance);
        }

        boolean finished = true;

        for (Function f : instance.getScheduled_Processes()) {
            for (Node p: Pre_Elem) {
                if (f.getID() == p.getID()) {
                    finished = false;
                    break;
                }
            }
        }

        if (finished) {
            Event_Gate.getEvent_List().add_transport_Process(instance);
        }*/
    }

    @Override
    public boolean CheckSettings() {
        boolean Check = true;
        if (Pre_Elem == null || Pre_Elem.isEmpty()) {
            Check = false;
        }
        if (event_gate == null) {
            Check = false;
        }
        return Check;
    }
}
