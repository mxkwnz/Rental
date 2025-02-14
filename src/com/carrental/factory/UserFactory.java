package com.carrental.factory;

import com.carrental.models.User;
import com.carrental.models.Role;

public class UserFactory {
    private static UserFactory instance;

    private UserFactory() {}

    public static UserFactory getInstance() {
        if (instance == null) {
            instance = new UserFactory();
        }
        return instance;
    }


    public User createUser(int id, String name, String email, String phone, String password, Role role) {
        return new User(id, name, email, phone, password, role.name());
    }
}