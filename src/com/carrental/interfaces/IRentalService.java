package com.carrental.interfaces;

import java.sql.SQLException;

public interface IRentalService {
    void rentCar(int carId, int userId, String startDate, String endDate) throws SQLException;
    void removeRental(int rentalId) throws SQLException;
    void printRentalInvoice(int rentalId) throws SQLException;
    boolean isCarOccupied(int carId, String startDate, String endDate) throws SQLException;
    void removeRentalDaysByPeriod(int rentalId, String startDate, String endDate) throws SQLException;  // Добавленный метод
    void updateRental(int rentalId, String newStartDate, String newEndDate) throws SQLException;
}
