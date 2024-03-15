package com.pweb.MyClinic.security.config;

import com.pweb.MyClinic.config.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static org.springframework.security.oauth2.jose.jws.SignatureAlgorithm.RS512;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtProperties jwtProperties;

    @Bean
    public RSAPublicKey rsaPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        final var publicKeyPEM = jwtProperties.getRsa().getPublicKey()
                .replace("-----BEGIN RSA PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END RSA PUBLIC KEY-----", "");

        final var keyFactory = KeyFactory.getInstance("RSA");
        final var keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyPEM));
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    @Bean
    public RSAPrivateKey rsaPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        final var privateKeyPEM = jwtProperties.getRsa().getPrivateKey()
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END RSA PRIVATE KEY-----", "");

        final var keyFactory = KeyFactory.getInstance("RSA");
        final var keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyPEM));
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    @Bean
    JwtDecoder jwtDecoder() throws NoSuchAlgorithmException, InvalidKeySpecException {
        var decoder = NimbusJwtDecoder.withPublicKey(rsaPublicKey())
                .signatureAlgorithm(RS512)
                .build();
        return new NimbusJwtDecoderAdapter(decoder);
    }

    @RequiredArgsConstructor
    private static final class NimbusJwtDecoderAdapter implements JwtDecoder {
        private final JwtDecoder decoder;

        @Override
        public Jwt decode(String token) throws JwtException {
            try {
                return decoder.decode(token);
            } catch (JwtException jwtException) {
                throw new RuntimeException(jwtException.getMessage());
            }
        }
    }
}
