package com.reddit.content;

import com.reddit.savedcontent.SavedContent;
import com.reddit.user.RedditUser;
import com.reddit.vote.Vote;
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
    public static final int INITIAL_SCORE = 0;
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
    private int score = INITIAL_SCORE;
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vote> votes = new HashSet<>();
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SavedContent> savedContent = new HashSet<>();

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
        Content content = (Content) o;
        return Objects.equals(id, content.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
