package me.jsbn.blogapplication.model;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(generator="user_generator")
    @SequenceGenerator(name="user_generator", sequenceName = "user_sequence")
    int id;

    @Column(nullable = false)
    String googleId;

    @Column(nullable = false)
    String givenName;

    @Column(nullable = false)
    String familyName;

    @Column(nullable = false)
    String profileImageUrl;

    public User() {

    }

    public User(String googleId) {
        this.setGoogleId(googleId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public User(String googleId, String givenName, String familyName, String profileImageUrl) {
        this.googleId = googleId;
        this.givenName = givenName;
        this.familyName = familyName;
        this.profileImageUrl = profileImageUrl;
    }
}

