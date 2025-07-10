package com.reddit.post;

import com.reddit.comment.Comment;
import com.reddit.content.Content;
import com.reddit.subreddit.Subreddit;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post extends Content {
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "subreddit_id", nullable = false)
    private Subreddit subreddit;
    @Column(name = "title", nullable = false)
    private String title;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    public Post() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
