package com.carrental.factory;

import com.carrental.db.PostgreDB;
import com.carrental.services.CarService;
import com.carrental.services.UserService;
import com.carrental.services.RentalService;

public class ServiceFactory {
    private static ServiceFactory instance;
    private CarService carService;
    private UserService userService;
    private RentalService rentalService;

    private ServiceFactory() {
        PostgreDB db = new PostgreDB();
        carService = new CarService(db);
        userService = new UserService(db);
        rentalService = new RentalService(db);
    }


    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public CarService getCarService() {
        return carService;
    }
    public UserService getUserService() {
        return userService;
    }
    public RentalService getRentalService() {
        return rentalService;
    }
}
