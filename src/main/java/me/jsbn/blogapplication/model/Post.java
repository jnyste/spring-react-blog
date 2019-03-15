package me.jsbn.blogapplication.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
}
