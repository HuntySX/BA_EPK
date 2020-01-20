package com.company.Simulation;

import com.company.Exceptions.NotEnoughStockException;

import java.util.List;

public class Warehouse {

    private List<Item> Stock;
    private List<Integer> Order_ID;

    public Warehouse(List<Item> stock) {
        Stock = stock;
        Order_ID = null;
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

    }

    public List<Integer> getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(List<Integer> order_ID) {
        Order_ID = order_ID;
    }

    public boolean check_for_SingleOrder_ID(int ID) {
        if (Order_ID.contains(ID)) {
            return true;
        } else {
            return false;
        }
    }

    public void add_OrderID(int ID) {

        if (!Order_ID.contains(ID)) {
            Order_ID.add(ID);
        }
    }

}
