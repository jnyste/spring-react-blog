package me.jsbn.blogapplication.model;

import javax.persistence.*;

@Entity
@Table(name = "post_likes")
public class PostLike {
    @Id
    @GeneratedValue(generator = "postlike_generator")
    @SequenceGenerator(name = "postlike_generator", sequenceName = "postlike_sequence")
    private Long id;
    private Long post;

    public PostLike() {

    }

    public PostLike(Long postId, String ip) {
        this.post = postId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPost() {
        return post;
    }

    public void setPost(Long post) {
        this.post = post;
    }

}
