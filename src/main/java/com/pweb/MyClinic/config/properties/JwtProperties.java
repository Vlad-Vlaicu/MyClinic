package com.pweb.MyClinic.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "pweb.jwt")
public class JwtProperties {

    private Duration validFor = Duration.ofDays(7);

    private String issuer;

    @NotNull
    private Rsa rsa;

    @Getter
    @Setter
    @Validated
    public static class Rsa {
        private String publicKey;
        private String privateKey;
    }
}