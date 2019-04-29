package me.jsbn.blogapplication.controller;

import me.jsbn.blogapplication.controller.exception.ResourceNotFoundException;
import me.jsbn.blogapplication.model.Post;
import me.jsbn.blogapplication.model.PostLike;
import me.jsbn.blogapplication.repository.PostLikeRepository;
import me.jsbn.blogapplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * REST controller for post likes.
 */

@RestController
public class PostLikeController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    PostLikeRepository likeRepository;

    @Autowired
    PostRepository postRepository;

    /**
     * Like a post.
     * @return A new PostLike.
     */
    @PostMapping("/api/posts/like")
    public PostLike likePost(@RequestBody PostLike like) throws IllegalAccessException {

        Post post = postRepository.findById(like.getPost()).orElseThrow(() -> new ResourceNotFoundException("Can't like a post by that ID!"));
        if (likeRepository.findByUserGoogleIdAndPost(like.getUserGoogleId(), like.getPost()).isPresent()) {
            throw new IllegalAccessException("User tried to like the same post twice.");
        }
        return likeRepository.save(like);
    }

    @PostMapping("/api/posts/unlike")
    public void unLikePost(@RequestBody PostLike like) throws IllegalAccessException {
        Optional<PostLike> optional = likeRepository.findByUserGoogleIdAndPost(like.getUserGoogleId(), like.getPost());
        if (optional.isPresent()) {
            likeRepository.delete(optional.get());
        }
    }

    @GetMapping("/api/posts/{postId}/likes/{userGoogleId}")
    public Optional<PostLike> checkForLikeByUserOnPost(@PathVariable Long postId, @PathVariable String userGoogleId) {
        return likeRepository.findByUserGoogleIdAndPost(userGoogleId, postId);
    }

}
