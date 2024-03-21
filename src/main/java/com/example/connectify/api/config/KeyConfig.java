package com.example.connectify.api.config;

import com.example.connectify.api.services.secrets.HmacSecretKeyGenerator;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
@AllArgsConstructor
@Configuration
public class KeyConfig {

    @Bean
    public SecretKey secretKey() {
        HmacSecretKeyGenerator hmacSecretKeyGenerator = new HmacSecretKeyGenerator();
        return hmacSecretKeyGenerator.generateSecretKey();
    }
}
