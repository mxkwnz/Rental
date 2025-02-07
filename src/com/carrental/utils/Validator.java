package com.carrental.utils;
public class Validator {
    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
    }

    public static boolean isValidPrice(double price) {
        return price > 0;
    }
}