package com.company.Process_Mining;

import com.company.Process_Mining.Base_Data.Mining_Instance;

import java.util.HashMap;
import java.util.List;

public class Log_Relation_Data {

    private HashMap<Mining_Instance, List<Mining_Instance>> Single_Mining_Instance_Map;
    private HashMap<Mining_Instance, List<Mining_Instance>> Mining_Instances_Relations;

    public Log_Relation_Data() {
        Single_Mining_Instance_Map = new HashMap<>();
        Mining_Instances_Relations = new HashMap<>();
    }

    public Log_Relation_Data(HashMap<Mining_Instance, List<Mining_Instance>> single_Mining_Instance_Map, HashMap<Mining_Instance, List<Mining_Instance>> mining_Instances_Relations) {
        Single_Mining_Instance_Map = single_Mining_Instance_Map;
        Mining_Instances_Relations = mining_Instances_Relations;
    }

    public HashMap<Mining_Instance, List<Mining_Instance>> getSingle_Mining_Instance_Map() {
        return Single_Mining_Instance_Map;
    }

    public void setSingle_Mining_Instance_Map(HashMap<Mining_Instance, List<Mining_Instance>> single_Mining_Instance_Map) {
        Single_Mining_Instance_Map = single_Mining_Instance_Map;
    }

    public HashMap<Mining_Instance, List<Mining_Instance>> getMining_Instances_Relations() {
        return Mining_Instances_Relations;
    }

    public void setMining_Instances_Relations(HashMap<Mining_Instance, List<Mining_Instance>> mining_Instances_Relations) {
        Mining_Instances_Relations = mining_Instances_Relations;
    }
}
