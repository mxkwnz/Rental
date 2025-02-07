package com.carrental.controller;

import com.carrental.interfaces.IUserService;
import com.carrental.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    public void displayUsers() throws SQLException {
        List<User> users = userService.getAllUsers();
        System.out.println("Пользователи:");
        users.forEach(user ->
                System.out.println("ID: " + user.getId() + ", Имя: " + user.getName() +
                        ", Email: " + user.getEmail() + ", Телефон: " + user.getPhone())
        );
    }

    public void addNewUser(Scanner scanner) throws SQLException {
        System.out.print("Введите имя пользователя: ");
        String name = scanner.nextLine();
        System.out.print("Введите email пользователя: ");
        String email = scanner.nextLine();
        System.out.print("Введите телефон пользователя: ");
        String phone = scanner.nextLine();

        userService.addNewUser(name, email, phone);
    }

    public void removeUser(Scanner scanner) throws SQLException {
        System.out.print("Введите ID пользователя для удаления: ");
        int userId = scanner.nextInt();
        userService.removeUser(userId);
    }
}
