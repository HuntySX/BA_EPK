package com.company.Run;

import com.company.Enums.Classification;
import com.company.Simulation.Data.Item;
import com.company.Simulation.Data.Warehouse;
import com.company.Simulation.Queues_Gates.Warehouse_Gate;
import com.company.Simulation.Queues_Gates.Warehouse_Queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Item Holz = new Item(1, "Holz", 0, 1.0f, Classification.Low);
        List<Item> items = new ArrayList<>();
        Random random = new Random();
        System.out.println(random.nextFloat());
        System.out.println(random.nextFloat());
        System.out.println(random.nextFloat());
        System.out.println(random.nextFloat());
        System.out.println(random.nextFloat());
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
