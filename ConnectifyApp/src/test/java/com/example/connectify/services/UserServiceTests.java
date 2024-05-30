package com.example.connectify.services;

import com.example.connectify.api.models.User;
import com.example.connectify.api.services.UserRepository;
import com.example.connectify.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_Success() {
        User user = new User();
        user.setUsername("testuser");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createUser_DataIntegrityViolationException() {
        User user = new User();
        when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
        assertEquals("User with the same username or email already exists.", exception.getMessage());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createUser_Exception() {
        User user = new User();
        when(userRepository.save(any(User.class))).thenThrow(RuntimeException.class);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.createUser(user));
        assertEquals("An error occurred while creating the user.", exception.getMessage());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getAllUsers_Success() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAllUsers_Exception() {
        when(userRepository.findAll()).thenThrow(RuntimeException.class);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getAllUsers());
        assertEquals("An error occurred while retrieving users.", exception.getMessage());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_Success() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_Exception() {
        when(userRepository.findById(1L)).thenThrow(RuntimeException.class);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
        assertEquals("An error occurred while retrieving the user with ID 1.", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void updateUser_Success() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("olduser");
        existingUser.setEmail("olduser@example.com");

        User updatedUser = new User();
        updatedUser.setUsername("newuser");
        updatedUser.setEmail("newuser@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User result = userService.updateUser(1L, updatedUser);

        assertEquals("newuser", result.getUsername());
        assertEquals("newuser@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void updateUser_NotFound() {
        User updatedUser = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.updateUser(1L, updatedUser));
        assertEquals("User with ID 1 not found.", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void updateUser_DataIntegrityViolationException() {
        User existingUser = new User();
        existingUser.setId(1L);

        User updatedUser = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.updateUser(1L, updatedUser));
        assertEquals("User with the same username or email already exists.", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void updateUser_Exception() {
        User existingUser = new User();
        existingUser.setId(1L);

        User updatedUser = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenThrow(RuntimeException.class);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.updateUser(1L, updatedUser));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void deleteUser_Success() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_Exception() {
        doThrow(RuntimeException.class).when(userRepository).deleteById(1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));
        assertEquals("An error occurred while deleting the user with ID 1.", exception.getMessage());
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void getUserByUsername_Success() {
        User user = new User();
        user.setUsername("testuser");
        when(userRepository.getUserByUsername("testuser")).thenReturn(Optional.of(user));

        User result = userService.getUserByUsername("testuser");

        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).getUserByUsername("testuser");
    }

    @Test
    void getUserByUsername_NotFound() {
        when(userRepository.getUserByUsername("testuser")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserByUsername("testuser"));
        assertEquals("User with username testuser not found.", exception.getMessage());
        verify(userRepository, times(1)).getUserByUsername("testuser");
    }

    @Test
    void getUserByUsername_Exception() {
        when(userRepository.getUserByUsername("testuser")).thenThrow(RuntimeException.class);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUserByUsername("testuser"));
        verify(userRepository, times(1)).getUserByUsername("testuser");
    }

    @Test
    void getUserByEmail_Success() {
        User user = new User();
        user.setEmail("testuser@example.com");
        when(userRepository.getUserByEmail("testuser@example.com")).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail("testuser@example.com");

        assertEquals("testuser@example.com", result.getEmail());
        verify(userRepository, times(1)).getUserByEmail("testuser@example.com");
    }

    @Test
    void getUserByEmail_NotFound() {
        when(userRepository.getUserByEmail("testuser@example.com")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserByEmail("testuser@example.com"));
        verify(userRepository, times(1)).getUserByEmail("testuser@example.com");
    }

    @Test
    void getUserByEmail_Exception() {
        when(userRepository.getUserByEmail("testuser@example.com")).thenThrow(RuntimeException.class);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUserByEmail("testuser@example.com"));
        verify(userRepository, times(1)).getUserByEmail("testuser@example.com");
    }


}