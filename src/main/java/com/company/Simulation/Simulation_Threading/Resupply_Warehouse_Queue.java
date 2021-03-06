package com.company.Simulation.Simulation_Threading;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;
import com.company.Simulation.Simulation_Base.Data.Threading_Data.Item;
import com.company.Simulation.Simulation_Base.Data.Threading_Data.Order_Instance;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Resupply_Warehouse_Queue implements Runnable {

    private final Warehouse_Gate warehouse_gate;
    private final Starting_Gate starting_gate;
    private final List<Item> min_stock;
    private final Threading_Simulator threadingSimulator;
    private Thread t;

    public Resupply_Warehouse_Queue(Threading_Simulator threadingSimulator) {
        this.warehouse_gate = Warehouse_Gate.get_Warehouse_Gate();
        this.starting_gate = Starting_Gate.getStarting_gate();
        this.threadingSimulator = threadingSimulator;
        this.min_stock = warehouse_gate.getWarehouse().getMin_Stock();
        this.t = null;
    }

    public void setT(Thread t) {
        this.t = t;
    }

    public Thread getT() {
        return t;
    }

    @Override
    public synchronized void run() {

        List<Item> orders;
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (warehouse_gate) {
            List<Item> items = warehouse_gate.getWarehouse().getStock();
            orders = check_stock(items);
        }
        if (!orders.isEmpty()) {

            resupply(orders);
        }

    }


    private List<Item> check_stock(List<Item> Items) {
        List<Item> result = new ArrayList<>();
        for (Item e : min_stock) {
            int Index = min_stock.indexOf(e);
            if (e.getI_ID() == Items.get(Index).getI_ID() && e.getQuantity() > Items.get(Index).getQuantity() / 2) {
                Item to_order = new Item(Items.get(Index).getI_ID(), Items.get(Index).getItem_Name(),
                        e.getQuantity() - Items.get(Index).getQuantity(),
                        Items.get(Index).getQuality(), Items.get(Index).getClassification());

                result.add(to_order);
            }
        }
        return result;
    }


    private void resupply(List<Item> orders) {
        Simulation_Instance instance;

        synchronized (threadingSimulator) {
            LocalTime time = threadingSimulator.get_OrderTime();
            instance = new Order_Instance(threadingSimulator.get_unique_caseID(), orders, time, true);
        }
        synchronized (starting_gate) {
            starting_gate.getStarting_order().add_Starting_Instance(instance);
        }
    }

}


