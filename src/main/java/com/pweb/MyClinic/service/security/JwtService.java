package com.pweb.MyClinic.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.pweb.MyClinic.config.properties.JwtProperties;
import com.pweb.MyClinic.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;
    private final JwtProperties jwtProperties;

    public String generateToken(String username) {

       var signature = Algorithm.RSA512(publicKey, privateKey);

        return JWT.create()
                .withIssuer(jwtProperties.getIssuer())
                .withClaim("username", username)
                .withNotBefore(new Date())
                .withExpiresAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).plus(jwtProperties.getValidFor()).toInstant()))
                .sign(signature);
    }

    private Map<String, Claim> extractAllClaims(String token) {
        return JWT.require(Algorithm.RSA512(publicKey, privateKey))
                .build()
                .verify(token)
                .getClaims();
    }

    public Claim extractClaim(String token, String claim) {
        final Map<String, Claim> claims = extractAllClaims(token);
        return claims.get(claim);
    }

    public String extractUsername(String token) {
        return extractClaim(token, "username").asString();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, "exp").asDate();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, User userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
