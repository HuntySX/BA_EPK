package com.company.EPK;

import com.company.Enums.Contype;
import com.company.Enums.Split_Decide_Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Event_Con_Split extends Connector implements Printable_Node {

    private Split_Decide_Type Decide_Type;
    private boolean is_Event_Driven;
    private List<Split_Node_Chances> Chances_List;

    public Event_Con_Split(List<EPK_Node> Next_Elem, int ID, Contype contype, Split_Decide_Type decide_type) {
        super(Next_Elem, ID, contype);
        this.Decide_Type = decide_type;
        this.Chances_List = new ArrayList<>();
    }

    public Event_Con_Split(List<EPK_Node> Next_Elem, int ID, Contype contype, Split_Decide_Type decide_type, List<Split_Node_Chances> Chances_List) {
        super(Next_Elem, ID, contype);
        this.Decide_Type = decide_type;
        this.Chances_List = Chances_List;

    }

    public List<EPK_Node> choose_Next() {

        List<EPK_Node> Next_Elem = this.getNext_Elem();
        List<EPK_Node> Result = new ArrayList<>();
        if (Decide_Type == Split_Decide_Type.SINGLE_RANDOM) {
            if (!Next_Elem.isEmpty()) {
                int count_Elem = Next_Elem.size();
                Random rand = new Random();
                count_Elem = rand.nextInt(count_Elem);
                Result.add(Next_Elem.get(count_Elem));
            }
        } else if (Decide_Type == Split_Decide_Type.FULL_RANDOM) {
            if (!Next_Elem.isEmpty()) {
                int count_Elem_Quantity = Next_Elem.size();
                Random rand = new Random();
                count_Elem_Quantity = rand.nextInt(count_Elem_Quantity);
                //if (count_Elem_Quantity == 0) {
                count_Elem_Quantity++;
                //}
                List<EPK_Node> working_On_EPK_Nodes = new ArrayList<>();
                working_On_EPK_Nodes.addAll(Next_Elem);
                for (int i = count_Elem_Quantity; i > 0; i--) {
                    int count_Elem = working_On_EPK_Nodes.size();
                    count_Elem = rand.nextInt(count_Elem);
                    Result.add(working_On_EPK_Nodes.get(count_Elem));
                    working_On_EPK_Nodes.remove(count_Elem);
                }
            }
        } else if (Decide_Type == Split_Decide_Type.FULL) {
            List<EPK_Node> ResultList = new ArrayList<>(Next_Elem);
            ResultList = MixResultList(ResultList);
            return ResultList;
        } else if (Decide_Type == Split_Decide_Type.CUSTOM) {
            if (getContype() == Contype.AND) {
                List<EPK_Node> ResultList = new ArrayList<>(Next_Elem);
                ResultList = MixResultList(ResultList);
                return ResultList;
            } else if (getContype() == Contype.EAGER_OR || getContype() == Contype.LAZY_OR) {
                List<EPK_Node> working_On_EPK_Nodes = new ArrayList<>();
                while (working_On_EPK_Nodes.isEmpty()) {
                    for (Split_Node_Chances Chance : Chances_List) {
                        if (Chance != null) {
                            Random rand = new Random();
                            int randchance = rand.nextInt(100);
                            if (randchance + 1 <= Chance.getChance()) {
                                for (EPK_Node next_Elem : Next_Elem) {
                                    if (next_Elem.getID() == Chance.getNode().getID()) {
                                        working_On_EPK_Nodes.add(next_Elem);
                                    }
                                }
                            }
                        }
                    }
                }

                List<EPK_Node> ResultList = new ArrayList<>(working_On_EPK_Nodes);
                ResultList = MixResultList(ResultList);
                return ResultList;
            } else if (getContype() == Contype.EAGER_XOR || getContype() == Contype.LAZY_XOR) {

                List<EPK_Node> working_On_EPK_Nodes = new ArrayList<>();
                Random rand = new Random();
                int randchance = rand.nextInt(100);
                randchance++;
                int calculatefirstbound = 0;
                int calculatelastbound = 0;
                boolean found = false;
                for (Split_Node_Chances Chance : Chances_List) {
                    if (found) {
                        break;
                    }
                    calculatelastbound = calculatelastbound + Chance.getChance();
                    if (calculatefirstbound < randchance && randchance <= calculatelastbound) {
                        for (EPK_Node next_Elem : Next_Elem) {
                            if (Chance.getNode().getID() == next_Elem.getID()) {
                                working_On_EPK_Nodes.add(next_Elem);
                                found = true;
                                break;
                            }
                        }
                    } else {
                        calculatefirstbound = calculatelastbound;
                    }
                }
                List<EPK_Node> ResultList = new ArrayList<>(working_On_EPK_Nodes);
                ResultList = MixResultList(ResultList);
                return ResultList;
            }
        }
        List<EPK_Node> ResultList = new ArrayList<>(Result);
        ResultList = MixResultList(ResultList);
        return ResultList;
    }

    private List<EPK_Node> MixResultList(List<EPK_Node> result) {
        if (result.size() == 1) {
            return result;
        }
        List<EPK_Node> mixed_Result_List = new ArrayList<>();
        while (!result.isEmpty()) {
            Random rand = new Random(result.size());
            EPK_Node node_to_Mix = result.get(rand.nextInt());
            result.remove(node_to_Mix);
            mixed_Result_List.add(node_to_Mix);
        }
        if (!mixed_Result_List.isEmpty()) {
            return mixed_Result_List;
        }
        return result;
    }

    public Split_Decide_Type getDecide_Type() {
        return Decide_Type;
    }

    @Override
    public boolean CheckSettings() {
        boolean Check = true;
        if (Decide_Type == null) {
            Check = false;
        }
        return Check;
    }

    public void setDecide_Type(Split_Decide_Type Type) {
        Decide_Type = Type;
    }

    public boolean isIs_Event_Driven() {
        return is_Event_Driven;
    }

    public void setIs_Event_Driven(boolean is_Event_Driven) {
        this.is_Event_Driven = is_Event_Driven;
    }

    public Event_Con_Split returnUpperClass() {
        return this;
    }

    public List<Split_Node_Chances> getChances_List() {
        return Chances_List;
    }

    public void setChances_List(List<Split_Node_Chances> chances_List) {
        Chances_List = chances_List;
    }

    public void generateChance(EPK_Node next_Elem) {
        boolean found = false;
        for (Split_Node_Chances Chances : Chances_List) {
            if (Chances.getNode().equals(next_Elem)) {
                found = true;
                break;
            }
        }
        if (!found) {
            Split_Node_Chances new_Chance = new Split_Node_Chances(next_Elem, 0);
            Chances_List.add(new_Chance);
        }
    }

    public void CleanupChances() {
        List<Split_Node_Chances> Mark_For_Deletion = new ArrayList<>();
        for (Split_Node_Chances Chances : Chances_List) {
            if (!getNext_Elem().contains(Chances.getNode())) {
                Mark_For_Deletion.add(Chances);
            }
        }
        if (!Mark_For_Deletion.isEmpty()) {
            Chances_List.removeAll(Mark_For_Deletion);
        }
    }
}
