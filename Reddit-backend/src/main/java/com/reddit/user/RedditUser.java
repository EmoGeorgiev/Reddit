package com.reddit.user;

import com.reddit.comment.Comment;
import com.reddit.content.Content;
import com.reddit.post.Post;
import com.reddit.savedcontent.SavedContent;
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
    private Set<Content> contents = new HashSet<>();
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SavedContent> savedContent = new HashSet<>();

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

    public Set<Content> getContents() {
        return contents;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
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

    public Set<SavedContent> getSavedContent() {
        return savedContent;
    }

    public void setSavedContent(Set<SavedContent> savedContent) {
        this.savedContent = savedContent;
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
