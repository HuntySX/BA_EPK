package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

public class Resource_Deactivating_External_Event extends External_Event {

    private Resource resource;
    private Integer count;

    public Resource_Deactivating_External_Event(Workingtime time, Resource resource, Integer count) {
        super(time);
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
        return "Res_Deact[ " + resource.getID() +
                ": " + resource.getName() +
                "; " + count +
                " at " + Time +
                " ]";
    }
}
