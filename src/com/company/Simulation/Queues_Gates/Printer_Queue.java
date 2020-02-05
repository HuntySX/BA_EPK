package com.company.Simulation.Queues_Gates;

import com.company.Simulation.Printfiles.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.print.Printer;

import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.*;

public class Printer_Queue implements Runnable {

    private Printer_Gate printer_gate;
    private Thread t;
    private XMLEncoder encoder;
    private FileOutputStream output;
    private boolean not_killed;
    private JAXBContext jaxbContext;
    private Marshaller jaxbMarshaller;

    public Printer_Queue() throws FileNotFoundException {
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
                String s = new Gson().toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                String JSONObject = gson.toJson(List);

                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter("./Dirty.json"));
                    writer.write(JSONObject);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
                String prettyJson = prettyGson.toJson(List);

                BufferedWriter prettyWriter = null;
                try {
                    prettyWriter = new BufferedWriter(new FileWriter("./Pretty.json"));
                    prettyWriter.write(prettyJson);
                    prettyWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Written!");
                List.clear();
            }
        }
    }
}

