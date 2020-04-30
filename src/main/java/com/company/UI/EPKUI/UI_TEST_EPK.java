package com.company.UI.EPKUI;

import com.company.EPK.*;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.SingleSelectionField;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class UI_TEST_EPK implements Initializable {

    @FXML
    Button OK_Button;
    @FXML
    VBox Error_Box;
    private UI_EPK EPK;
    private List<UI_Error_Message> Messages;
    private TextArea Area;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.Messages = new ArrayList<>();

    }

    public void setEPK(UI_EPK EPK) {
        this.EPK = EPK;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void generateTest() {


        List<EPK_Node> All_Elems = EPK.getAll_Elems();
        Messages = RunTests(All_Elems);
        Error_Box.getChildren().add(new Separator());
        Label label = new Label("Error messages: ");
        Error_Box.getChildren().add(label);
        SingleSelectionField<UI_Error_Message> UI_MESSAGES = Field.ofSingleSelectionType(Messages).label("Fehler");
        FormRenderer MESSAGES_UI = new FormRenderer(Form.of(Group.of(UI_MESSAGES)));
        Error_Box.getChildren().add(MESSAGES_UI);
        Button btn = new Button("Show Selected Error");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UI_Error_Message Mess = UI_MESSAGES.getSelection();
                if (Mess != null) {
                    if (Area != null) {
                        Area.clear();
                        Area.setText(Mess.generateErrorText());
                    } else {
                        Area = new TextArea(Mess.generateErrorText());
                        Area.setWrapText(true);
                        Error_Box.getChildren().add(Area);
                    }
                }
            }
        });
        ButtonBar bar = new ButtonBar();
        bar.getButtons().add(btn);
        Error_Box.getChildren().add(bar);
        Error_Box.getChildren().add(new Separator());
    }

    private List<UI_Error_Message> RunTests(List<EPK_Node> all_elems) {

        List<UI_Error_Message> Error_Messages = new ArrayList<>();

        Error_Messages.addAll(ReachableTest(all_elems));
        Error_Messages.addAll(SettingsTest(all_elems));
        Error_Messages.addAll(GraphTest(all_elems));
        Error_Messages.addAll(Missing_Sisterblock_Test(all_elems));
        return Error_Messages;
    }

    private List<UI_Error_Message> Missing_Sisterblock_Test(List<EPK_Node> all_elems) {
        List<UI_Error_Message> Error_Messages = new ArrayList<>();
        for (EPK_Node node : all_elems) {
            if (node instanceof Activating_Function) {
                if (((Activating_Function) node).getStart_Event() == null) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.ACTIVATING_MISSING_SISTERBLOCK, node, null);
                    Error_Messages.add(Message);
                }
            }
        }
        return Error_Messages;
    }

    private List<UI_Error_Message> GraphTest(List<EPK_Node> all_elems) {
        List<UI_Error_Message> Error_Messages = new ArrayList<>();
        for (EPK_Node node : all_elems) {
            if (node instanceof Event && !((Event) node).is_End_Event()) {
                if (node.getNext_Elem().size() > 1) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.EPK_RULE_MISSED_TOO_MANY_SUCESSORS, node, node.getNext_Elem());
                    Messages.add(Message);
                }
                if (node.getNext_Elem().size() < 1 && !(node instanceof Start_Event)) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.EPK_RULE_MISSED_TOO_FEW_SUCESSORS, node, null);
                    Messages.add(Message);
                }
                for (EPK_Node next : node.getNext_Elem()) {
                    if (next instanceof Event || next instanceof Event_Con_Split) {
                        UI_Error_Message Message = new UI_Error_Message(
                                Type_Of_Error.EPK_RULE_MISSED_WRONG_SUCESSORS, node, Collections.singletonList(next));
                        Messages.add(Message);
                    }
                }
            } else if (node instanceof Function && !(node instanceof Activating_Function)) {
                if (node.getNext_Elem().size() > 1) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.EPK_RULE_MISSED_TOO_MANY_SUCESSORS, node, node.getNext_Elem());
                    Error_Messages.add(Message);
                }
                if (node.getNext_Elem().size() < 1) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.EPK_RULE_MISSED_TOO_FEW_SUCESSORS, node, null);
                    Error_Messages.add(Message);
                }
                for (EPK_Node next : node.getNext_Elem()) {
                    if (next instanceof Function) {
                        UI_Error_Message Message = new UI_Error_Message(
                                Type_Of_Error.EPK_RULE_MISSED_WRONG_SUCESSORS, node, Collections.singletonList(next));
                        Error_Messages.add(Message);
                    }
                }
            } else if (node instanceof Activating_Start_Event) {
                if (node.getNext_Elem().size() > 1) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.EPK_RULE_MISSED_TOO_MANY_SUCESSORS, node, node.getNext_Elem());
                    Error_Messages.add(Message);
                }
                for (EPK_Node next : node.getNext_Elem()) {
                    if (next instanceof Event || next instanceof Event_Con_Split) {
                        UI_Error_Message Message = new UI_Error_Message(
                                Type_Of_Error.EPK_RULE_MISSED_WRONG_SUCESSORS, node, Collections.singletonList(next));
                        Error_Messages.add(Message);
                    }
                }
                if (((Activating_Start_Event) node).getActivating_Function() == null) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.ACTIVATING_MISSING_SISTERBLOCK, node, null);
                    Error_Messages.add(Message);
                }
            } else if (node instanceof Activating_Function) {
                if (node.getNext_Elem().size() > 1) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.EPK_RULE_MISSED_TOO_MANY_SUCESSORS, node, node.getNext_Elem());
                    Error_Messages.add(Message);
                }
                if (node.getNext_Elem().size() < 1) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.EPK_RULE_MISSED_TOO_FEW_SUCESSORS, node, null);
                    Error_Messages.add(Message);
                }
                for (EPK_Node next : node.getNext_Elem()) {
                    if (next instanceof Function) {
                        UI_Error_Message Message = new UI_Error_Message(
                                Type_Of_Error.EPK_RULE_MISSED_WRONG_SUCESSORS, node, Collections.singletonList(next));
                        Error_Messages.add(Message);
                    }
                }
                if (((Activating_Function) node).getStart_Event() == null) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.ACTIVATING_MISSING_SISTERBLOCK, node, null);
                    Error_Messages.add(Message);
                }
            } else if (node instanceof Event && ((Event) node).is_End_Event()) {
                if (node.getNext_Elem().size() > 0) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.EPK_RULE_MISSED_TOO_MANY_SUCESSORS, node, node.getNext_Elem());
                    Error_Messages.add(Message);
                }
            } else if (node instanceof Event_Con_Join) {
                if (node.getNext_Elem().size() > 1) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.EPK_RULE_MISSED_TOO_MANY_SUCESSORS, node, node.getNext_Elem());
                    Error_Messages.add(Message);
                }
                if (node.getNext_Elem().size() < 1) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.EPK_RULE_MISSED_TOO_FEW_SUCESSORS, node, node.getNext_Elem());
                    Error_Messages.add(Message);
                }
                if (node.getNext_Elem().size() == 1) {
                    EPK_Node next_Elem = node.getNext_Elem().get(0);
                    List<EPK_Node> PredecessorList = get_PredecessorList(all_elems, node);
                    if (PredecessorList.size() <= 1) {
                        UI_Error_Message Message = new UI_Error_Message(
                                Type_Of_Error.EPK_RULE_MISSED_TOO_FEW_PREDECESSORS, node, PredecessorList);
                        Error_Messages.add(Message);
                    }
                    for (EPK_Node check : PredecessorList) {
                        if (next_Elem instanceof Function && check instanceof Function ||
                                (next_Elem instanceof Event && check instanceof Event)) {
                            List<EPK_Node> Error = new ArrayList<>();
                            Error.add(next_Elem);
                            Error.add(check);
                            UI_Error_Message Message = new UI_Error_Message(
                                    Type_Of_Error.EPK_RULE_MISSED_GATE_SUCC_PRE_TYPE, node, Error);
                            Error_Messages.add(Message);
                        }
                    }
                }
            } else if (node instanceof Event_Con_Split) {
                if (node.getNext_Elem().size() <= 1) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.EPK_RULE_MISSED_TOO_FEW_SUCESSORS, node, node.getNext_Elem());
                    Messages.add(Message);
                }
                List<EPK_Node> PredecessorList = get_PredecessorList(all_elems, node);
                if (PredecessorList.size() > 1) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.EPK_RULE_MISSED_TOO_MANY_PREDECESSORS, node, PredecessorList);
                    Messages.add(Message);
                }
                if (PredecessorList.size() < 1) {
                    UI_Error_Message Message = new UI_Error_Message(
                            Type_Of_Error.EPK_RULE_MISSED_TOO_FEW_PREDECESSORS, node, PredecessorList);
                    Messages.add(Message);
                }
                if (PredecessorList.size() == 1) {
                    EPK_Node Predecessor = PredecessorList.get(0);
                    for (EPK_Node check : node.getNext_Elem()) {
                        if (Predecessor instanceof Function && check instanceof Function ||
                                (Predecessor instanceof Event && check instanceof Event)) {
                            List<EPK_Node> Error = new ArrayList<>();
                            Error.add(Predecessor);
                            Error.add(check);
                            UI_Error_Message Message = new UI_Error_Message(
                                    Type_Of_Error.EPK_RULE_MISSED_GATE_SUCC_PRE_TYPE, node, Error);
                            Messages.add(Message);
                        }
                    }
                }
            }
        }
        return Error_Messages;
    }

    private List<UI_Error_Message> SettingsTest(List<EPK_Node> all_elems) {
        List<UI_Error_Message> Error_Messages = new ArrayList<>();
        for (EPK_Node node : all_elems) {
            if (!((UI_Check_Settings) node).CheckSettings()) {
                UI_Error_Message Message = new UI_Error_Message(
                        Type_Of_Error.MISSING_OR_WRONG_SETTINGS, node, null);
                Error_Messages.add(Message);
            }
        }
        return Error_Messages;
    }

    private List<UI_Error_Message> ReachableTest(List<EPK_Node> all_elems) {
        List<ReachableTest_ListObject> Testlist = new ArrayList<>();
        for (EPK_Node node : all_elems) {
            ReachableTest_ListObject Object = new ReachableTest_ListObject(node, false);
            Testlist.add(Object);
        }

        for (ReachableTest_ListObject Object : Testlist) {
            for (EPK_Node node : Object.getNode().getNext_Elem()) {
                Object.findAndActivate(Testlist, node);
            }
        }
        List<UI_Error_Message> Error_Messages = new ArrayList<>();
        for (ReachableTest_ListObject Object : Testlist) {
            if (!Object.isFound() && !(Object.getNode() instanceof Start_Event)) {
                UI_Error_Message Message = new UI_Error_Message(
                        Type_Of_Error.NOT_REACHABLE, Object.getNode(), null);
                Error_Messages.add(Message);
            }
        }

        return Error_Messages;
    }

    private List<EPK_Node> get_PredecessorList(List<EPK_Node> all_elems, EPK_Node node) {
        List<EPK_Node> Predecessors = new ArrayList<>();
        for (EPK_Node check : all_elems) {
            if (check.getNext_Elem().contains(node)) {
                Predecessors.add(check);
            }
        }
        return Predecessors;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == OK_Button) {
            stage.close();
            System.out.println();
        }
    }

    public void initializeTest(UI_EPK epk, Stage stage) {
        this.EPK = epk;
        this.stage = stage;
        generateTest();
    }
}
