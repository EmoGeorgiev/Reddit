package com.reddit.post;

import com.reddit.content.ContentType;
import com.reddit.exception.content.ContentUpdateNotAllowedException;
import com.reddit.exception.post.PostNotFoundException;
import com.reddit.exception.subreddit.MissingModeratorPrivilegesException;
import com.reddit.post.dto.PostDto;
import com.reddit.subreddit.Subreddit;
import com.reddit.subreddit.SubredditService;
import com.reddit.user.RedditUser;
import com.reddit.user.UserService;
import com.reddit.util.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final SubredditService subredditService;

    public PostService(PostRepository postRepository, UserService userService, SubredditService subredditService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.subredditService = subredditService;
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

    @Transactional(readOnly = true)
    public Page<PostDto> getPosts(Pageable pageable) {
        return postRepository
                .findAll(pageable)
                .map(PostMapper::postToPostDto);
    }

    @Transactional(readOnly = true)
    public Page<PostDto> getPostsWhereTitleContainsWord(String word, Pageable pageable) {
        return postRepository
                .findByTitleContainingIgnoreCase(word, pageable)
                .map(PostMapper::postToPostDto);
    }

    @Transactional(readOnly = true)
    public Page<PostDto> getPostsByUserSubscriptions(Long userId, Pageable pageable) {
        return postRepository
                .findPostsByUserSubscriptions(userId, pageable)
                .map(PostMapper::postToPostDto);
    }

    @Transactional(readOnly = true)
    public Page<PostDto> getPostsByUserId(Long userId, Pageable pageable) {
        return postRepository
                .findByUserId(userId, pageable)
                .map(PostMapper::postToPostDto);
    }

    @Transactional(readOnly = true)
    public Page<PostDto> getPostsBySubredditId(Long subredditId, Pageable pageable) {
        return postRepository
                .findBySubredditId(subredditId, pageable)
                .map(PostMapper::postToPostDto);
    }

    public PostDto addPost(PostDto postDto) {
        RedditUser user = userService.getUserEntity(postDto.user().id());
        Subreddit subreddit = subredditService.getSubredditEntityById(postDto.subreddit().id());

        Post post = new Post();
        post.setDescription(postDto.description());
        post.setTitle(postDto.title());
        post.setContentType(ContentType.POST);
        post.setCreated(LocalDateTime.now());
        post.setUser(user);
        post.setSubreddit(subreddit);

        Post savedPost = postRepository.save(post);

        return PostMapper.postToPostDto(savedPost);
    }

    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = getPostEntity(id);

        if (!Objects.equals(post.getUser().getId(), postDto.user().id())) {
            throw new ContentUpdateNotAllowedException(ErrorMessages.CONTENT_UPDATE_NOT_ALLOWED);
        }

        post.setDescription(postDto.description());
        post.setTitle(postDto.title());

        return PostMapper.postToPostDto(post);
    }

    public void deletePost(Long postId, Long userId) {
        Post post = getPostEntity(postId);
        RedditUser user = userService.getUserEntity(userId);
        Subreddit subreddit = post.getSubreddit();

        if (!post.getUser().equals(user)) {
            if (!subreddit.getModerators().contains(user)) {
                throw new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES);
            }
        }

        postRepository.deleteById(postId);
    }
}
