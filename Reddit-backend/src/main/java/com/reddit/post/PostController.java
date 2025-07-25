package com.reddit.post;

import com.reddit.post.dto.PostDto;
import com.reddit.util.PaginationConstants;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        PostDto postDto = postService.getPost(id);
        return ResponseEntity
                .ok()
                .body(postDto);
    }

    @GetMapping
    public ResponseEntity<Page<PostDto>> getPosts(
            @PageableDefault(
                    size = PaginationConstants.POST_DEFAULT_SIZE,
                    sort = PaginationConstants.POST_DEFAULT_SORT,
                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostDto> posts = postService.getPosts(pageable);

        return ResponseEntity
                .ok()
                .body(posts);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PostDto>> getPostsWhereTitleContainsWord(
            @RequestParam String word,
            @PageableDefault(
                    size = PaginationConstants.POST_DEFAULT_SIZE,
                    sort = PaginationConstants.POST_DEFAULT_SORT,
                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostDto> posts = postService.getPostsWhereTitleContainsWord(word, pageable);

        return ResponseEntity
                .ok()
                .body(posts);
    }

    @GetMapping("/subscriptions/{userId}")
    public ResponseEntity<Page<PostDto>> getPostsByUserSubscriptions(
            @PathVariable Long userId,
            @PageableDefault(
                    size = PaginationConstants.POST_DEFAULT_SIZE,
                    sort = PaginationConstants.POST_DEFAULT_SORT,
                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostDto> posts = postService.getPostsByUserSubscriptions(userId, pageable);

        return ResponseEntity
                .ok()
                .body(posts);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<PostDto>> getPostsByUserId(
            @PathVariable Long userId,
            @PageableDefault(
                    size = PaginationConstants.POST_DEFAULT_SIZE,
                    sort = PaginationConstants.POST_DEFAULT_SORT,
                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostDto> posts = postService.getPostsByUserId(userId, pageable);

        return ResponseEntity
                .ok()
                .body(posts);
    }

    @GetMapping("/subreddits/{subredditId}")
    public ResponseEntity<Page<PostDto>> getPostsBySubredditId(
            @PathVariable Long subredditId,
            @PageableDefault(
                    size = PaginationConstants.POST_DEFAULT_SIZE,
                    sort = PaginationConstants.POST_DEFAULT_SORT,
                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostDto> posts = postService.getPostsBySubredditId(subredditId, pageable);

        return ResponseEntity
                .ok()
                .body(posts);
    }

    @PostMapping
    public ResponseEntity<PostDto> addPost(@RequestBody @Valid PostDto postDto) {
        PostDto resultPostDto = postService.addPost(postDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultPostDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody @Valid PostDto postDto) {
        PostDto updatedPostDto = postService.updatePost(id, postDto);
        return ResponseEntity
                .ok()
                .body(updatedPostDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, @RequestParam Long userId) {
        postService.deletePost(postId, userId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
