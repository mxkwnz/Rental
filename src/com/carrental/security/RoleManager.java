package com.carrental.security;

import java.util.HashMap;
import java.util.Map;

public class RoleManager {
    private static final Map<Integer, String> userRoles = new HashMap<>();

    public static void addUserRole(int userId, String role) {
        userRoles.put(userId, role);
    }


    public static boolean hasRole(int userId, String requiredRole) {
        return userRoles.getOrDefault(userId, "").equalsIgnoreCase(requiredRole);
    }
}
