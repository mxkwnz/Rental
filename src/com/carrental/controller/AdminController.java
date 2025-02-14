package com.carrental.controller;

import com.carrental.models.Admin;

public class AdminController {
    public void addManager(String name, String email) {
        Admin newAdmin = new Admin(1, name, email);
        System.out.println("Manager added: " + newAdmin.getName());
    }

    public void removeManager(int managerId) {
        System.out.println("Manager with ID " + managerId + " removed.");
    }
}