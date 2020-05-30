package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.EPK.*;
import com.company.Run.Discrete_Event_Generator;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Settings;

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
    private int act_runtimeDay;
    private boolean finished_cycle;
    private EPK epk;
    private Discrete_Event_Generator Generator;
    private List<Instance_Workflow> Activation_List;
    private int Unique_Waiting_Ticket_ID;

    public Event_Calendar(Settings settings, EPK epk, Discrete_Event_Generator generator) {

        this.settings = settings;
        this.epk = epk;
        finished_cycle = false;
        this.act_runtimeDay = 0;
        this.RuntimeDays = settings.getMax_RuntimeDays();
        this.Generator = generator;
        Begin_Time = settings.getBeginTime();
        End_Time = settings.getEndTime();
        runtime = LocalTime.of(Begin_Time.getHour(), Begin_Time.getMinute(), Begin_Time.getSecond());
        Waiting_List = new Simulation_Waiting_List();
        Upcoming_List = new ArrayList<>();
        Activation_List = new ArrayList<>();
        Unique_Waiting_Ticket_ID = 0;
        for (int i = 0; i < RuntimeDays; i++) {
            Simulation_Event_List Day_Upcoming_List = new Simulation_Event_List();
            Upcoming_List.add(Day_Upcoming_List);
        }

    }

    public int getUnique_Waiting_Ticket_ID() {
        int id = Unique_Waiting_Ticket_ID;
        Unique_Waiting_Ticket_ID++;
        return id;
    }

    public int getAct_runtimeDay() {
        return act_runtimeDay;
    }

    public void setAct_runtimeDay(int act_runtimeDay) {
        this.act_runtimeDay = act_runtimeDay;
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

    public int getRuntimeDays() {
        return RuntimeDays;
    }

    public void incrementRuntimeDay() {
        RuntimeDays++;
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
            } else if (Instance.getEPKNode() instanceof Function && Instance.isWorking()) {
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

        int days = RuntimeDays;
        int begintime = Begin_Time.toSecondOfDay();
        int endtime = End_Time.toSecondOfDay();
        List<Start_Event> Start_Events = epk.get_Discrete_Start_Events();
        for (Start_Event sv : Start_Events) {
            int counter_to_Instantiate = sv.getTo_Instantiate();
            if (sv.getStart_event_type() == NORMAL) {
                //DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
            } else if (sv.getStart_event_type() == RANDOM) {
                //TODO TAGE werden pro SV gefüllt, sorgt für unsortierte Case-ID´s
                for (int i = 0; i < RuntimeDays; i++) {
                    int[] timerlist = new int[counter_to_Instantiate];
                    int duration = endtime - begintime;
                    Random rand = new Random();
                    for (int j = 0; j < counter_to_Instantiate; j++) {
                        int start_Time = rand.nextInt(duration);
                        start_Time = begintime + start_Time;
                        timerlist[j] = start_Time;
                    }
                    QuickSort(timerlist, 0, counter_to_Instantiate - 1);
                    for (int k : timerlist) {
                        Event_Instance new_Ev_Instance = new Event_Instance(Generator.get_Unique_case_ID());
                        new_Ev_Instance.add_To_Scheduled_Work(sv);
                        LocalTime to_Start = LocalTime.ofSecondOfDay(k);
                        Instance_Workflow to_Instantiate = new Instance_Workflow(new_Ev_Instance, to_Start, sv);
                        Upcoming_List.get(i).addTimedEvent(to_Instantiate);
                    }
                }
            } else if (sv.getStart_event_type() == EXPONENTIAL) {
                //DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
            }
        /*else if(fillingType == ){

        }
        else if(fillingType == ){

        }*/


        }
    }

    public void jump() {
        runtime = runtime.plusSeconds(1);
        if (runtime.isAfter(getEnd_Time())) {
            act_runtimeDay++;
            if (act_runtimeDay >= getRuntimeDays()) {
                setFinished_cycle(true);
                System.out.println("Finished Sim");
            } else {
                runtime = getBegin_Time();
                System.out.println("Day Jumped");
            }
        }
    }

    public boolean isFinished_cycle() {
        return finished_cycle;
    }

    public void setFinished_cycle(boolean finished_cycle) {
        this.finished_cycle = finished_cycle;
    }

    public void instantiate_new_Activation_Event(Activating_Start_Event start, Activating_Function Func, int for_case_ID, int waiting_Ticket, Workingtime to_Start) {
        Activating_Event_Instance activating_instance = new Activating_Event_Instance(Generator.get_Unique_case_ID(), Func, for_case_ID, waiting_Ticket);
        LocalTime StartTime = getRuntime();

        int lasting_Shifttime_in_Seconds = getEnd_Time().toSecondOfDay() - getRuntime().toSecondOfDay();
        int Workingtime_in_Seconds = to_Start.get_Duration_to_Seconds();
        int advanceday = 0;

        if (Workingtime_in_Seconds <= lasting_Shifttime_in_Seconds) {
            Instance_Workflow activating_workflow = new Instance_Workflow(activating_instance, StartTime, start);
            Add_To_Upcoming_List(activating_workflow, getAct_runtimeDay());
        } else {
            Workingtime_in_Seconds = Workingtime_in_Seconds - lasting_Shifttime_in_Seconds;
            int Shifttime_in_Seconds = getEnd_Time().toSecondOfDay() - getBegin_Time().toSecondOfDay();
            advanceday++;

            while (Workingtime_in_Seconds > Shifttime_in_Seconds) {
                Workingtime_in_Seconds = Workingtime_in_Seconds - Shifttime_in_Seconds;
                advanceday++;
            }

            if (advanceday >= getRuntimeDays() - getAct_runtimeDay()) {
                // not instantiable in Runtime, Drop
            } else {
                advanceday = advanceday + getAct_runtimeDay();
                StartTime = getBegin_Time();
                StartTime.plusSeconds(Workingtime_in_Seconds);
                Instance_Workflow activating_workflow = new Instance_Workflow(activating_instance, StartTime, start);
                activating_workflow.getInstance().add_To_Scheduled_Work(start);
                Add_To_Upcoming_List(activating_workflow, getAct_runtimeDay() + advanceday);
            }
        }
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

    //swap elements
    private static void swap(int[] arr, int iElement1, int iElement2) {
        int temp = arr[iElement1];
        arr[iElement1] = arr[iElement2];
        arr[iElement2] = temp;
    }

    public List<Instance_Workflow> getActivationList() {
        return Activation_List;
    }

    public void addToActivationList(Instance_Workflow workflow) {
        if (!Activation_List.contains(workflow)) {
            Activation_List.add(workflow);
        }
    }

    public void removeFromActivationList(Instance_Workflow workflow) {
        if (Activation_List.contains(workflow)) {
            Activation_List.remove(workflow);
        }
    }
}



