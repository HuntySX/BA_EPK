package com.company.Print.ThreadingDriven;

import com.company.Enums.Process_Status;

import java.time.LocalTime;

public class Print_Process extends Print_File {

    public Print_Process(int case_ID, LocalTime timer, String process, Process_Status status) {
        super(case_ID, timer, process, status);
    }

    public Print_Process() {
        super();
    }
}
