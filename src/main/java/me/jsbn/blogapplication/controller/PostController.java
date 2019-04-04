package me.jsbn.blogapplication.controller;

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

    @GetMapping("/api/posts")
    public Page<Post> getPosts(Pageable pageable) {
        Pageable pageToGet = PageRequest.of(0, 5, Sort.Direction.DESC, "createdTime");
        return postRepository.findAll(pageToGet);
    }

    @GetMapping("/api/posts/all")
    public Page<Post> getAllPosts(Pageable pageable) {
        Pageable allPosts = PageRequest.of(0, 10000);
        return postRepository.findAll(allPosts);
    }

    @GetMapping("/api/posts/tag/{tag}")
    public List<Post> getPostsByTag(@PathVariable List<String> tag) {
        return postRepository.findByTags_ContentOrderByCreatedTimeDesc(tag);
    }

    @DeleteMapping("/api/posts/delete/{id}")
    public void deletePostById(@PathVariable Long id) {
        postRepository.deleteById(id);
    }

    @GetMapping("/api/posts/page/{page}")
    public Page<Post> getPostPage(Pageable pageable, @PathVariable int page) {
        Pageable pageToGet = PageRequest.of(page, 5, Sort.Direction.DESC, "createdTime");
        return postRepository.findAll(pageToGet);
    }

    @CrossOrigin
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
                    if (!postRequest.getTags().isEmpty())
                        post.setTags(postRequest.getTags());

                    return postRepository.save(post);
                }).orElseThrow(() -> new ResourceNotFoundException("Post not found with ID " + postId));
    }


    @GetMapping("/api/posts/{postId}")
    public Post getPost(@PathVariable Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with ID " + postId));
    }

}
