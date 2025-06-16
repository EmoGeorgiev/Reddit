package com.example.reddit.post;

import com.example.reddit.comment.Comment;
import com.example.reddit.subreddit.Subreddit;
import com.example.reddit.user.RedditUser;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Post {
    private Long id;
    private RedditUser user;
    private LocalDateTime dateTime;
    private Subreddit subreddit;
    private String title;
    private String description;
    private Set<Comment> comments = new HashSet<>();
    private Set<RedditUser> upVotedBy = new HashSet<>();
    private Set<RedditUser> downVotedBy = new HashSet<>();
    private Set<RedditUser> savedBy = new HashSet<>();

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RedditUser getUser() {
        return user;
    }

    public void setUser(RedditUser user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Subreddit getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(Subreddit subreddit) {
        this.subreddit = subreddit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<RedditUser> getUpVotedBy() {
        return upVotedBy;
    }

    public void setUpVotedBy(Set<RedditUser> upVotedBy) {
        this.upVotedBy = upVotedBy;
    }

    public Set<RedditUser> getDownVotedBy() {
        return downVotedBy;
    }

    public void setDownVotedBy(Set<RedditUser> downVotedBy) {
        this.downVotedBy = downVotedBy;
    }

    public Set<RedditUser> getSavedBy() {
        return savedBy;
    }

    public void setSavedBy(Set<RedditUser> savedBy) {
        this.savedBy = savedBy;
    }

    public int getKarmaScore() {
        return upVotedBy.size() - downVotedBy.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
