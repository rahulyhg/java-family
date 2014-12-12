package org.demis.familh.core;

import org.demis.familh.core.jpa.entity.Model;

import java.util.List;

public class ResourcesPage<M extends Model> {

    private long start;

    private long end;

    private long numberOfElement;

    private List<M> resources;

    public ResourcesPage(List<M> resources,long numberOfElement) {
        this.numberOfElement = numberOfElement;
        this.resources = resources;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public long getNumberOfElement() {
        return numberOfElement;
    }

    public List<M> getResources() {
        return resources;
    }
}
