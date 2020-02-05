package com.company.Simulation.Printfiles;

import com.company.EPK.Function;
import com.company.EPK.Node;
import com.company.Enums.Process_Status;
import com.company.Simulation.Data.User;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalTime;
import java.util.Timer;

public class Print_Activate extends Print_File {

    private String name;
    private String familyname;


    public Print_Activate(int case_id, String process, LocalTime timer, Process_Status status, String Name, String Familyname) {
        super(case_id, timer, process, status);
        this.name = Name;
        this.familyname = Familyname;

    }

    public Print_Activate() {
        super();
    }

    //@XmlElement (name = "Name")
    public String getName() {
        return name;
    }

    //@XmlElement (name = "Familyname")
    public String getFamilyname() {
        return familyname;
    }
}
