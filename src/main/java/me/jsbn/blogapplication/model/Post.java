package me.jsbn.blogapplication.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Post")
public class Post extends BlogEntityModel {
    @Id
    @GeneratedValue(generator = "post_generator")
    @SequenceGenerator(name = "post_generator", sequenceName = "post_sequence")
    private Long id;

    @NotBlank
    @Size(min = 5, max = 200)
    private String title;

    @Column(columnDefinition = "text")
    private String content;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })

    @JoinTable(name = "post_tags", joinColumns = { @JoinColumn(name = "post_id") }, inverseJoinColumns = { @JoinColumn(name = "tag_id")})
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })

    @JoinColumn(name = "post")
    private Set<PostLike> likes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<PostLike> getLikes() {
        return likes;
    }

    public void setLikes(Set<PostLike> likes) {
        this.likes = likes;
    }
}
