package com.example.connectify.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendRequestDTO {
    private Long userId;
    private Long friendId;
}
