package com.example.connectify.api.controllers;

import com.example.connectify.api.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping("/add")
    public ResponseEntity<String> addFriend(@RequestParam Long userId, @RequestParam Long friendId) {
        try {
            friendService.addFriend(userId, friendId);
            return new ResponseEntity<>("Friend added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeFriend(@RequestParam Long userId, @RequestParam Long friendId) {
        try {
            friendService.removeFriend(userId, friendId);
            return new ResponseEntity<>("Friend removed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
