package com.example.connectify.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String userName;
    private String email;
    private String accessToken;
    private String tokenType;

}
