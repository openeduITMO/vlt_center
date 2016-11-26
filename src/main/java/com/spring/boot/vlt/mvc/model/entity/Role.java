package com.spring.boot.vlt.mvc.model.entity;

public enum Role {
    ADMIN,
    DEVELOPER,
    STUDENT;
    
    public String authority() {
        return "ROLE_" + this.name();
    }
}
