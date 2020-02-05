package com.company.Simulation.Printfiles;

import com.company.EPK.Function;
import com.company.EPK.Node;
import com.company.Enums.Process_Status;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalTime;
import java.util.Timer;

public class Print_Process extends Print_File {

    public Print_Process(int case_ID, LocalTime timer, String process, Process_Status status) {
        super(case_ID, timer, process, status);
    }

    public Print_Process() {
        super();
    }
}
