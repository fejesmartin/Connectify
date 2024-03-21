package com.example.connectify.api.services.secrets;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class HmacSecretKeyGenerator {

    // Method to generate a secret key using HmacSHA256 algorithm
    public SecretKey generateSecretKey() {
        try {
            // Initialize the KeyGenerator with HmacSHA256 algorithm
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            // Generate a secret key
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            // Handle NoSuchAlgorithmException if the algorithm is not available
            throw new RuntimeException("Error generating secret key", e);
        }
    }
}
