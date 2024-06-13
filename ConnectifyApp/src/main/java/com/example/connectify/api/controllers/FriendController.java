package com.example.connectify.api.controllers;

import com.example.connectify.api.models.User;
import com.example.connectify.api.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping("/add")
    public ResponseEntity<User> addFriend(@RequestParam Long userId, @RequestParam Long friendId) {
        Optional<User> friendOptional = friendService.addFriend(userId, friendId);
        return friendOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/remove")
    public ResponseEntity<User> removeFriend(@RequestParam Long userId, @RequestParam Long friendId) {
        Optional<User> friendOptional = friendService.removeFriend(userId, friendId);
        return friendOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
