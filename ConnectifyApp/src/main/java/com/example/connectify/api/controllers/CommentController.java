package com.example.connectify.api.controllers;

import com.example.connectify.api.models.Comment;
import com.example.connectify.api.models.CommentDTO;
import com.example.connectify.api.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO commentDTO){
        Comment comment = commentService.createComment(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @GetMapping("/getById/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId){
        Comment comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/updateById/{commentId}")
    public ResponseEntity<Comment> updateCommentById(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO){
        Comment comment = commentService.updateCommentById(commentId, commentDTO);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/deleteById/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
        return ResponseEntity.noContent().build();
    }
}
