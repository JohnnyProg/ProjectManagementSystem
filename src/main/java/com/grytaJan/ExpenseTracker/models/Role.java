package com.grytaJan.ExpenseTracker.models;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;

public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_MANAGER
    ;


    @Override
    public String getAuthority() {
        return name();
    }

    public static String[] getAuthorities(Role... roles) {
        return Arrays.stream(roles) .map(Role::getAuthority) .toArray(String[]::new);
    }
}

