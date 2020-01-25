package com.company.Simulation.Queues_Gates;

import com.company.Simulation.Data.Event_List;

import java.util.concurrent.locks.Lock;

public class Printer {

    private static Printer printer;
    private java.util.concurrent.locks.Lock printer_Lock;

    public static Printer get_Event_Gate() {
        if (printer == null) {
            printer = new Printer();
        }
        return printer;
    }

    public Lock getPrinter_Lock() {
        return printer_Lock;
    }

    public void Print() {
        //TODO Print je nach Art der Meldung.
    }
}
