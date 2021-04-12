package com.company.Process_Mining;

import com.company.Process_Mining.Base_Data.Complete_Time_Activity;
import com.company.Process_Mining.Base_Data.Mining_Activity;
import com.company.Process_Mining.Base_Data.Mining_Resource;
import com.company.Process_Mining.Base_Data.Mining_User;
import guru.nidi.graphviz.attribute.Attributes;
import guru.nidi.graphviz.attribute.ForLink;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

import static com.company.Process_Mining.Relation_Type.Related;
import static guru.nidi.graphviz.attribute.Attributes.attr;
import static guru.nidi.graphviz.attribute.Rank.RankDir.LEFT_TO_RIGHT;
import static guru.nidi.graphviz.model.Factory.*;

public class Mining_Logger {

    private HashMap<Integer, Mining_Activity> activity_hashmap;
    private HashMap<Mining_User, HashMap<Mining_User, Relation_Count>> user_relation_hashmap;
    private HashMap<Mining_Activity, Complete_Time_Activity> time_log_by_activity;
    private List<Transition_Relation> All_Places;
    private Integer PlaceID;
    private Integer Total_User_Relation_Count;
    private HashMap<Mining_Resource, List<Timed_Resource_Usage_By_Activity>> Timed_Mining_Activity_By_Resource;
    private HashMap<Mining_User, List<Timed_User_Usage_By_Activity>> Timed_Mining_Activity_per_User;


    private HashMap<Integer, List<Integer>> Transitions_To_Places;
    private HashMap<Integer, Relation_Places> Places_to_Relations;
    private HashMap<Integer, List<Integer>> Places_to_Transitions;


    public Mining_Logger(HashMap<Integer, Mining_Activity> activity_hashmap,
                         HashMap<Mining_User, HashMap<Mining_User, Relation_Count>> user_relation_hashmap,
                         Integer total_User_Relation_Count,
                         HashMap<Mining_User, List<Timed_User_Usage_By_Activity>> Timed_Mining_Activity_per_User,
                         HashMap<Mining_Resource, List<Timed_Resource_Usage_By_Activity>> Timed_Mining_Activity_By_Resource,
                         HashMap<Mining_Activity, Complete_Time_Activity> time_log_by_activity,
                         List<Transition_Relation> all_Places) {
        Total_User_Relation_Count = total_User_Relation_Count;
        this.activity_hashmap = activity_hashmap;
        this.user_relation_hashmap = user_relation_hashmap;
        this.time_log_by_activity = time_log_by_activity;
        this.All_Places = all_Places;
        this.Timed_Mining_Activity_By_Resource = Timed_Mining_Activity_By_Resource;
        this.Timed_Mining_Activity_per_User = Timed_Mining_Activity_per_User;
        PlaceID = 0;

        Transitions_To_Places = new HashMap<>();
        Places_to_Relations = new HashMap<>();
        Places_to_Transitions = new HashMap<>();

    }

    public void LogMining() {

        GeneratePrintablePlaces();
        Write_User_Activity_Log_To_File();
        Write_Resource_Activity_Log_To_File();
        Write_Delay_On_Activity_To_File();
        Write_Workingtime_On_Activity_To_File();
        Write_Completetime_On_Activity_To_File();
        try {
            createPetriNet();
            createUserRelationGraph();
        } catch (IOException e) {
            System.out.println("Graphviz Error Detected. Possible File Problem.");
        }
    }

