package com.company.EPK;

public class Split_Node_Chances {
    private EPK_Node node;
    private Integer Chance;

    public Split_Node_Chances(EPK_Node node, Integer chance) {
        this.node = node;
        Chance = chance;
    }

    public EPK_Node getNode() {
        return node;
    }

    public void setNode(EPK_Node node) {
        this.node = node;
    }

    public Integer getChance() {
        return Chance;
    }

    public void setChance(Integer chance) {
        Chance = chance;
    }
}
