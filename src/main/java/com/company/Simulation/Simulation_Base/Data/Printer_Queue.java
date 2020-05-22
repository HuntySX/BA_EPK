package com.company.Simulation.Simulation_Base.Data;

import com.company.Print.EventDriven.Print_Event_Driven_File;
import com.company.Print.ThreadingDriven.Print_File;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.beans.XMLEncoder;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Printer_Queue implements Runnable {

    private Printer_Gate printer_gate;
    private Thread t;
    private XMLEncoder encoder;
    private FileOutputStream output;
    private boolean not_killed;

    public Printer_Queue() {
        printer_gate = Printer_Gate.get_Printer_Gate();
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

    public List<Print_File> get_PrintList() {
        synchronized (Printer_Gate.get_Printer_Gate()) {
            return Printer_Gate.get_Printer_Gate().getPrinterList();
        }
    }

    @Override
    public synchronized void run() {
        {
            synchronized (t) {
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            synchronized (Printer_Gate.get_Printer_Gate()) {
                List<Print_File> List = Printer_Gate.get_Printer_Gate().getPrinterList();
                List<Print_Event_Driven_File> Nodelist = Printer_Gate.get_Printer_Gate().getNodeList();
                if (!List.isEmpty()) {
                    String s = new Gson().toString();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    String JSONObject = gson.toJson(List);

                    BufferedWriter writer = null;
                    try {
                        writer = new BufferedWriter(new FileWriter("./General/Dirty.json"));
                        writer.write(JSONObject);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
                    String prettyJson = prettyGson.toJson(List);

                    BufferedWriter prettyWriter = null;
                    try {
                        prettyWriter = new BufferedWriter(new FileWriter("./General/Pretty.json"));
                        prettyWriter.write(prettyJson);
                        prettyWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("General Written!");
                    List.clear();
                }
                if (!Nodelist.isEmpty()) {
                    String s = new Gson().toString();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    String JSONObject = gson.toJson(Nodelist);

                    BufferedWriter writer = null;
                    try {
                        writer = new BufferedWriter(new FileWriter("./Nodelist/Dirty.json"));
                        writer.write(JSONObject);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
                    String prettyJson = prettyGson.toJson(Nodelist);

                    BufferedWriter prettyWriter = null;
                    try {
                        prettyWriter = new BufferedWriter(new FileWriter("./Nodelist/Pretty.json"));
                        prettyWriter.write(prettyJson);
                        prettyWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Nodelist Written!");
                    Nodelist.clear();
                }

            }
        }
    }
}

