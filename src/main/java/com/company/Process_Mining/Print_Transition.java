package com.company.Process_Mining;

import java.util.ArrayList;
import java.util.List;

public class Print_Transition {
    private Integer Activity_ID;
    private List<Integer> to_Place;

    public Print_Transition(Integer activity_name, ArrayList<Integer> print_places_ID) {
        to_Place = print_places_ID;
        Activity_ID = activity_name;
    }

    public Integer getActivity_ID() {
        return Activity_ID;
    }

    public void setActivity_ID(Integer activity_ID) {
        Activity_ID = activity_ID;
    }

    public List<Integer> getTo_Place() {
        return to_Place;
    }

    public void setTo_Place(List<Integer> to_Place) {
        this.to_Place = to_Place;
    }
}
