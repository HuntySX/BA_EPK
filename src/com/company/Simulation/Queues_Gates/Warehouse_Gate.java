package com.company.Simulation.Queues_Gates;

import com.company.Simulation.Data.Warehouse;
import com.company.Simulation.Instance.Process_instance;

import java.util.List;

public class Warehouse_Gate {

    private static Warehouse_Gate warehouse_gate;
    private List<Process_instance> waiting_Orders;
    private Warehouse warehouse;

    public static synchronized Warehouse_Gate get_Warehouse_Gate() {
        if (warehouse_gate == null) {
            warehouse_gate = new Warehouse_Gate();
            return warehouse_gate;
        }
        return warehouse_gate;
    }

    public synchronized Warehouse getWarehouse() {
        return warehouse;
    }

    public synchronized void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public List<Process_instance> getWaiting_Orders() {
        return waiting_Orders;
    }

    public void setWaiting_Orders(List<Process_instance> waiting_Orders) {
        this.waiting_Orders = waiting_Orders;
    }
}
