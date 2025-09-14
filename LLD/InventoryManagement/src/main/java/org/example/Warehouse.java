package org.example;

public class Warehouse {
    private final String id;
    private final String name;

    public Warehouse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
