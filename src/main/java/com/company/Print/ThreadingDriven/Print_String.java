package com.company.Print.ThreadingDriven;

public class Print_String extends Print_File {


    private String to_String;


    public Print_String(String string) {
        super(null, null, null, null);
        this.to_String = string;

    }

    public Print_String() {
        super();
    }

    //@XmlElement (name = "Name")
    public String getTo_String() {
        return to_String;
    }

}

