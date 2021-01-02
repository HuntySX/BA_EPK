package com.company.Process_Mining;

import com.company.Process_Mining.Base_Data.Mining_Activity;
import com.company.Process_Mining.Base_Data.Mining_Instance;
import com.company.Process_Mining.Base_Data.Mining_Resource;
import com.company.Process_Mining.Base_Data.Mining_User;

import java.util.List;

public class Process_Mining_Main {

    Process_Mining_JSON_Read Reader;
    Process_Mining_Miner Miner;

    public Process_Mining_Main() {
        Reader = new Process_Mining_JSON_Read();
        Miner = new Process_Mining_Miner();
    }

    public void run() {
        Reader.Read_From_File();
        List<Mining_Resource> Resource_List = Reader.getResourceList();
        List<Mining_Activity> Activity_List = Reader.getActivityList();
        List<Mining_User> User_List = Reader.getUserlist();
        for (List<Mining_Instance> Mining_List_By_ID : Reader.getSorted_Instance_List()) {
            for (Mining_Instance Instance_By_ID : Mining_List_By_ID) {
                System.out.println(Instance_By_ID.toString());
            }
        }
        Miner.setReader(Reader);
        Miner.start_Mining();
    }
}
