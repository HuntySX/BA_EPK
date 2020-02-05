package com.company.Simulation.Queues_Gates;

import com.company.EPK.Function;
import com.company.EPK.Node;
import com.company.Enums.Process_Status;
import com.company.Simulation.Data.Item;
import com.company.Simulation.Data.User;
import com.company.Simulation.Printfiles.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Printer_Gate {

    private static Printer_Gate printerGate;
    private static java.util.concurrent.locks.Lock printer_Lock;

    private static ArrayList<Print_File> to_Print;

    public static Printer_Gate get_Printer_Gate() {
        if (printerGate == null) {
            printerGate = new Printer_Gate();
            to_Print = new ArrayList<>();
            printer_Lock = new ReentrantLock();
        }
        return printerGate;
    }

    public synchronized Lock getPrinter_Lock() {
        return printer_Lock;
    }

    public synchronized void PrintProcess(int case_ID, Function Process, LocalTime timer, Process_Status status) {

        Print_Process to_print = new Print_Process(case_ID, timer, Process.getFunction_tag(), status);
        synchronized (Printer_Gate.get_Printer_Gate()) {
            to_Print.add(to_print);
        }
    }

    public synchronized void PrintStorage(int case_ID, Function Process, LocalTime timer, Process_Status status, List<Item> items) {

        Print_Storage to_print = new Print_Storage(case_ID, timer, Process.getFunction_tag(), status, items);

        synchronized (Printer_Gate.get_Printer_Gate()) {
            to_Print.add(to_print);
        }
    }

    public synchronized void PrintActivate(int case_ID, Function Process, LocalTime timer, Process_Status status, String Name, String Familyname) {

        Print_Activate to_print = new Print_Activate(case_ID, Process.getFunction_tag(), timer, status, Name, Familyname);

        synchronized (Printer_Gate.get_Printer_Gate()) {
            to_Print.add(to_print);
        }
    }

    public synchronized List<Print_File> getPrinterList() {
        return to_Print;
    }
}
