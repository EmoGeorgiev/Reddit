package com.example.reddit.post;

import com.example.reddit.comment.CommentService;
import com.example.reddit.exception.PostNotFoundException;
import com.example.reddit.subreddit.Subreddit;
import com.example.reddit.subreddit.SubredditService;
import com.example.reddit.user.RedditUser;
import com.example.reddit.user.UserService;
import com.example.reddit.util.ErrorMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final SubredditService subredditService;
    private final CommentService commentService;

    public PostService(PostRepository postRepository, UserService userService, SubredditService subredditService, CommentService commentService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.subredditService = subredditService;
        this.commentService = commentService;
    }

    @Transactional(readOnly = true)
    public Post getPostEntity(Long id) {
        return postRepository
                .findById(id)
                .orElseThrow(() -> new PostNotFoundException(ErrorMessages.POST_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public PostDto getPost(Long id) {
        return PostMapper.postToPostDto(getPostEntity(id));
    }

    public PostDto addPost(PostDto postDto) {
        RedditUser user = userService.getUserEntity(postDto.userId());
        Subreddit subreddit = subredditService.getSubredditEntity(postDto.subredditId());

        Post post = PostMapper.postDtoToPost(postDto);
        post.setUser(user);
        post.setSubreddit(subreddit);

        Post savedPost = postRepository.save(post);

        return PostMapper.postToPostDto(savedPost);
    }

    public PostDto updatePost(PostDto postDto) {
        throw new UnsupportedOperationException();
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException(ErrorMessages.POST_NOT_FOUND);
        }
        postRepository.deleteById(id);
    }
}
