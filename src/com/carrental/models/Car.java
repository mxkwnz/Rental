package com.carrental.models;

public class Car {
    private int id;
    private String name;
    private String model;
    private boolean availability;
    private double pricePerDay;

    public Car(int id, String name, String model, boolean availability, double pricePerDay) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.availability = availability;
        this.pricePerDay = pricePerDay;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public boolean isAvailability() {
        return availability;
    }
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
    public double getPricePerDay() {
        return pricePerDay;
    }
    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}