package com.example.reddit.subreddit;

import com.example.reddit.post.Post;
import com.example.reddit.user.RedditUser;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Subreddit {
    private Long id;
    private String title;
    private Set<Post> posts = new HashSet<>();
    private Set<RedditUser> users = new HashSet<>();
    private Set<RedditUser> moderators = new HashSet<>();
    public Subreddit() {
    }

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

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<RedditUser> getUsers() {
        return users;
    }

    public void setUsers(Set<RedditUser> users) {
        this.users = users;
    }

    public Set<RedditUser> getModerators() {
        return moderators;
    }

    public void setModerators(Set<RedditUser> moderators) {
        this.moderators = moderators;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subreddit subreddit = (Subreddit) o;
        return Objects.equals(id, subreddit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
