package me.jsbn.blogapplication.controller;

import me.jsbn.blogapplication.controller.exception.ResourceNotFoundException;
import me.jsbn.blogapplication.model.Post;
import me.jsbn.blogapplication.model.PostLike;
import me.jsbn.blogapplication.repository.PostLikeRepository;
import me.jsbn.blogapplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
public class PostLikeController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    PostLikeRepository likeRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/api/posts/like/{postId}")
    public PostLike likePost(@PathVariable Long postId) {

        String ip = "";

        if (request != null) {
            ip = request.getHeader("X-FORWARDED-FOR");
            if (ip == null || "".equals(ip)) {
                ip = request.getRemoteAddr();
            }
        }

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Can't like a post by that ID!"));
        return likeRepository.save(new PostLike(post.getId(), ip));
    }
}
