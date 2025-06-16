package com.example.reddit.comment;

import com.example.reddit.post.Post;
import com.example.reddit.user.RedditUser;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Comment {
    private Long id;
    private RedditUser user;
    private LocalDateTime dateTime;
    private String content;
    private Set<Comment> replies = new HashSet<>();
    private Comment parent;
    private Post post;
    private Set<RedditUser> upVotedBy = new HashSet<>();
    private Set<RedditUser> downVotedBy = new HashSet<>();
    private Set<RedditUser> savedBy = new HashSet<>();

    public Comment() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Comment> getReplies() {
        return replies;
    }

    public void setReplies(Set<Comment> replies) {
        this.replies = replies;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getKarmaScore() {
        return upVotedBy.size() - downVotedBy.size();
    }
}
