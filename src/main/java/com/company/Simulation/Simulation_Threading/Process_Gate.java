package com.company.Simulation.Simulation_Threading;

import com.company.Simulation.Simulation_Base.Data.Threading_Data.Threading_Process_List;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Process_Gate {

    private static Process_Gate process_gate;
    private static Threading_Process_List threadingProcess_list;
    private static Lock lock;

    public static Process_Gate getProcess_gate() {
        if (process_gate == null) {
            process_gate = new Process_Gate();
            threadingProcess_list = new Threading_Process_List();
            lock = new ReentrantLock();
        }
        return process_gate;
    }

    public synchronized Threading_Process_List getProcess_list() {
        return threadingProcess_list;
    }

    public synchronized void setProcess_list(Threading_Process_List threadingProcess_list) {
        Process_Gate.threadingProcess_list = threadingProcess_list;
    }

    public synchronized Lock getLock() {
        return lock;
    }
}
