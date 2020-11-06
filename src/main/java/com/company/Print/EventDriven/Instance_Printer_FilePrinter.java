package com.company.Print.EventDriven;

import com.company.Simulation.Simulation_Base.Data.Instance_Printer_Gate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Instance_Printer_FilePrinter {

    private Instance_Printer_Gate instance_printer_gate;


    public Instance_Printer_FilePrinter() {
        instance_printer_gate = Instance_Printer_Gate.getInstance_Printer_Gate();

    }

    public List<Instance_Print_File> get_PrintList() {
        synchronized (instance_printer_gate.getI_printer_Lock()) {
            return instance_printer_gate.getInstancePrintList();
        }
    }


    public void run() {
        {
            synchronized (instance_printer_gate.getI_printer_Lock()) {
                List<Instance_Print_File> List = get_PrintList();
                if (!List.isEmpty()) {
                    String s = new Gson().toString();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    String JSONObject = gson.toJson(List);

                    BufferedWriter writer = null;
                    try {
                        writer = new BufferedWriter(new FileWriter("./InstanceLog/Dirty.json"));
                        writer.write(JSONObject);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
                    String prettyJson = prettyGson.toJson(List);

                    BufferedWriter prettyWriter = null;
                    try {
                        prettyWriter = new BufferedWriter(new FileWriter("./InstanceLog/Line.json"));
                        prettyWriter.write(prettyJson);
                        prettyWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("General Written!");
                    List.clear();
                }
            }
        }
    }
}
