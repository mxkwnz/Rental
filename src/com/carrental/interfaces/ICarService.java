package com.carrental.interfaces;

import com.carrental.models.Car;

import java.sql.SQLException;
import java.util.List;

public interface ICarService {
    void addNewCar(String name, String model, double pricePerDay, int categoryId) throws SQLException;

    List<Car> getAvailableCars() throws SQLException;
    void removeCar(int carId) throws SQLException;
    Car getCarById(int carId) throws SQLException;
    boolean isCarAvailable(int carId) throws SQLException;
}
