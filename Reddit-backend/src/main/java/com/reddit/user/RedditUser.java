package com.reddit.user;

import com.reddit.comment.Comment;
import com.reddit.content.Content;
import com.reddit.post.Post;
import com.reddit.subreddit.Subreddit;
import com.reddit.vote.Vote;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class RedditUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Post> posts = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Comment> comments = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "users_subreddits",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subreddit_id")
    )
    private Set<Subreddit> subscribedTo = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "moderators_subreddits",
            joinColumns = @JoinColumn(name = "moderator_id"),
            inverseJoinColumns = @JoinColumn(name = "subreddit_id")
    )
    private Set<Subreddit> moderated = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vote> voted = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "users_saved",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "saved_id")
    )
    private Set<Content> saved = new HashSet<>();

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

    public Set<Subreddit> getModerated() {
        return moderated;
    }

    public void setModerated(Set<Subreddit> moderated) {
        this.moderated = moderated;
    }

    public Set<Vote> getVoted() {
        return voted;
    }

    public void setVoted(Set<Vote> voted) {
        this.voted = voted;
    }

    public Set<Content> getSaved() {
        return saved;
    }

    public void setSaved(Set<Content> saved) {
        this.saved = saved;
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
