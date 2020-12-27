package com.company.Process_Mining.Base_Data;

public class Mining_Resource_Count {
    private Mining_Resource Resource;
    private int count;

    public Mining_Resource_Count(Mining_Resource resource, int count) {
        Resource = resource;
        this.count = count;
    }

    public Mining_Resource getResource() {
        return Resource;
    }

    public void setResource(Mining_Resource resource) {
        Resource = resource;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
