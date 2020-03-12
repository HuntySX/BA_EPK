package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.EPK.Function;
import com.company.EPK.Node;
import com.company.Enums.FillingType;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Event_Instance;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Simulation_Event_List;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Simulation_Waiting_List;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Settings;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.company.Enums.FillingType.*;

public class Event_Calendar {

    private int RuntimeDays;
    private LocalTime runtime;
    private LocalTime Begin_Time;
    private LocalTime End_Time;
    private List<Simulation_Event_List> Upcoming_List;
    private Simulation_Waiting_List Waiting_List;
    private Settings settings;

    public Event_Calendar(Settings settings) {

        this.settings = settings;
        this.RuntimeDays = settings.getRuntimeDays();
        Begin_Time = settings.getBeginTime();
        End_Time = settings.getEndTime();
        runtime = settings.getBeginTime();
        Waiting_List = new Simulation_Waiting_List();
        Upcoming_List = new ArrayList<>();
        for (int i = 0; i < RuntimeDays; i++) {
            Simulation_Event_List Day_Upcoming_List = new Simulation_Event_List();
            Upcoming_List.add(Day_Upcoming_List);
        }

    }

    public LocalTime getRuntime() {
        return runtime;
    }

    public void setRuntime(LocalTime runtime) {
        this.runtime = runtime;
    }

    public LocalTime getBegin_Time() {
        return Begin_Time;
    }

    public LocalTime getEnd_Time() {
        return End_Time;
    }

    public List<Simulation_Event_List> getUpcoming_List() {
        return Upcoming_List;
    }

    public Simulation_Event_List get_Single_Upcoming_List(int day) {
        return Upcoming_List.get(day);
    }

    public Simulation_Waiting_List getWaiting_List() {
        return Waiting_List;
    }

    public void Add_To_Waiting_List(Instance_Workflow Instance) {
        if (Instance != null) {
            Waiting_List.addTimedEvent(Instance);
        }
    }

    public void Add_To_Upcoming_List(Instance_Workflow Instance, int day) {

        if (Instance != null) {

            if (Instance.getTo_Start().isBefore(End_Time)) {
                Upcoming_List.get(day).addTimedEvent(Instance);
            } else if (Instance.getNode() instanceof Function && Instance.isWorking()) {
                if (day + 1 < RuntimeDays) {
                    LocalTime Calculation = Instance.getTo_Start();
                    int to_finish = Calculation.toSecondOfDay() - End_Time.toSecondOfDay();
                    Instance.setTo_Start(Begin_Time.plusSeconds(to_finish));
                    Upcoming_List.get(day + 1).addTimedEvent(Instance);
                }
                //Else Drop
            }
        }
    }

    public Event_Instance calculate_Next_Event() {
        return null;
    }

    public void Remove_From_Upcoming_List(Instance_Workflow Instance, int day) {
        Upcoming_List.get(day).remove_from_EventList(Instance);
    }

    public void Remove_From_Waiting_List(Instance_Workflow Instance) {
        Waiting_List.remove_from_WaitingList(Instance);
    }

    public void fillCalendar() {

        FillingType fillingType = settings.getfillingType();
        int days = RuntimeDays;
        int begintime = Begin_Time.toSecondOfDay();
        int endtime = End_Time.toSecondOfDay();

        if (fillingType == NORMAL) {

        } else if (fillingType == RANDOM) {
            for (int i = 0; i < RuntimeDays; i++) {

            }
        } else if (fillingType == EXPONENTIAL) {

        }
        /*else if(fillingType == ){

        }
        else if(fillingType == ){

        }*/

        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
    }

    public void jump() {
        runtime = runtime.plusSeconds(1);

    }
}
