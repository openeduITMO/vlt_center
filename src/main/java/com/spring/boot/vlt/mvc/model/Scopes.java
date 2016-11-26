package com.spring.boot.vlt.mvc.model;

public enum Scopes {
    REFRESH_TOKEN, Scopes;

    public String authority() {
        return "ROLE_" + this.name();
    }
}
