package com.company.Print.EventDriven;

public class Print_Resources {
    private Integer Res_ID;
    private String Res_Name;
    private Integer Res_Count;

    public Print_Resources(Integer res_ID, String res_Name, Integer res_Count) {
        Res_ID = res_ID;
        Res_Name = res_Name;
        Res_Count = res_Count;
    }

    public Integer getRes_ID() {
        return Res_ID;
    }

    public void setRes_ID(Integer res_ID) {
        Res_ID = res_ID;
    }

    public String getRes_Name() {
        return Res_Name;
    }

    public void setRes_Name(String res_Name) {
        Res_Name = res_Name;
    }

    public Integer getRes_Count() {
        return Res_Count;
    }

    public void setRes_Count(Integer res_Count) {
        Res_Count = res_Count;
    }
}
