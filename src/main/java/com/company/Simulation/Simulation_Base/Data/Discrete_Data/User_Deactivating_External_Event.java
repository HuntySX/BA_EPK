package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

public class User_Deactivating_External_Event extends External_Event {

    private User user;

    public User_Deactivating_External_Event(Workingtime time, int day, User user, int EEV_ID) {
        super(time, day, EEV_ID);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "User_Deact[" +
                user.getFirst_Name() +
                " " + user.getLast_Name() +
                " " + Time +
                ']';
    }

}
