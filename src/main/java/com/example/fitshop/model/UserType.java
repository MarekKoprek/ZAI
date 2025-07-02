package com.example.fitshop.model;

import lombok.Getter;

@Getter
public enum UserType {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public static UserType fromValue(String value) {
        for (UserType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + value);
    }
}
