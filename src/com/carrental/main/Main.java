package com.carrental.main;

import com.carrental.controller.CarController;
import com.carrental.controller.UserController;
import com.carrental.controller.RentalController;
import com.carrental.services.CarService;
import com.carrental.services.UserService;
import com.carrental.services.RentalService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        CarService carService = new CarService();
        UserService userService = new UserService();
        RentalService rentalService = new RentalService();

        CarController carController = new CarController(carService);
        UserController userController = new UserController(userService);
        RentalController rentalController = new RentalController(rentalService, userService, carService);

        MyAppApplication app = new MyAppApplication(carController, userController, rentalController);
        app.run();
    }
}
