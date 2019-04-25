package me.jsbn.blogapplication.model;

import javax.persistence.*;

@Entity
@Table(name = "post_comments")
public class PostComment {
    @Id
    @GeneratedValue(generator = "postcomment_generator")
    @SequenceGenerator(name = "postcomment_generator", sequenceName = "postcomment_sequence")
    private Long id;
    private Long post;
    private String userGoogleId;
    private String content;
    private String givenName;
    private String familyName;

    public PostComment() {

    }

    public PostComment(Long post, String userGoogleId, String content, String givenName, String familyName) {
        this.post = post;
        this.userGoogleId = userGoogleId;
        this.content = content;
        this.givenName = givenName;
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getUserGoogleId() {
        return userGoogleId;
    }

    public void setUserGoogleId(String userGoogleId) {
        this.userGoogleId = userGoogleId;
    }
}
