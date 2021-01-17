package com.company.Process_Mining;

import com.company.Process_Mining.Base_Data.Mining_User;

public class Mining_User_Event extends Mining_Event {
    private final Mining_User User;

    public Mining_User_Event(int eventTimeFromStart, Mining_User user, int Day) {
        super(eventTimeFromStart, Day);
        User = user;
    }

    public Mining_User getUser() {
        return User;
    }
}
