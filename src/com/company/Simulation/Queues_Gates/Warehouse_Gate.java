package com.company.Simulation.Queues_Gates;

import com.company.Simulation.Data.Warehouse;
import com.company.Simulation.Instance.Process_instance;

import java.util.List;

public class Warehouse_Gate {

    private static Warehouse_Gate warehouse_gate;
    private List<Process_instance> waiting_Orders;
    private List<Process_instance> arriving_Orders;
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

    public List<Process_instance> getArriving_Orders() {
        return arriving_Orders;
    }

    public void setArriving_Orders(List<Process_instance> arriving_Orders) {
        this.arriving_Orders = arriving_Orders;
    }

    public void add_waiting_Orders(Process_instance instance) {
        if (instance != null) {
            waiting_Orders.add(instance);
        }
    }

    public void add_arriving_Orders(Process_instance instance) {
        if (instance != null) {
            arriving_Orders.add(instance);
        }
    }

    public void delete_from_waiting_Orders(Process_instance instance) {
        if (instance != null) {
            waiting_Orders.remove(instance);
        }
    }

    public void delete_from_arriving_Orders(Process_instance instance) {
        if (instance != null) {
            arriving_Orders.remove(instance);
        }
    }

}
