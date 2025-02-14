package com.carrental.models;

public class Car {
    private int id;
    private String brand;
    private String model;
    private boolean available;
    private double pricePerDay;
    private VehicleCategory category;


    public Car(int id, String brand, String model, boolean available, double pricePerDay, VehicleCategory category) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.available = available;
        this.pricePerDay = pricePerDay;
        this.category = category;
    }

    // Getters and setters for the fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public VehicleCategory getCategory() {
        return category;
    }

    public void setCategory(VehicleCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("Car ID: %d, Brand: %s, Model: %s, Price per day: %.2f, Available: %b, Category: %s",
                id, brand, model, pricePerDay, available, category);

    }

}