package com.company.EPK;

import java.util.List;

public class Event extends Node {
    private String Event_Tag;
    private int successor = 1;
    private boolean is_Start_Event;
    private boolean is_End_Event;

    //Konstruktor Intermetierende Events

    public Event(List<Node> Next_Elem, int ID, String Event_Tag) {
        super(Next_Elem, ID);
        this.Event_Tag = Event_Tag;
        this.is_Start_Event = false;
        this.is_End_Event = false;

    }

    //Konstruktor Start Event

    public Event(List<Node> Next_Elem, String Event_Tag, boolean is_Start_Event) {
        super(Next_Elem, 0);
        this.Event_Tag = Event_Tag;
        this.is_Start_Event = is_Start_Event;
        this.is_End_Event = false;

    }


    //Konstruktor End Event

    public Event(int ID, String Event_Tag, boolean is_End_Event) {
        super(null, ID);
        this.Event_Tag = Event_Tag;
        this.is_Start_Event = false;
        this.is_End_Event = is_End_Event;

    }


    //Allgemeiner Standart Konstruktor
    public Event(List<Node> Next_Elem, int ID, String Event_Tag, boolean is_Start_Event, boolean is_End_Event) {
        super(Next_Elem, ID);
        this.Event_Tag = Event_Tag;
        this.is_Start_Event = is_Start_Event;
        this.is_End_Event = is_End_Event;

    }


}
