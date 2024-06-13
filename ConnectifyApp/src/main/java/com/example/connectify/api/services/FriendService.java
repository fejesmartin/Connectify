package com.example.connectify.api.services;

import com.example.connectify.api.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FriendService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addFriendRequest(Long userId, Long friendId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> friendOpt = userRepository.findById(friendId);

        if (userOpt.isPresent() && friendOpt.isPresent()) {
            User user = userOpt.get();
            User friend = friendOpt.get();

            if (user.getFriends().contains(friend)) {
                throw new IllegalArgumentException("Users are already friends.");
            }

            if (friend.getFriendRequests().contains(user)) {
                throw new IllegalArgumentException("Friend request already sent.");
            }

            friend.getFriendRequests().add(user);
            userRepository.save(friend);
        } else {
            throw new IllegalArgumentException("User or friend not found.");
        }
    }

    @Transactional
    public void acceptFriendRequest(Long userId, Long friendId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> friendOpt = userRepository.findById(friendId);

        if (userOpt.isPresent() && friendOpt.isPresent()) {
            User user = userOpt.get();
            User friend = friendOpt.get();

            if (!friend.getFriendRequests().contains(user)) {
                throw new IllegalStateException("No friend request found.");
            }

            friend.getFriendRequests().remove(user);
            friend.getFriends().add(user);
            user.getFriends().add(friend);

            userRepository.save(friend);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User or friend not found.");
        }
    }

    @Transactional
    public void removeFriend(Long userId, Long friendId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> friendOpt = userRepository.findById(friendId);

        if (userOpt.isPresent() && friendOpt.isPresent()) {
            User user = userOpt.get();
            User friend = friendOpt.get();

            if (!user.getFriends().contains(friend)) {
                throw new IllegalStateException("Users are not friends.");
            }

            user.getFriends().remove(friend);
            friend.getFriends().remove(user);

            userRepository.save(user);
            userRepository.save(friend);
        } else {
            throw new IllegalArgumentException("User or friend not found.");
        }
    }
}
