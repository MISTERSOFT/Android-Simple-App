package me.sofianehamadi.tp1simpleapp.models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class UserDetailsGithub implements Serializable {
    private String name;
    private String avatarUrl;
    private String biography;
    private Integer followers;
    private Integer following;
    private ArrayList<UserRepositoryDetails> repositories;
    private String createAt;

    public UserDetailsGithub() {
        this.repositories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        if (biography == "null") {
            this.biography = "No biography";
        }
        else {
            this.biography = biography;
        }
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public ArrayList<UserRepositoryDetails> getRepositories() {
        return repositories;
    }

    public void setRepositories(ArrayList<UserRepositoryDetails> repositories) {
        this.repositories = repositories;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}