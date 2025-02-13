package com.carrental.models;

import java.util.regex.Pattern;

public class Password {
    private String password;

    public Password(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid() {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        return Pattern.matches(regex, this.password);
    }

    public boolean matches(Password other) {
        return this.password.equals(other.getPassword());
    }
}
