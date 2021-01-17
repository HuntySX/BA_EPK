package com.company.Simulation.Simulation_Base.Data;

import com.company.Print.EventDriven.Instance_Print_File;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Instance_Printer_Queue implements Runnable {

    private final Instance_Printer_Gate instance_printer_gate;
    private Thread t;
    private boolean not_killed;

    public Instance_Printer_Queue() {
        instance_printer_gate = Instance_Printer_Gate.getInstance_Printer_Gate();
        not_killed = true;
        this.t = null;
    }

    public void setT(Thread t) {
        this.t = t;
    }

    public boolean isNot_killed() {
        return not_killed;
    }

    public void setNot_killed(boolean not_killed) {
        this.not_killed = not_killed;
    }

    public List<Instance_Print_File> get_PrintList() {
        synchronized (instance_printer_gate.getI_printer_Lock()) {
            return instance_printer_gate.getInstancePrintList();
        }
    }

    @Override
    public synchronized void run() {
        {
            while (not_killed) {
                synchronized (t) {
                    try {
                        wait(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
}
