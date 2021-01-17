package com.company.UI.EPKUI;

import com.company.EPK.EPK_Node;

import java.util.List;

public class ReachableTest_ListObject {
    private final EPK_Node node;
    private boolean found;

    public ReachableTest_ListObject(EPK_Node node, boolean found) {
        this.node = node;
        this.found = found;
    }

    public EPK_Node getNode() {
        return node;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public void findAndActivate(List<ReachableTest_ListObject> List, EPK_Node node) {
        for (ReachableTest_ListObject Object : List) {
            if (Object.getNode().equals(node)) {
                Object.setFound(true);
            }
        }
    }
}
