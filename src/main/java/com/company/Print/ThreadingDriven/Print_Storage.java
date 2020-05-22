package com.company.Print.ThreadingDriven;

import com.company.Enums.Process_Status;
import com.company.Simulation.Simulation_Base.Data.Threading_Data.Item;

import java.time.LocalTime;
import java.util.List;


public class Print_Storage extends Print_File {

    private List<Item> items;

    public Print_Storage(int case_ID, LocalTime timer, String process, Process_Status status, List<Item> items) {
        super(case_ID, timer, process, status);
        this.items = items;
    }

    public Print_Storage() {
        super();
    }

    public List<Item> getItems() {
        return items;
    }
}
