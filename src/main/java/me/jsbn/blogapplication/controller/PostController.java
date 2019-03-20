package me.jsbn.blogapplication.controller;

import me.jsbn.blogapplication.controller.exception.ResourceNotFoundException;
import me.jsbn.blogapplication.model.Post;
import me.jsbn.blogapplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/api/posts")
    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @GetMapping("/api/posts/page/{page}")
    public Page<Post> getPostPage(Pageable pageable, @PathVariable int page) {
        Pageable pageToGet = PageRequest.of(page, 5);
        return postRepository.findAll(pageToGet);
    }

    @PostMapping("/api/posts")
    public Post createPost(@Valid @RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/api/posts/{postId}")
    public Post updatePost(@PathVariable Long postId, @Valid @RequestBody Post postRequest) {
        return postRepository.findById(postId).
                map(post -> {
                    post.setTitle(postRequest.getTitle());
                    post.setContent(postRequest.getContent());
                    return postRepository.save(post);
                }).orElseThrow(() -> new ResourceNotFoundException("Post not found with ID " + postId));
    }

    @GetMapping("/api/posts/{postId}")
    public Post getPost(@PathVariable Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with ID " + postId));
    }

}
