package me.jsbn.blogapplication.controller;

/**
 * REST controller for comments
 */

import me.jsbn.blogapplication.controller.exception.ResourceNotFoundException;
import me.jsbn.blogapplication.model.PostComment;
import me.jsbn.blogapplication.repository.CommentRepository;
import me.jsbn.blogapplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    /**
     * Find comments by post ID.
     * @param postId Post ID to search by.
     * @return comments by post ID.
     */

    @GetMapping("/api/posts/{postId}/comments")
    public List<PostComment> getCommentsByPostId(@PathVariable Long postId) {
        return commentRepository.findByPost(postId);
    }

    /**
     * Add a comment to a post.
     * @param postId Post to add the comment to.
     * @param comment The comment entity.
     * @return The comment.
     * @throws ResourceNotFoundException
     */

    @PostMapping("/api/posts/{postId}/comments")
    public PostComment addComment(@PathVariable Long postId, @Valid @RequestBody PostComment comment) {
        return postRepository.findById(postId).map(
                post -> {
                    comment.setPost(postId);
                    return commentRepository.save(comment);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("No post with that ID exists."));
    }

    /**
     * Delete a comment.
     * @param postId Post to delete comment from.
     * @param commentId Comment ID to delete.
     */

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
