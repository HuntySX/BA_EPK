package com.company.Simulation.Printfiles;

import com.company.EPK.Function;
import com.company.EPK.Node;
import com.company.Enums.Process_Status;
import com.company.Simulation.Data.Item;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalTime;
import java.util.List;
import java.util.Timer;


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
