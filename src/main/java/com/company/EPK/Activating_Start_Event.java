package com.company.EPK;

import com.company.Enums.Start_Event_Type;
import com.company.Run.Discrete_Event_Generator;

import java.util.List;

import static com.company.Enums.Start_Event_Type.INSTANTIATED;

public class Activating_Start_Event extends Event {

    private Start_Event_Type start_event_type;
    private Discrete_Event_Generator Generator;
    private Activating_Function Function;

    public Activating_Start_Event(Activating_Function function, int ID, Discrete_Event_Generator generator, List<EPK_Node> Next_Elem, String Event_Tag, boolean is_Start_Event) {
        super(Next_Elem, ID, Event_Tag, is_Start_Event);
        this.Generator = generator;
        this.start_event_type = INSTANTIATED;
        this.Function = function;
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


}
