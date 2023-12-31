package com.example.todofamilyapi.security.jwt;

import com.example.todofamilyapi.entities.Users;
import com.example.todofamilyapi.security.services.UserDetailsImpl;
import com.example.todofamilyapi.services.UserService;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.util.Arrays;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JWTUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtils.class);

    private final UserService userService;
    private final Environment environment;

    @Value("${api.app.jwtSecret}")
    private String jwtSecret;

    @Value("${api.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${api.app.jwtCookieName}")
    private String jwtCookie;

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        final var findedUser = userService.findByEmail(userPrincipal.getUsername());

        if (findedUser.isPresent()) {
            String jwt = generateTokenFromUsername(findedUser.get());
            final var builder = ResponseCookie.from(jwtCookie, jwt)
                    .path("/").maxAge(3600000L)
                    .secure(true)
                    .httpOnly(true);

            if (Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
                builder.domain(".samirlaguardia.com");
            }

            return builder.build();
        }

        return null;
    }

    public ResponseCookie getCleanJwtCookie() {
        final var builder = ResponseCookie.from(jwtCookie, "")
                .path("/")
                .maxAge(0)
                .secure(true)
                .httpOnly(true);

        if (Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
            builder.domain(".samirlaguardia.com");
        }

        return builder.build();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String generateTokenFromUsername(Users user) {
        return Jwts.builder()
                .claim("id", user.getId())
                .setSubject(user.getEmail())
                .claim("name", user.getName())
                .claim("admin", user.isAdmin())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
