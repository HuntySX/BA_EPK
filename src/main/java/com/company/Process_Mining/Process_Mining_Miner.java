package com.company.Process_Mining;

import com.company.Process_Mining.Base_Data.Mining_Instance;
import com.company.Process_Mining.Base_Data.Mining_Resource;
import com.company.Process_Mining.Base_Data.Mining_Resource_Count;
import com.company.Process_Mining.Base_Data.Mining_User;

import java.util.ArrayList;
import java.util.List;

public class Process_Mining_Miner {

    private Process_Mining_JSON_Read Reader;
    private List<List<List<Mining_User>>> Used_Users_Filtered_List;
    private List<List<List<Mining_Resource_Count>>> Used_Resource_Count_Filtered_List;


    public Process_Mining_Miner() {
    }

    public Process_Mining_Miner(Process_Mining_JSON_Read Reader) {
        this.Reader = Reader;
        Used_Users_Filtered_List = new ArrayList<>();

    }


    public Process_Mining_JSON_Read getReader() {
        return Reader;
    }

    public void setReader(Process_Mining_JSON_Read reader) {
        Reader = reader;
    }

    public void start_Mining() {
        System.out.println("Starting Filter");

        FilterLog();

    }

    private void FilterLog() {
        FilterByUser();
        FilterByResource();
        FilterByActivity();
        FilterByActivityWorkingTime();
        FilterByUserWorkingTime();
        FilterByResourceWorkingTime();
    }

    private void FilterByActivity() {
    }

    private void FilterByResource() {
        Mining_Resource_Count Empty_Resource_Count = new Mining_Resource_Count(new Mining_Resource("Empty", 0), 0);
        List<List<Mining_Instance>> sorted_Instance_List = Reader.getSorted_Instance_List();
        List<List<List<Mining_Resource_Count>>> used_Resource_Count_per_Instance = new ArrayList<>();
        for (List<Mining_Instance> single_Instance_List : sorted_Instance_List) {
            List<List<Mining_Resource_Count>> used_Resource_Count_By_Single_Instance = new ArrayList<>();
            for (Mining_Instance single_Activity : single_Instance_List) {
                if (single_Activity.getActivity_Status().equals("Working")) {
                    boolean hasResource = false;
                    List<Mining_Resource_Count> used_Resource_Count_By_Single_Instance_Per_Activity = new ArrayList<>();
                    for (Mining_Resource_Count used_Resource_Count : single_Activity.getUsed_Resources()) {
                        hasResource = true;
                        used_Resource_Count_By_Single_Instance_Per_Activity.add(used_Resource_Count);
                    }
                    if (!hasResource) {
                        used_Resource_Count_By_Single_Instance_Per_Activity.add(Empty_Resource_Count);
                    }
                    used_Resource_Count_By_Single_Instance.add(used_Resource_Count_By_Single_Instance_Per_Activity);
                }
            }
            used_Resource_Count_per_Instance.add(used_Resource_Count_By_Single_Instance);
        }
        Used_Resource_Count_Filtered_List = used_Resource_Count_per_Instance;
    }

    private void FilterByUser() {
        Mining_User Empty_User = new Mining_User("Empty", "Empty", 0);
        List<List<Mining_Instance>> sorted_Instance_List = Reader.getSorted_Instance_List();
        List<List<List<Mining_User>>> used_Users_per_Instance = new ArrayList<>();
        for (List<Mining_Instance> single_Instance_List : sorted_Instance_List) {
            List<List<Mining_User>> used_Users_By_Single_Instance = new ArrayList<>();
            for (Mining_Instance single_Activity : single_Instance_List) {
                if (single_Activity.getActivity_Status().equals("Working")) {
                    boolean hasUser = false;
                    List<Mining_User> used_User_By_Single_Instance_Per_Activity = new ArrayList<>();
                    for (Mining_User used_User : single_Activity.getUsed_Users()) {
                        hasUser = true;
                        used_User_By_Single_Instance_Per_Activity.add(used_User);
                    }
                    if (!hasUser) {
                        used_User_By_Single_Instance_Per_Activity.add(Empty_User);
                    }
                    used_Users_By_Single_Instance.add(used_User_By_Single_Instance_Per_Activity);
                }
            }
            used_Users_per_Instance.add(used_Users_By_Single_Instance);
        }
        Used_Users_Filtered_List = used_Users_per_Instance;
    }
}
