package com.company.EPK;

import java.util.List;

public class Con_Join extends Connector {

    private List<Node> Pre_Elem;

    public Con_Join(List<Node> Next_Elem, int ID, Contype type) {
        super(Next_Elem, ID, type);
    }


    public Node check_Previous_Elem() {
        /* if Processinstanzlistenelemente != Pre_Elem
        return getNext_Elem().get(0);
            else
            return null;
         */
        return null;
    }

}
