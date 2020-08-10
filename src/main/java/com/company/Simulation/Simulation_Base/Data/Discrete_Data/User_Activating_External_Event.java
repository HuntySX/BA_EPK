package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

public class User_Activating_External_Event extends External_Event {

    private User user;

    public User_Activating_External_Event(Workingtime time, User user) {
        super(time);
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
        return "User_Act[" +
                user.getFirst_Name() +
                " " + user.getLast_Name() +
                " " + Time +
                ']';
    }
}
