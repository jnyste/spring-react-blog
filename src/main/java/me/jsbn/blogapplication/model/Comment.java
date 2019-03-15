package me.jsbn.blogapplication.model;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "Comment")
public class Comment extends BlogEntityModel {
    @Id
    @GeneratedValue(generator = "comment_generator")
    @SequenceGenerator(name = "comment_generator", sequenceName = "comment_sequence")
    private Long id;

    @Column(columnDefinition = "text")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Post post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
