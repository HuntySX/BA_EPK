package com.company.EPK;

import com.company.Enums.Start_Event_Type;
import com.company.Run.Discrete_Event_Generator;

import java.util.List;

public class Start_Event extends Event implements Printable_Node, Is_Tagged {

    private Start_Event_Type start_event_type;
    private Discrete_Event_Generator Generator;
    private int to_Instantiate;

    public Start_Event(Start_Event_Type type, int ID, Discrete_Event_Generator generator, int to_Instantiate, List<EPK_Node> Next_Elem, String Event_Tag, boolean is_Start_Event) {
        super(Next_Elem, ID, Event_Tag, is_Start_Event); //TODO ID HIER FALSCH, Muss richtig weitergegeben werden!!!!(Update Mai: ???)
        this.to_Instantiate = to_Instantiate;
        this.Generator = generator;
        this.start_event_type = type;
    }

    public Start_Event_Type getStart_event_type() {
        return start_event_type;
    }

    public void setStart_event_type(Start_Event_Type start_event_type) {
        this.start_event_type = start_event_type;
    }

    public Discrete_Event_Generator getGenerator() {
        return Generator;
    }

    public void setGenerator(Discrete_Event_Generator generator) {
        Generator = generator;
    }

    public int getTo_Instantiate() {
        return to_Instantiate;
    }

    public void setTo_Instantiate(int to_Instantiate) {
        this.to_Instantiate = to_Instantiate;
    }

    @Override
    public boolean CheckSettings() {
        boolean Check = true;
        if (start_event_type == null) {
            Check = false;
        }
        return Check;
    }

    public String getTag() {
        return getEvent_Tag();
    }

    public Start_Event returnUpperClass() {
        return this;
    }
}
