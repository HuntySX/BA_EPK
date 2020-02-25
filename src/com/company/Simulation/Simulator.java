package com.company.Simulation;

import com.company.Simulation.Simulation_Base.Data.Item;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.company.Enums.Classification.Low;
import static com.company.Enums.Classification.Middle;

public class Simulator {

    private int case_ID;
    private List<Item> all_Items;

    public Simulator() {
        this.all_Items = new ArrayList<>();
        this.case_ID = 0;
    }

    public Simulator(int case_ID, List<Item> all_Items) {
        this.case_ID = case_ID;
        this.all_Items = all_Items;
    }

    public int get_unique_caseID() {
        case_ID = case_ID + 1;
        return case_ID;
    }

    public void add_newItem(Item i) {
        all_Items.add(i);
    }

    public Item generate_singleRandomItem() {
        Random random = new Random();
        if (!all_Items.isEmpty()) {
            Item item = all_Items.get(random.nextInt(all_Items.size()));
            float Quality = generate_Quality();
            Item new_Item = new Item(item.getI_ID(), item.getItem_Name(), 1, Quality, item.getClassification());
            return item;
        }
        return null;
    }

    public List<Item> generate_severalRandomItems() {
        Random random = new Random();
        int distinct_item_counter = random.nextInt(all_Items.size());
        List<Item> result = new ArrayList<>();
        List<Item> worklist = new ArrayList<>();
        List<Item> list = new ArrayList<>(all_Items);
        if (distinct_item_counter == 0) {
            List<Item> oneresult = new ArrayList<>(all_Items);
            return oneresult;
        }
        while (distinct_item_counter > 0) {
            int i = list.size();
            int index = random.nextInt(i);
            worklist.add(list.get(index));
            list.remove(index);
            distinct_item_counter--;
        }
        for (Item item : worklist) {
            Item new_Item = new Item(item.getI_ID(), item.getItem_Name(), generate_Quantity(item), 1.0f, item.getClassification());
            result.add(new_Item);
        }
        return result;
    }

    private int generate_Quantity(Item item) {
        Random random = new Random();
        if (item.getClassification() == Low) {
            return 50 + random.nextInt(151);
        }
        if (item.getClassification() == Middle) {
            return 10 + random.nextInt(51);
        } else {
            return 1 + random.nextInt(6);
        }
    }

    private float generate_Quality() {
        Random random = new Random();
        float result = random.nextFloat();
        if (result == 0) {
            return 0;
        } else if (result > 0 && result <= 0.1) {
            return 0.1f;
        } else if (result > 0.1 && result <= 0.2) {
            return 0.2f;
        } else if (result > 0.2 && result <= 0.3) {
            return 0.3f;
        } else if (result > 0.3 && result <= 0.4) {
            return 0.4f;
        } else if (result > 0.4 && result <= 0.5) {
            return 0.5f;
        } else if (result > 0.5 && result <= 0.6) {
            return 0.6f;
        } else if (result > 0.6 && result <= 0.7) {
            return 0.7f;
        } else if (result > 0.7 && result <= 0.8) {
            return 0.8f;
        } else if (result > 0.8 && result <= 0.9) {
            return 0.9f;
        } else return 1f;
    }

    public LocalTime get_OrderTime() {
        LocalTime time = LocalTime.now();
        time = time.plusMinutes(3);
        return time;
    }

    public LocalTime get_Big_OrderTime() {
        LocalTime time = LocalTime.now();
        time = time.plusSeconds(3);
        return time;
    }
}