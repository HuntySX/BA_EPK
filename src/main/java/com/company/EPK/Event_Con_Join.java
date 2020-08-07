package com.company.EPK;

import com.company.Enums.Contype;
import com.company.Enums.Gate_Check_Status;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Instance_Workflow;

import java.util.ArrayList;
import java.util.List;

import static com.company.Enums.Gate_Check_Status.*;

public class Event_Con_Join extends Connector implements Printable_Node {

    private List<EPK_Node> Previous_Elements;
    private List<Gate_Waiting_Instance> Waiting_Instance_List;

    public Event_Con_Join(List<EPK_Node> Next_Elem, int ID, Contype contype) {
        super(Next_Elem, ID, contype);
        Previous_Elements = new ArrayList<>();
        Waiting_Instance_List = new ArrayList<>();
    }

    public List<EPK_Node> getPrevious_Elements() {
        return Previous_Elements;
    }

    public void setPrevious_Elements(List<EPK_Node> previous_Elements) {
        Previous_Elements = previous_Elements;
    }

    public Gate_Check_Status check_Previous_Elem(Instance_Workflow instance) {

        List<EPK_Node> finished_EPK_Nodes = instance.getInstance().getFinished_Work();
        List<EPK_Node> scheduled_EPK_Nodes = instance.getInstance().getScheduled_Work();
        List<Gate_Waiting_Instance> Waiting_Instance_At_This_Gate = new ArrayList<>();

        for (Gate_Waiting_Instance Waiting_Instance : Waiting_Instance_List) {
            if (Waiting_Instance.getFirst_Instance().getInstance().equals(instance.getInstance())) {
                Waiting_Instance_At_This_Gate.add(Waiting_Instance);
            }
        }
        if (Waiting_Instance_At_This_Gate.isEmpty()) {
            switch (getContype()) {
                case LAZY_OR:
                case LAZY_XOR: {
                    Gate_Waiting_Instance new_Wait = new Gate_Waiting_Instance(instance, instance.getComing_From(), 0, this);
                    instance.setWaiting_At_Gate(new_Wait);
                    Waiting_Instance_List.add(new_Wait);
                    return BLOCK;
                }
                case EAGER_OR: {
                    Gate_Waiting_Instance new_Wait = new Gate_Waiting_Instance(instance, instance.getComing_From(), 0, this);
                    instance.setWaiting_At_Gate(new_Wait);
                    Waiting_Instance_List.add(new_Wait);
                    if (possiblePreCondition(new_Wait)) {
                        return DELAY;
                    } else {
                        Waiting_Instance_List.remove(new_Wait);
                        instance.setWaiting_At_Gate(null);
                        if (new_Wait.getArrived().size() >= 1) {
                            return ADVANCE;
                        } else {
                            return BLOCK;
                        }
                    }
                }
                case AND: {
                    Gate_Waiting_Instance new_Wait = new Gate_Waiting_Instance(instance, instance.getComing_From(), 0, this);
                    instance.setWaiting_At_Gate(new_Wait);
                    Waiting_Instance_List.add(new_Wait);
                    if (possiblePreCondition(new_Wait)) {
                        return DELAY;
                    } else {
                        Waiting_Instance_List.remove(new_Wait);
                        instance.setWaiting_At_Gate(null);
                        boolean check_AND_Cond = true;
                        for (EPK_Node Prev_Cond : Previous_Elements) {
                            if (!new_Wait.getArrived().contains(Prev_Cond)) {
                                check_AND_Cond = false;
                            }
                        }
                        if (check_AND_Cond) {
                            return ADVANCE;
                        } else {
                            return BLOCK;
                        }
                    }
                }
                case EAGER_XOR:
                    Gate_Waiting_Instance new_Wait = new Gate_Waiting_Instance(instance, instance.getComing_From(), 0, this);
                    instance.setWaiting_At_Gate(new_Wait);
                    Waiting_Instance_List.add(new_Wait);
                    if (possiblePreCondition(new_Wait)) {
                        return DELAY;
                    } else {
                        Waiting_Instance_List.remove(new_Wait);
                        instance.setWaiting_At_Gate(null);
                        if (new_Wait.getArrived().size() == 1 && Previous_Elements.contains(new_Wait.getArrived().get(0))) {
                            return ADVANCE;
                        } else {
                            return BLOCK;
                        }
                    }
                default:
                    return BLOCK;
            }
        } else {
            switch (getContype()) {
                case LAZY_OR:
                case LAZY_XOR: {
                    for (Gate_Waiting_Instance Waiting : Waiting_Instance_At_This_Gate) {
                        if (!Waiting.getArrived().contains(instance.getComing_From())) {
                            Waiting.getArrived().add(instance.getComing_From());
                            return BLOCK;
                        }
                    }
                    Gate_Waiting_Instance new_Wait = new Gate_Waiting_Instance(instance, instance.getComing_From(), Waiting_Instance_At_This_Gate.size() + 1, this);
                    instance.setWaiting_At_Gate(new_Wait);
                    Waiting_Instance_List.add(new_Wait);
                    return BLOCK;
                }
                case EAGER_OR: {
                    if (instance.getWaiting_At_Gate() == null) {
                        for (Gate_Waiting_Instance Waiting : Waiting_Instance_At_This_Gate) {
                            if (!Waiting.getArrived().contains(instance.getComing_From())) {
                                Waiting.getArrived().add(instance.getComing_From());
                                return BLOCK;
                            }
                        }
                        Gate_Waiting_Instance new_Wait = new Gate_Waiting_Instance(instance, instance.getComing_From(), Waiting_Instance_At_This_Gate.size() + 1, this);
                        instance.setWaiting_At_Gate(new_Wait);
                        Waiting_Instance_List.add(new_Wait);
                        return DELAY;
                    } else {
                        Gate_Waiting_Instance Waiting = instance.getWaiting_At_Gate();
                        if (possiblePreCondition(Waiting)) {
                            return DELAY;
                        } else {
                            Waiting_Instance_List.remove(Waiting);
                            instance.setWaiting_At_Gate(null);
                            if (Waiting.getArrived().size() >= 1) {
                                return ADVANCE;
                            } else {
                                return BLOCK;
                            }
                        }
                    }
                }
                case AND: {
                    if (instance.getWaiting_At_Gate() == null) {
                        for (Gate_Waiting_Instance Waiting : Waiting_Instance_At_This_Gate) {
                            if (!Waiting.getArrived().contains(instance.getComing_From())) {
                                Waiting.getArrived().add(instance.getComing_From());
                                return BLOCK;
                            }
                        }
                        Gate_Waiting_Instance new_Wait = new Gate_Waiting_Instance(instance, instance.getComing_From(), Waiting_Instance_At_This_Gate.size() + 1, this);
                        instance.setWaiting_At_Gate(new_Wait);
                        Waiting_Instance_List.add(new_Wait);
                        return DELAY;
                    } else {
                        Gate_Waiting_Instance Waiting = instance.getWaiting_At_Gate();
                        if (possiblePreCondition(Waiting)) {
                            return DELAY;
                        } else {
                            Waiting_Instance_List.remove(Waiting);
                            instance.setWaiting_At_Gate(null);
                            boolean check_AND_Cond = true;
                            for (EPK_Node Prev_Cond : Previous_Elements) {
                                if (!Waiting.getArrived().contains(Prev_Cond)) {
                                    check_AND_Cond = false;
                                }
                            }
                            if (check_AND_Cond) {
                                return ADVANCE;
                            } else {
                                return BLOCK;
                            }
                        }
                    }
                }
                case EAGER_XOR:
                    if (instance.getWaiting_At_Gate() == null) {
                        for (Gate_Waiting_Instance Waiting : Waiting_Instance_At_This_Gate) {
                            if (!Waiting.getArrived().contains(instance.getComing_From())) {
                                Waiting.getArrived().add(instance.getComing_From());
                                return BLOCK;
                            }
                        }
                        Gate_Waiting_Instance new_Wait = new Gate_Waiting_Instance(instance, instance.getComing_From(), Waiting_Instance_At_This_Gate.size() + 1, this);
                        instance.setWaiting_At_Gate(new_Wait);
                        Waiting_Instance_List.add(new_Wait);
                        return DELAY;
                    } else {
                        Gate_Waiting_Instance Waiting = instance.getWaiting_At_Gate();
                        if (possiblePreCondition(Waiting)) {
                            return DELAY;
                        } else {
                            Waiting_Instance_List.remove(Waiting);
                            instance.setWaiting_At_Gate(null);
                            if (Waiting.getArrived().size() == 1 && Previous_Elements.contains(Waiting.getArrived().get(0))) {
                                return ADVANCE;
                            } else {
                                return BLOCK;
                            }
                        }
                    }
                default:
                    return BLOCK;
            }
        }
        /* OLD Check_Prev_Element
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
            */
    }

