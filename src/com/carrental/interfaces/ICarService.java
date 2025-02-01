package com.carrental.interfaces;

import com.carrental.models.Car;

import java.sql.SQLException;
import java.util.List;

public interface ICarService {
    List<Car> getAvailableCars() throws SQLException;
    void addNewCar(String name, String model, double pricePerDay) throws SQLException;
    void removeCar(int carId) throws SQLException;
    Car getCarById(int carId) throws SQLException;
    boolean isCarAvailable(int carId) throws SQLException;
}
