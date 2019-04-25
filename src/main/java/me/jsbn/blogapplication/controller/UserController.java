package me.jsbn.blogapplication.controller;

/**
 * REST controller for users.
 */

import me.jsbn.blogapplication.model.User;
import me.jsbn.blogapplication.repository.CommentRepository;
import me.jsbn.blogapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Find a user by their Google ID.
     * @param googleId The Google ID to search by.
     * @return An optional maybe containing a user.
     */

    @GetMapping("/api/users/{googleId}")
    public Optional<User> findUserByGoogleId(@PathVariable String googleId) {
        return userRepository.findUserByGoogleId(googleId);
    }

    /**
     * Create a new user.
     * @param user A user entity.
     * @return A new user.
     */

    @PostMapping("/api/users/{googleId}")
    public int saveUser(@RequestBody User user) {
        userRepository.save(user);
        return user.getId();
    }
}
