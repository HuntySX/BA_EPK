package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.EPK.EPK;
import com.company.EPK.Event;
import com.company.EPK.Function;
import com.company.Enums.Start_Event_Type;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Settings;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.company.Enums.Start_Event_Type.*;

public class Event_Calendar {

    private int RuntimeDays;
    private LocalTime runtime;
    private LocalTime Begin_Time;
    private LocalTime End_Time;
    private List<Simulation_Event_List> Upcoming_List;
    private Simulation_Waiting_List Waiting_List;
    private Settings settings;
    private EPK epk;

    public Event_Calendar(Settings settings, EPK epk) {

        this.settings = settings;
        this.epk = epk;
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

    private static void QuickSort(int[] inputArray, int low, int high) {

        int iLowerIndex = low;
        int iHighIndex = high;

        // Take middle as pivot element.
        int middle = low + (high - low) / 2;
        int pivotElement = inputArray[middle];

        while (iLowerIndex <= iHighIndex) {

            // Keep scanning lower half till value is less than pivot element
            while (inputArray[iLowerIndex] < pivotElement) {
                iLowerIndex++;
            }

            // Keep scanning upper half till value is greater than pivot element
            while (inputArray[iHighIndex] > pivotElement) {
                iHighIndex--;
            }

            //swap element if they are out of place
            if (iLowerIndex <= iHighIndex) {
                swap(inputArray, iLowerIndex, iHighIndex);
                iLowerIndex++;
                iHighIndex--;
            }
        }

        // Sort lower half -- low to iHighIndex
        if (low < iHighIndex) {
            QuickSort(inputArray, low, iHighIndex);
        }

        // Sort upper half -- iLowerIndex to high
        if (iLowerIndex < high) {
            QuickSort(inputArray, iLowerIndex, high);
        }
    }

    public void jump() {
        runtime = runtime.plusSeconds(1);

    }

    //swap elements
    private static void swap(int[] arr, int iElement1, int iElement2) {
        int temp = arr[iElement1];
        arr[iElement1] = arr[iElement2];
        arr[iElement2] = temp;
    }

    public void fillCalendar() {

        Start_Event_Type startEventType = settings.getfillingType();
        int days = RuntimeDays;
        int begintime = Begin_Time.toSecondOfDay();
        int endtime = End_Time.toSecondOfDay();
        int instances_Per_Day = settings.getNumber_Instances_Per_Day();

        if (startEventType == NORMAL) {

        } else if (startEventType == RANDOM) {

            List<Event> Start_Events = epk.getStart_Events();
            int case_ID = 0;

            for (Event Start_ev : Start_Events) {
                for (int i = 0; i < RuntimeDays; i++) {
                    int[] timerlist = new int[instances_Per_Day];
                    int duration = endtime - begintime;
                    Random rand = new Random();
                    for (int j = 0; j < instances_Per_Day; j++) {
                        int start_Time = rand.nextInt(duration);
                        start_Time = begintime + start_Time;
                        timerlist[j] = start_Time;
                    }
                    QuickSort(timerlist, 0, instances_Per_Day - 1);
                    for (int k : timerlist) {
                        Event_Instance new_Ev_Instance = new Event_Instance(case_ID);
                        case_ID++;
                        LocalTime to_Start = LocalTime.ofSecondOfDay(k);
                        Instance_Workflow to_Instantiate = new Instance_Workflow(new_Ev_Instance, to_Start, Start_ev);
                        Upcoming_List.get(i).addTimedEvent(to_Instantiate);
                    }
                }
            }
        } else if (startEventType == EXPONENTIAL) {

        }
        /*else if(fillingType == ){

        }
        else if(fillingType == ){

        }*/

        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
    }
}