    private boolean possiblePreCondition(Gate_Waiting_Instance instance) {
        List<EPK_Node> Left_Elements = new ArrayList<>(Previous_Elements);
        for (EPK_Node arrived : instance.getArrived()) {
            Left_Elements.remove(arrived);
        }
        for (EPK_Node Node : Left_Elements) {
            for (EPK_Node Scheduled : instance.getFirst_Instance().getInstance().getScheduled_Work()) {
                if (Scheduled.getReachable_Elements().contains(Node) || Scheduled.equals(Node)) {
                    return true;
                }
            }

        }
        return false;
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

    public List<Gate_Waiting_Instance> getWaiting_Instance_List() {
        return Waiting_Instance_List;
    }

    public void setWaiting_Instance_List(List<Gate_Waiting_Instance> waiting_Instance_List) {
        Waiting_Instance_List = waiting_Instance_List;
    }

    public boolean isCorrectLazyState(Gate_Waiting_Instance Instance) {
        switch (getContype()) {
            case LAZY_OR: {
                if (Instance.getArrived().size() >= 1) {
                    return true;
                }
            }
            case LAZY_XOR: {
                if (Instance.getArrived().size() == 1 && getPrevious_Elements().contains(Instance.getArrived().get(0))) {
                    return true;
                }
            }
            default:
                return false;
        }
    }

}

