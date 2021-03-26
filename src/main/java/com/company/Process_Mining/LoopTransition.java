package com.company.Process_Mining;

import com.company.Process_Mining.Base_Data.Mining_Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class LoopTransition {

    private Integer LoopID;
    private List<Integer> FromRelations;
    private List<Integer> ToRelations;

    public LoopTransition() {
        LoopID = 0;
        FromRelations = new ArrayList<>();
        ToRelations = new ArrayList<>();
    }

    public LoopTransition(Integer loopID, List<Integer> fromRelations, List<Integer> toRelations) {
        LoopID = loopID;
        FromRelations = fromRelations;
        ToRelations = toRelations;
    }

    public Integer getLoopID() {
        return LoopID;
    }

    public void setLoopID(Integer loopID) {
        LoopID = loopID;
    }

    public List<Integer> getFromRelations() {
        return FromRelations;
    }

    public void setFromRelations(List<Integer> fromRelations) {
        FromRelations = fromRelations;
    }

    public List<Integer> getToRelations() {
        return ToRelations;
    }

    public void setToRelations(List<Integer> toRelations) {
        ToRelations = toRelations;
    }

    //generates a Maximum Amount of Places which a Loop Transition COULD be connected to. (for Example
    //if there is a Place (a to b,c) it would generate 3 places (a to b) (a to c) and (a to b,c)) i.e. the Crossproduct
    // of all single Activities in a Loop Transition Place.
    public List<Transition_Relation> generateMaxPlaceSet(HashMap<Integer, Mining_Activity> Activity_Hashmap) {

        List<Transition_Relation> ResultList = new ArrayList<>();
        List<Relation_Places> MinSet = new ArrayList<>();
        for (Integer From : FromRelations) {
            for (Integer To : ToRelations) {
                if (!From.equals(To)) {
                    Relation_Places newPlace = new Relation_Places();
                    newPlace.getFrom().add(Activity_Hashmap.get(From));
                    newPlace.getTo().add(Activity_Hashmap.get(To));
                    MinSet.add(newPlace);
                }
            }
        }

        List<Relation_Places> Mark_For_Deletion = new ArrayList<>();
        List<Relation_Places> Working_List = new ArrayList<>(MinSet);
        List<Relation_Places> Unique_Place_List = new ArrayList<>(MinSet);
        List<Relation_Places> newPlaceWorking_List = new ArrayList<>();

        while (!Working_List.isEmpty()) {
            boolean foundnew = false;
            ListIterator<Relation_Places> first_Iterator_Right = Working_List.listIterator();
            while (first_Iterator_Right.hasNext()) {
                Relation_Places first_Relation_Place = first_Iterator_Right.next();
                ListIterator<Relation_Places> second_iterator_right = Working_List.listIterator();
                while (second_iterator_right.hasNext()) {
                    Relation_Places second_Relation_Place = second_iterator_right.next();
                    if (first_Relation_Place.has_Same_to(second_Relation_Place) && !first_Relation_Place.equals(second_Relation_Place)) {
                        Relation_Places biggerSet = new Relation_Places();
                        biggerSet.getFrom().addAll(first_Relation_Place.getFrom());
                        for (Mining_Activity to_Add : second_Relation_Place.getFrom()) {
                            if (!biggerSet.getFrom().contains(to_Add)) {
                                biggerSet.getFrom().add(to_Add);
                            }
                        }
                        for (Mining_Activity To : first_Relation_Place.getTo()) {
                            if (!biggerSet.getTo().contains(To)) {
                                biggerSet.getTo().add(To);
                            }
                            if (!Mark_For_Deletion.contains(first_Relation_Place)) {
                                Mark_For_Deletion.add(first_Relation_Place);
                            }
                            if (!Mark_For_Deletion.contains(second_Relation_Place)) {
                                Mark_For_Deletion.add(second_Relation_Place);
                            }
                            Unique_Place_List.add(biggerSet);
                            newPlaceWorking_List.add(biggerSet);
                            foundnew = true;
                        }
                    }
                }
            }
            ListIterator<Relation_Places> first_Iterator_Left = Working_List.listIterator();
            while (first_Iterator_Left.hasNext()) {
                Relation_Places first_Relation_Place = first_Iterator_Left.next();
                ListIterator<Relation_Places> second_iterator_right = Working_List.listIterator();
                while (second_iterator_right.hasNext()) {
                    Relation_Places second_Relation_Place = second_iterator_right.next();
                    if (first_Relation_Place.has_Same_From(second_Relation_Place) && !first_Relation_Place.equals(second_Relation_Place)) {
                        Relation_Places biggerSet = new Relation_Places();
                        biggerSet.getTo().addAll(first_Relation_Place.getTo());
                        for (Mining_Activity to_Add : second_Relation_Place.getTo()) {
                            if (!biggerSet.getTo().contains(to_Add)) {
                                biggerSet.getTo().add(to_Add);
                            }
                        }
                        for (Mining_Activity From : first_Relation_Place.getFrom()) {
                            if (!biggerSet.getFrom().contains(From)) {
                                biggerSet.getFrom().add(From);
                            }
                            if (!Mark_For_Deletion.contains(first_Relation_Place)) {
                                Mark_For_Deletion.add(first_Relation_Place);
                            }
                            if (!Mark_For_Deletion.contains(second_Relation_Place)) {
                                Mark_For_Deletion.add(second_Relation_Place);
                            }
                            Unique_Place_List.add(biggerSet);
                            newPlaceWorking_List.add(biggerSet);

                            foundnew = true;
                        }
                    }
                }
            }

            if (!foundnew) {
                Working_List.clear();
            } else {
                Working_List.removeAll(Mark_For_Deletion);
                Working_List.addAll(newPlaceWorking_List);
                newPlaceWorking_List.clear();
                Clean_Up_Place_List(Working_List);
            }
        }

        Clean_Up_Place_List(Unique_Place_List);
        for (Relation_Places PossibleLoopPlace : Unique_Place_List) {
            Transition_Relation newLoopFromLeftRelation = new Transition_Relation(LoopID, PossibleLoopPlace, true);
            Transition_Relation newLoopFromRightRelation = new Transition_Relation(LoopID, PossibleLoopPlace, false);
            ResultList.add(newLoopFromLeftRelation);
            ResultList.add(newLoopFromRightRelation);
        }
        return ResultList;
    }


    //Helper Method to Delete Copies of Places
    private void Clean_Up_Place_List(List<Relation_Places> place_list) {
        ListIterator<Relation_Places> Place_Iterator_One = place_list.listIterator();
        List<Relation_Places> Mark_For_Deletion = new ArrayList<>();
        while (Place_Iterator_One.hasNext()) {
            Relation_Places place_one = Place_Iterator_One.next();
            ListIterator<Relation_Places> Place_Iterator_Two = place_list.listIterator();
            if (!Mark_For_Deletion.contains(place_one)) {
                while (Place_Iterator_Two.hasNext()) {
                    Relation_Places place_two = Place_Iterator_Two.next();
                    if (!Mark_For_Deletion.contains(place_two) && !place_one.equals(place_two) && place_one.has_Same_From(place_two) && place_one.has_Same_to(place_two)) {
                        Mark_For_Deletion.add(place_two);
                    }
                }
            }
        }
        place_list.removeAll(Mark_For_Deletion);
    }

}