    private void Write_Resource_Activity_Log_To_File() {
        int maxDay = 0;
        for (Map.Entry<Mining_Resource, List<Timed_Resource_Usage_By_Activity>> Resource_Activities : Timed_Mining_Activity_By_Resource.entrySet()) {
            for (Timed_Resource_Usage_By_Activity Activity : Resource_Activities.getValue()) {
                if (Activity.getDay() > maxDay) {
                    maxDay = Activity.getDay();
                }
            }
        }
        HashMap<Mining_Resource, List<List<Timed_Resource_Usage_By_Activity>>> Print_HashMap = new HashMap<>();
        for (Map.Entry<Mining_Resource, List<Timed_Resource_Usage_By_Activity>> Resource_Activities : Timed_Mining_Activity_By_Resource.entrySet()) {
            List<Timed_Resource_Usage_By_Activity> ActivityList = Resource_Activities.getValue();
            List<List<Timed_Resource_Usage_By_Activity>> Sorted_Resource_Activity_List = new ArrayList<>();
            for (int i = 0; i <= maxDay; i++) {
                List<Timed_Resource_Usage_By_Activity> newDayList = new ArrayList<>();
                Sorted_Resource_Activity_List.add(newDayList);
            }

            for (Timed_Resource_Usage_By_Activity Timed_Activity : ActivityList) {
                List<Timed_Resource_Usage_By_Activity> Daylist = Sorted_Resource_Activity_List.get(Timed_Activity.getDay());
                if (Daylist.isEmpty()) {
                    Daylist.add(Timed_Activity);
                } else {
                    ListIterator<Timed_Resource_Usage_By_Activity> Iter = Daylist.listIterator();
                    boolean added = false;
                    while (Iter.hasNext() && !added) {
                        Timed_Resource_Usage_By_Activity next = Iter.next();
                        if (Timed_Activity.getTime().isBefore(next.getTime())) {
                            Iter.previous();
                            Iter.add(Timed_Activity);
                            added = true;
                        }
                    }
                    if (!added) {
                        Daylist.add(Timed_Activity);
                    }
                }
            }
            if (Print_HashMap.containsKey(Resource_Activities.getKey())) {
                System.out.println("Error at puting Userlist to Print_HashMap in Write_User_Activity_Log_To_File");
            } else {
                Print_HashMap.put(Resource_Activities.getKey(), Sorted_Resource_Activity_List);
            }
        }
        printResoureHashMapToFile(Print_HashMap);
    }

