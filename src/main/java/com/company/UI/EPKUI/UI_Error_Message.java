package com.company.UI.EPKUI;

import com.company.EPK.EPK_Node;

import java.util.ArrayList;
import java.util.List;

public class UI_Error_Message {
    private String ErrorText;
    private EPK_Node Error_Node;
    private List<EPK_Node> Affiliated_Error_Nodes;
    private Error_Rating Rating;
    private Type_Of_Error Errortype;


    public UI_Error_Message(Type_Of_Error Type, EPK_Node Source, List<EPK_Node> Affiliated) {
        this.Error_Node = Source;
        this.Affiliated_Error_Nodes = Affiliated;
        if (this.Affiliated_Error_Nodes == null) {
            Affiliated_Error_Nodes = new ArrayList<>();
        }
        this.Errortype = Type;
        generateText_Rating();
    }

    public void generateText_Rating() {
        switch (Errortype) {
            case NO_SUCCESSORS: {
                ErrorText = "Node: " + Error_Node.toString() + " is missing successors. " +
                        "Although this won´t stop the program from working, it is not in " +
                        "the sense of an EPK for this blocktype to have no sucessors";
                this.Rating = Error_Rating.LOW;
                break;

            }
            case NOT_REACHABLE: {
                ErrorText = "Node: " + Error_Node.toString() + "is not reachable. it either misses " +
                        "nodes leading to it, or a Gate has no chance to split instances to a Path " +
                        "leading to this Node.";
                this.Rating = Error_Rating.LOW;
                break;

            }
            case MISSING_OR_WRONG_SETTINGS: {
                ErrorText = "Node: " + Error_Node.toString() + " misses critical Settings. you need to " +
                        "set those up.";
                this.Rating = Error_Rating.HIGH;
                break;

            }
            case ACTIVATING_MISSING_SISTERBLOCK: {
                ErrorText = "Node: " + Error_Node.toString() + " misses an Activating Sisterblock. This could " +
                        "either be a Activating Function or an Activating Start Instance. " +
                        "Leaving this error results in Functions not instantiating those special instances";
                this.Rating = Error_Rating.LOW;
                break;

            }
            case EPK_RULE_MISSED_WRONG_SUCESSORS: {
                ErrorText = "Node: " + Error_Node.toString() + " is connected to wrong sucessors. Although " +
                        "this won´t stop the program to work correctly you might wan´t to fix that in the " +
                        "sense of an correct EPC. ";
                if (!Affiliated_Error_Nodes.isEmpty()) {
                    ErrorText.concat("Affiliated Nodes: ");
                    for (EPK_Node node : Affiliated_Error_Nodes) {
                        ErrorText.concat(node.toString() + " , ");
                    }
                }
                this.Rating = Error_Rating.LOW;
                break;

            }
            case EPK_RULE_MISSED_TOO_MANY_SUCESSORS: {
                ErrorText = "Node: " + Error_Node.toString() + " is connected to too many sucessors for " +
                        "its blocktype. This will prevent the simulation to work correctly and you might " +
                        "want to fix it. ";
                if (!Affiliated_Error_Nodes.isEmpty()) {
                    ErrorText.concat("Affiliated Nodes: ");
                    for (EPK_Node node : Affiliated_Error_Nodes) {
                        ErrorText.concat(node.toString() + " , ");
                    }
                }
                this.Rating = Error_Rating.HIGH;
                break;

            }
            case EPK_RULE_MISSED_GATE_SUCC_PRE_TYPE: {
                ErrorText = "Node: " + Error_Node.toString() + " has Same Predecessor / Successor. This " +
                        "won´t prevent the Simulation to Run but is not in the sense of a correct EPC. ";
                if (!Affiliated_Error_Nodes.isEmpty()) {
                    ErrorText.concat("Affiliated Nodes: ");
                    for (EPK_Node node : Affiliated_Error_Nodes) {
                        ErrorText.concat(node.toString() + " , ");
                    }
                }
                this.Rating = Error_Rating.HIGH;
                break;

            }
            case EPK_RULE_MISSED_TOO_FEW_SUCESSORS: {
                ErrorText = "Node: " + Error_Node.toString() + " is connected to too few sucessors for " +
                        "its blocktype. This will prevent the simulation to work correctly and you will need " +
                        "to fix this. ";
                this.Rating = Error_Rating.HIGH;
                break;

            }
            case EPK_RULE_MISSED_TOO_MANY_PREDECESSORS: {
                ErrorText = "Node: " + Error_Node.toString() + " has too Many Predecessors. This is against" +
                        " the correctness of an EPC an will cause a nonfunctional Simulation. ";
                if (!Affiliated_Error_Nodes.isEmpty()) {
                    ErrorText.concat("Affiliated Nodes: ");
                    for (EPK_Node node : Affiliated_Error_Nodes) {
                        ErrorText.concat(node.toString() + " , ");
                    }
                }
                this.Rating = Error_Rating.HIGH;
                break;

            }
            case EPK_RULE_MISSED_TOO_FEW_PREDECESSORS: {
                ErrorText = "Node: " + Error_Node.toString() + " has too Few Predecessors. This is against" +
                        " the correctness of an EPC an will cause a nonfunctional Simulation. ";
                if (!Affiliated_Error_Nodes.isEmpty()) {
                    ErrorText.concat("Affiliated Nodes: ");
                    for (EPK_Node node : Affiliated_Error_Nodes) {
                        ErrorText.concat(node.toString() + " , ");
                    }
                }
                this.Rating = Error_Rating.HIGH;
                break;

            }
        }
    }

    @Override
    public String toString() {
        return "[ Error_Node= " + Error_Node +
                ", Rating= " + Rating +
                ", Errortype= " + Errortype +
                ']';
    }

    public String generateErrorText() {
        return "[ Rating= " + Rating + "] " + ErrorText;
    }
}
