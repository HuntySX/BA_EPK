package com.company.EPK;

import java.util.List;

public class Event extends EPK_Node implements Printable_Node, Is_Tagged {
    private String Event_Tag;
    private final int successor = 1;
    private boolean is_Start_Event;
    private boolean is_End_Event;

    //Konstruktor Intermetierende Events

    public Event(List<EPK_Node> Next_Elem, int ID, String Event_Tag) {
        super(Next_Elem, ID);
        if (Event_Tag == null) {
            String a = "Event ";
            String b = Integer.toString(ID);
            a = a.concat(b);
            this.Event_Tag = a;
        } else {
            this.Event_Tag = Event_Tag;
        }
        this.is_Start_Event = false;
        this.is_End_Event = false;

    }

    //Konstruktor Start Event

    public Event(List<EPK_Node> Next_Elem, int ID, String Event_Tag, boolean is_Start_Event) {
        super(Next_Elem, ID);
        if (Event_Tag == null) {
            String a = "Event ";
            String b = Integer.toString(ID);
            a = a.concat(b);
            this.Event_Tag = a;
        } else {
            this.Event_Tag = Event_Tag;
        }
        this.is_Start_Event = is_Start_Event;
        this.is_End_Event = false;

    }


    //Konstruktor End Event

    public Event(int ID, String Event_Tag, boolean is_End_Event) {
        super(null, ID);
        if (Event_Tag == null) {
            String a = "Event ";
            String b = Integer.toString(ID);
            a = a.concat(b);
            this.Event_Tag = a;
        } else {
            this.Event_Tag = Event_Tag;
        }
        this.is_Start_Event = false;
        this.is_End_Event = is_End_Event;

    }


    //Allgemeiner Standart Konstruktor
    public Event(List<EPK_Node> Next_Elem, int ID, String Event_Tag, boolean is_Start_Event, boolean is_End_Event) {
        super(Next_Elem, ID);
        if (Event_Tag == null) {
            String a = "Event ";
            String b = Integer.toString(ID);
            a = a.concat(b);
            this.Event_Tag = a;
        } else {
            this.Event_Tag = Event_Tag;
        }
        this.is_Start_Event = is_Start_Event;
        this.is_End_Event = is_End_Event;

    }

    public boolean is_Start_Event() {
        return is_Start_Event;
    }

    public void setIs_Start_Event(boolean is_Start_Event) {
        this.is_Start_Event = is_Start_Event;
    }

    public boolean is_End_Event() {
        return is_End_Event;
    }

    public void setIs_End_Event(boolean is_End_Event) {
        this.is_End_Event = is_End_Event;
    }

    public String getEvent_Tag() {
        return Event_Tag;
    }

    public void setEvent_Tag(String Tag) {
        this.Event_Tag = Tag;
    }


    @Override
    public boolean CheckSettings() {
        boolean Check = true;

        if (Event_Tag == null || Event_Tag.equals("")) {
            Check = false;
        }
        return Check;
    }

    public String getTag() {
        return Event_Tag;
    }

    public Event returnUpperClass() {
        return this;
    }
}
