package com.carrental.models;

import java.util.List;

public class Customer {
    private int id;
    private String name;
    private String email;
    private List<String> rentalHistory;

    public Customer(int id, String name, String email, List<String> rentalHistory) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.rentalHistory = rentalHistory;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRentalHistory() {
        return rentalHistory;
    }

    public void setRentalHistory(List<String> rentalHistory) {
        this.rentalHistory = rentalHistory;
    }

}