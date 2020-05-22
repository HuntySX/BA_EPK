package com.company.Print.ThreadingDriven;

import com.company.Enums.Process_Status;

import java.time.LocalTime;

public abstract class Print_File {


    private Integer case_ID;

    private LocalTime timer;

    private String Process;

    private Process_Status status;

    public Print_File(Integer case_ID, LocalTime timer, String process, Process_Status status) {
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
