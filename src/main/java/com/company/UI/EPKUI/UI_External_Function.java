package com.company.UI.EPKUI;

import com.company.EPK.EPK_Node;
import com.company.EPK.External_Function;
import javafx.scene.layout.VBox;

import java.util.List;

public class UI_External_Function extends External_Function implements UI_Instantiable {

    public UI_External_Function(int id, UI_EPK epk, VBox rightbox) {
        super();
    }


    @Override
    public VBox Get_UI() {
        return null;
    }

    @Override
    public void save_Settings() {

    }

    @Override
    public List<EPK_Node> getNodelist() {
        return null;
    }

    @Override
    public int get_Next_Elem_ID() {
        return 0;
    }

    @Override
    public EPK_Node getthis() {
        return null;
    }
}
