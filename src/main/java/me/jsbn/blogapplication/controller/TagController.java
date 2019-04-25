package me.jsbn.blogapplication.controller;

/**
 * REST controller for tags.
 */

import me.jsbn.blogapplication.model.Post;
import me.jsbn.blogapplication.model.Tag;
import me.jsbn.blogapplication.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {

    @Autowired
    TagRepository tagRepository;

    /**
     * Find tags by their content.
     * @param content The content of the tag.
     * @return List of tags.
     */

    @GetMapping("/api/tags/{content}")
    public List<Tag> getTagsByContent(@PathVariable String content) {
        return tagRepository.findByContent(content);
    }
}
