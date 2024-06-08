package com.lab.model.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

}

