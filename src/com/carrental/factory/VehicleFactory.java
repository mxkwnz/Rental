package com.carrental.factory;

import com.carrental.models.Car;
import com.carrental.models.VehicleCategory;

public class VehicleFactory {
    private static VehicleFactory instance;

    private VehicleFactory() {}

    public static VehicleFactory getInstance() {
        if (instance == null) {
            instance = new VehicleFactory();
        }
        return instance;
    }


    public Car createVehicle(int id, String brand, String model, boolean available, double pricePerDay, VehicleCategory category) {
        return new Car(id, brand, model, available, pricePerDay, category);
    }
}
