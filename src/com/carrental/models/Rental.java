package com.carrental.models;

public class Rental {
    private int rentalId;
    private int carId;
    private int userId;
    private String startDate;
    private String endDate;
    private String status;

    public Rental(int rentalId, int carId, int userId, String startDate, String endDate, String status) {
        this.rentalId = rentalId;
        this.carId = carId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getRentalId() {
        return rentalId;
    }

    public int getCarId() {
        return carId;  // Геттер для carId
    }

    public int getUserId() {
        return userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

}
