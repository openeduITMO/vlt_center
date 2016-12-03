package com.spring.boot.vlt.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    GLOBAL(2),

    AUTHENTICATION(10),
    JWT_TOKEN_EXPIRED(11),
    USER_EXIST_IN_DARABASE(12),
    USER_NOT_SAVE(13),
    NOT_ACCESS_RIGHT(14);

    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
