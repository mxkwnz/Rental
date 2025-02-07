package com.carrental.factory;

import com.carrental.db.PostgreDB;
import com.carrental.db.interfaces.IDB;
import com.carrental.services.CarService;
import com.carrental.services.RentalService;
import com.carrental.services.UserService;

public class ServiceFactory {
    private static final IDB db = new PostgreDB("jdbc:postgresql://localhost:5432", "postgres", "password", "carrental");

    public static CarService getCarService() {
        return new CarService(db);
    }

    public static UserService getUserService() {
        return new UserService(db);
    }

    public static RentalService getRentalService() {
        return new RentalService(db);
    }
}