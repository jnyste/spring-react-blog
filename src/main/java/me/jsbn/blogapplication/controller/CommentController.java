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

}
