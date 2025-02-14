package com.carrental.controller;

import com.carrental.security.RoleManager;
import com.carrental.services.RentalService;

import java.sql.SQLException;

public class SecureRentalController {
    private RentalService rentalService;

    public SecureRentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    public void approveRental(int userId, int rentalId, boolean approve) {
        if (!RoleManager.hasRole(userId, "ADMIN") && !RoleManager.hasRole(userId, "MANAGER")) {
            System.out.println("Access denied. Only admins and managers can approve rentals.");
            return;
        }
        try {
            rentalService.updateRentalStatus(rentalId, approve);
        } catch (SQLException e) {
            System.out.println("Error updating rental status: " + e.getMessage());
        }
        System.out.println("Rental status updated.");
    }
}