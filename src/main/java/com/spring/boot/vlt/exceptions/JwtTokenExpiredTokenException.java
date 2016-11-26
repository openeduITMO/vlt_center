package com.spring.boot.vlt.exceptions;

import com.spring.boot.vlt.mvc.model.token.JwtToken;
import org.springframework.security.core.AuthenticationException;

public class JwtTokenExpiredTokenException extends AuthenticationException {

    private JwtToken jwtToken;

    public JwtTokenExpiredTokenException(JwtToken jwtToken, String msg, Throwable t) {
        super(msg, t);
        this.jwtToken = jwtToken;
    }

    public JwtTokenExpiredTokenException(String msg) {
        super(msg);
    }

    public String token() {
        return this.jwtToken.getToken();
    }
}
