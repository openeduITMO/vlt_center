package com.spring.boot.vlt.security.auth.token.verifier;

public interface TokenVerifier {
    public boolean verify(String jti);
}
