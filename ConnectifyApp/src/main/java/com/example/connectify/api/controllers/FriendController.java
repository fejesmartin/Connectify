package com.example.connectify.api.controllers;

import com.example.connectify.api.models.FriendRequestDTO;
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
    public ResponseEntity<String> addFriend(@RequestBody FriendRequestDTO friendshipRequest) {
        try {
            friendService.addFriendRequest(friendshipRequest.getUserId(), friendshipRequest.getFriendId());
            return ResponseEntity.ok("Friend request sent successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriend(@RequestBody FriendRequestDTO friendshipRequest) {
        try {
            friendService.acceptFriendRequest(friendshipRequest.getFriendId(), friendshipRequest.getUserId());
            return ResponseEntity.ok("Friend request accepted successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeFriend(@RequestBody FriendRequestDTO friendshipRequest) {
        try {
            friendService.removeFriend(friendshipRequest.getUserId(), friendshipRequest.getFriendId());
            return ResponseEntity.ok("Friend removed successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
