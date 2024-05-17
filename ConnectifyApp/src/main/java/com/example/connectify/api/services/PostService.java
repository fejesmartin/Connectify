package com.example.connectify.api.services;

import com.example.connectify.api.models.Post;
import com.example.connectify.api.models.PostDTO;
import com.example.connectify.api.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Post createPost(PostDTO postDTO) {
        // Convert DTO to entity
        Post post = new Post();
        User author = userRepository.findById(postDTO.getAuthorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        post.setAuthor(author);
        post.setContent(postDTO.getContent());

        System.out.println("Post created: Id = " + post.getId() +
                ", Author = " + post.getAuthor().getUsername() +
                ", Author ID = " + post.getAuthor().getId() +
                ", Content = " + post.getContent() +
                ", Timestamp = " + post.getTimestamp());
        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }

    @Transactional
    public Post updatePostById(Long postId, PostDTO postDTO){
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        if(postDTO.getContent() != null){
            existingPost.setContent(postDTO.getContent());
        }

        return postRepository.save(existingPost);
    }

    @Transactional
    public void deletePostById(Long postId){
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        postRepository.delete(existingPost);
    }
}
