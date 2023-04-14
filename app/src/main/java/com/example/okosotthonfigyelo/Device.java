package com.example.okosotthonfigyelo;

public class Device {
    private String name;
    private String type;
    private boolean onOrNot;
    private String description;

    public Device(String name, String type, boolean onOrNot, String description) {
        this.name = name;
        this.type = type;
        this.onOrNot = onOrNot;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isOnOrNot() {
        return onOrNot;
    }

    public String getDescription() {
        return description;
    }
}
