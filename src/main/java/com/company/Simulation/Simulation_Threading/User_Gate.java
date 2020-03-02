package com.company.Simulation.Simulation_Threading;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class User_Gate {

    private static User_Gate user_gate;
    private static List<User> User_List;
    private static java.util.concurrent.locks.Lock user_Lock;

    public static User_Gate get_User_Gate() {
        if (user_gate == null) {
            user_gate = new User_Gate();
            User_List = new ArrayList<>();
            user_Lock = new ReentrantLock();
        }
        return user_gate;
    }

    public List<User> getUser_List() {
        return User_List;
    }

    public void setUser_List(List<User> user_List) {
        User_List = user_List;
    }

    public void add_Single_User(User user) {
        User_List.add(user);
    }

    public Lock getUser_Lock() {
        return user_Lock;
    }

    public boolean check_inactivity() {
        for (User u : User_List) {
            if (!u.isActive()) {
                return true;
            }
        }
        return false;
    }
}
