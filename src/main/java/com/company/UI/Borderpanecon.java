package com.company.UI;

import com.company.EPK.*;
import com.company.Print.EventDriven.*;
import com.company.Run.Discrete_Event_Generator;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.External_Event;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
import com.company.Simulation.Simulation_Base.Data.Instance_Printer_Gate;
import com.company.Simulation.Simulation_Base.Data.Instance_Printer_Queue;
import com.company.Simulation.Simulation_Base.Data.Printer_Gate;
import com.company.Simulation.Simulation_Base.Data.Printer_Queue;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Settings;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;
import com.company.UI.EPKUI.*;
import com.company.UI.javafxgraph.fxgraph.cells.UI_View_Gen;
import com.company.UI.javafxgraph.fxgraph.graph.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.company.UI.UI_Button_Active_Type.*;


public class Borderpanecon implements Initializable {

    UI_Button_Active_Type Btn_Type;
    Model model;
    UI_EPK EPK;
    Stage primarystage;
    Stage ThisStage;

    @FXML
    Button ExternalMan;
    @FXML
    Button SimulationMan;
    @FXML
    Button WorkforcesMan;
    @FXML
    Button ResourceMan;
    @FXML
    Button UserMan;
    @FXML
    Button Testing;
    @FXML
    BorderPane Canvaspane;
    @FXML
    VBox Rightbox;
    @FXML
    Button Normal;
    @FXML
    Button Connect;
    @FXML
    Button Event;
    @FXML
    Button Function;
    @FXML
    Button AND_Join;
    @FXML
    Button OR_Join;
    @FXML
    Button XOR_Join;
    @FXML
    Button AND_Split;
    @FXML
    Button OR_Split;
    @FXML
    Button XOR_Split;
    @FXML
    Button Start_Event;
    @FXML
    Button End_Event;
    @FXML
    Button Activating_Start_Event;
    @FXML
    Button Activating_Function;
    @FXML
    Button Delete;
    @FXML
    Button Save_Node;
    @FXML
    Button Generatebtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Delete.setDisable(true);
        Btn_Type = NORMAL;
        Normal.setTooltip(new Tooltip("Auswahl"));
        Connect.setTooltip(new Tooltip("Verbindung"));
        Event.setTooltip(new Tooltip("Event"));
        Function.setTooltip(new Tooltip("Funktion"));
        OR_Join.setTooltip(new Tooltip("OR-Join"));
        OR_Split.setTooltip(new Tooltip("OR-Split"));
        AND_Join.setTooltip(new Tooltip("AND-Join"));
        AND_Split.setTooltip(new Tooltip("AND-Split"));
        XOR_Join.setTooltip(new Tooltip("XOR-Join"));
        XOR_Split.setTooltip(new Tooltip("XOR-Split"));
        Activating_Function.setTooltip(new Tooltip("Aktivierende Funktion"));
        Activating_Start_Event.setTooltip(new Tooltip("Aktivierendes Start Event"));
        Start_Event.setTooltip(new Tooltip("Start Event"));
        End_Event.setTooltip(new Tooltip("End Event"));


    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setThisStage(Stage stage) {
        this.ThisStage = stage;
    }

    public void setEPK(UI_EPK EPK) {
        this.EPK = EPK;
    }

    public BorderPane getCanvaspane() {
        return Canvaspane;
    }

    public VBox getRightbox() {
        return Rightbox;
    }

    public void setRightbox(VBox box) {
        Rightbox = box;
    }

    public void initializeButtons() {

    }

