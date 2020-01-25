package com.company.Simulation.Queues_Gates;

import com.company.Simulation.Data.Process_List;
import com.company.Simulation.Data.Starting_List;

import java.util.concurrent.locks.Lock;

public class Process_Gate {

    private static Process_Gate process_gate;
    private Process_List process_list;
    private Lock lock;

    public static Process_Gate getProcess_gate() {
        if (process_gate == null) {
            process_gate = new Process_Gate();
        }
        return process_gate;
    }

    public synchronized Process_List getProcess_list() {
        return process_list;
    }

    public synchronized void setProcess_list(Process_List process_list) {
        this.process_list = process_list;
    }

    public Lock getLock() {
        return lock;
    }
}
