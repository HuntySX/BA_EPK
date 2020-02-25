package com.company.Run;

import com.company.EPK.*;
import com.company.Enums.Classification;
import com.company.Enums.Contype;
import com.company.Enums.Function_Type;
import com.company.Enums.Split_Status;
import com.company.Simulation.Simulation_Base.Data.Item;
import com.company.Simulation.Simulation_Base.Data.User;
import com.company.Simulation.Simulation_Base.Data.Warehouse;
import com.company.Simulation.Simulation_Base.Threading_Instance.Buy_Instance;
import com.company.Simulation.Simulation_Base.Threading_Instance.Process_instance;
import com.company.Simulation.Simulation_Base.Threading_Instance.Rep_Instance;
import com.company.Simulation.Simulation_Base.Threading_Instance.Simulation_Instance;
import com.company.Simulation.Simulation_Threading.*;
import com.company.Simulation.Simulation_Threading.Queues_Gates.*;
import com.company.Simulation.Simulator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Generator {

    public Generator() {

    }

    public void instantiate() throws InterruptedException, FileNotFoundException {

        //EPK
        //Beginn
        EPK epk = new EPK();
        Event start = new Event(null, 0, "Instanz generiert", true, false);
        Function f1 = new Function("Instanz einteilen", Function_Type.Waiting, 1);
        Con_Split cs1 = new Con_Split(null, 2, Contype.XOR, Split_Status.ChooseFirst, null, null);
        Event e1 = new Event(null, 3, "Instanz ist Kaufauftrag");
        Event e2 = new Event(null, 4, "Instanz ist Reparaturauftrag");
        Event e3 = new Event(null, 5, "Instanz ist Lagerauftrag");

        // Kauf
        Function f2 = new Function("Bestandsprüfung", Function_Type.Waiting, 6); //eig Waiting
        Con_Split cs2 = new Con_Split(null, 7, Contype.XOR, Split_Status.General, null, null);
        Event e4 = new Event(null, 8, "Lager ausreichend gefüllt");
        Event e5 = new Event(null, 9, "fehlende Waren");
        Function f3 = new Function("Waren Bestellen und Warten", Function_Type.Waiting, 10);
        Event e6 = new Event(null, 11, "Waren eingetroffen");
        Con_Join cj1 = new Con_Join(null, 12, Contype.XOR);

        //Reparatur

        Function f4 = new Function("Reparatur Bewerten", Function_Type.Waiting, 13);
        Con_Split cs3 = new Con_Split(null, 14, Contype.XOR, Split_Status.General, null, null);
        Event e7 = new Event(null, 15, "Reparatur möglich");
        Event e8 = new Event(null, 16, "Reparatur unmöglich");
        Function f5 = new Function("Reparatur durchführen", Function_Type.Waiting, 17); //TODO
        Event e9 = new Event(null, 18, "Reparatur durchgeführt");
        Function f6 = new Function("Kunde Kontaktieren", Function_Type.Waiting, 19);
        Event e10 = new Event(null, 20, "Kunde Kontaktiert");

        //Zusammenschluss Kauf Rep
        Con_Join cj2 = new Con_Join(null, 21, Contype.XOR);
        Function f7 = new Function("Versand vorbereiten und Zahlung einleiten", Function_Type.Waiting, 22);
        Con_Split cs4 = new Con_Split(null, 23, Contype.AND, Split_Status.ANDsupply, null, null);
        //Lagerauftrag
        Function f8 = new Function("Bestellung prüfen", Function_Type.Waiting, 24); //eig Supplying
        Con_Split cs5 = new Con_Split(null, 25, Contype.XOR, Split_Status.General, null, null);
        Event e11 = new Event(null, 26, "Routine-Bestellung");
        Event e12 = new Event(null, 27, "Kauf-Bestellung");
        Function f9 = new Function("Waren einsortieren", Function_Type.Waiting, 28);
        Event e13 = new Event(null, 29, "Waren einsortiert");
        Function f10 = new Function("Kaufinstanz benachrichten", Function_Type.Waiting, 30);
        Event e14 = new Event(null, 31, "Instanz benachrichtigt");
        Con_Join cj3 = new Con_Join(null, 32, Contype.XOR);
        Function f11 = new Function("Zahlung einleiten", Function_Type.Waiting, 33);
        // Verbund Zusammenschluss + Lagerzahlung
        Con_Join cj4 = new Con_Join(null, 34, Contype.XOR);

        //Versand
        Event e15 = new Event(null, 35, "Versand wird vorbereitet");
        Function f12 = new Function("Waren verpacken", Function_Type.Waiting, 36);
        Event e16 = new Event(null, 37, "Waren transportbereit");
        Function f13 = new Function("Waren versenden", Function_Type.Waiting, 38);
        Event e17 = new Event(null, 39, "Waren versendet");

        //Zahlung

        Event e18 = new Event(null, 40, "Zahlung bearbeiten");
        Function f14 = new Function("Zahlungstyp bestimmen", Function_Type.Waiting, 41);
        Con_Split cs6 = new Con_Split(null, 42, Contype.XOR, Split_Status.General, null, null);
        Event e19 = new Event(null, 43, "Ausgehende Zahlung");
        Function f15 = new Function("Rechnung schreiben und Versenden", Function_Type.Waiting, 44);
        Event e20 = new Event(null, 45, "Ausgehende Zahlung verbucht");
        Event e21 = new Event(null, 46, "Eingehende Zahlung");
        Function f16 = new Function("Zahlung verbuchen", Function_Type.Waiting, 47);
        Event e22 = new Event(null, 48, "Eingehende Zahlung verbucht");
        Con_Join cj5 = new Con_Join(null, 49, Contype.XOR);

        //Ende
        Con_Join cj6 = new Con_Join(null, 50, Contype.OR);
        Function f17 = new Function("Instanz beenden", Function_Type.Waiting, 51);
        Event end = new Event(null, 52, "Instanz beendet", true, false);


        //Con_Split Liste für 1.Entscheidung:
        List<Node> EventforSplit = new ArrayList<>();
        EventforSplit.add(e1);
        EventforSplit.add(e2);
        EventforSplit.add(e3);

        cs1.setSingle_Elem(EventforSplit);

        epk.setStart_Event(start);
        epk.setEnd_Event(end);
        epk.add_Event(start);
        epk.add_Event(e1);
        epk.add_Event(e2);
        epk.add_Event(e3);
        epk.add_Event(e4);
        epk.add_Event(e5);
        epk.add_Event(e6);
        epk.add_Event(e7);
        epk.add_Event(e8);
        epk.add_Event(e9);
        epk.add_Event(e10);
        epk.add_Event(e11);
        epk.add_Event(e12);
        epk.add_Event(e13);
        epk.add_Event(e14);
        epk.add_Event(e15);
        epk.add_Event(e16);
        epk.add_Event(e17);
        epk.add_Event(e18);
        epk.add_Event(e19);
        epk.add_Event(e20);
        epk.add_Event(e21);
        epk.add_Event(e22);
        epk.add_Event(end);
        epk.add_Function(f1);
        epk.add_Function(f2);
        epk.add_Function(f3);
        epk.add_Function(f4);
        epk.add_Function(f5);
        epk.add_Function(f6);
        epk.add_Function(f7);
        epk.add_Function(f8);
        epk.add_Function(f9);
        epk.add_Function(f10);
        epk.add_Function(f11);
        epk.add_Function(f12);
        epk.add_Function(f13);
        epk.add_Function(f14);
        epk.add_Function(f15);
        epk.add_Function(f16);
        epk.add_Function(f17);
        epk.add_con_Split(cs1);
        epk.add_con_Split(cs2);
        epk.add_con_Split(cs3);
        epk.add_con_Split(cs4);
        epk.add_con_Split(cs5);
        epk.add_con_Join(cj1);
        epk.add_con_Join(cj2);
        epk.add_con_Join(cj3);
        epk.add_con_Join(cj4);
        epk.add_con_Join(cj5);
        epk.add_con_Join(cj6);


        // User:

        List<Function> list1 = new ArrayList<>();
        list1.add(f2);
        list1.add(f3);
        list1.add(f7);
        List<Function> list2 = new ArrayList<>();
        list2.add(f2);
        list2.add(f3);
        list2.add(f7);

        List<Function> list3 = new ArrayList<>();
        list3.add(f1);
        list3.add(f4);
        list3.add(f5);
        list3.add(f6);

        List<Function> list4 = new ArrayList<>();
        list4.add(f8);
        list4.add(f9);
        list4.add(f11);

        List<Function> list5 = new ArrayList<>();
        list5.add(f8);
        list5.add(f10);
        list5.add(f11);

        List<Function> list6 = new ArrayList<>();
        list6.add(f12);
        list6.add(f13);

        List<Function> list7 = new ArrayList<>();
        list6.add(f14);
        list6.add(f15);
        list6.add(f16);
        list6.add(f17);

        User user1 = new User("Karl", "Kopf", 1, list1);
        User user2 = new User("Mira", "Bellenbaum", 2, list2);
        User user3 = new User("Bernhard", "Diener", 3, list3);
        User user4 = new User("Paul", "Lahner", 4, list4);
        User user5 = new User("Ulli", "Unterberg", 5, list5);
        User user7 = new User("Ann", "Zug", 7, list6);
        User user6 = new User("Dagobert", "Dack", 6, list7);

        // Consumer
        //Short Wait
        Consumer<Process_instance> shortWait = (Process_instance instance) -> {
            synchronized (instance.getLock()) {
                try {
                    instance.getLock().wait(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Consumer<Process_instance> middleWait = (Process_instance instance) -> {
            synchronized (instance.getLock()) {
                try {
                    instance.getLock().wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Consumer<Process_instance> longWait = (Process_instance instance) -> {
            synchronized (instance.getLock()) {
                try {
                    instance.getLock().wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        //Consumer<Process_instance> Buy_Method = (Process_instance instance) ->{
        //    synchronized (Warehouse_Gate.get_Warehouse_Gate()) {
        //        Warehouse_Gate.get_Warehouse_Gate().add_waiting_Orders(instance);
        //    }
        //};

        /*Consumer<Process_instance> Supply_Method = (Process_instance instance) ->{

                synchronized (instance.getLock()) {
                   try {
                        instance.getLock().wait(1000);
                    } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                }
            synchronized (Warehouse_Gate.get_Warehouse_Gate()) {
                Warehouse_Gate.get_Warehouse_Gate().add_arriving_Orders(instance);
            }
        };*/

        //Function Consumer Binden

        f1.setConsumableMethod(shortWait);
        f2.setConsumableMethod(shortWait);
        f3.setConsumableMethod(longWait);
        f4.setConsumableMethod(shortWait);
        f5.setConsumableMethod(longWait);
        f6.setConsumableMethod(middleWait);
        f7.setConsumableMethod(shortWait);
        f8.setConsumableMethod(shortWait);
        f9.setConsumableMethod(middleWait);
        f10.setConsumableMethod(shortWait);
        f11.setConsumableMethod(shortWait);
        f12.setConsumableMethod(longWait);
        f13.setConsumableMethod(middleWait);
        f14.setConsumableMethod(shortWait);
        f15.setConsumableMethod(middleWait);
        f16.setConsumableMethod(shortWait);
        f17.setConsumableMethod(shortWait);


        Supplier<List<Node>> Andchoose = cs4::getNext_Elem;

        BiFunction<Simulation_Instance, List<Node>, List<Node>> buying = (Simulation_Instance instance, List<Node> f) -> {
            List<Node> result = new ArrayList<>();
            if (instance instanceof Buy_Instance) {
                result.add(f.get(0));
                return result;
            } else if (instance instanceof Rep_Instance) {
                result.add(f.get(1));
                return result;
            } else {
                result.add(f.get(2));
                return result;
            }
        };

        BiFunction<Simulation_Instance, List<Node>, List<Node>> general = (Simulation_Instance instance, List<Node> Elem) -> {
            List<Node> result = new ArrayList<>();
            Random random = new Random();
            int i = random.nextInt(Elem.size());
            result.add(Elem.get(i));
            return result;
        };

        cs1.setPathfinder(buying);
        cs2.setPathfinder(general);
        cs3.setPathfinder(general);
        cs4.setPathfi(Andchoose);
        cs5.setPathfinder(general);
        cs6.setPathfinder(general);

        // NextElems + Gates

        List<Node> Preelem_g1 = new ArrayList<>();
        List<Node> Preelem_g2 = new ArrayList<>();
        List<Node> Preelem_g3 = new ArrayList<>();
        List<Node> Preelem_g4 = new ArrayList<>();
        List<Node> Preelem_g5 = new ArrayList<>();
        List<Node> Preelem_g6 = new ArrayList<>();

        Preelem_g1.add(e4);
        Preelem_g1.add(e6);

        Preelem_g2.add(cj1);
        Preelem_g2.add(e10);

        Preelem_g3.add(e13);
        Preelem_g3.add(e14);

        Preelem_g4.add(f11);
        Preelem_g4.add(cs4);

        Preelem_g5.add(e20);
        Preelem_g5.add(e22);

        Preelem_g6.add(e17);
        Preelem_g6.add(cj5);

        cj1.setPre_Elem(Preelem_g1);
        cj2.setPre_Elem(Preelem_g2);
        cj3.setPre_Elem(Preelem_g3);
        cj4.setPre_Elem(Preelem_g4);
        cj5.setPre_Elem(Preelem_g5);
        cj6.setPre_Elem(Preelem_g6);


        //Zahlung
        cj5.add_Next_Elem(cj6);
        e20.add_Next_Elem(cj5);
        e22.add_Next_Elem(cj5);
        f16.add_Next_Elem(e22);
        e21.add_Next_Elem(f16);
        f15.add_Next_Elem(e20);
        e19.add_Next_Elem(f15);
        cs6.add_Next_Elem(e21);
        cs6.add_Next_Elem(e19);
        f14.add_Next_Elem(cs6);
        e18.add_Next_Elem(f14);
        cj4.add_Next_Elem(e18);


        //Versand
        e17.add_Next_Elem(cj6);
        f13.add_Next_Elem(e17);
        e16.add_Next_Elem(f13);
        f12.add_Next_Elem(e16);
        e15.add_Next_Elem(f12);
        cs4.add_Next_Elem(e15);

        //Lager

        cs4.add_Next_Elem(cj4);
        f11.add_Next_Elem(cj4);
        cj3.add_Next_Elem(f11);
        e14.add_Next_Elem(cj3);
        e13.add_Next_Elem(cj3);
        f10.add_Next_Elem(e14);
        e12.add_Next_Elem(f10);
        f9.add_Next_Elem(e13);
        e11.add_Next_Elem(f9);
        cs5.add_Next_Elem(e12);
        cs5.add_Next_Elem(e11);
        f8.add_Next_Elem(cs5);
        e3.add_Next_Elem(f8);


        //Rep
        e8.add_Next_Elem(f17);
        e10.add_Next_Elem(cj2);
        f6.add_Next_Elem(e10);
        e9.add_Next_Elem(f6);
        f5.add_Next_Elem(e9);
        e7.add_Next_Elem(f5);
        cs3.add_Next_Elem(e8);
        cs3.add_Next_Elem(e7);
        f4.add_Next_Elem(cs3);

        //links
        f7.add_Next_Elem(cs4);
        cj2.add_Next_Elem(f7);
        cj1.add_Next_Elem(cj2);
        e6.add_Next_Elem(cj1);
        e4.add_Next_Elem(cj1);
        f3.add_Next_Elem(e6);
        e5.add_Next_Elem(f3);
        cs2.add_Next_Elem(e4);
        cs2.add_Next_Elem(e5);
        f2.add_Next_Elem(cs2);
        e1.add_Next_Elem(f2);
        cs1.add_Next_Elem(e1);
        cs1.add_Next_Elem(e2);
        cs1.add_Next_Elem(e3);
        f1.add_Next_Elem(cs1);
        start.add_Next_Elem(f1);


        Item i1 = new Item(1, "Holz", 0, 1f, Classification.Low);
        Item i2 = new Item(2, "Schraube", 0, 1f, Classification.Low);
        Item i3 = new Item(3, "Schublade", 0, 1f, Classification.Middle);
        Item i4 = new Item(4, "Kommode", 0, 1f, Classification.Middle);
        Item i5 = new Item(5, "Schrank", 0, 1f, Classification.High);

        Simulator sim = new Simulator();
        sim.add_newItem(i1);
        sim.add_newItem(i2);
        sim.add_newItem(i3);
        sim.add_newItem(i4);
        sim.add_newItem(i5);


        Event_Gate eg = Event_Gate.get_Event_Gate();
        Printer_Gate pg = Printer_Gate.get_Printer_Gate();
        Process_Gate prg = Process_Gate.getProcess_gate();
        Starting_Gate sg = Starting_Gate.getStarting_gate();
        User_Gate ug = User_Gate.get_User_Gate();
        Warehouse_Gate wg = Warehouse_Gate.get_Warehouse_Gate();
        sg.setStarting_Event(start);

        ug.add_Single_User(user1);
        ug.add_Single_User(user2);
        ug.add_Single_User(user3);
        ug.add_Single_User(user4);
        ug.add_Single_User(user5);
        ug.add_Single_User(user6);
        ug.add_Single_User(user7);

        Warehouse wh = new Warehouse(sim);
        wg.setWarehouse(wh);
        wg.getWarehouse().AddDistinctToStock(i1);
        wg.getWarehouse().AddDistinctToStock(i2);
        wg.getWarehouse().AddDistinctToStock(i3);
        wg.getWarehouse().AddDistinctToStock(i4);
        wg.getWarehouse().AddDistinctToStock(i5);

        Event_Queue event_queue = new Event_Queue();
        Printer_Queue printer_queue = new Printer_Queue();
        Process_Queue process_queue = new Process_Queue(epk);
        Resupply_Warehouse_Queue resupply_warehouse_queue = new Resupply_Warehouse_Queue(sim);
        Starting_Queue starting_queue = new Starting_Queue(sim, epk);
        Warehouse_Queue warehouse_queue = new Warehouse_Queue();

        Thread EQ = new Thread(event_queue);
        Thread PQ = new Thread(printer_queue);
        Thread PRQ = new Thread(process_queue);
        Thread RSQ = new Thread(resupply_warehouse_queue);
        Thread SQ = new Thread(starting_queue);
        Thread WQ = new Thread(warehouse_queue);

        event_queue.setEQ(EQ);
        printer_queue.setT(PQ);
        process_queue.setProcess_Queue(PRQ);
        resupply_warehouse_queue.setT(RSQ);
        starting_queue.setSQ(SQ);
        warehouse_queue.setT(WQ);

        PRQ.start();
        SQ.start();
        EQ.start();
        WQ.start();
        RSQ.start();
        synchronized (this) {
            wait(300000);
        }

        PQ.start();

    }
}