    public void actionPerformed(ActionEvent e) throws Exception {

        if (e.getSource() == Normal) {
            Btn_Type = NORMAL;
            System.out.println("Normal");
        } else if (e.getSource() == Event) {
            Btn_Type = EVENT;
            System.out.println("Event");
        } else if (e.getSource() == Function) {
            Btn_Type = FUNCTION;
            System.out.println("Function");
        } else if (e.getSource() == AND_Join) {
            Btn_Type = AND_JOIN;
            System.out.println("AND_Join");
        } else if (e.getSource() == OR_Join) {
            Btn_Type = OR_JOIN;
            System.out.println("OR_Join");
        } else if (e.getSource() == XOR_Join) {
            Btn_Type = XOR_JOIN;
            System.out.println("XOR_Join");
        } else if (e.getSource() == AND_Split) {
            Btn_Type = AND_SPLIT;
            System.out.println("AND_Split");
        } else if (e.getSource() == OR_Split) {
            Btn_Type = OR_SPLIT;
            System.out.println("OR_Split");
        } else if (e.getSource() == XOR_Split) {
            Btn_Type = XOR_SPLIT;
            System.out.println("XOR_Split");
        } else if (e.getSource() == Start_Event) {
            Btn_Type = START_EVENT;
            System.out.println("Start_Event");
        } else if (e.getSource() == End_Event) {
            Btn_Type = END_EVENT;
            System.out.println("End_Event");
        } else if (e.getSource() == Activating_Start_Event) {
            Btn_Type = ACTIVATING_START_EVENT;
            System.out.println("Activating_Start_Event");
        } else if (e.getSource() == Activating_Function) {
            Btn_Type = ACTIVATING_FUNCTION;
            System.out.println("Activating_Function");
        } else if (e.getSource() == Connect) {
            Btn_Type = CONNECT;
            System.out.println("Connect");
        } else if (e.getSource() == Testing) {

            URL url = new File("src/main/java/com/company/UI/javafxgraph/Testing_UI.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            Parent root = loader.load();
            Scene scene = new Scene(root, 700, 500);

            UI_TEST_EPK Testing_UI = (UI_TEST_EPK) loader.getController();


            Stage newWindow = new Stage();
            Testing_UI.initializeTest(EPK, newWindow);
            newWindow.setTitle("EPCSim Test");
            newWindow.setScene(scene);
            newWindow.setResizable(false);
            newWindow.initOwner(primarystage);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.setX(primarystage.getX() + 200);
            newWindow.setY(primarystage.getY() + 100);
            newWindow.show();

        } else if (e.getSource() == UserMan) {

            URL url = new File("src/main/java/com/company/UI/javafxgraph/User_UI.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 500);

            UI_USER_MANAGEMENT USER_UI = (UI_USER_MANAGEMENT) loader.getController();


            Stage newWindow = new Stage();
            USER_UI.setEPK(EPK);
            USER_UI.setMainStage(ThisStage);
            USER_UI.setThisstage(newWindow);
            newWindow.setTitle("EPCSim User-Management");
            newWindow.setScene(scene);
            newWindow.setResizable(false);
            newWindow.initOwner(primarystage);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.setX(primarystage.getX() + 200);
            newWindow.setY(primarystage.getY() + 100);
            USER_UI.generateUI();
            newWindow.show();

        } else if (e.getSource() == ResourceMan) {
            URL url = new File("src/main/java/com/company/UI/javafxgraph/Resource_UI.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 500);

            UI_RESOURCE_MANAGEMENT RESOURCE_UI = (UI_RESOURCE_MANAGEMENT) loader.getController();
            Stage newWindow = new Stage();
            RESOURCE_UI.setEPK(EPK);
            RESOURCE_UI.setMainStage(ThisStage);
            RESOURCE_UI.setThisstage(newWindow);
            newWindow.setTitle("EPCSim Resource-Management");
            newWindow.setScene(scene);
            newWindow.setResizable(false);
            newWindow.initOwner(primarystage);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.setX(primarystage.getX() + 200);
            newWindow.setY(primarystage.getY() + 100);
            RESOURCE_UI.generateUI();
            newWindow.show();
        } else if (e.getSource() == WorkforcesMan) {
            URL url = new File("src/main/java/com/company/UI/javafxgraph/Workforce_UI.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 500);

            UI_WORKFORCE_MANAGEMENT WORKFORCE_UI = (UI_WORKFORCE_MANAGEMENT) loader.getController();
            Stage newWindow = new Stage();
            WORKFORCE_UI.setEPK(EPK);
            WORKFORCE_UI.setMainStage(ThisStage);
            WORKFORCE_UI.setThisstage(newWindow);
            newWindow.setTitle("EPCSim Workforce-Management");
            newWindow.setScene(scene);
            newWindow.setResizable(false);
            newWindow.initOwner(primarystage);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.setX(primarystage.getX() + 200);
            newWindow.setY(primarystage.getY() + 100);
            WORKFORCE_UI.generateUI();
            newWindow.show();
        } else if (e.getSource() == SimulationMan) {

            URL url = new File("src/main/java/com/company/UI/javafxgraph/Simulation_UI.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200, 800);

            UI_Settings Settings = EPK.getUI_Settings();
            if (Settings == null) {
                Settings = new UI_Settings();
                EPK.setUI_Settings(Settings);
            }

            UI_SIMULATION_MANAGEMENT SIMULATION_UI = (UI_SIMULATION_MANAGEMENT) loader.getController();
            Stage newWindow = new Stage();
            SIMULATION_UI.setEPK(EPK);
            SIMULATION_UI.setSettings(Settings);
            SIMULATION_UI.setMainStage(ThisStage);
            SIMULATION_UI.setThisstage(newWindow);
            newWindow.setTitle("EPCSim Workforce-Management");
            newWindow.setScene(scene);
            newWindow.setResizable(false);
            newWindow.initOwner(primarystage);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.setX(primarystage.getX() + 200);
            newWindow.setY(primarystage.getY() + 100);
            SIMULATION_UI.generateUI();
            newWindow.show();
        } else if (e.getSource() == ExternalMan) {
            URL url = new File("src/main/java/com/company/UI/javafxgraph/ExternalEvent_UI.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200, 900);

            UI_EXTERNAL_EVENT_MANAGER Manager_UI = (UI_EXTERNAL_EVENT_MANAGER) loader.getController();


            Stage newWindow = new Stage();
            Manager_UI.setEPK(EPK);
            Manager_UI.setMainStage(ThisStage);
            Manager_UI.setThisstage(newWindow);
            newWindow.setTitle("EPCSim External-Event-Management");
            newWindow.setScene(scene);
            newWindow.setResizable(false);
            newWindow.initOwner(primarystage);
            newWindow.setX(primarystage.getX() + 200);
            newWindow.setY(primarystage.getY() + 100);
            Manager_UI.generateUI();
            newWindow.show();

        } else if (e.getSource() == Delete) {
            UI_View_Gen Active_Elem = EPK.getActive_Elem();
            EPK.getAll_Elems().remove(Active_Elem.getNodeView());

            for (EPK_Node node : EPK.getAll_Elems()) {
                node.getNext_Elem().remove(Active_Elem.getEPKNode());
                if (node instanceof Activating_Function) {
                    if (((Activating_Function) node).getStart_Event() != null && ((Activating_Function) node).getStart_Event().equals(Active_Elem.getEPKNode())) {
                        ((Activating_Function) node).setStart_Event(null);
                        EPK.getAll_Activating_Functions().remove(node);
                    }
                } else if (node instanceof Activating_Start_Event) {
                    if (((Activating_Start_Event) node).getActivating_Function() != null &&
                            ((Activating_Start_Event) node).getActivating_Function().equals(Active_Elem.getEPKNode())) {
                        ((Activating_Start_Event) node).setFunction(null);
                        EPK.getAll_Activating_Start_Events().remove(node);
                    }
                } else if (node instanceof Event_Con_Join) {
                    ((Event_Con_Join) node).getPrevious_Elements().remove(Active_Elem.getEPKNode());

                }
            }

            model.removebySourceEdge(Active_Elem.getCellId());
            model.removebyTargetEdge(Active_Elem.getCellId());
            model.getRemovedCells().add(model.getCellByID(Active_Elem.getCellId()));
            model.getGraph().endUpdate();


        } else if (e.getSource() == Save_Node) {
            UI_View_Gen Active_Elem = EPK.getActive_Elem();
            ((UI_Instantiable) Active_Elem.getNodeView()).save_Settings();
        } else if (e.getSource() == Generatebtn) {
            for (EPK_Node Node : EPK.getAll_Elems()) {
                ((UI_Instantiable) Node).save_Settings();
            }
            List<EPK_Node> Final_List = new ArrayList<>();
            List<Resource> Final_Resource = new ArrayList<>();
            List<User> Final_User = new ArrayList<>();
            List<Workforce> Final_Workforce = new ArrayList<>();
            List<User> UI_Users = EPK.getAll_Users();
            List<Resource> UI_Resource = EPK.getAll_Resources();
            List<Workforce> UI_Workforces = EPK.getAll_Workforces();
            List<EPK_Node> UI_All_Nodes = EPK.getAll_Elems();
            List<Start_Event> Final_Start_Events = new ArrayList<>();
            //INSTANTIATE SETTINGS


            UI_Settings UISettings = EPK.getUI_Settings();
            Settings settings = new Settings();
            settings.setBeginTime(UISettings.getBeginTime());
            settings.setEndTime(UISettings.getEndTime());
            settings.setDecide_Event_choosing(UISettings.getDecide_Event_choosing());
            settings.setMax_RuntimeDays(UISettings.getMax_RuntimeDays());
            settings.setOptimal_User_Layout(UISettings.isOptimal_User_Layout());
            settings.setPrint_Only_Function(UISettings.isPrint_Only_Function());
            settings.setGet_Only_Start_Finishable_Functions(UISettings.isOnly_Start_Finishable_Functions());


            /*List<External_Event> external_events = new ArrayList<>();
            UI_EXTERNAL_EVENT_MANAGER UI_External_Events = EPK.getUI_External_Events();
            external_events.addAll(UI_External_Events.getList());*/

            //INSTANTIATE ALL UNITS
            for (Resource Res : UI_Resource) {
                Resource newRes = new Resource(Res.getName(), Res.getCount(), Res.getID());
                Final_Resource.add(newRes);
            }
            for (Workforce work : UI_Workforces) {
                Workforce newWork = new Workforce(work.getW_ID(), work.getPermission());
                Final_Workforce.add(newWork);
            }
            for (User user : UI_Users) {
                User newUser = new User(user.getFirst_Name(), user.getLast_Name(), user.getP_ID(), user.getEfficiency());
                Final_User.add(newUser);
            }


            //INSTANTIATE ALL NODES WITHOUT BINDINGS
            for (EPK_Node Node : UI_All_Nodes) {

                if (Node instanceof Function && !(Node instanceof Activating_Function)) {
                    if (((Function) Node).isDeterministic()) {
                        Function newFunc = new Function(null, Node.getID(), ((Function) Node).getFunction_tag(),
                                ((Function) Node).isConcurrently(), ((Function) Node).getNeeded_Resources(), null,
                                ((Function) Node).getWorkingTime().getHours(), ((Function) Node).getWorkingTime().getMinutes(), ((Function) Node).getWorkingTime().getSeconds());
                        Final_List.add(newFunc);
                    } else {
                        Function newFunc = new Function(null, Node.getID(), ((Function) Node).getFunction_tag(),
                                ((Function) Node).isConcurrently(), ((Function) Node).getNeeded_Resources(), null,
                                ((Function) Node).getMin_Workingtime().getHours(), ((Function) Node).getMin_Workingtime().getMinutes(), ((Function) Node).getMin_Workingtime().getSeconds(),
                                ((Function) Node).getMax_Workingtime().getHours(), ((Function) Node).getMax_Workingtime().getMinutes(), ((Function) Node).getMax_Workingtime().getSeconds(),
                                ((Function) Node).getMean_Value().getHours(), ((Function) Node).getMean_Value().getMinutes(), ((Function) Node).getMean_Value().getSeconds(),
                                ((Function) Node).getTime_Standart_Deviation().getHours(), ((Function) Node).getTime_Standart_Deviation().getMinutes(), ((Function) Node).getTime_Standart_Deviation().getSeconds());
                        Final_List.add(newFunc);
                    }
                    //NEXTELEMBINDING
                } else if (Node instanceof Event && !(Node instanceof Activating_Start_Event) && !(Node instanceof Start_Event)) {
                    Event newEvent = new Event(null, Node.getID(), ((Event) Node).getEvent_Tag(),
                            ((Event) Node).is_Start_Event(), ((Event) Node).is_End_Event());
                    Final_List.add(newEvent);
                    //NEXTELEMBINDING
                } else if (Node instanceof Activating_Start_Event) {
                    Activating_Start_Event newActivateStartEvent = new Activating_Start_Event(null, Node.getID(),
                            null, null, ((Event) Node).getEvent_Tag(), true);
                    Final_List.add(newActivateStartEvent);
                    //ACTIVATE FUNCTION/ NEXTELEMBINDING
                } else if (Node instanceof Activating_Function) {
                    if (((Function) Node).isDeterministic()) {
                        Activating_Function newActivatingFunction = new Activating_Function(((Function) Node).getFunction_tag(),
                                ((Activating_Function) Node).getInstantiate_Time(), ((Activating_Function) Node).getChance_for_instantiation(), ((Function) Node).getWorkingTime(), ((Function) Node).getFunction_type(), Node.getID(),
                                null, null, ((Activating_Function) Node).getDecisionType());
                        newActivatingFunction.setNeeded_Resources(((Activating_Function) Node).getNeeded_Resources());
                        Final_List.add(newActivatingFunction);
                    } else {
                        Activating_Function newActivatingFunction = new Activating_Function(((Function) Node).getFunction_tag(),
                                ((Activating_Function) Node).getInstantiate_Time(), ((Activating_Function) Node).isConcurrently(),
                                ((Activating_Function) Node).getNeeded_Resources(), ((Activating_Function) Node).getChance_for_instantiation(),
                                ((Function) Node).getMin_Workingtime().getHours(), ((Function) Node).getMin_Workingtime().getMinutes(), ((Function) Node).getMin_Workingtime().getSeconds(),
                                ((Function) Node).getMax_Workingtime().getHours(), ((Function) Node).getMax_Workingtime().getMinutes(), ((Function) Node).getMax_Workingtime().getSeconds(),
                                ((Function) Node).getMean_Value().getHours(), ((Function) Node).getMean_Value().getMinutes(), ((Function) Node).getMean_Value().getSeconds(),
                                ((Function) Node).getTime_Standart_Deviation().getHours(), ((Function) Node).getTime_Standart_Deviation().getMinutes(), ((Function) Node).getTime_Standart_Deviation().getSeconds(),
                                ((Activating_Function) Node).getMin_Instantiate_Time().getHours(), ((Activating_Function) Node).getMin_Instantiate_Time().getMinutes(), ((Activating_Function) Node).getMin_Instantiate_Time().getSeconds(),
                                ((Activating_Function) Node).getMax_Instantiate_Time().getHours(), ((Activating_Function) Node).getMax_Instantiate_Time().getMinutes(), ((Activating_Function) Node).getMax_Instantiate_Time().getSeconds(),
                                ((Activating_Function) Node).getMean_Instantiate_Time().getHours(), ((Activating_Function) Node).getMean_Instantiate_Time().getMinutes(), ((Activating_Function) Node).getMean_Instantiate_Time().getSeconds(),
                                ((Activating_Function) Node).getStandard_Distribution_Instantiate_Time().getHours(), ((Activating_Function) Node).getStandard_Distribution_Instantiate_Time().getMinutes(), ((Activating_Function) Node).getStandard_Distribution_Instantiate_Time().getSeconds(),
                                ((Function) Node).getFunction_type(), Node.getID(),
                                null, null, ((Activating_Function) Node).getDecisionType());
                        newActivatingFunction.setNeeded_Resources(((Activating_Function) Node).getNeeded_Resources());
                        Final_List.add(newActivatingFunction);
                    }
                    //NEXTELEM;STARTEVENTBINDING
                } else if (Node instanceof Event_Con_Join) {
                    Event_Con_Join newCon_Join = new Event_Con_Join(null, Node.getID(), ((Event_Con_Join) Node).getContype());
                    Final_List.add(newCon_Join);
                    //MAPPEDBRANCHEDELEMENTS Binding
                } else if (Node instanceof Event_Con_Split) {
                    Event_Con_Split newCon_Split = new Event_Con_Split(null, Node.getID(),
                            ((Event_Con_Split) Node).getContype(), ((Event_Con_Split) Node).getDecide_Type(), ((Event_Con_Split) Node).getChances_List());
                    Final_List.add(newCon_Split);
                } else if (Node instanceof Start_Event) {
                    Start_Event newStart = new Start_Event(((Start_Event) Node).getStart_event_type(), Node.getID(),
                            null, ((Start_Event) Node).getTo_Instantiate(),
                            null, ((Start_Event) Node).getEvent_Tag(), ((Start_Event) Node).is_Start_Event());
                    Final_List.add(newStart);
                    Final_Start_Events.add(newStart);
                }
            }

            //Instantiate Bindings
            for (EPK_Node newNode : Final_List) {
                EPK_Node UI_Node = null;
                for (EPK_Node uiNode : UI_All_Nodes) {
                    if (newNode.getID() == uiNode.getID()) {
                        UI_Node = uiNode;
                        break;
                    }
                }
                if (UI_Node != null) {
                    List<EPK_Node> next_Elems = new ArrayList<>();
                    List<EPK_Node> UI_Next_elems = UI_Node.getNext_Elem();
                    if (UI_Next_elems != null && !UI_Next_elems.isEmpty()) {
                        for (EPK_Node UI_Next_elem : UI_Next_elems) {
                            for (EPK_Node new_elem : Final_List) {
                                if (UI_Next_elem.getID() == new_elem.getID()) {
                                    next_Elems.add(new_elem);
                                }
                            }
                        }
                    }
                    if (!next_Elems.isEmpty()) {
                        newNode.setNext_Elem(next_Elems);
                    }

                    if (newNode instanceof Function && !(newNode instanceof Activating_Function)) {
                        for (Workforce force : UI_Workforces) {
                            List<Function> Used_In = force.getUsed_in();
                            for (Function Used_In_UI : Used_In) {
                                if (Used_In_UI.getID() == newNode.getID()) {
                                    for (Workforce newforce : Final_Workforce) {
                                        if (newforce.getW_ID() == force.getW_ID()) {
                                            newforce.getUsed_in().add((Function) newNode);
                                            ((Function) newNode).getNeeded_Workforce().add(newforce);
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    } else if (newNode instanceof Activating_Start_Event) {
                        Activating_Function Function = ((Activating_Start_Event) UI_Node).getActivating_Function();
                        if (Function != null) {
                            for (EPK_Node Node : Final_List) {
                                if (Node.getID() == Function.getID()) {
                                    ((Activating_Start_Event) newNode).setFunction(((Activating_Function) Node));
                                    break;
                                }
                            }
                        } else {
                            ((Activating_Start_Event) newNode).setFunction(null);
                        }
                    } else if (newNode instanceof Activating_Function) {
                        Activating_Start_Event Event = ((Activating_Function) UI_Node).getStart_Event();
                        if (Event != null) {
                            for (EPK_Node Node : Final_List) {
                                if (Node.getID() == Event.getID()) {
                                    ((Activating_Function) newNode).setStart_Event((Activating_Start_Event) Node);
                                    break;
                                }

                            }
                        } else {
                            ((Activating_Function) newNode).setStart_Event(null);
                        }
                        for (Workforce force : UI_Workforces) {
                            List<Function> Used_In = force.getUsed_in();
                            for (Function Used_In_UI : Used_In) {
                                if (Used_In_UI.getID() == newNode.getID()) {
                                    for (Workforce newforce : Final_Workforce) {
                                        if (newforce.getW_ID() == force.getW_ID()) {
                                            newforce.getUsed_in().add((Activating_Function) newNode);
                                            ((Activating_Function) newNode).getNeeded_Workforce().add(newforce);
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        }

                    } else if (newNode instanceof Event_Con_Join) {

                        List<EPK_Node> MappedElems = ((Event_Con_Join) UI_Node).getPrevious_Elements();
                        List<EPK_Node> newMap = new ArrayList<>();
                        for (EPK_Node mapnode : MappedElems) {
                            for (EPK_Node Node : Final_List) {
                                if (Node.getID() == mapnode.getID()) {
                                    newMap.add(Node);
                                }
                            }
                        }
                            if (!newMap.isEmpty()) {
                                ((Event_Con_Join) newNode).setPrevious_Elements(newMap);
                            }
                    }
                }
            }
            for (Resource Res : Final_Resource) {
                for (Resource UI_Res : UI_Resource) {
                    if (Res.getID() == UI_Res.getID()) {
                        List<Function> UI_Res_Func = UI_Res.getUsed_In();
                        for (Function f : UI_Res_Func) {
                            for (EPK_Node newfunc : Final_List) {
                                if ((newfunc instanceof Function && newfunc.getID() == f.getID())) {
                                    Res.add_Used_In((Function) newfunc);
                                }
                            }
                        }
                    }
                }
            }
            for (User newuser : Final_User) {
                for (User oldUser : UI_Users) {
                    if (newuser.getP_ID() == oldUser.getP_ID()) {
                        List<Workforce> Granted = oldUser.getWorkforces();
                        for (Workforce w : Granted) {
                            for (Workforce newforce : Final_Workforce) {
                                if (newforce.getW_ID() == w.getW_ID()) {
                                    newforce.getGranted_to().add(newuser);
                                    newuser.getWorkforces().add(newforce);
                                }
                            }
                        }
                    }
                }
            }


            List<Print_Event_Driven_File> PrinterList = new ArrayList<>();
            for (EPK_Node Node : Final_List) {
                List<Connected_Elem_Print> Next_elems = new ArrayList<>();
                for (EPK_Node Next : Node.getNext_Elem()) {
                    Connected_Elem_Print Next_Single_Elem = null;
                    if (Next instanceof Is_Tagged) {
                        Next_Single_Elem = new Connected_Elem_Print(Next.getID(), ((Is_Tagged) Next).getTag());
                    } else {
                        Next_Single_Elem = new Connected_Elem_Print(Next.getID(), "Gate: " + Next.getID());
                    }
                    Next_elems.add(Next_Single_Elem);
                }

                if (Node instanceof Event && !(Node instanceof Activating_Start_Event)
                        && !(Node instanceof Start_Event) && !(((Event) Node).is_End_Event())) {

                    Print_Event Ev = new Print_Event(Node.getID(), Next_elems, ((Event) Node).getEvent_Tag(), ((Event) Node).is_Start_Event(), ((Event) Node).is_End_Event());
                    PrinterList.add(Ev);
                } else if (Node instanceof Event && !(Node instanceof Activating_Start_Event)
                        && !(Node instanceof Start_Event) && (((Event) Node).is_End_Event())) {

                    Print_End_Event Ev = new Print_End_Event(Node.getID(), Next_elems, ((Event) Node).getEvent_Tag());
                    PrinterList.add(Ev);
                } else if (Node instanceof Event && (Node instanceof Start_Event)) {
                    Print_Start_Event Ev = new Print_Start_Event(Node.getID(), Next_elems,
                            ((Start_Event) Node).getEvent_Tag(),
                            ((Start_Event) Node).getStart_event_type(),
                            ((Start_Event) Node).getTo_Instantiate());
                    PrinterList.add(Ev);
                } else if (Node instanceof Function && !(Node instanceof Activating_Function)) {
                    List<Connected_Resource_Print> Resource_List = new ArrayList<>();
                    List<Connected_Workforce_Print> Workforce_List = new ArrayList<>();
                    for (Resource needed : ((Function) Node).getNeeded_Resources()) {
                        Connected_Resource_Print res = new Connected_Resource_Print(needed.getID(), needed.getCount(), needed.getName());
                        Resource_List.add(res);
                    }
                    for (Workforce needed : ((Function) Node).getNeeded_Workforce()) {
                        Connected_Workforce_Print force = new Connected_Workforce_Print(needed.getW_ID(), needed.getPermission());
                        Workforce_List.add(force);
                    }
                    Print_Function Ev = new Print_Function(Node.getID(), Next_elems,
                            ((Function) Node).getTag(),
                            ((Function) Node).getFunction_type(),
                            ((Function) Node).isConcurrently(),
                            Resource_List, Workforce_List,
                            ((Function) Node).getDeterministicWorkingTime());
                    PrinterList.add(Ev);
                } else if (Node instanceof Activating_Function) {
                    List<Connected_Resource_Print> Resource_List = new ArrayList<>();
                    List<Connected_Workforce_Print> Workforce_List = new ArrayList<>();
                    for (Resource needed : ((Function) Node).getNeeded_Resources()) {
                        Connected_Resource_Print res = new Connected_Resource_Print(needed.getID(), needed.getCount(), needed.getName());
                        Resource_List.add(res);
                    }
                    for (Workforce needed : ((Function) Node).getNeeded_Workforce()) {
                        Connected_Workforce_Print force = new Connected_Workforce_Print(needed.getW_ID(), needed.getPermission());
                        Workforce_List.add(force);
                    }
                    Connected_Elem_Print Start_Event = null;
                    if (((Activating_Function) Node).getStart_Event() != null) {
                        Start_Event = new Connected_Elem_Print(((Activating_Function) Node).getStart_Event().getID(), ((Activating_Function) Node).getStart_Event().getEvent_Tag());
                    } else {
                        Start_Event = new Connected_Elem_Print(0, "");

                    }
                    Print_Activating_Function Ev = new Print_Activating_Function(Node.getID(), Next_elems,
                            ((Activating_Function) Node).getTag(),
                            ((Activating_Function) Node).getFunction_type(),
                            ((Activating_Function) Node).isConcurrently(),
                            Resource_List, Workforce_List,
                            ((Activating_Function) Node).getDeterministicWorkingTime(),
                            Start_Event,
                            ((Activating_Function) Node).getInstantiate_Time(),
                            ((Activating_Function) Node).getDecisionType());
                    PrinterList.add(Ev);
                } else if (Node instanceof Activating_Start_Event) {
                    Connected_Elem_Print Activate_Function = null;
                    if (((Activating_Start_Event) Node).getActivating_Function() != null) {
                        Activate_Function = new Connected_Elem_Print(
                                ((Activating_Start_Event) Node).getActivating_Function().getID(),
                                ((Activating_Start_Event) Node).getActivating_Function().getFunction_tag());
                    } else {
                        Activate_Function = new Connected_Elem_Print(0, "");
                    }
                    Print_Activating_Start_Event Ev = new Print_Activating_Start_Event(Node.getID(), Next_elems, ((Activating_Start_Event) Node).getEvent_Tag(), true, false, ((Activating_Start_Event) Node).getStart_event_type(), Activate_Function);
                    PrinterList.add(Ev);
                } else if (Node instanceof Event_Con_Join) {
                    List<Connected_Elem_Print> Previous_Elems = new ArrayList<>();
                    List<Connected_Node_Map_Print> Mapped_Branched_Elements = new ArrayList<>();
                    for (EPK_Node Mapped : ((Event_Con_Join) Node).getPrevious_Elements()) {
                        Connected_Elem_Print mappedNode = null;
                        if (Mapped instanceof Is_Tagged) {
                            mappedNode = new Connected_Elem_Print(Mapped.getID(), ((Is_Tagged) Mapped).getTag());
                        } else {
                            mappedNode = new Connected_Elem_Print(Mapped.getID(), "Gate: " + Mapped.getID());
                        }
                        Previous_Elems.add(mappedNode);
                    }

                    Print_Event_Con_Join Ev = new Print_Event_Con_Join(Node.getID(), Next_elems, Mapped_Branched_Elements, Previous_Elems, ((Event_Con_Join) Node).getContype());
                    PrinterList.add(Ev);
                } else if (Node instanceof Event_Con_Split) {
                    Print_Event_Con_Split Ev = new Print_Event_Con_Split(Node.getID(), Next_elems, ((Event_Con_Split) Node).getDecide_Type(), ((Event_Con_Split) Node).isIs_Event_Driven(), ((Event_Con_Split) Node).getContype());
                    PrinterList.add(Ev);
                }


            }
            Printer_Gate pg = Printer_Gate.get_Printer_Gate();
            pg.setNodelistToPrint(PrinterList);
            Printer_Queue printer_queue = new Printer_Queue();
            Thread PQ = new Thread(printer_queue);
            printer_queue.setT(PQ);
            PQ.start();

            Instance_Printer_Gate instance_printer_gate = Instance_Printer_Gate.getInstance_Printer_Gate();
            Instance_Printer_Queue I_PQ = new Instance_Printer_Queue();
            Thread I_Pq_T = new Thread(I_PQ);
            I_PQ.setT(I_Pq_T);
            I_Pq_T.start();

            Settings Final_settings = new Settings();
            Final_settings.setBeginTime(settings.getBeginTime());
            Final_settings.setEndTime(settings.getEndTime());
            Final_settings.setDecide_Event_choosing(settings.getDecide_Event_choosing());
            Final_settings.setGet_Only_Start_Finishable_Functions(settings.isGet_Only_Start_Finishable_Functions());
            Final_settings.setMax_RuntimeDays(settings.getMax_RuntimeDays());
            Final_settings.setOptimal_User_Layout(settings.isOptimal_User_Layout());
            Final_settings.setPrint_Only_Function(settings.isPrint_Only_Function());
            Final_settings.setNumber_Instances_Per_Day(settings.getNumber_Instances_Per_Day());
            Final_settings.setStartEventType(settings.getStartEventType());
            EPK epk = new EPK(Final_List, Final_Start_Events);

            List<List<External_Event>> external_Events = EPK.getExternal_Events_by_Day();

            Discrete_Event_Generator Generator = new Discrete_Event_Generator(epk, Final_settings, Final_User, Final_Resource, external_Events);
            Generator.run();
            I_PQ.setNot_killed(false);
            printer_queue.setNot_killed(false);


        }
    }

    public void activateDeleteButton() {
        Delete.setDisable(false);
    }

    public void deactivateDeleteButton() {
        Delete.setDisable(true);
    }

    public UI_Button_Active_Type getBtn_Type() {
        return Btn_Type;
    }

    public void setBtn_Type(UI_Button_Active_Type Type) {
        Btn_Type = Type;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primarystage = primaryStage;
    }
}

