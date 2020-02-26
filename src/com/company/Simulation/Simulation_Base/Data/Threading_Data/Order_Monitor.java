package com.company.Simulation.Simulation_Base.Data.Threading_Data;

import com.company.Enums.Order_Status;

import java.util.ArrayList;
import java.util.List;

import static com.company.Enums.Order_Status.*;

public class Order_Monitor {
    private List<Item> Orders;
    private List<Order_Status> Order_Status;

    public Order_Monitor(List<Item> orders) {
        Orders = orders;
        Order_Status = new ArrayList<>();
        for (Item o : orders) {
            Order_Status.add(Unchecked);
        }
    }

    public List<Item> getOrders() {
        return Orders;
    }

    public void setOrders(List<Item> orders) {
        Orders = orders;
    }

    public List<Order_Status> getOrder_Status() {
        return Order_Status;
    }

    public void setOrder_Status(List<Order_Status> order_Status) {
        Order_Status = order_Status;
    }

    public void change_Status(Item item, Order_Status status) {
        for (Item o : Orders) {
            if (o.getI_ID() == item.getI_ID()) {
                Order_Status.set(Orders.indexOf(o), status);
            }
        }
    }

    public Order_Status get_Single_Status(Item item) {
        for (Item o : Orders) {
            if (o.getI_ID() == item.getI_ID()) {
                return Order_Status.get(Orders.indexOf(o));
            }
        }
        return null;
    }

    public List<Item> get_unchecked_Items() {
        List<Item> result = new ArrayList<>();
        for (Item order : Orders) {
            if (Order_Status.get(Orders.indexOf(order)) == Unchecked) {
                result.add(order);
            }
        }
        return result;
    }

    public List<Item> get_waiting_Items() {
        List<Item> result = new ArrayList<>();
        for (Item order : Orders) {
            if (Order_Status.get(Orders.indexOf(order)) == Waiting) {
                result.add(order);
            }
        }
        return result;
    }

    public List<Item> get_received_Items() {
        List<Item> result = new ArrayList<>();
        for (Item order : Orders) {
            if (Order_Status.get(Orders.indexOf(order)) == Received) {
                result.add(order);
            }
        }
        return result;
    }
}
