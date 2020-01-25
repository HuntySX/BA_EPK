package com.company.Run;

import com.company.EPK.Function;
import com.company.Enums.Quality;
import com.company.Simulation.Data.Item;
import com.company.Simulation.Data.Warehouse;
import com.company.Simulation.Queues_Gates.Warehouse_Gate;
import com.company.Simulation.Queues_Gates.Warehouse_Queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Main {

    public static void main(String[] args) {

        Item Holz = new Item(1, "Holz", 0, Quality.Low);
        List<Item> items = new ArrayList<>();
        items.add(Holz);
        Warehouse w = new Warehouse(items, items);
        Warehouse_Gate gate = Warehouse_Gate.get_Warehouse_Gate();
        gate.setWarehouse(w);
        Warehouse_Queue Q = new Warehouse_Queue();
        Q.getT().start();





       /*//TODO Prozess Instanzieren Hier!

        System.out.println("Hello World");
        Function f = new Function();
        f.setFunction_tag("Hello A");
        System.out.println(f.toString());*/
    }
}
