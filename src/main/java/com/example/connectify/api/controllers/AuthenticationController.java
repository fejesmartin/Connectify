package com.example.connectify.api.controllers;

import com.example.connectify.api.models.*;
import com.example.connectify.api.services.UserAuthService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/login")
    public ResponseEntity<?> LoginUser( @RequestBody UserLoginDTO body, @NotNull BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid login request");
        }

        try {
            System.out.println("EMAIL: " + body.getEmail() + "PASSWORD: " + body.getPassword());
            UserLoginResponseDTO userLoginResponseDTO =  userAuthService.loginUser(body.getEmail(), body.getPassword());


            return ResponseEntity.status(HttpStatus.OK).body(userLoginResponseDTO);


        } catch (Exception e)
        {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login user");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> RegisterUser(@RequestBody UserRegistrationDTO body, @NotNull BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid register request");
        }
        try {
            userAuthService.registerUser(body.getUsername(), body.getEmail(), body.getPassword());

            return ResponseEntity.status(HttpStatus.OK).body(new UserRegistrationResponseDTO(body.getUsername(), body.getEmail()));

        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during registering user");
        }

    }
}



