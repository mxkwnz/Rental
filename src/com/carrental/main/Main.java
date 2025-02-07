package com.carrental.main;

import com.carrental.controller.CarController;
import com.carrental.controller.UserController;
import com.carrental.controller.RentalController;
import com.carrental.services.CarService;
import com.carrental.services.UserService;
import com.carrental.services.RentalService;
import com.carrental.db.PostgreDB;
import com.carrental.db.interfaces.IDB;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        IDB db = new PostgreDB("jdbc:postgresql://localhost:5432", "postgres", "taktalif12", "postgres");

        CarService carService = new CarService(db);
        UserService userService = new UserService(db);
        RentalService rentalService = new RentalService(db);

        CarController carController = new CarController(carService);
        UserController userController = new UserController(userService);
        RentalController rentalController = new RentalController(rentalService, userService, carService);

        MyAppApplication app = new MyAppApplication(carController, userController, rentalController);
        app.run();
    }
}
