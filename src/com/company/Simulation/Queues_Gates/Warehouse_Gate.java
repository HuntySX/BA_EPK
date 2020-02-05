package com.company.Simulation.Queues_Gates;

import com.company.Simulation.Data.Warehouse;
import com.company.Simulation.Instance.Process_instance;

import java.util.ArrayList;
import java.util.List;

public class Warehouse_Gate {

    private static Warehouse_Gate warehouse_gate;
    private static List<Process_instance> waiting_Orders;
    private static List<Process_instance> arriving_Orders;
    private static Warehouse warehouse;

    public static synchronized Warehouse_Gate get_Warehouse_Gate() {
        if (warehouse_gate == null) {
            warehouse_gate = new Warehouse_Gate();
            waiting_Orders = new ArrayList<>();
            arriving_Orders = new ArrayList<>();
        }
        return warehouse_gate;
    }

    public synchronized Warehouse getWarehouse() {
        return warehouse;
    }

    public synchronized void setWarehouse(Warehouse warehouse) {
        Warehouse_Gate.warehouse = warehouse;
    } //TODO Unsicher ob WG.wh = wh oder wh = warehouse;

    public List<Process_instance> getWaiting_Orders() {
        return waiting_Orders;
    }

    public void setWaiting_Orders(List<Process_instance> waiting_Orders) {
        Warehouse_Gate.waiting_Orders = waiting_Orders;
    }

    public List<Process_instance> getArriving_Orders() {
        return arriving_Orders;
    }

    public void setArriving_Orders(List<Process_instance> arriving_Orders) {
        Warehouse_Gate.arriving_Orders = arriving_Orders;
    }

    public synchronized void add_waiting_Orders(Process_instance instance) {
        if (instance != null) {
            waiting_Orders.add(instance);
        }
    }

    public synchronized void add_arriving_Orders(Process_instance instance) {
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
