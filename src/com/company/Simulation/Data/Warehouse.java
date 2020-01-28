package com.company.Simulation.Data;

import com.company.Exceptions.ItemNotListedException;
import com.company.Exceptions.NotEnoughStockException;
import com.company.Simulation.Data.Item;
import com.company.Simulation.Instance.Order_Instance;
import com.company.Simulation.Instance.Process_instance;
import com.sun.deploy.security.SelectableSecurityManager;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.R;

public class Warehouse {

    private List<Item> Stock;
    private List<Item> min_Stock;
    private List<Order_Instance> Ordered;
    private Thread Resupply;

    public Warehouse(List<Item> Stock, List<Item> min_Stock, Thread resupply) {
        this.min_Stock = min_Stock;
        this.Stock = Stock;
        this.Ordered = null;
        this.Resupply = resupply;
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

    public void supplySingleStock(int i_ID, int Quantity) {

    }

    public boolean takeSingleStock(Process_instance instance, List<Item> Order) throws NotEnoughStockException {
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
    }


    public List<Order_Instance> getOrdered() {
        return Ordered;
    }

    public void large_Ordering() {
        //TODO Große Bestellung durchführen wenn Bestellwert > Minwert.
        //Wenn Bestellung < Akt.Wert Bestellung abschicken und Process bestätigen
        //Sonst Bestellung Warten lassen.
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

}
