package me.jsbn.blogapplication.controller;

import me.jsbn.blogapplication.controller.exception.ResourceNotFoundException;
import me.jsbn.blogapplication.model.Comment;
import me.jsbn.blogapplication.repository.CommentRepository;
import me.jsbn.blogapplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/api/posts/{postId}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @PostMapping("/api/posts/{postId}/comments")
    public Comment addComment(@PathVariable Long postId, @Valid @RequestBody Comment comment) {
        return postRepository.findById(postId).map(
                post -> {
                    comment.setPost(post);
                    return commentRepository.save(comment);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("No post with that ID exists."));
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public Comment updateComment(@PathVariable Long postId, @PathVariable Long commentId, @Valid @RequestBody Comment commentRequest) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("No post with that ID.");
        }

        return commentRepository.findById(commentId).map(
                comment -> {
                    comment.setContent(commentRequest.getContent());
                    return commentRepository.save(comment);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("No comment with that ID."));
    }

    @DeleteMapping("/posts/{postId}/answers/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long postId,
                                          @PathVariable Long commentId) {
        if(!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id " + postId);
        }

        return commentRepository.findById(commentId)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId));

    }
}
