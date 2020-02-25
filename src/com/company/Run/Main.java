package com.company.Run;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        Generator gen = new Generator();
        gen.instantiate();
        /*Item Holz = new Item(1, "Holz", 0, 1.0f, Classification.Low);
        List<Item> items = new ArrayList<>();
        Random random = new Random();
        System.out.println(random.nextFloat());
        System.out.println(random.nextFloat());
        System.out.println(random.nextFloat());
        System.out.println(random.nextFloat());
        System.out.println(random.nextFloat());
        items.add(Holz);
        Warehouse w = new Warehouse(items, items, null);
        Warehouse_Gate gate = Warehouse_Gate.get_Warehouse_Gate();
        gate.setWarehouse(w);
        Warehouse_Queue Q = new Warehouse_Queue();
        Q.getT().start();

        LocalTime time = LocalTime.now();
        System.out.println("TIME NOW: " + time);
        Duration dur = Duration.ofMinutes(2);
        time = time.plusMinutes(2);
        System.out.println("TIME THEN: " + time);





       //TODO Prozess Instanzieren Hier!

        System.out.println("Hello World");
        Function f = new Function();
        f.setFunction_tag("Hello A");
        System.out.println(f.toString());
        */

    }
}
