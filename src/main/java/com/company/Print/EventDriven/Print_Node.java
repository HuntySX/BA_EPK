package com.company.Print.EventDriven;

import com.company.EPK.EPK_Node;
import com.company.Print.ThreadingDriven.Print_File;

public class Print_Node extends Print_File {


    private EPK_Node Node;

    public Print_Node(EPK_Node node) {
        super(null, null, null, null);
        this.Node = node;

    }

    public Print_Node() {
        super();
    }

    public EPK_Node getTo_String() {
        return Node;
    }

}