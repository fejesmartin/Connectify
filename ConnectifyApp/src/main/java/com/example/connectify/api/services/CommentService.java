package com.example.connectify.api.services;

import com.example.connectify.api.models.Comment;
import com.example.connectify.api.models.CommentDTO;
import com.example.connectify.api.models.Post;
import com.example.connectify.api.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public Comment createComment(CommentDTO commentDTO) {
        // Convert DTO to entity
        Comment comment = new Comment();
        User author = userRepository.findById(commentDTO.getAuthorId()).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(commentDTO.getPostId()).orElseThrow(() -> new RuntimeException("Post not found"));

        comment.setAuthor(author);
        comment.setPost(post);
        comment.setContent(commentDTO.getContent());
        System.out.println("Comment created: Id = " + comment.getId() +
                ", Author = " + comment.getAuthor().getUsername() +
                ", Content = " + comment.getContent() +
                ", Timestamp = " + comment.getTimestamp());
        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public Comment updateCommentById(Long commentId, CommentDTO commentDTO){
        Comment existingComment = commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("Comment not found"));
        if(commentDTO.getContent() != null){
            existingComment.setContent(commentDTO.getContent());
        }
        System.out.println("Comment updated");
        return commentRepository.save(existingComment);

    }

    public void deleteCommentById(Long commentId){
        Comment existingComment = commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("Comment not found"));

        commentRepository.delete(existingComment);
        System.out.println("Comment deleted");
    }
}
