package com.company.Simulation.Simulation_Threading;

import com.company.Enums.Order_Status;
import com.company.Simulation.Simulation_Base.Data.Threading_Data.*;

import java.util.ArrayList;
import java.util.List;

import static com.company.Enums.Order_Status.*;

public class Warehouse_Queue implements Runnable {

    private final Warehouse_Gate warehouse_gate;
    private Thread t;
    private final boolean not_killed;

    public Warehouse_Queue() {
        this.warehouse_gate = Warehouse_Gate.get_Warehouse_Gate();
        t = null;
        not_killed = true;
    }


    public synchronized Warehouse getWarehouse() {
        return Warehouse_Gate.get_Warehouse_Gate().getWarehouse();
    }


    public synchronized void run() {
        while (not_killed) {
            List<Process_instance> arriving = null;
            List<Process_instance> waiting = null;
            List<Process_instance> to_delete = new ArrayList<>();

            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (Warehouse_Gate.get_Warehouse_Gate()) {
                arriving = Warehouse_Gate.get_Warehouse_Gate().getArriving_Orders();
                waiting = Warehouse_Gate.get_Warehouse_Gate().getWaiting_Orders();

                for (Process_instance arrive : arriving) {
                    if (!(((Order_Instance) arrive.getInstance()).isGeneral_Stock())) {
                        for (Process_instance wait : waiting) {
                            if (wait.getInstance().getCase_ID() == ((Order_Instance) arrive.getInstance()).getFor_caseID()) {
                                List<Item> Ordered_Items = ((Buy_Instance) wait.getInstance()).getBuy_Items();
                                for (Item i : Ordered_Items) {
                                    if (((Buy_Instance) wait.getInstance()).getOrder_Monitor().get_Single_Status(i) == OrderWaiting) {
                                        ((Buy_Instance) wait.getInstance()).getOrder_Monitor().change_Status(i, Received);
                                    }
                                }
                                to_delete.add(arrive);
                                break;
                            }
                        }
                    } else {
                        List<Item> arriving_Items = ((Order_Instance) arrive.getInstance()).getOrdered_Items();
                        for (Item i : arriving_Items) {
                            getWarehouse().addSingleStock(i);
                        }
                        to_delete.add(arrive);
                    }

                }
                if (!to_delete.isEmpty()) {
                    arriving.removeAll(to_delete);
                }


                if (!waiting.isEmpty()) {
                    synchronized (Warehouse_Gate.get_Warehouse_Gate()) {
                        for (Process_instance instance : Warehouse_Gate.get_Warehouse_Gate().getWaiting_Orders()) {
                            List<Item> Big_Order = new ArrayList<>();
                            Order_Monitor Monitor = ((Buy_Instance) instance.getInstance()).getOrder_Monitor();
                            for (Item i : Monitor.getOrders()) {
                                if (Monitor.get_Single_Status(i) == Unchecked || Monitor.get_Single_Status(i) == Waiting) {
                                    Order_Status status = getWarehouse().takeSingleStock(instance, i);
                                    if (status == Received) {
                                        Monitor.change_Status(i, Received);
                                    } else if (status == OrderWaiting) {
                                        Monitor.change_Status(i, OrderWaiting);
                                        Big_Order.add(i);
                                    } else if (status == Waiting) {
                                        Monitor.change_Status(i, Waiting);

                                    }
                                }
                            }

                            if (!Big_Order.isEmpty()) {
                                getWarehouse().large_Ordering(instance, Big_Order);

                            }
                        }
                    }
                }
            }

            for (Process_instance instance : waiting) {
                check_and_Notify_Threads(instance);
            }
            /*if (!arriving.isEmpty()) {
                for (Process_instance a : arriving) {
                    synchronized (a) {
                        if (((Order_Instance) a.getInstance()).isGeneral_Stock()) {
                            to_delete.add(a);
                        } else {

                            for (Process_instance w : waiting) {
                                synchronized (w) {
                                    if (a.getInstance().getCase_ID() == w.getInstance().getCase_ID()) {
                                        w.getT().notify();
                                        to_delete.add(a);
                                    }
                                }
                            }
                        }

                    }

                }
            }
            if (!to_delete.isEmpty()) {
                for (Process_instance del : to_delete) {
                    arriving.remove(del);
                    if (!((Order_Instance) del.getInstance()).isGeneral_Stock()) {
                        waiting.remove(del);
                    }
                }
            }

        }
    }*/
        }
    }


    private void check_and_Notify_Threads(Process_instance instance) {
        for (Order_Status status : ((Buy_Instance) instance.getInstance()).getOrder_Monitor().getOrder_Status()) {
            if (status != Received) {

            } else {
                synchronized (instance.getLock()) {
                    ((Buy_Instance) instance.getInstance()).setFullfilled(true);
                    instance.getLock().notify();
                }
            }
        }
    }

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }

}