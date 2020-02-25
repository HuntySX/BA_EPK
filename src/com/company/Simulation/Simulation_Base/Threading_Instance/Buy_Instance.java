package com.company.Simulation.Simulation_Base.Threading_Instance;

import com.company.Simulation.Simulation_Base.Data.Item;
import com.company.Simulation.Simulation_Base.Data.Order_Monitor;

import java.util.List;

public class Buy_Instance extends Simulation_Instance {
    private List<Item> Buy_Items;
    private float price;
    private boolean fullfilled;
    private Order_Monitor order_Monitor;

    public Buy_Instance(int case_ID, List<Item> buy_Items) {
        super(case_ID);
        Buy_Items = buy_Items;
        fullfilled = false;
        order_Monitor = new Order_Monitor(buy_Items);
    }

    public List<Item> getBuy_Items() {
        return Buy_Items;
    }

    public void setBuy_Items(List<Item> buy_Items) {
        Buy_Items = buy_Items;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isFullfilled() {
        return fullfilled;
    }

    public void setFullfilled(boolean fullfilled) {
        this.fullfilled = fullfilled;
    }

    public Order_Monitor getOrder_Monitor() {
        return order_Monitor;
    }

    public void setOrder_Monitor(Order_Monitor order_Monitor) {
        this.order_Monitor = order_Monitor;
    }
}