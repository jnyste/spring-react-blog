package me.jsbn.blogapplication.repository;

import me.jsbn.blogapplication.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserGoogleIdAndPost(String googleId, Long postId);
}
