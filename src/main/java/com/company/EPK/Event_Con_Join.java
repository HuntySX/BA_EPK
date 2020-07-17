package com.company.EPK;

import com.company.Enums.Contype;
import com.company.Enums.Gate_Check_Status;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Event_Instance;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Instance_Workflow;

import java.util.ArrayList;
import java.util.List;

import static com.company.Enums.Contype.*;
import static com.company.Enums.Gate_Check_Status.*;

public class Event_Con_Join extends Connector implements Printable_Node {

    private List<Nodemap> Mapped_Branch_Elements;
    private List<Event_Instance> Throughput_Instances;
    private List<EPK_Node> Mapped_Branch_Elements_AND;

    public Event_Con_Join(List<EPK_Node> Next_Elem, int ID, Contype contype) {
        super(Next_Elem, ID, contype);
        Mapped_Branch_Elements = new ArrayList<>();
        Throughput_Instances = new ArrayList<>();
        Mapped_Branch_Elements_AND = new ArrayList<>();
    }

    public List<EPK_Node> getMapped_Branch_Elements_AND() {
        return Mapped_Branch_Elements_AND;
    }

    public void setMapped_Branch_Elements_AND(List<EPK_Node> mapped_Branch_Elements_AND) {
        Mapped_Branch_Elements_AND = mapped_Branch_Elements_AND;
    }

    public Gate_Check_Status check_Previous_Elem(Instance_Workflow instance) {

        List<EPK_Node> finished_EPK_Nodes = instance.getInstance().getFinished_Work();
        List<EPK_Node> scheduled_EPK_Nodes = instance.getInstance().getScheduled_Work();

        boolean Check_Single_Predecessor = false;
        boolean Check_Multiple_Predecessor = true;
        List<EPK_Node> Predecessors = new ArrayList<>();

        if (this.getContype() == EAGER_XOR) {

            if (!scheduled_EPK_Nodes.isEmpty()) {
                return WAIT;
                //TODO Check Scheduled Nodes for Functions, to Exclude Race Conditioning Gates
            } else {

                for (Nodemap m : Mapped_Branch_Elements) {
                    if (finished_EPK_Nodes.contains(m.getFinished_Elem())) {
                        if (Check_Single_Predecessor) {
                            Check_Multiple_Predecessor = true;
                            break;
                        }
                        Check_Single_Predecessor = true;
                    }
                }
                if (Check_Multiple_Predecessor) {
                    return BLOCK;
                } else if (Check_Single_Predecessor) {
                    return ADVANCE;
                } else
                    return WAIT;
            }
        } else if (this.getContype() == LAZY_XOR) {

            if (Throughput_Instances.contains(instance.getInstance())) {
                return BLOCK;
            }
            Check_Single_Predecessor = false;
            Check_Multiple_Predecessor = false;
            finished_EPK_Nodes = instance.getInstance().getFinished_Work();


            for (Nodemap m : Mapped_Branch_Elements) {
                Predecessors.add(m.getFinished_Elem());
            }

            for (EPK_Node n : finished_EPK_Nodes) {
                for (EPK_Node m : Predecessors) {
                    if (n.getID() == m.getID()) {
                        if (Check_Single_Predecessor) {
                            Check_Multiple_Predecessor = true;
                            break;
                        }
                        Check_Single_Predecessor = true;
                    }
                }
                if (Check_Single_Predecessor || Check_Multiple_Predecessor) {
                    break;
                }
            }
            if (Check_Multiple_Predecessor) {
                return BLOCK;
            } else if (Check_Single_Predecessor) {
                Throughput_Instances.add(instance.getInstance());
                return ADVANCE;
            } else {
                return WAIT;
            }
        } else if (this.getContype() == EAGER_OR) {
            finished_EPK_Nodes = instance.getInstance().getFinished_Work();
            scheduled_EPK_Nodes = instance.getInstance().getScheduled_Work();
            List<Nodemap> Finishing_Map = new ArrayList<>();
            if (!scheduled_EPK_Nodes.isEmpty()) {
                return WAIT;
                //TODO Check Scheduled Notes for Functions, to Exclude Race Conditioning Gates
            } else {


                for (Nodemap m : Mapped_Branch_Elements) {
                    if (finished_EPK_Nodes.contains(m.getStarted_Elem()) || scheduled_EPK_Nodes.contains(m.getStarted_Elem())) {
                        Finishing_Map.add(m);
                    }
                }
                for (Nodemap m : Finishing_Map) {
                    if (!finished_EPK_Nodes.contains(m.getFinished_Elem())) {
                        return WAIT;
                    }
                }
                return ADVANCE;
            }
        } else if (this.getContype() == LAZY_OR) {

            if (Throughput_Instances.contains(instance.getInstance())) {
                return BLOCK;
            }

            Check_Single_Predecessor = false;

            for (Nodemap m : Mapped_Branch_Elements) {
                Predecessors.add(m.getFinished_Elem());
            }

            for (EPK_Node n : finished_EPK_Nodes) {
                for (EPK_Node m : Predecessors) {
                    if (n.getID() == m.getID()) {
                        Check_Single_Predecessor = true;
                        break;
                    }
                }
                if (Check_Single_Predecessor) {
                    break;
                }
            }
            if (Check_Single_Predecessor) {
                Throughput_Instances.add(instance.getInstance());
                return ADVANCE;
            } else {
                return WAIT;
            }

        } else {
            for (EPK_Node m : Mapped_Branch_Elements_AND) {
                if (!finished_EPK_Nodes.contains(m)) {
                    return WAIT;
                }
            }
            return ADVANCE;
        }
    }

    public void AddNode(EPK_Node to_wait_for) {
        if (this.getContype() == AND && !Mapped_Branch_Elements_AND.contains(to_wait_for)) {
            Mapped_Branch_Elements_AND.add(to_wait_for);
        }
    }

    public void AddMap(EPK_Node start, EPK_Node end) {
        boolean mapped = false;
        for (Nodemap added_Node : Mapped_Branch_Elements) {
            if (added_Node.containsboth(start, end)) {
                mapped = true;
                break;
            }
        }
        if (!mapped) {
            Nodemap to_Map = new Nodemap(start, end);
            Mapped_Branch_Elements.add(to_Map);
        }
    }

    public List<Nodemap> getMapped_Branch_Elements() {
        return Mapped_Branch_Elements;
    }

    public void setMapped_Branch_Elements(List<Nodemap> mapped_Branch_Elements) {
        Mapped_Branch_Elements = mapped_Branch_Elements;
    }


    public List<Event_Instance> getThroughput_Instances() {
        return Throughput_Instances;
    }

    public void setThroughput_Instances(List<Event_Instance> throughput_Instances) {
        Throughput_Instances = throughput_Instances;
    }

    @Override
    public boolean CheckSettings() {
        boolean Check = true;
        /*if(Mapped_Branch_Elements == null || Mapped_Branch_Elements.isEmpty())
        {
            Check = false;
        }*/
        return Check;
    }

    public Event_Con_Join returnUpperClass() {
        return this;
    }
}
