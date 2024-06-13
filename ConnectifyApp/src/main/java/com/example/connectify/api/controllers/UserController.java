package com.example.connectify.api.controllers;
import com.example.connectify.api.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.connectify.api.services.UserService;

import java.util.List;
import java.util.Set;

/** ---- CONTROLLER FOR ADMIN USAGE ---- **/
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getById/{id}")
    public User getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }

    @GetMapping("/getByName/{name}")
    public User getUserByName(@PathVariable String name){return userService.getUserByUsername(name);}

    @DeleteMapping("/deleteById/{id}")
    public void deleteUserById(@PathVariable long id){
         userService.deleteUser(id);
    }

    @GetMapping("/getFriendsById/{id}")
    public Set<User> getFriendsById(@PathVariable long id){
        return userService.getUserById(id).getFriends();
    }

    @GetMapping("/getFriendRequests/{id}")
    public Set<User> getFriendRequestsById(@PathVariable long id){
        return userService.getUserById(id).getFriendRequests();
    }

}

