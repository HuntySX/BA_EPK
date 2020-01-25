package com.company.Simulation.Queues_Gates;

import com.company.Exceptions.NotEnoughStockException;
import com.company.Simulation.Data.Warehouse;
import com.company.Simulation.Instance.Process_instance;
import com.company.Simulation.Instance.Simulation_Instance;

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

        while (Warehouse_Gate.get_Warehouse_Gate().getWarehouse().getStock().get(0).getQuantity() <= 10) {
            try {
                synchronized (t) {
                    t.wait(2000); //TODO Notifyer wenn Lager zur Hälfte Leer BSP
                    warehouse_gate.getWarehouse().setSingleStock(1, 1);
                    System.out.println("Holz: " + warehouse_gate.getWarehouse().getSingleStock(1));
                }
            } catch (InterruptedException e) {
            } catch (NotEnoughStockException e) {
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