    private void printResoureHashMapToFile(HashMap<Mining_Resource, List<List<Timed_Resource_Usage_By_Activity>>> print_hashMap) {
        try {
            for (Map.Entry<Mining_Resource, List<List<Timed_Resource_Usage_By_Activity>>> Resource_Log : print_hashMap.entrySet()) {
                int Actvalue = 0;
                int Sortnumber = 1;
                int day = 0;
                for (List<Timed_Resource_Usage_By_Activity> DayList : Resource_Log.getValue()) {
                    String ResultUsagepath = "MiningResults/ResourceUsage_" + Resource_Log.getKey().getName() + "_Day" + day + ".txt";
                    String ResultTimepath = "MiningResults/ResourceTime" + Resource_Log.getKey().getName() + "_Day" + day + ".txt";
                    File ResourceUsage = new File(ResultUsagepath);
                    FileWriter Writer = new FileWriter(ResourceUsage);
                    File ResourceTime = new File(ResultTimepath);
                    FileWriter ResourceTimeWriter = new FileWriter(ResourceTime);
                    for (Timed_Resource_Usage_By_Activity Single_Resource_Activity : DayList) {
                        Actvalue = Actvalue + Single_Resource_Activity.getCount();
                        Writer.write(Sortnumber + "|" + Resource_Log.getKey().getName() + "|" + Single_Resource_Activity.getActivity().getActivity_Name() + "|" + Single_Resource_Activity.getTime().toString() + "|" + Actvalue);
                        Writer.write(System.getProperty("line.separator"));
                        ResourceTimeWriter.write(Sortnumber + "|" + Resource_Log.getKey().getName() + "|" + Single_Resource_Activity.getTime().toString() + "|" + Actvalue);
                        ResourceTimeWriter.write(System.getProperty("line.separator"));
                        Sortnumber++;
                    }
                    day++;
                    Sortnumber = 1;
                    Writer.close();
                    ResourceTimeWriter.close();
                }
            }
            System.out.println("Wrote ResourceUsage to File ResourceUsage.txt");
            System.out.println("Wrote ResourceTimeWriter to File ResourceTime.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Write_User_Activity_Log_To_File() {
        int maxDay = 0;
        for (Map.Entry<Mining_User, List<Timed_User_Usage_By_Activity>> User_Activities : Timed_Mining_Activity_per_User.entrySet()) {
            for (Timed_User_Usage_By_Activity Activity : User_Activities.getValue()) {
                if (Activity.getDay() > maxDay) {
                    maxDay = Activity.getDay();
                }
            }
        }
        HashMap<Mining_User, List<List<Timed_User_Usage_By_Activity>>> Print_HashMap = new HashMap<>();
        for (Map.Entry<Mining_User, List<Timed_User_Usage_By_Activity>> User_Activities : Timed_Mining_Activity_per_User.entrySet()) {
            List<Timed_User_Usage_By_Activity> ActivityList = User_Activities.getValue();
            List<List<Timed_User_Usage_By_Activity>> Sorted_User_Activity_List = new ArrayList<>();
            for (int i = 0; i <= maxDay; i++) {
                List<Timed_User_Usage_By_Activity> newDayList = new ArrayList<>();
                Sorted_User_Activity_List.add(newDayList);
            }

            for (Timed_User_Usage_By_Activity Timed_Activity : ActivityList) {
                List<Timed_User_Usage_By_Activity> Daylist = Sorted_User_Activity_List.get(Timed_Activity.getDay());
                if (Daylist.isEmpty()) {
                    Daylist.add(Timed_Activity);
                } else {
                    ListIterator<Timed_User_Usage_By_Activity> Iter = Daylist.listIterator();
                    boolean added = false;
                    while (Iter.hasNext() && !added) {
                        Timed_User_Usage_By_Activity next = Iter.next();
                        if (Timed_Activity.getTime().isBefore(next.getTime())) {
                            Iter.previous();
                            Iter.add(Timed_Activity);
                            added = true;
                        }
                    }
                    if (!added) {
                        Daylist.add(Timed_Activity);
                    }
                }
            }
            if (Print_HashMap.containsKey(User_Activities.getKey())) {
                System.out.println("Error at puting Userlist to Print_HashMap in Write_User_Activity_Log_To_File");
            } else {
                Print_HashMap.put(User_Activities.getKey(), Sorted_User_Activity_List);
            }
        }
        printUserHashMapToFile(Print_HashMap);
    }

    private void printUserHashMapToFile(HashMap<Mining_User, List<List<Timed_User_Usage_By_Activity>>> print_hashMap) {
        try {
            for (Map.Entry<Mining_User, List<List<Timed_User_Usage_By_Activity>>> User_Log : print_hashMap.entrySet()) {
                int day = 0;
                int sortnumber = 1;
                for (List<Timed_User_Usage_By_Activity> DayList : User_Log.getValue()) {

                    String UserUsageDayPath = "MiningResults/UserUsage " + User_Log.getKey().getName() + " Day" + day + ".txt";
                    String UserTimeDayPath = "MiningResults/UserAtActivity " + User_Log.getKey().getName() + " Day" + day + ".txt";
                    File UserUsage = new File(UserUsageDayPath);
                    File UserTime = new File(UserTimeDayPath);
                    FileWriter UsageWriter = new FileWriter(UserUsage);
                    FileWriter UserTimeWriter = new FileWriter(UserTime);
                    for (Timed_User_Usage_By_Activity Single_User_Activity : DayList) {
                        if (Single_User_Activity.isFinishing()) {
                            UsageWriter.write(sortnumber + "|" + Single_User_Activity.getActivity().getActivity_Name() + "|" + Single_User_Activity.getTime().toString() + "|" + "0");
                            UsageWriter.write(System.getProperty("line.separator"));
                            UserTimeWriter.write(sortnumber + "|" + Single_User_Activity.getTime().toString() + "|" + "0");
                            UserTimeWriter.write(System.getProperty("line.separator"));
                        } else {
                            UsageWriter.write(sortnumber + "|" + Single_User_Activity.getActivity().getActivity_Name() + "|" + Single_User_Activity.getTime().toString() + "|" + "1");
                            UsageWriter.write(System.getProperty("line.separator"));
                            UserTimeWriter.write(sortnumber + "|" + Single_User_Activity.getTime().toString() + "|" + "1");
                            UserTimeWriter.write(System.getProperty("line.separator"));
                        }
                        sortnumber++;
                    }
                    day++;
                    sortnumber = 1;
                    UsageWriter.close();
                    UserTimeWriter.close();
                }
            }
            System.out.println("Wrote UserUsage to File UserUsage.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Write_Delay_On_Activity_To_File() {
        try {
            File Delay = new File("MiningResults/Activities/Delay.txt");
            FileWriter Writer = new FileWriter(Delay);
            int Sortnumber = 1;
            for (Map.Entry<Mining_Activity, Complete_Time_Activity> activity_Times : time_log_by_activity.entrySet()) {
                String ActivityDelayTimePath = "MiningResults/Activities/Delaytime" + activity_Times.getKey().getActivity_Name() + ".txt";
                File ActivityDelayTime = new File(ActivityDelayTimePath);
                FileWriter SingleActivityFile = new FileWriter(ActivityDelayTime);
                for (Time_Log_Data Single_Delay : activity_Times.getValue().getSingle_Instance_Activity_Time()) {

                    String result = "" + Single_Delay.getDelay().getSeconds();
                    /*int result = (int)Single_Delay.getDelay().getSeconds();
                    int Dhours = (int) (Dseconds / 3600);
                    Dseconds -= Dhours * 3600;
                    int Dminutes = (int) (Dseconds / 60);
                    Dseconds -= Dminutes * 60;
                    String result = ""+ Dhours + ":" + Dminutes + ":" + Dseconds;*/
                    SingleActivityFile.write(Sortnumber + "|" + activity_Times.getKey().getActivity_Name() + "|" + result);
                    SingleActivityFile.write(System.getProperty("line.separator"));
                    Writer.write(activity_Times.getKey().getActivity_Name() + "|" + result);
                    Writer.write(System.getProperty("line.separator"));
                    Sortnumber++;
                }
                SingleActivityFile.close();
                Sortnumber = 1;
            }
            Writer.close();
            System.out.println("Wrote Delay to File Delay.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Write_Completetime_On_Activity_To_File() {
        try {
            File Complete = new File("MiningResults/Activities/Completetime.txt");
            FileWriter Writer = new FileWriter(Complete);
            int Sortnumber = 1;
            for (Map.Entry<Mining_Activity, Complete_Time_Activity> activity_Times : time_log_by_activity.entrySet()) {
                String ActivityCompleteTimePath = "MiningResults/Activities/Completetime" + activity_Times.getKey().getActivity_Name() + ".txt";
                File ActivityCompleteTime = new File(ActivityCompleteTimePath);
                FileWriter SingleActivityFile = new FileWriter(ActivityCompleteTime);
                for (Time_Log_Data Single_Delay : activity_Times.getValue().getSingle_Instance_Activity_Time()) {

                    String result = "" + (int) Single_Delay.getWorkingtime().plus(Single_Delay.getDelay()).getSeconds();

                    /*int Cseconds = (int)Single_Delay.getWorkingtime().plus(Single_Delay.getDelay()).getSeconds();
                    int Chours = (int) (Cseconds / 3600);
                    Cseconds -= Chours * 3600;
                    int Cminutes = (int) (Cseconds / 60);
                    Cseconds -= Cminutes * 60;
                    String result = ""+ Chours + ":" + Cminutes +":" + Cseconds;*/
                    SingleActivityFile.write(Sortnumber + "|" + activity_Times.getKey().getActivity_Name() + "|" + result);
                    SingleActivityFile.write(System.getProperty("line.separator"));
                    Writer.write(activity_Times.getKey().getActivity_Name() + "|" + result);
                    Writer.write(System.getProperty("line.separator"));
                    Sortnumber++;
                }
                SingleActivityFile.close();
                Sortnumber = 1;
            }
            Writer.close();
            System.out.println("Wrote Completetime to File Completetime.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Write_Workingtime_On_Activity_To_File() {
        try {
            File Working = new File("MiningResults/Activities/Workingtime.txt");
            FileWriter Writer = new FileWriter(Working);
            int Sortnumber = 1;
            for (Map.Entry<Mining_Activity, Complete_Time_Activity> activity_Times : time_log_by_activity.entrySet()) {
                String ActivityWorkingTimePath = "MiningResults/Activities/Workingtime" + activity_Times.getKey().getActivity_Name() + ".txt";
                File ActivityWorkingTime = new File(ActivityWorkingTimePath);
                FileWriter SingleActivityFile = new FileWriter(ActivityWorkingTime);
                for (Time_Log_Data Single_Delay : activity_Times.getValue().getSingle_Instance_Activity_Time()) {
                    String result = "" + (int) Single_Delay.getWorkingtime().getSeconds();
                    SingleActivityFile.write(Sortnumber + "|" + activity_Times.getKey().getActivity_Name() + "|" + result);
                    SingleActivityFile.write(System.getProperty("line.separator"));
                    Writer.write(activity_Times.getKey().getActivity_Name() + "|" + result);
                    Writer.write(System.getProperty("line.separator"));
                    Sortnumber++;
                }
                SingleActivityFile.close();
                Sortnumber = 1;
            }
            Writer.close();
            System.out.println("Wrote Workingtime to File Workingtime.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void GeneratePrintablePlaces() {

        for (Transition_Relation Relation : All_Places) {
            if (!Transitions_To_Places.containsKey(Relation.getActivityID())) {
                Transitions_To_Places.put(Relation.getActivityID(), new ArrayList<>());
            }
            boolean Place_to_Relation_Found = false;
            for (Map.Entry<Integer, Relation_Places> Place_to_Relation : Places_to_Relations.entrySet()) {
                if (Place_to_Relation.getValue().has_Same_From(Relation.getConnected_To_Place()) &&
                        Place_to_Relation.getValue().has_Same_to(Relation.getConnected_To_Place())) {
                    Place_to_Relation_Found = true;
                    break;
                }
            }
            if (!Place_to_Relation_Found) {
                Integer newPlaceID = getUniquePlaceID();
                Places_to_Relations.put(newPlaceID, Relation.getConnected_To_Place());
                if (!Places_to_Transitions.containsKey(newPlaceID)) {
                    Places_to_Transitions.put(newPlaceID, new ArrayList<>());
                } else {
                    System.out.println("Routing ERROR: Place couldn´t be bound to Relation");
                }
            }
        }

        for (Transition_Relation Relation : All_Places) {
            if (Relation.isFromTransaction()) {
                Integer Con_Transition_To_PlaceID = 0;
                for (Map.Entry<Integer, Relation_Places> Place_to_Relation : Places_to_Relations.entrySet()) {
                    Relation_Places toSearch = Place_to_Relation.getValue();
                    if (toSearch.has_Same_From(Relation.getConnected_To_Place()) && toSearch.has_Same_to(Relation.getConnected_To_Place())) {
                        Con_Transition_To_PlaceID = Place_to_Relation.getKey();
                        break;
                    }
                }
                if (!Transitions_To_Places.get(Relation.getActivityID()).contains(Con_Transition_To_PlaceID)) {
                    Transitions_To_Places.get(Relation.getActivityID()).add(Con_Transition_To_PlaceID);
                }
            } else {
                Integer Con_Place_To_TransitionID = 0;
                for (Map.Entry<Integer, Relation_Places> Place_to_Relation : Places_to_Relations.entrySet()) {
                    Relation_Places toSearch = Place_to_Relation.getValue();
                    if (toSearch.has_Same_From(Relation.getConnected_To_Place()) && toSearch.has_Same_to(Relation.getConnected_To_Place())) {
                        Con_Place_To_TransitionID = Place_to_Relation.getKey();
                        break;
                    }
                }
                if (!Places_to_Transitions.get(Con_Place_To_TransitionID).contains(Relation.getActivityID())) {
                    Places_to_Transitions.get(Con_Place_To_TransitionID).add(Relation.getActivityID());
                }
            }
        }
    }

    public void createUserRelationGraph() throws IOException {
        List<Node> Nodelist = new ArrayList<>();
        for (Map.Entry<Mining_User, HashMap<Mining_User, Relation_Count>> User_From : user_relation_hashmap.entrySet()) {
            for (Map.Entry<Mining_User, Relation_Count> User_to : User_From.getValue().entrySet()) {
                List<Attributes<? extends ForLink>> Attributes = new ArrayList<>();
                List<Attributes<? extends ForLink>> Style = new ArrayList<>();
                if (User_to.getValue().getRelation_type() == Related) {
                    Attributes.add(attr("weight", (float) User_to.getValue().getCount() / Total_User_Relation_Count));
                    Attributes.add(attr("label", (float) User_to.getValue().getCount() / Total_User_Relation_Count));
                    Nodelist.add(node(User_From.getKey().getName())
                            .link(to(node(User_to.getKey().getName()))
                                    .with(Attributes)));
                }
            }
        }
        Graph g = graph("UserRelation").directed()
                .graphAttr().with(Rank.dir(LEFT_TO_RIGHT))
                .linkAttr().with("class", "link-class")
                .with(Nodelist);
        Graphviz.fromGraph(g).height(300).render(Format.PNG).toFile(new File("MiningResults/UserRelation.png"));
    }

    public void createPetriNet() throws IOException {
        HashMap<Integer, MutableNode> Placelist = new HashMap<>();
        HashMap<Integer, MutableNode> TransitionList = new HashMap<>();

        /*List<Node> Resultlist = new ArrayList<>();
        for(Map.Entry<Integer,List<Integer>> Place_to_Transition: Places_to_Transitions.entrySet()){
            List<String> Transitionname = new ArrayList<>();
            for (Integer i :Place_to_Transition.getValue()) {
                Transitionname.add(activity_hashmap.get(i).getActivity_Name());
            }
            MutableNode n = mutNode(Place_to_Transition.getKey().toString());
            for (String string: Transitionname) {
                n.addlink(to(mutNode(string)));
                n.
            }
            Resultlist.add(n);
        }
        for(Map.Entry<Integer,List<Integer>> Transition_to_Place: Transitions_To_Places.entrySet()){
            List<String> PlaceIDs = new ArrayList<>();
            for (Integer i :Transition_to_Place.getValue()) {
                PlaceIDs.add(i.toString());
            }
            String name = activity_hashmap.get(Transition_to_Place.getKey()).getActivity_Name();
            Node n = node(name);
            for (String ID: PlaceIDs) {
                n.link(to(node(ID)));
            }
            Resultlist.add(n);
        }*/

        for (Map.Entry<Integer, List<Integer>> Place_to_Transition : Places_to_Transitions.entrySet()) {
            List<Attributes<? extends ForLink>> Attributes = new ArrayList<>();
            Placelist.put(Place_to_Transition.getKey(), mutNode("" + Place_to_Transition.getKey()).add(attr("shape1", Shape.CIRCLE)));
        }

        for (Map.Entry<Integer, List<Integer>> Transition_to_Place : Transitions_To_Places.entrySet()) {
            Integer visited = time_log_by_activity.get(activity_hashmap.get(Transition_to_Place.getKey())).getSingle_Instance_Activity_Time().size();

            Duration Delay = time_log_by_activity.get(activity_hashmap.get(Transition_to_Place.getKey())).getComplete_Delay();
            int Dseconds = (int) Delay.getSeconds();
            int Dhours = (int) (Dseconds / 3600);
            Dseconds -= Dhours * 3600;
            int Dminutes = (int) (Dseconds / 60);
            Dseconds -= Dminutes * 60;
            String Delaystring = Dhours + ":" + Dminutes + ":" + Dseconds;

            Duration Workingtime = time_log_by_activity.get(activity_hashmap.get(Transition_to_Place.getKey())).getComplete_WorkingTime();
            int Wseconds = (int) Workingtime.getSeconds();
            int Whours = (int) (Wseconds / 3600);
            Wseconds -= Whours * 3600;
            int Wminutes = (int) (Wseconds / 60);
            Wseconds -= Wminutes * 60;
            String WorkingtimeString = Whours + ":" + Wminutes + ":" + Wseconds;

            Duration Completetime = time_log_by_activity.get(activity_hashmap.get(Transition_to_Place.getKey())).getComplete_Time();
            int Cseconds = (int) Completetime.getSeconds();
            int Chours = (int) (Cseconds / 3600);
            Cseconds -= Chours * 3600;
            int Cminutes = (int) (Cseconds / 60);
            Cseconds -= Cminutes * 60;
            String CompletetimeString = Chours + ":" + Cminutes + ":" + Cseconds;

            TransitionList.put(Transition_to_Place.getKey(), mutNode(activity_hashmap.get(Transition_to_Place.getKey()).getActivity_Name()
                    + "\n Visited: " + visited.toString()
                    + "\n Delay: " + Delaystring
                    + "\n Working: " + WorkingtimeString
                    + "\n Complete: " + CompletetimeString).add(attr("shape", Shape.BOX)));
        }

        for (Map.Entry<Integer, List<Integer>> Place_to_Transition : Places_to_Transitions.entrySet()) {
            List<MutableNode> toLink = new ArrayList<>();
            for (Integer TransitionID : Place_to_Transition.getValue()) {
                toLink.add(TransitionList.get(TransitionID));
            }
            MutableNode n = Placelist.get(Place_to_Transition.getKey());
            n.addLink(toLink);
            Placelist.replace(Place_to_Transition.getKey(), n);
        }

        for (Map.Entry<Integer, List<Integer>> Transition_to_Place : Transitions_To_Places.entrySet()) {
            List<MutableNode> toLink = new ArrayList<>();
            for (Integer PlaceID : Transition_to_Place.getValue()) {
                toLink.add(Placelist.get(PlaceID));
            }
            MutableNode n = TransitionList.get(Transition_to_Place.getKey());
            n.addLink(toLink);
            TransitionList.replace(Transition_to_Place.getKey(), n);
        }

        List<MutableNode> Resultlist = new ArrayList<>();
        for (Map.Entry<Integer, MutableNode> Place : Placelist.entrySet()) {
            Resultlist.add(Place.getValue());
        }

        for (Map.Entry<Integer, MutableNode> Transition : TransitionList.entrySet()) {
            Resultlist.add(Transition.getValue());
        }


        /*for (Map.Entry<Mining_User,HashMap<Mining_User,Relation_Count>> User_From : user_relation_hashmap.entrySet()) {
            for (Map.Entry<Mining_User,Relation_Count> User_to : User_From.getValue().entrySet()){
                List<Attributes<? extends ForLink>> Attributes = new ArrayList<>();
                List<Attributes<? extends ForLink>> Style = new ArrayList<>();
                if(User_to.getValue().getRelation_type() == Related) {
                    Attributes.add(attr("weight", User_to.getValue().getCount()));
                    Attributes.add(attr("label",User_to.getValue().getCount()));
                    Nodelist.add(node(User_From.getKey().getName())
                            .link(to(node(User_to.getKey().getName()))
                                    .with(Attributes)));
                }
            }
        }*/
        Graph g = graph("PetriNet").directed()
                .graphAttr().with(Rank.dir(LEFT_TO_RIGHT))
                .linkAttr().with("class", "link-class")
                .with(Resultlist);
        Graphviz.fromGraph(g).height(300).render(Format.PNG).toFile(new File("MiningResults/PetriNet.png"));
    }

    private Integer getUniquePlaceID() {
        PlaceID++;
        return PlaceID;
    }
}
