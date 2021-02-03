package com.company.Process_Mining;

import com.company.Process_Mining.Base_Data.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Process_Mining_JSON_Read {

    JSONArray JSONArrayInstanceList;
    private List<Mining_User> Userlist;
    private List<Mining_Resource> ResourceList;
    private List<Mining_Instance> InstanceList;
    private List<Mining_Activity> ActivityList;
    private List<List<Mining_Instance>> Sorted_Instance_List;

    public Process_Mining_JSON_Read() {

    }

    public void Read_From_File() {

        JSONParser Functionparser = new JSONParser();

        try (FileReader reader = new FileReader("./InstanceLog/Line.json")) {
            Object obj = Functionparser.parse(reader);

            Userlist = new ArrayList<>();
            ResourceList = new ArrayList<>();
            InstanceList = new ArrayList<>();
            ActivityList = new ArrayList<>();

            JSONArrayInstanceList = (JSONArray) obj;

            System.out.println("Resolving Parser");

            /*for (Object object: InstanceList) {
                System.out.println( object);
            }*/

            JSONArrayInstanceList.forEach(Instance -> parseInstanceObject((JSONObject) Instance));

            System.out.println("Parser resolved");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Sorted_Instance_List = generateSortedInstanceList();
    }


    //TODO Schau hier rein du sack
    private List<List<Mining_Instance>> generateSortedInstanceList() {
        List<List<Mining_Instance>> sorted_Instance_List = new ArrayList<>();
        for (Mining_Instance mining_Instance : InstanceList) {
            boolean found = false;
            for (List<Mining_Instance> Mining_List : sorted_Instance_List) {
                if (Mining_List.get(0).getInstance_ID() == mining_Instance.getInstance_ID()) {
                    Mining_List.add(mining_Instance);
                    found = true;
                    break;
                }
            }
            if (!found) {
                List<Mining_Instance> newMining_Instance_List = new ArrayList<>();
                newMining_Instance_List.add(mining_Instance);
                sorted_Instance_List.add(newMining_Instance_List);
            }
        }
        return sorted_Instance_List;
    }

    private void parseInstanceObject(JSONObject inst) {

        String Activity_Status = (String) inst.get("Status");
        String Activity_Name = (String) inst.get("Nodename");
        String Activity_Type = (String) inst.get("Type_of_act_Node");
        long Activity_ID = (long) inst.get("Node_ID");
        long Activity_Day = (long) inst.get("Day");

        Mining_Activity newActivity = CreateOrLoadActivity(Activity_Name, (int) Activity_ID, Activity_Type);

        long Instance_ID = (long) inst.get("Instance_ID");
        JSONObject Timestamp = (JSONObject) inst.get("Timestamp");
        long Hour = (long) Timestamp.get("hour");
        long Minute = (long) Timestamp.get("minute");
        long Second = (long) Timestamp.get("second");

        Mining_Instance newInstance = new Mining_Instance(LocalTime.of((int) Hour, (int) Minute, (int) Second), (int) Activity_Day,
                (int) Instance_ID, Activity_Status, newActivity);

        if (Activity_Status.equals("Working") || Activity_Status.equals("Finished")) {
            JSONArray UserArray = (JSONArray) inst.get("used_User");
            JSONArray RessourceArray = (JSONArray) inst.get("used_Resources");
            UserArray.forEach(Value -> {
                String Name = (String) ((JSONObject) Value).get("name");
                String LastName = (String) ((JSONObject) Value).get("Lastname");
                long U_ID = (long) ((JSONObject) Value).get("User_ID");
                Mining_User newUser = CreateOrLoadUser(Name, LastName, U_ID);
                newInstance.getUsed_Users().add(newUser);
            });
            RessourceArray.forEach(Value -> {
                String Resname = (String) ((JSONObject) Value).get("Res_Name");
                long count = (long) ((JSONObject) Value).get("Res_Count");
                if (Activity_Status.equals("Finished")) {
                    count = -count;
                }
                long R_ID = (long) ((JSONObject) Value).get("Res_ID");
                Mining_Resource newResource = CreateOrLoadResource(Resname, (int) R_ID);
                addResourceToInstance(newInstance, newResource, (int) count);
            });
        }
        InstanceList.add(newInstance);
    }

    private void addResourceToInstance(Mining_Instance newInstance, Mining_Resource newResource, int count) {
        for (Mining_Resource_Count ListResourceCount : newInstance.getUsed_Resources()) {
            if (ListResourceCount.getResource().getR_ID() == newResource.getR_ID()) {
                ListResourceCount.setCount(ListResourceCount.getCount() + count);
            }
        }
        Mining_Resource_Count newResCount = new Mining_Resource_Count(newResource, count);
        newInstance.getUsed_Resources().add(newResCount);
    }

    private Mining_Resource CreateOrLoadResource(String resname, int r_id) {
        for (Mining_Resource ListResource : ResourceList) {
            if (ListResource.getR_ID() == r_id) {
                return ListResource;
            }
        }
        Mining_Resource newResource = new Mining_Resource(resname, r_id);
        ResourceList.add(newResource);
        return newResource;
    }

    private Mining_Activity CreateOrLoadActivity(String activity_name, int activity_id, String activity_type) {
        for (Mining_Activity ListActivity : ActivityList) {
            if (ListActivity.getNode_ID() == activity_id) {
                return ListActivity;
            }
        }
        Mining_Activity newActivity = new Mining_Activity(activity_name, activity_type, activity_id);
        ActivityList.add(newActivity);
        return newActivity;
    }

    private Mining_User CreateOrLoadUser(String name, String lastName, long u_id) {

        for (Mining_User listuser : Userlist) {
            if (listuser.getP_ID() == u_id) {
                return listuser;
            }
        }

        Mining_User newUser = new Mining_User(null, null, 0);
        newUser.setP_ID((int) u_id);
        newUser.setName(name);
        newUser.setLastname(lastName);
        Userlist.add(newUser);
        return newUser;
    }

    public List<Mining_User> getUserlist() {
        return Userlist;
    }

    public List<Mining_Resource> getResourceList() {
        return ResourceList;
    }

    public List<Mining_Instance> getInstanceList() {
        return InstanceList;
    }

    public List<List<Mining_Instance>> getSorted_Instance_List() {
        return Sorted_Instance_List;
    }

    public void setSorted_Instance_List(List<List<Mining_Instance>> sorted_Instance_List) {
        Sorted_Instance_List = sorted_Instance_List;
    }

    public List<Mining_Activity> getActivityList() {
        return ActivityList;
    }
}
