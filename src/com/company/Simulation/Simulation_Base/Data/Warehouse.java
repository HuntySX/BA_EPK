package com.company.Simulation.Simulation_Base.Data;

import com.company.Enums.Order_Status;
import com.company.Exceptions.ItemNotListedException;
import com.company.Simulation.Simulation_Base.Threading_Instance.Order_Instance;
import com.company.Simulation.Simulation_Base.Threading_Instance.Process_instance;
import com.company.Simulation.Simulation_Threading.Warehouse_Gate;
import com.company.Simulation.Simulator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.company.Enums.Classification.*;
import static com.company.Enums.Order_Status.*;

public class Warehouse {

    private List<Item> Stock;
    private Simulator simulator;
    private List<Item> min_Stock;
    private List<Order_Instance> Ordered;
    private Thread Resupply;

    public Warehouse(List<Item> Stock, List<Item> min_Stock, Simulator simulator) {
        this.min_Stock = min_Stock;
        this.simulator = simulator;
        this.Stock = Stock;
        this.Ordered = null;
        this.Resupply = null;
    }

    public Warehouse(Simulator simulator) {
        this.min_Stock = new ArrayList<>();
        this.simulator = simulator;
        this.Stock = new ArrayList<>();
        this.Ordered = null;
        this.Resupply = null;
    }

    public void setResupply(Thread resupply) {
        Resupply = resupply;
    }

    public List<Item> getStock() {
        return Stock;
    }

    public void setStock(List<Item> stock) {
        Stock = stock;
    }

    public int getSingleStock(int i_ID) throws ItemNotListedException {

        for (Item i : Stock) {
            if (i.getI_ID() == i_ID) {
                return i.getQuantity();
            }
        }
        throw new ItemNotListedException("Gegenstand unbekannt.");
    }

    public Order_Status takeSingleStock(Process_instance instance, Item i) {
        for (Item to_get : Stock) {
            if (to_get.getI_ID() == i.getI_ID()) {
                Item min_supply = min_Stock.get(Stock.indexOf(to_get));
                if (to_get.getQuantity() - i.getQuantity() >= 0 && i.getQuantity() <= min_supply.getQuantity()) {
                    to_get.setQuantity(to_get.getQuantity() - i.getQuantity());
                    if (to_get.getQuantity() <= min_supply.getQuantity() / 2) {
                        Resupply.notify();

                    }
                    return Received;
                } else if (i.getQuantity() > min_supply.getQuantity()) {
                    Warehouse_Gate.get_Warehouse_Gate().add_waiting_Orders(instance);
                    return OrderWaiting;
                } else {
                    Resupply.notify();
                    return Waiting;
                }
            }
            break;
        }
        return null;
    }

    public void large_Ordering(Process_instance instance, List<Item> Order) {
        LocalTime time = simulator.get_Big_OrderTime();
        Order_Instance generate_Order = new Order_Instance(simulator.get_unique_caseID(), Order, time, false, instance.getInstance().getCase_ID());

    }

    /*public boolean takeSingleStock(Process_instance instance, List<Item> Order) throws NotEnoughStockException {
        List<Item> result = new ArrayList<>();
        for (Item order : Order) {
            for (Item item : Stock) {
                if (item.getI_ID() == order.getI_ID()) {
                    Item min_supply = min_Stock.get(Stock.indexOf(item));
                    if (item.getQuantity() - order.getQuantity() >= 0 && order.getQuantity() <= min_supply.getQuantity()) {
                        item.setQuantity(item.getQuantity() - order.getQuantity());
                        order.setQuantity(0);
                        if (item.getQuantity() <= min_supply.getQuantity() / 2) {
                            Resupply.notify();
                            return true;
                        }
                    } else if (order.getQuantity() > min_supply.getQuantity()) {
                        //large_Ordering(instance, Order);
                        return false;
                    } else {
                        Resupply.notify();
                        throw new NotEnoughStockException("Lager nicht ausreichend gefüllt, Bestellung durchführen");

                    }
                    break;
                }
            }
        }
        return true;
    }*/


    public List<Order_Instance> getOrdered() {
        return Ordered;
    }

    public void setOrdered(List<Order_Instance> ordered) {
        Ordered = ordered;
    }

    public List<Item> getMin_Stock() {
        return min_Stock;
    }

    public void setMin_Stock(List<Item> min_Stock) {
        this.min_Stock = min_Stock;
    }

    public void addSingleStock(Item i) {
        for (Item s : Stock) {
            if (s.getI_ID() == i.getI_ID()) {
                s.setQuantity(s.getQuantity() + i.getQuantity());
            }
        }
    }

    public void AddDistinctToStock(Item i) {
        int quantity;
        if (i.getClassification() == Low) {
            quantity = 150;
        } else if (i.getClassification() == Middle) {
            quantity = 60;
        } else {
            quantity = 5;
        }
        Item add_newStock = new Item(i.getI_ID(), i.getItem_Name(), quantity, i.getQuality(), i.getClassification());
        min_Stock.add(add_newStock);
        Stock.add(add_newStock);

    }
}
