package com.example.connectify.api.services;

import com.example.connectify.api.models.Post;
import com.example.connectify.api.models.PostDTO;
import com.example.connectify.api.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(PostDTO postDTO) {
        // Convert DTO to entity
        Post post = new Post();
        User author = userRepository.findById(postDTO.getAuthorId()).orElseThrow(() -> new RuntimeException("User not found"));
        post.setAuthor(author);
        post.setContent(postDTO.getContent());

        System.out.println("Post created: Id = " + post.getId() +
                ", Author = " + post.getAuthor().getUsername() +
                ", Content = " + post.getContent() +
                ", Timestamp = " + post.getTimestamp());
        return postRepository.save(post);
    }

    public Post getPostById(Long postId) {

        return postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public Post updatePostById(Long postId, PostDTO postDTO){
        Post existingPost = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("Post not found"));

        if(postDTO.getContent() != null){
            existingPost.setContent(postDTO.getContent());
        }

        return postRepository.save(existingPost);
    }

    public void deletePostById(Long postId){
        Post existingPost = postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        postRepository.delete(existingPost);
    }
}
