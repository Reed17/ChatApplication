package com.impltech.chatApp.security;

import com.impltech.chatApp.config.JwtProperties;
import com.impltech.chatApp.enums.Message;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger LOG = LoggerFactory.getLogger(JwtProvider.class);

    private final JwtProperties jwtProperties;

    @Autowired
    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateAccessToken(final UserPrincipal userPrincipal) {
        final Date now = new Date();
        final Date expiryDate = new Date(now.getTime() + jwtProperties.getExpirationTimeMs());
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getUserId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .compact();
    }

    public String getTokenFromRequest(final HttpServletRequest request) {
        final String jwt = request.getHeader("Authorization");
        if (StringUtils.hasText(jwt) && jwt.startsWith("Bearer ")) {
            return jwt.substring(7);
        }
        return null;
    }

    public Long getUserIdFromToken(final String accessToken) {
        final Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(accessToken)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public Boolean validateAccessToken(final String accessToken) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(accessToken);

            return true;
        } catch (SignatureException ex) {
            LOG.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            LOG.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            LOG.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            LOG.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOG.error("JWT claims string is empty.");
        }

        return false;
    }
}
