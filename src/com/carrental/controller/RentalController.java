package com.carrental.controller;

import com.carrental.models.VehicleCategory;
import com.carrental.services.RentalService;
import com.carrental.services.CarService;
import com.carrental.models.Rental;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class RentalController {
    private RentalService rentalService;
    private CarService carService;

    public RentalController(RentalService rentalService, CarService carService) {
        this.rentalService = rentalService;
        this.carService = carService;
    }

    public void startRentalProcess(Scanner scanner) throws SQLException {
        while (true) {
            System.out.println("Choose category of the car to rent:");
            for (VehicleCategory category : VehicleCategory.values()) {
                System.out.println(category.ordinal() + 1 + ". " + category);
            }
            System.out.print("Enter number of category (or 0, to exit): ");
            int categoryChoice = scanner.nextInt();
            scanner.nextLine();

            if (categoryChoice == 0) {
                System.out.println("Returning to main menu...");
                return;
            }

            if (categoryChoice < 1 || categoryChoice > VehicleCategory.values().length) {
                System.out.println("Wrong category choice, try again.");
            } else {
                VehicleCategory category = VehicleCategory.values()[categoryChoice - 1];

                while (true) {
                    System.out.println("Available cars in category " + category + ":");
                    carService.getCarsByCategory(category);

                    System.out.print("Enter ID of the car you want to rent (or 0 to return back): ");
                    int carId = scanner.nextInt();
                    scanner.nextLine();

                    if (carId == 0) {
                        System.out.println("Returning to category...");
                        break;
                    }

                    System.out.print("Enter your user ID: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter start date of the rent (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine();

                    System.out.print("Enter end date of the rent (YYYY-MM-DD): ");
                    String endDate = scanner.nextLine();

                    rentalService.rentCar(carId, userId, startDate, endDate);
                    System.out.println("Rental process completed successfully.");
                    return;
                }
            }
        }
    }

    public void viewRentals() throws SQLException {
        System.out.println("Showing all rentals...");
        rentalService.getAllRentals();
    }

    public void approveOrRejectRentals(Scanner scanner) throws SQLException {
        while (true) {
            System.out.print("Enter Rental ID to approve/reject (or 0 to go back): ");
            int rentalId = scanner.nextInt();
            scanner.nextLine();

            if (rentalId == 0) {
                System.out.println("Returning to Rental Management...");
                return;
            }

            System.out.print("Approve (true) or Reject (false)?: ");
            boolean approve = scanner.nextBoolean();
            scanner.nextLine();
            rentalService.updateRentalStatus(rentalId, approve);
            System.out.println("Rental decision recorded successfully.");
        }
    }

    public void manageRentals(Scanner scanner) throws SQLException {
        System.out.println("Managing rentals...");
        List<Rental> rentals = rentalService.getAllRentals();
        if (rentals.isEmpty()) {
            System.out.println("No rentals available.");
            return;
        }

        System.out.println("\nList of Rentals:");
        for (int i = 0; i < rentals.size(); i++) {
            Rental rental = rentals.get(i);
            System.out.println((i + 1) + ". Rental ID: " + rental.getRentalId() +
                    ", Car ID: " + rental.getCarId() +
                    ", User ID: " + rental.getUserId() +
                    ", Start Date: " + rental.getStartDate() +
                    ", End Date: " + rental.getEndDate() +
                    ", Status: " + rental.getStatus());
        }

        System.out.print("Enter rental ID to approve/reject (or 0 to return): ");
        int rentalId = scanner.nextInt();
        scanner.nextLine();

        if (rentalId == 0) {
            System.out.println("Returning to menu...");
            return;
        }

        Rental selectedRental = null;
        for (Rental rental : rentals) {
            if (rental.getRentalId() == rentalId) {
                selectedRental = rental;
                break;
            }
        }

        if (selectedRental == null) {
            System.out.println("Invalid rental ID. Returning to menu...");
            return;
        }

        System.out.print("Approve this rental? (yes/no): ");
        String decision = scanner.nextLine();
        boolean approve = decision.equalsIgnoreCase("yes");

        rentalService.updateRentalStatus(rentalId, approve);
        System.out.println("Rental status updated to " + (approve ? "Approved" : "Rejected"));
    }

    public void viewRentedCars() throws SQLException {
        System.out.println("Viewing rented cars...");
        rentalService.getRentedCars();
    }
}
