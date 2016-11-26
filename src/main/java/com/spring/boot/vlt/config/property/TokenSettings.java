package com.spring.boot.vlt.config.property;

import com.spring.boot.vlt.mvc.model.token.JwtToken;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.token")
public class TokenSettings {
    /**
     * {@link JwtToken} will expire after this time.
     */
    private Integer tokenExpirationTime;

    /**
     * JwtToken issuer.
     */
    private String tokenIssuer;

    /**
     * Key is used to sign {@link JwtToken}.
     */
    private String tokenSigningKey;

    /**
     * {@link JwtToken} can be refreshed during this timeframe.
     */
    private Integer refreshTokenExpTime;

    public Integer getRefreshTokenExpTime() {
        return refreshTokenExpTime;
    }

    public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
        this.refreshTokenExpTime = refreshTokenExpTime;
    }

    public Integer getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public void setTokenExpirationTime(Integer tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }
    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    public String getTokenSigningKey() {
        return tokenSigningKey;
    }

    public void setTokenSigningKey(String tokenSigningKey) {
        this.tokenSigningKey = tokenSigningKey;
    }
}
