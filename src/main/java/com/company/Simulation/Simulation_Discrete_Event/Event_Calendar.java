package com.company.Simulation.Simulation_Discrete_Event;

import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Event_Instance;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Simulation_Event_List;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Simulation_Waiting_List;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Settings;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.time.LocalTime;

public class Event_Calendar {

    private LocalTime runtime;
    private LocalTime Begin_Time;
    private LocalTime End_Time;
    private Simulation_Event_List Upcoming_List;
    private Simulation_Waiting_List Waiting_List;

    public Event_Calendar(Settings settings) {
        Begin_Time = settings.getBeginTime();
        End_Time = settings.getEndTime();
        runtime = settings.getBeginTime();
        Waiting_List = new Simulation_Waiting_List();
        Upcoming_List = new Simulation_Event_List();
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

    public Simulation_Event_List getUpcoming_List() {
        return Upcoming_List;
    }

    public Simulation_Waiting_List getWaiting_List() {
        return Waiting_List;
    }

    public Event_Instance calculate_Next_Event() {
        return null;
    }

    public void fillCalendar() {

        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();


    }
}
