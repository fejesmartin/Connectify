package com.example.connectify.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User author;

    private String content;

    private Instant timestamp = Instant.now();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

}
