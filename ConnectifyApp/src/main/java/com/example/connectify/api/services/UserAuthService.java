package com.example.connectify.api.services;

import com.example.connectify.api.models.*;
import com.example.connectify.api.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserAuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("userAuthManager")
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenService;


    public UserRegistrationResponseDTO registerUser(String username, String email, String password) {
        try {
            String encodedPassword = passwordEncoder.encode(password);

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(encodedPassword);

            userRepository.save(newUser);

            return new UserRegistrationResponseDTO(username, email);

        } catch (Exception e) {
            return new UserRegistrationResponseDTO("", "");
        }
    }

    public UserLoginResponseDTO loginUser(String email, String password)
    {
        try{

            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            String token = tokenService.generateJwt(auth, userRepository.getUserByEmail(email).get().getUserId());

            User user = userRepository.getUserByEmail(email).get();
            return new UserLoginResponseDTO( user.getEmail(), token);

        }catch (AuthenticationException e) {
            return new UserLoginResponseDTO( "", "");
        }
    }
}