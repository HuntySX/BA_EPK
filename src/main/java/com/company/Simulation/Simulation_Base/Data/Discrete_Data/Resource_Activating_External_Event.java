package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

public class Resource_Activating_External_Event extends External_Event {

    private Resource resource;
    private Integer count;

    public Resource_Activating_External_Event(Workingtime time, int day, Resource resource, Integer count, int EEV_ID) {
        super(time, day, EEV_ID);
        this.resource = resource;
        this.count = count;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Res_Act[ " + resource.getID() +
                ": " + resource.getName() +
                "; " + count +
                " at " + Time +
                " ]";
    }
}
