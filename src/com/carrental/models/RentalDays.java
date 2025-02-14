package com.carrental.models;

import java.sql.Date;

public class RentalDays {
    private int rentalId;
    private Date rentalDate;

    public RentalDays(int rentalId, Date rentalDate) {
        this.rentalId = rentalId;
        this.rentalDate = rentalDate;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

}