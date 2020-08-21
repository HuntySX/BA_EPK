package com.company.Simulation.Simulation_Base.Data;

import com.company.Print.EventDriven.Instance_Print_File;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Instance_Printer_Gate {

    private static Instance_Printer_Gate instance_Printer_Gate;
    private static java.util.concurrent.locks.Lock i_printer_Lock;

    private static ArrayList<Instance_Print_File> to_Print;

    public static Instance_Printer_Gate getInstance_Printer_Gate() {
        if (instance_Printer_Gate == null) {
            instance_Printer_Gate = new Instance_Printer_Gate();
            to_Print = new ArrayList<>();
            i_printer_Lock = new ReentrantLock();
        }
        return instance_Printer_Gate;
    }

    public synchronized Lock getI_printer_Lock() {
        return i_printer_Lock;
    }

    public List<Instance_Print_File> getInstancePrintList() {
        return to_Print;
    }
}
