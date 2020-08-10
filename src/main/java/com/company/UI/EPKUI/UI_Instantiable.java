package com.company.UI.EPKUI;

import com.company.EPK.EPK_Node;
import javafx.scene.layout.VBox;

import java.util.List;

public interface UI_Instantiable {
     VBox Get_UI();

    void save_Settings();

    List<EPK_Node> getNodelist();

    int get_Next_Elem_ID();

    EPK_Node getthis();

}
