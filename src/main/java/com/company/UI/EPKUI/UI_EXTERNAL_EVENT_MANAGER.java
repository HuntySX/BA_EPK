package com.company.UI.EPKUI;

import com.company.EPK.Workforce;
import com.company.Enums.Option_Event_Choosing;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.*;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;
import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.controls.SimpleRadioButtonControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.company.UI.EPKUI.UI_External_Event_Manager_Status.*;


public class UI_EXTERNAL_EVENT_MANAGER implements Initializable {
    @FXML
    Button OK_Button;
    @FXML
    VBox EDIT_Event_Button;
    @FXML
    VBox Choosebox;
    @FXML
    VBox EditResourceBox;
    @FXML
    VBox EditUserBox;

    private UI_EPK EPK;
    private Stage Mainstage;
    private Stage this_stage;
    private List<List<External_Event>> External_Events_by_Day;
    private List<User> Users;
    private List<Resource> Resources;
    private List<Workforce> Workforces;
    private SingleSelectionField<External_Event> Choose_Event;
    private SingleSelectionField<Option_Event_Choosing> Event_Choosing;
    private SingleSelectionField<Integer> Choose_Day;
    private SingleSelectionField<Integer> Show_Day;
    private IntegerField Event_Time_Second;
    private IntegerField Event_Time_Minute;
    private IntegerField Event_Time_Hour;
    private IntegerField RuntimeDays;
    private FormRenderer BooleanSettings;
    private FormRenderer Begintime;
    private FormRenderer Endingtime;
    private FormRenderer RunningDays;
    private FormRenderer Choose_UI;
    private Integer chosenDay;
    private External_Event EditableExternalEvent;
    private UI_External_Event_Manager_Status UI_Status;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chosenDay = 0;
    }

    public void setEPK(UI_EPK EPK) {
        this.EPK = EPK;
    }

    public void setMainStage(Stage stage) {
        this.Mainstage = stage;
    }

    public void setThisstage(Stage stage) {
        this_stage = stage;
    }

    public UI_External_Event_Manager_Status getUI_Status() {
        return UI_Status;
    }

    public void setUI_Status(UI_External_Event_Manager_Status UI_Status) {
        this.UI_Status = UI_Status;
    }

    public void generateUI() {
        Choosebox.getChildren().clear();
        InstantiateStandardUI();
    }

    private void InstantiateStandardUI() {
        Choosebox.getChildren().clear();
        EditResourceBox.getChildren().clear();
        EditUserBox.getChildren().clear();
        Integer countday = EPK.getExternal_Events_by_Day().size() - 1;
        External_Events_by_Day = EPK.getExternal_Events_by_Day();
        Users = EPK.getAll_Users();
        Resources = EPK.getAll_Resources();

        List<External_Event_Activation_Property> User_Activation_Status = new ArrayList<>();
        User_Activation_Status.add(External_Event_Activation_Property.Activate);
        User_Activation_Status.add(External_Event_Activation_Property.Deactivate);

        SingleSelectionField UserActivation = Field.ofSingleSelectionType(User_Activation_Status).editable(false).label("Eventtype");

        SingleSelectionField Userslist = Field.ofSingleSelectionType(Users).editable(false).label("Selected User");
        IntegerField UserHour = null;
        IntegerField UserMinute = null;
        IntegerField UserSecond = null;

        if (EditableExternalEvent != null && UI_Status == EditUser) {
            SimpleIntegerProperty PropHour = new SimpleIntegerProperty(EditableExternalEvent.getTime().getHours());
            SimpleIntegerProperty PropMinute = new SimpleIntegerProperty(EditableExternalEvent.getTime().getMinutes());
            SimpleIntegerProperty PropSecond = new SimpleIntegerProperty(EditableExternalEvent.getTime().getSeconds());

            UserHour = Field.ofIntegerType(PropHour).editable(false).label("Eventhour");
            UserMinute = Field.ofIntegerType(PropMinute).editable(false).label("Eventminute");
            UserSecond = Field.ofIntegerType(PropSecond).editable(false).label("Eventsecond");
        } else {
            UserHour = Field.ofIntegerType(0).editable(false).label("Eventhour");
            UserMinute = Field.ofIntegerType(0).editable(false).label("Eventminute");
            UserSecond = Field.ofIntegerType(0).editable(false).label("Eventsecond");
        }
        List<External_Event_Activation_Property> Resource_Activation_Status = new ArrayList<>();
        Resource_Activation_Status.add(External_Event_Activation_Property.Increase);
        Resource_Activation_Status.add(External_Event_Activation_Property.Decrease);
        SingleSelectionField ResourceActivation = Field.ofSingleSelectionType(Resource_Activation_Status).editable(false).label("Eventtype");

        SingleSelectionField Resourcelist = Field.ofSingleSelectionType(Resources).editable(false).editable(false).label("Selected resource");

        IntegerField ResHour = null;
        IntegerField ResMinute = null;
        IntegerField ResSecond = null;
        IntegerField Count = null;

        if (EditableExternalEvent != null && UI_Status == EditResource) {
            SimpleIntegerProperty PropHour = new SimpleIntegerProperty(EditableExternalEvent.getTime().getHours());
            SimpleIntegerProperty PropMinute = new SimpleIntegerProperty(EditableExternalEvent.getTime().getMinutes());
            SimpleIntegerProperty PropSecond = new SimpleIntegerProperty(EditableExternalEvent.getTime().getSeconds());
            SimpleIntegerProperty PropCount = null;
            if (EditableExternalEvent instanceof Resource_Activating_External_Event) {

                PropCount = new SimpleIntegerProperty(((Resource_Activating_External_Event) EditableExternalEvent).getCount());
            } else if (EditableExternalEvent instanceof Resource_Deactivating_External_Event) {

                PropCount = new SimpleIntegerProperty(((Resource_Deactivating_External_Event) EditableExternalEvent).getCount());
            }
            ResHour = Field.ofIntegerType(PropHour).editable(false).label("Eventhour");
            ResMinute = Field.ofIntegerType(PropMinute).editable(false).label("Eventminute");
            ResSecond = Field.ofIntegerType(PropSecond).editable(false).label("Eventsecond");
            Count = Field.ofIntegerType(PropCount).editable(false).label("Count");
        } else {
            ResHour = Field.ofIntegerType(0).editable(false).label("Eventhour");
            ResMinute = Field.ofIntegerType(0).editable(false).label("Eventminute");
            ResSecond = Field.ofIntegerType(0).editable(false).label("Eventsecond");
            Count = Field.ofIntegerType(0).editable(false).label("Count");
        }

        ButtonBar SaveCancelExtEvBar = new ButtonBar();
        Button SaveEv = new Button("Save event");
        Button CancelEv = new Button("Cancel");
        Button DeleteEv = new Button("Remove event");
        SaveEv.setDisable(true);
        CancelEv.setDisable(true);
        DeleteEv.setDisable(true);
        SaveCancelExtEvBar.getButtons().add(DeleteEv);
        SaveCancelExtEvBar.getButtons().add(SaveEv);
        SaveCancelExtEvBar.getButtons().add(CancelEv);

        FormRenderer ResourceRender = new FormRenderer(Form.of(Group.of(Resourcelist, ResourceActivation.render(new SimpleRadioButtonControl()), ResHour, ResMinute, ResSecond, Count)));
        FormRenderer UserRender = new FormRenderer(Form.of(Group.of(Userslist, UserActivation.render(new SimpleRadioButtonControl()), UserHour, UserMinute, UserSecond)));

        List<Integer> ChooseDayProperty = new ArrayList<>();
        for (int i = 0; i <= countday; i++) {
            ChooseDayProperty.add(i);
        }

        SingleSelectionField<Integer> DayField = Field.ofSingleSelectionType(ChooseDayProperty, chosenDay).label("Chosen day");

        ButtonBar ChooseDayBar = new ButtonBar();
        Button ChooseDayBtn = new Button("Choose day");
        ChooseDayBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!chosenDay.equals(DayField.getSelection())) {
                    chosenDay = DayField.getSelection();
                    InstantiateStandardUI();
                }
            }
        });
        ChooseDayBar.getButtons().add(ChooseDayBtn);

        SingleSelectionField<External_Event> Chosefield = Field.ofSingleSelectionType(External_Events_by_Day.get(chosenDay)).label("Select ext. event");
        Button EditChosenExternalEventBtn = new Button("Edit chosen event");
        EditChosenExternalEventBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                External_Event Chosen = Chosefield.getSelection();
                if (Chosen != null) {
                    if (Chosen instanceof Resource_Activating_External_Event || Chosen instanceof Resource_Deactivating_External_Event) {
                        UI_Status = UI_External_Event_Manager_Status.EditResource;
                        EditableExternalEvent = Chosen;
                    } else if (Chosen instanceof User_Activating_External_Event || Chosen instanceof User_Deactivating_External_Event) {
                        UI_Status = UI_External_Event_Manager_Status.EditUser;
                        EditableExternalEvent = Chosen;
                    }
                    InstantiateStandardUI();
                }
            }
        });
        Button NewResourceEvent = new Button("New Resourceevent");
        NewResourceEvent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UI_Status = UI_External_Event_Manager_Status.NewResource;
                EditableExternalEvent = null;
                InstantiateStandardUI();
            }
        });
        Button NewUserEvent = new Button("New Userevent");
        NewUserEvent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UI_Status = UI_External_Event_Manager_Status.NewUser;
                EditableExternalEvent = null;
                InstantiateStandardUI();
            }
        });
        ButtonBar ActionBar = new ButtonBar();
        ActionBar.getButtons().add(EditChosenExternalEventBtn);
        ActionBar.getButtons().add(NewUserEvent);
        ActionBar.getButtons().add(NewResourceEvent);

        FormRenderer ChoseRender = new FormRenderer(Form.of(Group.of(DayField, Chosefield)));
        Choosebox.getChildren().add(ChoseRender);
        Choosebox.getChildren().add(ChooseDayBar);
        Choosebox.getChildren().add(new Separator());
        Choosebox.getChildren().add(ActionBar);
        Choosebox.getChildren().add(new Separator());

        EditResourceBox.getChildren().add(ResourceRender);
        EditResourceBox.getChildren().add(new Separator());
        EditResourceBox.getChildren().add(SaveCancelExtEvBar);

        EditUserBox.getChildren().add(UserRender);
        EditUserBox.getChildren().add(new Separator());

        if (UI_Status == Standard) {

        } else if (UI_Status == UI_External_Event_Manager_Status.NewUser) {

            UserActivation.editable(true);
            Userslist.editable(true);
            UserHour.editable(true);
            UserMinute.editable(true);
            UserSecond.editable(true);

            IntegerField finalUserHour = UserHour;
            IntegerField finalUserMinute = UserMinute;
            IntegerField finalUserSecond = UserSecond;
            SaveEv.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    if (UserActivation.getSelection() == null || Userslist.getSelection() == null ||
                            (finalUserHour.getValue() < 0 || finalUserHour.getValue() >= 24) ||
                            (finalUserMinute.getValue() < 0 || finalUserMinute.getValue() >= 60) ||
                            (finalUserSecond.getValue() < 0 || finalUserSecond.getValue() >= 60)) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Wrong Input Detected");
                        alert.setContentText("Please check your inserted values for a new user-event. Something seems wrong");

                        Optional<ButtonType> result = alert.showAndWait();
                    } else {
                        External_Event_Activation_Property Status = (External_Event_Activation_Property) UserActivation.getSelection();
                        if (Status == External_Event_Activation_Property.Activate) {
                            Workingtime InstantiateTime = new Workingtime(finalUserHour.getValue(), finalUserMinute.getValue(), finalUserSecond.getValue());
                            User_Activating_External_Event to_Instantiate = new User_Activating_External_Event(InstantiateTime, chosenDay, (User) Userslist.getSelection(), EPK.getUniqueExternalEventID());
                            List<External_Event> Instantiate_into = External_Events_by_Day.get(chosenDay);
                            Instantiate_into.add(to_Instantiate);
                        } else if (Status == External_Event_Activation_Property.Deactivate) {
                            Workingtime InstantiateTime = new Workingtime(finalUserHour.getValue(), finalUserMinute.getValue(), finalUserSecond.getValue());
                            User_Deactivating_External_Event to_Instantiate = new User_Deactivating_External_Event(InstantiateTime, chosenDay, (User) Userslist.getSelection(), EPK.getUniqueExternalEventID());
                            List<External_Event> Instantiate_into = External_Events_by_Day.get(chosenDay);
                            Instantiate_into.add(to_Instantiate);
                        }
                        EditableExternalEvent = null;
                        UI_Status = Standard;
                        InstantiateStandardUI();
                    }
                }
            });
            CancelEv.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    EditableExternalEvent = null;
                    UI_Status = Standard;
                    InstantiateStandardUI();
                }
            });
            SaveEv.setDisable(false);
            CancelEv.setDisable(false);

        } else if (UI_Status == UI_External_Event_Manager_Status.NewResource) {
            ResourceActivation.editable(true);
            Resourcelist.editable(true);
            ResHour.editable(true);
            ResMinute.editable(true);
            ResSecond.editable(true);
            Count.editable(true);

            IntegerField finalResHour = ResHour;
            IntegerField finalResMinute = ResMinute;
            IntegerField finalResSecond = ResSecond;
            IntegerField finalCount = Count;
            IntegerField finalUserHour1 = UserHour;
            SaveEv.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (ResourceActivation.getSelection() == null || Resourcelist.getSelection() == null ||
                            (finalResHour.getValue() < 0 || finalResHour.getValue() >= 24) ||
                            (finalResMinute.getValue() < 0 || finalResMinute.getValue() >= 60) ||
                            (finalResSecond.getValue() < 0 || finalResSecond.getValue() >= 60) ||
                            finalCount.getValue() <= 0) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Wrong Input Detected");
                        alert.setContentText("Please check your inserted values for a new Resource-event. Something seems wrong");

                        Optional<ButtonType> result = alert.showAndWait();
                    } else {
                        External_Event_Activation_Property Status = (External_Event_Activation_Property) ResourceActivation.getSelection();
                        if (Status == External_Event_Activation_Property.Decrease) {
                            Workingtime InstantiateTime = new Workingtime(finalResHour.getValue(), finalResMinute.getValue(), finalResSecond.getValue());
                            Resource_Activating_External_Event to_Instantiate = new Resource_Activating_External_Event(InstantiateTime, chosenDay, (Resource) Resourcelist.getSelection(), finalCount.getValue(), EPK.getUniqueExternalEventID());
                            List<External_Event> Instantiate_into = External_Events_by_Day.get(chosenDay);
                            Instantiate_into.add(to_Instantiate);
                        } else if (Status == External_Event_Activation_Property.Increase) {
                            Workingtime InstantiateTime = new Workingtime(finalResHour.getValue(), finalResMinute.getValue(), finalResSecond.getValue());
                            Resource_Deactivating_External_Event to_Instantiate = new Resource_Deactivating_External_Event(InstantiateTime, chosenDay, (Resource) Resourcelist.getSelection(), finalCount.getValue(), EPK.getUniqueExternalEventID());
                            List<External_Event> Instantiate_into = External_Events_by_Day.get(chosenDay);
                            Instantiate_into.add(to_Instantiate);
                        }
                        EditableExternalEvent = null;
                        UI_Status = Standard;
                        InstantiateStandardUI();
                    }
                }
            });
            CancelEv.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    EditableExternalEvent = null;
                    UI_Status = Standard;
                    InstantiateStandardUI();
                }
            });
            SaveEv.setDisable(false);
            CancelEv.setDisable(false);

        } else if (UI_Status == UI_External_Event_Manager_Status.EditUser) {
            UserActivation.editable(false);
            Userslist.editable(true);
            UserHour.editable(true);
            UserMinute.editable(true);
            UserSecond.editable(true);
            if (EditableExternalEvent instanceof User_Activating_External_Event) {
                UserActivation.select(0);
                Userslist.select(Users.indexOf(((User_Activating_External_Event) EditableExternalEvent).getUser()));
            } else if (EditableExternalEvent instanceof User_Deactivating_External_Event) {
                UserActivation.select(1);
                Userslist.select(Users.indexOf(((User_Deactivating_External_Event) EditableExternalEvent).getUser()));
            }
            IntegerField finalUserHour1 = UserHour;
            IntegerField finalUserMinute1 = UserMinute;
            IntegerField finalUserSecond1 = UserSecond;
            SaveEv.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    if (UserActivation.getSelection() == null || Userslist.getSelection() == null ||
                            (finalUserHour1.getValue() < 0 || finalUserHour1.getValue() >= 24) ||
                            (finalUserMinute1.getValue() < 0 || finalUserMinute1.getValue() >= 60) ||
                            (finalUserSecond1.getValue() < 0 || finalUserSecond1.getValue() >= 60)) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Wrong Input Detected");
                        alert.setContentText("Please check your inserted values for a new user-event. Something seems wrong");

                        Optional<ButtonType> result = alert.showAndWait();
                    } else {
                        Workingtime newTime = new Workingtime(finalUserHour1.getValue(), finalUserMinute1.getValue(), finalUserSecond1.getValue());
                        if (newTime.get_Duration_to_Seconds() == EditableExternalEvent.getTime().get_Duration_to_Seconds()) {
                            newTime = null;
                        }
                        User newUser = (User) Userslist.getSelection();
                        if (newTime != null) {
                            EditableExternalEvent.setTime(newTime);
                        }
                        if (EditableExternalEvent instanceof User_Activating_External_Event) {
                            if (!newUser.equals(((User_Activating_External_Event) EditableExternalEvent).getUser())) {
                                ((User_Activating_External_Event) EditableExternalEvent).setUser(newUser);
                            }
                        } else if (EditableExternalEvent instanceof User_Deactivating_External_Event) {
                            if (!newUser.equals(((User_Deactivating_External_Event) EditableExternalEvent).getUser())) {
                                ((User_Deactivating_External_Event) EditableExternalEvent).setUser(newUser);
                            }
                        }
                        EditableExternalEvent = null;
                        UI_Status = Standard;
                        InstantiateStandardUI();
                    }
                }
            });
            CancelEv.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    EditableExternalEvent = null;
                    UI_Status = Standard;
                    InstantiateStandardUI();
                }
            });
            DeleteEv.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    External_Events_by_Day.get(chosenDay).remove(EditableExternalEvent);
                    EditableExternalEvent = null;
                    UI_Status = Standard;
                    InstantiateStandardUI();
                }
            });
            DeleteEv.setDisable(false);
            SaveEv.setDisable(false);
            CancelEv.setDisable(false);

        } else if (UI_Status == UI_External_Event_Manager_Status.EditResource) {
            ResourceActivation.editable(false);
            Resourcelist.editable(true);
            ResHour.editable(true);
            ResMinute.editable(true);
            ResSecond.editable(true);
            Count.editable(true);

            SimpleIntegerProperty PropCount = null;
            if (EditableExternalEvent instanceof Resource_Activating_External_Event) {
                ResourceActivation.select(0);
                Resourcelist.select(Resources.indexOf(((Resource_Activating_External_Event) EditableExternalEvent).getResource()));
            } else if (EditableExternalEvent instanceof Resource_Deactivating_External_Event) {
                ResourceActivation.select(1);
                Resourcelist.select(Resources.indexOf(((Resource_Deactivating_External_Event) EditableExternalEvent).getResource()));
            }


            IntegerField finalResHour1 = ResHour;
            IntegerField finalResSecond1 = ResSecond;
            IntegerField finalResMinute1 = ResMinute;
            IntegerField finalCount1 = Count;
            SaveEv.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    if (ResourceActivation.getSelection() == null || Resourcelist.getSelection() == null ||
                            (finalResHour1.getValue() < 0 || finalResHour1.getValue() >= 24) ||
                            (finalResMinute1.getValue() < 0 || finalResMinute1.getValue() >= 60) ||
                            (finalResSecond1.getValue() < 0 || finalResSecond1.getValue() >= 60)) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Wrong Input Detected");
                        alert.setContentText("Please check your inserted values for a new Resource-event. Something seems wrong");

                        Optional<ButtonType> result = alert.showAndWait();
                    } else {
                        Workingtime newTime = new Workingtime(finalResHour1.getValue(), finalResMinute1.getValue(), finalResSecond1.getValue());
                        if (newTime.get_Duration_to_Seconds() == EditableExternalEvent.getTime().get_Duration_to_Seconds()) {
                            newTime = null;
                        }
                        if (newTime != null) {
                            EditableExternalEvent.setTime(newTime);
                        }
                        Resource newResource = (Resource) Resourcelist.getSelection();
                        if (EditableExternalEvent instanceof Resource_Activating_External_Event) {
                            if (!newResource.equals(((Resource_Activating_External_Event) EditableExternalEvent).getResource())) {
                                ((Resource_Activating_External_Event) EditableExternalEvent).setResource(newResource);
                            }
                            if (!((Resource_Activating_External_Event) EditableExternalEvent).getCount().equals(finalCount1.getValue())) {
                                ((Resource_Activating_External_Event) EditableExternalEvent).setCount(finalCount1.getValue());
                            }
                        } else if (EditableExternalEvent instanceof Resource_Deactivating_External_Event) {
                            if (!newResource.equals(((Resource_Deactivating_External_Event) EditableExternalEvent).getResource())) {
                                ((Resource_Deactivating_External_Event) EditableExternalEvent).setResource(newResource);
                            }
                            if (!((Resource_Deactivating_External_Event) EditableExternalEvent).getCount().equals(finalCount1.getValue())) {
                                ((Resource_Deactivating_External_Event) EditableExternalEvent).setCount(finalCount1.getValue());
                            }

                        }
                    }
                    EditableExternalEvent = null;
                    UI_Status = Standard;
                    InstantiateStandardUI();

                }
            });


            CancelEv.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    EditableExternalEvent = null;
                    UI_Status = Standard;
                    InstantiateStandardUI();
                }
            });
            DeleteEv.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    External_Events_by_Day.get(chosenDay).remove(EditableExternalEvent);
                    EditableExternalEvent = null;
                    UI_Status = Standard;
                    InstantiateStandardUI();
                }
            });
            DeleteEv.setDisable(false);
            SaveEv.setDisable(false);
            CancelEv.setDisable(false);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == OK_Button) {
            this_stage.close();
        }
    }

    public void setExternal_Events_by_Day(List<List<External_Event>> external_Events_by_Day) {
        External_Events_by_Day = external_Events_by_Day;
    }
}

