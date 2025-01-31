package com.carrental.interfaces;

import java.sql.Date;
import java.sql.SQLException;

public interface IRentalService {
    void rentCar(int carId, int userId, Date startDate, Date endDate) throws SQLException;
}