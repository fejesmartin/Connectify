package com.example.connectify.api.services;

import com.example.connectify.api.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private UserRepository userRepository;

    public void addFriend(Long userId, Long friendId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> friendOpt = userRepository.findById(friendId);

        if (userOpt.isPresent() && friendOpt.isPresent()) {
            User user = userOpt.get();
            User friend = friendOpt.get();

            user.getFriends().add(friend);
            friend.getFriends().add(user);

            userRepository.save(user);
            userRepository.save(friend);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void removeFriend(Long userId, Long friendId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> friendOpt = userRepository.findById(friendId);

        if (userOpt.isPresent() && friendOpt.isPresent()) {
            User user = userOpt.get();
            User friend = friendOpt.get();

            user.getFriends().remove(friend);
            friend.getFriends().remove(user);

            userRepository.save(user);
            userRepository.save(friend);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
