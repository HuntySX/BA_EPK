package com.company.Process_Mining;

import java.util.List;

public class Print_Place {
    private Integer id;
    private List<Print_Transition> Transitions;

    public Print_Place(Integer id, List<Print_Transition> Transitions) {
        this.id = id;
        this.Transitions = Transitions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Print_Transition> getPlace() {
        return Transitions;
    }

    public void setPlace(List<Print_Transition> place) {
        Transitions = place;
    }
}
