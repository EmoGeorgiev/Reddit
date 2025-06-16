package com.example.reddit.subreddit;

import com.example.reddit.post.Post;
import com.example.reddit.user.RedditUser;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "subreddits")
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @OneToMany(mappedBy = "subreddit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();
    @ManyToMany(mappedBy = "subscribedTo")
    private Set<RedditUser> users = new HashSet<>();
    @ManyToMany(mappedBy = "moderated")
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
