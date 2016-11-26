package com.spring.boot.vlt.mvc.model.token;

import com.spring.boot.vlt.exceptions.JwtExpiredTokenException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

public class RawAccessJwtToken implements JwtToken {
    private static Logger logger = LoggerFactory.getLogger(RawAccessJwtToken.class);

    private String token;

    public RawAccessJwtToken(String token) {
        this.token = token;
    }

    public Jws<Claims> parseClaims(String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.error("Invalid JWT JwtToken", ex);
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            logger.info("JWT JwtToken is expired", expiredEx);
            throw new JwtExpiredTokenException(this, "JWT JwtToken expired", expiredEx);
        }
    }

    @Override
    public String getToken() {
        return token;
    }
}
