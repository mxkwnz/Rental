package com.carrental.interfaces;

import com.carrental.models.Car;
import java.sql.SQLException;
import java.util.List;

public interface ICarService {
    boolean isCarAvailable(int carId) throws SQLException;
    List<Car> getAvailableCars() throws SQLException;
    void addNewCar(String name, String model, double pricePerDay, int available) throws SQLException;
    void removeCar(int carId) throws SQLException;
}
