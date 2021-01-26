package com.company.Process_Mining;

import com.company.Exceptions.PM_Instance_Relation_Error;
import com.company.Process_Mining.Base_Data.*;

import java.time.LocalTime;
import java.util.*;

public class Process_Mining_Miner {


    private final Process_Mining_Settings Mining_Settings;
    private Process_Mining_JSON_Read Reader;
    private List<List<List<Mining_User>>> Used_Users_Filtered_List;
    private List<List<List<Mining_Resource_Count>>> Used_Resource_Count_Filtered_List;
    private List<List<Mining_Activity>> Used_Activity_Filtered_List;


    public Process_Mining_Miner(Process_Mining_JSON_Read Reader, Process_Mining_Settings Settings) {
        Used_Users_Filtered_List = new ArrayList<>();
        this.Reader = Reader;
        Used_Resource_Count_Filtered_List = new ArrayList<>();
        Used_Activity_Filtered_List = new ArrayList<>();
        Mining_Settings = Settings;
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
        //FilterByUserWorkingTime();
        //FilterByResourceWorkingTime();
    }

    private void FilterByActivityWorkingTime() {

        List<List<Mining_Activity_Event>> Activity_Resolution_List_per_Day = new ArrayList<>();


        Mining_Activity Empty_Activity = new Mining_Activity("Null", "Function", 0);
        List<List<Mining_Instance>> sorted_Instance_List = Reader.getSorted_Instance_List();
        List<List<Mining_Activity>> used_Activity_per_Instance = new ArrayList<>();
        for (List<Mining_Instance> single_Instance_List : sorted_Instance_List) {
            List<Mining_Activity> used_Activity_By_Single_Instance = new ArrayList<>();
            for (Mining_Instance single_Activity : single_Instance_List) {
                if (single_Activity.getActivity_Status().equals("Working") && single_Activity.getActivity().getType_of_Activity().equals("Function")) {
                    used_Activity_By_Single_Instance.add(single_Activity.getActivity());
                }
            }
            used_Activity_per_Instance.add(used_Activity_By_Single_Instance);
        }
        Used_Activity_Filtered_List = used_Activity_per_Instance;

    }

    private void FilterByActivity() {
        Mining_Activity Empty_Activity = new Mining_Activity("Null", "Function", 0);
        List<List<Mining_Instance>> sorted_Instance_List = Reader.getSorted_Instance_List();
        List<List<Mining_Activity>> used_Activity_per_Instance = new ArrayList<>();
        for (List<Mining_Instance> single_Instance_List : sorted_Instance_List) {
            List<Mining_Activity> used_Activity_By_Single_Instance = new ArrayList<>();
            for (Mining_Instance single_Activity : single_Instance_List) {
                if (single_Activity.getActivity_Status().equals("Working") && single_Activity.getActivity().getType_of_Activity().equals("Function")) {
                    used_Activity_By_Single_Instance.add(single_Activity.getActivity());
                }
            }
            used_Activity_per_Instance.add(used_Activity_By_Single_Instance);
        }
        Used_Activity_Filtered_List = used_Activity_per_Instance;

    }

    private void generate_Activity_Standart_Relation() {

        HashMap<Integer, HashMap<Integer, Relation_Count>> Relation_Hashmap = new HashMap<>();

        Initialize_Relation_Hashmap(Relation_Hashmap);

        for (List<Mining_Instance> Single_Mining_Instance : Reader.getSorted_Instance_List()) {
            check_For_Activity_Relation(Relation_Hashmap, Single_Mining_Instance);
        }
        generate_Direct_Dependency(Relation_Hashmap);
        generate_Activity_Parallel_Execution(Relation_Hashmap);
        generate_one_Step_Execution(Relation_Hashmap);
        generate_two_Step_Execution(Relation_Hashmap);
        List<Relation_Places> Min_Set = new ArrayList<>();
        generate_Min_Places(Min_Set, Relation_Hashmap);
        Clean_Min_Places(Min_Set, Relation_Hashmap);
        generate_max_Set(Min_Set, Relation_Hashmap);

    }

