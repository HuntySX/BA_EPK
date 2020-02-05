package com.company.Simulation.Printfiles;

import com.company.EPK.Function;
import com.company.EPK.Node;
import com.company.Enums.Process_Status;
import com.company.Simulation.Queues_Gates.Printer_Gate;
import com.company.Simulation.Queues_Gates.Printer_Queue;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.time.LocalTime;
import java.util.Timer;

public abstract class Print_File {


    private int case_ID;

    private LocalTime timer;

    private String Process;

    private Process_Status status;

    public Print_File(int case_ID, LocalTime timer, String process, Process_Status status) {
        this.case_ID = case_ID;
        this.timer = timer;
        this.Process = process;
        this.status = status;
    }

    public Print_File() {
    }

    public int getCase_ID() {
        return case_ID;
    }

    public String getTimer() {
        return timer.toString();
    }

    public String getProcess() {
        return Process;
    }

    public Process_Status getStatus() {
        return status;
    }
}
