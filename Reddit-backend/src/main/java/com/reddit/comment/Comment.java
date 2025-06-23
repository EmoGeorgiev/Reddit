package com.reddit.comment;

import com.reddit.content.Content;
import com.reddit.post.Post;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment extends Content {
    public static final String DELETED_TEXT = "[deleted]";
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Comment> replies = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Comment() {
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
