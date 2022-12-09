package com.dam.damrestoloader;

public class ModelTable {
    private String id;
    private String name;
    private String zone_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZone_id() {
        return zone_id;
    }

    public void setZone_id(String zone_id) {
        this.zone_id = zone_id;
    }

    public ModelTable() {
    }

    public ModelTable(String id, String name, String zone_id) {
        this.id = id;
        this.name = name;
        this.zone_id = zone_id;
    }
}
