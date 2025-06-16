package com.example.reddit.user;

import com.example.reddit.comment.Comment;
import com.example.reddit.post.Post;
import com.example.reddit.subreddit.Subreddit;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class RedditUser {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<Post> posts = new HashSet<>();
    private Set<Comment> comments = new HashSet<>();
    private Set<Subreddit> subscribedTo = new HashSet<>();
    private Set<Post> upVotedPosts = new HashSet<>();
    private Set<Post> downVotedPosts = new HashSet<>();
    private Set<Comment> upVotedComments = new HashSet<>();
    private Set<Comment> downVotedComments = new HashSet<>();
    private Set<Post> savedPosts = new HashSet<>();
    private Set<Comment> savedComments = new HashSet<>();

    public RedditUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Subreddit> getSubscribedTo() {
        return subscribedTo;
    }

    public void setSubscribedTo(Set<Subreddit> subscribedTo) {
        this.subscribedTo = subscribedTo;
    }

    public Set<Post> getUpVotedPosts() {
        return upVotedPosts;
    }

    public void setUpVotedPosts(Set<Post> upVotedPosts) {
        this.upVotedPosts = upVotedPosts;
    }

    public Set<Post> getDownVotedPosts() {
        return downVotedPosts;
    }

    public void setDownVotedPosts(Set<Post> downVotedPosts) {
        this.downVotedPosts = downVotedPosts;
    }

    public Set<Comment> getUpVotedComments() {
        return upVotedComments;
    }

    public void setUpVotedComments(Set<Comment> upVotedComments) {
        this.upVotedComments = upVotedComments;
    }

    public Set<Comment> getDownVotedComments() {
        return downVotedComments;
    }

    public void setDownVotedComments(Set<Comment> downVotedComments) {
        this.downVotedComments = downVotedComments;
    }

    public Set<Post> getSavedPosts() {
        return savedPosts;
    }

    public void setSavedPosts(Set<Post> savedPosts) {
        this.savedPosts = savedPosts;
    }

    public Set<Comment> getSavedComments() {
        return savedComments;
    }

    public void setSavedComments(Set<Comment> savedComments) {
        this.savedComments = savedComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RedditUser that = (RedditUser) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
