package com.company.Simulation.Simulation_Threading;

import com.company.Simulation.Simulation_Base.Data.Process_List;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Process_Gate {

    private static Process_Gate process_gate;
    private static Process_List process_list;
    private static Lock lock;

    public static Process_Gate getProcess_gate() {
        if (process_gate == null) {
            process_gate = new Process_Gate();
            process_list = new Process_List();
            lock = new ReentrantLock();
        }
        return process_gate;
    }

    public synchronized Process_List getProcess_list() {
        return process_list;
    }

    public synchronized void setProcess_list(Process_List process_list) {
        Process_Gate.process_list = process_list;
    }

    public synchronized Lock getLock() {
        return lock;
    }
}
