package com.carrental.interfaces;

import com.carrental.models.Car;

import java.sql.Date;
import java.sql.SQLException;

public interface ICarService {
    void showAvailableCars() throws SQLException;

    Car getCarById(int carId) throws SQLException;
}
