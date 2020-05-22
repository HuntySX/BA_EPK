package com.company.Print.ThreadingDriven;

import java.util.ArrayList;
import java.util.List;

public class Print_List {

    List<Print_File> Print_List = null;

    public Print_List() {
        Print_List = new ArrayList<>();
    }


    public void add_file(Print_File f) {
        Print_List.add(f);
    }

    public List<Print_File> getFile() {
        return Print_List;
    }

    public void setInstances(List<Print_File> employees) {
        this.Print_List = employees;
    }

}


