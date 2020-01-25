package com.company.Simulation.Data;

import com.company.Exceptions.NotEnoughStockException;
import com.company.Simulation.Data.Item;
import com.company.Simulation.Instance.Order_Instance;

import java.util.List;

public class Warehouse {

    private List<Item> Stock;
    private List<Item> min_Stock;
    private List<Order_Instance> Ordered;

    public Warehouse(List<Item> Stock, List<Item> min_Stock) {
        this.min_Stock = min_Stock;
        this.Stock = Stock;
        this.Ordered = null;
    }

    public List<Item> getStock() {
        return Stock;
    }

    public void setStock(List<Item> stock) {
        Stock = stock;
    }

    public int getSingleStock(int i_ID) {

        for (Item i : Stock) {
            if (i.getI_ID() == i_ID) {
                return i.getQuantity();
            }
        }
        {
            throw new RuntimeException();
        }
    }

    public void setSingleStock(int i_ID, int Quantity) throws NotEnoughStockException {

        for (Item item : Stock) {
            if (item.getI_ID() == i_ID) {
                if (item.getQuantity() + Quantity >= 0) {
                    item.setQuantity(item.getQuantity() + Quantity);
                    break;
                } else {
                    throw new NotEnoughStockException("Lager nicht ausreichend gefüllt, Bestellung durchführen");
                }

            }
        }
        //TODO If Quanity > 0 Throw Event

    }

    public List<Order_Instance> getOrdered() {
        return Ordered;
    }

    public void large_Ordering() {
        //TODO Große Bestellung durchführen wenn Bestellwert > Minwert.
        //Wenn Bestellung < Akt.Wert Bestellung abschicken und Process bestätigen
        //Sonst Bestellung Warten lassen.
    }

    public void Restock() {
        //TODO Lager: aktuellwert+Orderwert <Min_wert-> Bestellung auslösen.
        /*
        if(Bestellung < Minwert && < akt.Wert)
           Aus Lager entnehmen
        else if (Bestellung < Minwert)
            Throw new Not Enough Stock Exception
        else
            large Ordering();
         */
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
