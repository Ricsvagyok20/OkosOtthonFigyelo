package com.example.okosotthonfigyelo;

public class Device {
    private String name;
    private String manufacturer;

    private String description;
    private boolean active;
    private final int imageResource;

    public Device(String name, String manufacturer, String description, boolean active, int imageResource) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.description = description;
        this.active = active;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public boolean isActive() {
        return active;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
