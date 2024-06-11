package com.example.connectify.controllers;

import com.example.connectify.api.controllers.UserController;
import com.example.connectify.api.models.User;
import com.example.connectify.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController; // Automatically injects the mocked UserService into the UserController instance

    @BeforeEach // Runs before each test method. Here, it initializes the mocks and injects them.
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        User user = new User();
        user.setUsername("testuser");
        when(userService.createUser(any(User.class))).thenReturn(user); // Creates a new User object and sets a username. Configures the mock userService to return this user when createUser is called with any User object.

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(201, response.getStatusCodeValue()); // Checks if the HTTP status code of the response is 201 (Created)
        assertEquals("testuser", response.getBody().getUsername()); // Verifies that the username in the response is "testuser"
        verify(userService, times(1)).createUser(any(User.class)); // Ensures that createUser was called exactly once on userService
    }

    @Test // Creates two User objects with different usernames and adds them to a list. Configures the mock userService to return this list when getAllUsers is called.
    void getAllUsers() {
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");
        List<User> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        List<User> result = userController.getAllUsers();

        assertEquals(2, result.size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userService.getUserById(1L)).thenReturn(user);

        User result = userController.getUserById(1L);

        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void getUserByName() {
        User user = new User();
        user.setUsername("testuser");

        when(userService.getUserByUsername("testuser")).thenReturn(user);

        User result = userController.getUserByName("testuser");

        assertEquals("testuser", result.getUsername());
        verify(userService, times(1)).getUserByUsername("testuser");
    }

    @Test
    void deleteUserById() {
        doNothing().when(userService).deleteUser(1L);

        userController.deleteUserById(1L);

        verify(userService, times(1)).deleteUser(1L);
    }
}
