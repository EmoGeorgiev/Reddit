package com.example.reddit.post;

import com.example.reddit.comment.Comment;
import com.example.reddit.subreddit.Subreddit;
import com.example.reddit.user.RedditUser;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private RedditUser user;
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "subreddit_id", nullable = false)
    private Subreddit subreddit;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
    @ManyToMany(mappedBy = "upVotedPosts")
    private Set<RedditUser> upVotedBy = new HashSet<>();
    @ManyToMany(mappedBy = "downVotedPosts")
    private Set<RedditUser> downVotedBy = new HashSet<>();
    @ManyToMany(mappedBy = "savedPosts")
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

    public int getKarmaScore() {
        return upVotedBy.size() - downVotedBy.size();
    }
}
