package com.company.Print.EventDriven;

import java.util.List;

public class Connected_User_Print {

    private int ID;
    private String Firstname;
    private String Lastname;
    private List<Connected_Workforce_Print> Workforces;

    public Connected_User_Print(int ID, String firstname, String lastname, List<Connected_Workforce_Print> workforces) {
        this.ID = ID;
        Firstname = firstname;
        Lastname = lastname;
        Workforces = workforces;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public List<Connected_Workforce_Print> getWorkforces() {
        return Workforces;
    }

    public void setWorkforces(List<Connected_Workforce_Print> workforces) {
        Workforces = workforces;
    }
}
