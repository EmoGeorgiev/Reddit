package com.example.reddit.content;

import com.example.reddit.user.RedditUser;
import com.example.reddit.vote.Vote;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "contents")
public abstract class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private RedditUser user;
    @Enumerated(EnumType.STRING)
    private ContentType contentType;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    @Column(name = "text", nullable = false)
    @Size(max = 5000)
    private String text;
    @Column(name = "score", nullable = false)
    private int score = 0;
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    private Set<Vote> votes = new HashSet<>();
    @ManyToMany(mappedBy = "saved")
    private Set<RedditUser> savedBy = new HashSet<>();

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

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
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
        Content content = (Content) o;
        return Objects.equals(id, content.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
