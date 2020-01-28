package com.company.Simulation.Queues_Gates;

import com.company.Exceptions.HugeOrderException;
import com.company.Exceptions.NotEnoughStockException;
import com.company.Simulation.Data.Item;
import com.company.Simulation.Data.Warehouse;
import com.company.Simulation.Instance.Order_Instance;
import com.company.Simulation.Instance.Process_instance;
import com.company.Simulation.Instance.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Warehouse_Queue implements Runnable {

    private Warehouse_Gate warehouse_gate;
    private Thread t;

    public Warehouse_Queue() {
        this.warehouse_gate = Warehouse_Gate.get_Warehouse_Gate();
        t = new Thread(this);
    }

    public synchronized Warehouse getWarehouse() {
        return warehouse_gate.getWarehouse();
    }


    public void run() {
        //TODO Threading Fürs auffüllen des Lagers. Evtl auch Bestellungen? eine Geteilte Liste zwischen Warehouse Gate /
        //TODO Bestellvergleich über die Warehousegate für großbestellungen Durchführen, jeweiligen Thread reaktivieren wenn Bestellung vorhanden.

        synchronized (warehouse_gate) {
            List<Process_instance> arriving = warehouse_gate.getArriving_Orders();
            List<Process_instance> waiting = warehouse_gate.getWaiting_Orders();
            List<Process_instance> to_delete = new ArrayList<>();
            if (!arriving.isEmpty()) {
                for (Process_instance a : arriving) {
                    synchronized (a) {
                        if (((Order_Instance) a.getInstance()).isGeneral_Stock()) {
                            to_delete.add(a);
                        } else {

                            for (Process_instance w : waiting) {
                                synchronized (w) {
                                    if (a.getInstance().getCase_ID() == w.getInstance().getCase_ID()) {
                                        w.getT().notify();
                                        to_delete.add(a);
                                    }
                                }
                            }
                        }

                    }

                }
            }
            if (!to_delete.isEmpty()) {
                for (Process_instance del : to_delete) {
                    arriving.remove(del);
                    if (!((Order_Instance) del.getInstance()).isGeneral_Stock()) {
                        waiting.remove(del);
                    }
                }
            }

        }
    }

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }

}