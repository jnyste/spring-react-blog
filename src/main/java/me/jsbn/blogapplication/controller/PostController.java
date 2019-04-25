package me.jsbn.blogapplication.controller;

/**
 * REST controller for posts.
 */

import me.jsbn.blogapplication.controller.exception.ResourceNotFoundException;
import me.jsbn.blogapplication.model.Post;
import me.jsbn.blogapplication.model.Tag;
import me.jsbn.blogapplication.repository.PostRepository;
import me.jsbn.blogapplication.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    /**
     * Get a page of posts.
     * @param pageable Pageable.
     * @return Page of posts.
     */

    @GetMapping("/api/posts")
    public Page<Post> getPosts(Pageable pageable) {
        Pageable pageToGet = PageRequest.of(0, 5, Sort.Direction.DESC, "createdTime");
        return postRepository.findAll(pageToGet);
    }

    /**
     * Get every post.
     * @param pageable Pageable.
     * @return Every post.
     */

    @GetMapping("/api/posts/all")
    public Page<Post> getAllPosts(Pageable pageable) {
        Pageable allPosts = PageRequest.of(0, 10000);
        return postRepository.findAll(allPosts);
    }

    /**
     * Get posts with a specific tag, ordered by time created.
     * @param tag The tag to search by.
     * @return A list of posts.
     */

    @GetMapping("/api/posts/tag/{tag}")
    public List<Post> getPostsByTag(@PathVariable List<String> tag) {
        return postRepository.findByTags_ContentOrderByCreatedTimeDesc(tag);
    }

    /**
     * Delete a post.
     * @param id The post ID to delete.
     */

    @DeleteMapping("/api/posts/delete/{id}")
    public void deletePostById(@PathVariable Long id) {
        postRepository.deleteById(id);
    }

    /**
     * Get a specific page of posts.
     * @param pageable Pageable.
     * @param page Page number.
     * @return A page of posts.
     */

    @GetMapping("/api/posts/page/{page}")
    public Page<Post> getPostPage(Pageable pageable, @PathVariable int page) {
        Pageable pageToGet = PageRequest.of(page, 5, Sort.Direction.DESC, "createdTime");
        return postRepository.findAll(pageToGet);
    }

    /**
     * Create a new post.
     * @param post The post entity to create.
     * @return New post.
     */

    @CrossOrigin
    @PostMapping("/api/posts")
    public Post createPost(@Valid @RequestBody Post post) {
        return postRepository.save(post);
    }

    /**
     * Edit a post.
     * @param postId The post ID to edit.
     * @param postRequest The new post entity.
     * @return The new post.
     */

    @PutMapping("/api/posts/{postId}")
    public Post updatePost(@PathVariable Long postId, @Valid @RequestBody Post postRequest) {
        return postRepository.findById(postId).
                map(post -> {
                    post.setTitle(postRequest.getTitle());
                    post.setContent(postRequest.getContent());
                    if (!postRequest.getTags().isEmpty())
                        post.setTags(postRequest.getTags());

                    return postRepository.save(post);
                }).orElseThrow(() -> new ResourceNotFoundException("Post not found with ID " + postId));
    }

    /**
     * Get a specific post.
     * @param postId The post ID to get.
     * @return A post.
     */

    @GetMapping("/api/posts/{postId}")
    public Post getPost(@PathVariable Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with ID " + postId));
    }

}