    private void Clean_Min_Places(List<Relation_Places> min_set, HashMap<Integer, HashMap<Integer, Relation_Count>> relation_hashmap) {
        List<Relation_Places> Mark_For_Deletion = new ArrayList<>();
        for (Relation_Places relation : min_set) {
            boolean delete_relation = false;
            for (Mining_Activity from_Activity_A : relation.getFrom()) {
                if (!delete_relation) {
                    for (Mining_Activity from_Activity_B : relation.getFrom()) {
                        if (relation_hashmap.get(from_Activity_A.getNode_ID()).get(from_Activity_B.getNode_ID()).getRelation_type() != Relation_Type.None) {
                            Mark_For_Deletion.add(relation);
                            delete_relation = true;
                            break;
                        }
                    }
                } else {
                    break;
                }
            }

            if (!delete_relation) {
                for (Mining_Activity to_Activity_A : relation.getTo()) {
                    if (!delete_relation) {
                        for (Mining_Activity to_Activity_B : relation.getTo()) {
                            if (relation_hashmap.get(to_Activity_A.getNode_ID()).get(to_Activity_B.getNode_ID()).getRelation_type() != Relation_Type.None) {
                                Mark_For_Deletion.add(relation);
                                delete_relation = true;
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        if (!Mark_For_Deletion.isEmpty()) {
            min_set.removeAll(Mark_For_Deletion);
        }
    }

    private void generate_Min_Places(List<Relation_Places> min_set, HashMap<Integer, HashMap<Integer, Relation_Count>> relation_Hashmap) {
        HashMap<Integer, Mining_Activity> All_Activities = new HashMap<>();
        for (Map.Entry<Integer, HashMap<Integer, Relation_Count>> Relation_By_Single_Elem : relation_Hashmap.entrySet()) {
            for (Map.Entry<Integer, Relation_Count> Relation_From_Single_To_Single_Elem : Relation_By_Single_Elem.getValue().entrySet()) {
                if (Relation_From_Single_To_Single_Elem.getValue().getRelation_type().equals(Relation_Type.Followed_by)) {
                    Mining_Activity From = All_Activities.get(Relation_By_Single_Elem.getKey());
                    if (From == null) {
                        List<Mining_Activity> All_Activities_List = Reader.getActivityList();
                        for (Mining_Activity Activity : All_Activities_List) {
                            if (Activity.getNode_ID() == Relation_By_Single_Elem.getKey()) {
                                All_Activities.put(Activity.getNode_ID(), Activity);
                                From = Activity;
                                break;
                            }
                        }
                    }
                    Mining_Activity To = All_Activities.get(Relation_From_Single_To_Single_Elem.getKey());
                    if (To == null) {
                        List<Mining_Activity> All_Activities_List = Reader.getActivityList();
                        for (Mining_Activity Activity : All_Activities_List) {
                            if (Activity.getNode_ID() == Relation_From_Single_To_Single_Elem.getKey()) {
                                All_Activities.put(Activity.getNode_ID(), Activity);
                                To = Activity;
                                break;
                            }
                        }
                    }
                    Relation_Places new_Min_Place = new Relation_Places();
                    new_Min_Place.getFrom().add(From);
                    new_Min_Place.getTo().add(To);
                    min_set.add(new_Min_Place);
                }
            }
        }
    }

    private void generate_max_Set(List<Relation_Places> min_Set, HashMap<Integer, HashMap<Integer, Relation_Count>> relation_hashmap) {


    }

    private void generate_Direct_Dependency(HashMap<Integer, HashMap<Integer, Relation_Count>> Relation_Hashmap) {

        for (Map.Entry<Integer, HashMap<Integer, Relation_Count>> Relation_By_Single_Elem : Relation_Hashmap.entrySet()) {

            for (Map.Entry<Integer, Relation_Count> Relation_From_Single_To_Single_Elem : Relation_By_Single_Elem.getValue().entrySet()) {

                if (Relation_From_Single_To_Single_Elem.getValue().getRelation_type() == Relation_Type.Related) {
                    if (Relation_Hashmap.get(Relation_From_Single_To_Single_Elem.getKey()).get(Relation_By_Single_Elem.getKey()).getRelation_type() != Relation_Type.Related) {
                        Relation_From_Single_To_Single_Elem.getValue().setRelation_type(Relation_Type.Followed_by);
                    }
                }
            }
        }
    }

    private void generate_Activity_Parallel_Execution(HashMap<Integer, HashMap<Integer, Relation_Count>> Relation_Hashmap) {

        for (Map.Entry<Integer, HashMap<Integer, Relation_Count>> Relation_By_Single_Elem : Relation_Hashmap.entrySet()) {

            for (Map.Entry<Integer, Relation_Count> Relation_From_Single_To_Single_Elem : Relation_By_Single_Elem.getValue().entrySet()) {

                if (Relation_From_Single_To_Single_Elem.getValue().getRelation_type() == Relation_Type.Related) {
                    if (Relation_Hashmap.get(Relation_From_Single_To_Single_Elem.getKey()).get(Relation_By_Single_Elem.getKey()).getRelation_type() == Relation_Type.Related) {

                        Relation_Count inverted_Value = Relation_Hashmap.get(Relation_From_Single_To_Single_Elem.getKey()).get(Relation_By_Single_Elem.getKey());

                        Integer added_Count = Relation_From_Single_To_Single_Elem.getValue().getCount() + inverted_Value.getCount();

                        Relation_From_Single_To_Single_Elem.getValue().setRelation_type(Relation_Type.Parallel);
                        Relation_Hashmap.get(Relation_From_Single_To_Single_Elem.getKey()).get(Relation_By_Single_Elem.getKey()).setRelation_type(Relation_Type.Parallel);

                    }
                }
            }
        }
    }

    private void generate_one_Step_Execution(HashMap<Integer, HashMap<Integer, Relation_Count>> Relation_Hashmap) {
        for (Map.Entry<Integer, HashMap<Integer, Relation_Count>> Relation_By_Single_Elem : Relation_Hashmap.entrySet()) {
            if (Relation_By_Single_Elem.getValue().get(Relation_By_Single_Elem.getKey()).getRelation_type() == Relation_Type.Related) {
                Relation_By_Single_Elem.getValue().get(Relation_By_Single_Elem.getKey()).setRelation_type(Relation_Type.Follow_by_one);
            }
        }
    }

    private void generate_two_Step_Execution(HashMap<Integer, HashMap<Integer, Relation_Count>> Relation_Hashmap) {

    }

    private void Initialize_Relation_Hashmap(HashMap<Integer, HashMap<Integer, Relation_Count>> relation_hashmap) {
        List<Mining_Activity> ActivityList = Reader.getActivityList();
        for (Mining_Activity Y_Activity : ActivityList) {
            if (!relation_hashmap.containsKey(Y_Activity)) {
                HashMap<Integer, Relation_Count> newRow = new HashMap<>();
                for (Mining_Activity X_Activity : ActivityList) {
                    newRow.put(X_Activity.getNode_ID(), new Relation_Count());
                }
                relation_hashmap.put(Y_Activity.getNode_ID(), newRow);
            }
        }
    }

    private void check_For_Activity_Relation(HashMap<Integer, HashMap<Integer, Relation_Count>> relation_hashmap,
                                             List<Mining_Instance> single_mining_instance) {

        List<Mining_Instance> Working_List = new ArrayList<>(single_mining_instance);
        List<Integer> to_Work_On = new ArrayList<>();
        to_Work_On.add(0);
        while (!to_Work_On.isEmpty()) {
            int index = to_Work_On.get(0);
            Mining_Instance Instance_to_Get_Related_Elements = Working_List.get(to_Work_On.get(0));
            to_Work_On.remove(0);
            ListIterator<Mining_Instance> iter = Working_List.listIterator();

            //get Start iterator index
            while (iter.hasNext()) {
                if (iter.nextIndex() == index) {
                    //start from index to search for related activities
                    List<Mining_Instance> possible_Relations = new ArrayList<>();
                    iter.next();
                    boolean Relation_Found = false;
                    boolean Error = false;
                    while (iter.hasNext() && !Relation_Found) {
                        //search for working and finishing instance
                        Mining_Instance possible_Relation = iter.next();
                        if (possible_Relation.getActivity().equals(Instance_to_Get_Related_Elements.getActivity())
                                && possible_Relation.getActivity_Status().equals("Working")) {
                            //TODO Hier Verspätung eintragen (get_Related_Element Time + possible Relation Time)
                        } else if (possible_Relation.getActivity().equals(Instance_to_Get_Related_Elements.getActivity())
                                && possible_Relation.getActivity_Status().equals("Finished")) {
                            try {
                                LocalTime Timestamp = possible_Relation.getDuration();
                                boolean finished_find_Scheduled = false;

                                while (iter.hasNext() && !finished_find_Scheduled) {
                                    Mining_Instance to_Check_Relation = iter.next();

                                    if (to_Check_Relation.getDuration().isAfter(Timestamp)) {
                                        finished_find_Scheduled = true;
                                    } else if (to_Check_Relation.getActivity_Status().equals("Finished")) {
                                        throw new PM_Instance_Relation_Error("Instance relations can´t be resolved correctly");
                                    } else if (to_Check_Relation.getActivity_Status().equals("Scheduled")) {
                                        possible_Relations.add(to_Check_Relation);
                                    }
                                }
                                Relation_Found = true;
                            } catch (PM_Instance_Relation_Error e) {
                                Relation_Found = true;
                                Error = true;
                            }
                        }
                        if (Error) {
                            possible_Relations.clear();
                        }
                    }

                    //TODO wenn possible Relations leer, dann existiert hier ein Knoten ohne Nachfolger -> Final Task

                    for (Mining_Instance Relation : possible_Relations) {
                        to_Work_On.add(Working_List.indexOf(Relation));

                        if (relation_hashmap.containsKey(Instance_to_Get_Related_Elements.getActivity().getNode_ID())) {
                            HashMap<Integer, Relation_Count> related_to_Activity_Hashmap
                                    = relation_hashmap.get(Instance_to_Get_Related_Elements.getActivity().getNode_ID());

                            if (related_to_Activity_Hashmap.containsKey(Relation.getActivity().getNode_ID())) {
                                related_to_Activity_Hashmap.get(Relation.getActivity().getNode_ID()).incrementCount();
                                related_to_Activity_Hashmap.get(Relation.getActivity().getNode_ID()).setRelation_type(Relation_Type.Related);
                            }

                        } else {

                        }
                    }
                } else if (iter.nextIndex() > index) {
                    throw new RuntimeException("Error in Handling Iterator for Process Mining!");
                } else {
                    iter.next();
                }
            }
        }
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

    public void generateMaxRelationSet(HashMap<Integer, HashMap<Integer, Relation_Count>> Relation_Hashmap) {
        List<Relation_Places> Places = new ArrayList<>();
        List<Relation_Places> WorkingList = new ArrayList<>();

        for (Map.Entry<Integer, HashMap<Integer, Relation_Count>> Relation_By_Single_Elem : Relation_Hashmap.entrySet()) {

            for (Map.Entry<Integer, Relation_Count> Relation_From_Single_To_Single_Elem : Relation_By_Single_Elem.getValue().entrySet()) {

                Relation_By_Single_Elem.getValue();
                Relation_From_Single_To_Single_Elem.getValue();
            }
        }


    }
}
