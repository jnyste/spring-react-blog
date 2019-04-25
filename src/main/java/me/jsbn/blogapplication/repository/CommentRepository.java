package me.jsbn.blogapplication.repository;

import me.jsbn.blogapplication.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findByPost(Long postId);
}
