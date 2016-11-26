package com.spring.boot.vlt.exceptions;

import com.spring.boot.vlt.mvc.model.token.JwtToken;
import org.springframework.security.core.AuthenticationException;

public class JwtExpiredTokenException extends AuthenticationException {

    private JwtToken jwtToken;

    public JwtExpiredTokenException(String msg) {
        super(msg);
    }

    public JwtExpiredTokenException(JwtToken jwtToken, String msg, Throwable t) {
        super(msg, t);
        this.jwtToken = jwtToken;
    }

    public String token() {
        return this.jwtToken.getToken();
    }
}