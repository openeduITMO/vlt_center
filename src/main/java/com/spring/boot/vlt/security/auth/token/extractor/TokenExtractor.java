package com.spring.boot.vlt.security.auth.token.extractor;

public interface TokenExtractor {
    public String extract(String payload);
}
