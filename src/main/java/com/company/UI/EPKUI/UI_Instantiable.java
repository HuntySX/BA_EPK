package com.company.UI.EPKUI;

import com.company.EPK.EPK_Node;
import javafx.scene.layout.VBox;

import java.util.List;

public interface UI_Instantiable {
    public VBox Get_UI();

    public void save_Settings();

    public List<EPK_Node> getNodelist();

    public int get_Next_Elem_ID();

    public EPK_Node getthis();

}
