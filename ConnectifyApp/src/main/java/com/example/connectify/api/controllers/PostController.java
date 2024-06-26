package com.example.connectify.api.controllers;

import com.example.connectify.api.models.Post;
import com.example.connectify.api.models.PostDTO;
import com.example.connectify.api.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO){
        Post post = postService.createPost(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping("/getById/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId){
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/updateById/{postId}")
    public ResponseEntity<Post> updatePostById(@PathVariable Long postId, @RequestBody PostDTO postDTO){
        Post post = postService.updatePostById(postId, postDTO);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/deleteById/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long postId) {
        postService.deletePostById(postId);
        return ResponseEntity.noContent().build();
    }

}
